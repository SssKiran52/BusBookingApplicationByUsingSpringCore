package com.jsp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jsp.model.BusDetails;
import com.jsp.model.UserInfo;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.PreparableStatement;
import com.mysql.cj.xdevapi.Result;

@Component
public class UserDaoImpl implements UserDao{
	
	@Value("${spring.jdbc.url}")
	private String url;
	@Value("${spring.jdbc.username}")
	private String username;
	@Value("${spring.jdbc.password}")
	private String password;
	@Value("${spring.jdbc.driver-class-name}")
	private String driverclassname;
	
	
	int count = 1;
	Connection connection;
	@Autowired
	Scanner scanner;
	@Autowired
	private List<BusDetails> list;
	@Autowired
	UserInfo userInfo;
	@Autowired
	Random random;
	
	public UserDaoImpl() {
		super();
	}



	public UserDaoImpl(String url, String username, String password, String driverclassname) {
	super();
	this.url = url;
	this.username = username;
	this.password = password;
	this.driverclassname = driverclassname;
}
	
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverclassname() {
		return driverclassname;
	}

	public void setDriverclassname(String driverclassname) {
		this.driverclassname = driverclassname;
	}

	
	@Override
	public String toString() {
		return "UserDaoImpl [url=" + url + ", username=" + username + ", password=" + password + ", driverclassname="
				+ driverclassname + "]";
	}
	
	@PostConstruct
	public void establishmentOfConnection() throws SQLException{
		
		connection = DriverManager.getConnection(url,username,password);
		
		System.out.println("Establishment Of Connection");
	}
	
	@PreDestroy
	public void closingTheConnection() throws SQLException {
		
		connection.close();
		
		System.out.println("Closing The Connection");
	}

	public boolean userLogIn(String emailid, String password) {
		
		try {
			PreparedStatement ps = connection.prepareStatement("select * from userinfo where User_Emailid=? and User_Password=?");
			ps.setString(1, emailid);
			ps.setString(2, password);
			ResultSet resultset =  ps.executeQuery();
			if (resultset.next()) {
				
//				System.out.println("Login Successfull...");
				
				userInfo.setUserId(resultset.getInt(1));
				userInfo.setUserName(resultset.getString(2));
				userInfo.setUserEmailid(resultset.getString(3));
				userInfo.setUserMobileNumber(resultset.getString(5));
				userInfo.setUserAddress(resultset.getString(6));
				return true;
				
			} else {
				
//				System.out.println("Invalid Details..!");
				return false;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

	public void busBooking() {
		
		System.out.println("Enter From :");
		String from = scanner.next();
		System.out.println("Enter To :");
		String to = scanner.next();
		System.out.println("Enter Date Format : YYYY-MM-DD");
		System.out.println("Enter Date :");
		String date = scanner.next();
		LocalDate dateOfBooking = LocalDate.parse(date);
		if (dateOfBooking.isAfter(LocalDate.now()) || dateOfBooking.isEqual(LocalDate.now())) {	
//			System.out.println("🚌...Comming Soon");
			
			try {
				PreparedStatement ps = connection.prepareStatement("select * from busdetails where `from`=? and `to`=?");
				ps.setString(1, from);
				ps.setString(2, to);
				ResultSet resultSet = ps.executeQuery();
				
				System.out.println("S.No\tSerNumber\tFrom\t	To\t   	Time\t        Price");
				System.out.println("********************************************************************************");
				if (resultSet.isBeforeFirst()) {
					
					while (resultSet.next()) {
						
//						System.out.println("Serial Number : "+resultSet.getInt(1));
//						System.out.println("From : "+resultSet.getString(2));
//						System.out.println("To : "+resultSet.getString(3));
//						System.out.println("Time : "+resultSet.getTime(4));
//						System.out.println("Price : "+resultSet.getDouble(5));
						
						BusDetails busDetails = new BusDetails();
						busDetails.setBusnumber(resultSet.getInt(1));
						busDetails.setFrom(resultSet.getString(2));
						busDetails.setTo(resultSet.getString(3));
						busDetails.setTime(resultSet.getTime(4).toLocalTime());
						busDetails.setPrice(resultSet.getDouble(5));
						list.add(busDetails);
//						
						
						System.out.println(count++ +"\t"+resultSet.getInt(1)+"      "
						+"\t"+resultSet.getString(2)
						+"\t"+resultSet.getString(3)
						+"\t"+resultSet.getTime(4)
						+"\t"+resultSet.getDouble(5)); 
						
					}
					
				} else {
					
					System.out.println("No Data Found...");
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Enter S.No To Book Your Ticket : ");
			int booking = scanner.nextInt();
			BusDetails details = list.get(booking-1);
			System.out.println(details);
			
			System.out.println("Total Cost : "+details.getPrice());
			
//			System.out.println("Enter \n 1. For Card Payment" + "\n 2. For UPI Payment" + "\n 3. Through Bank Payment");
			
			System.out.println("Enter Amount to Book the Ticket : ");
			double useramount = scanner.nextDouble();
			
			if (useramount == details.getPrice()) {
				
				int bookingId = random.nextInt(10000);
				
				if (bookingId < 1000) {
					
					bookingId += 1000;
					
				}
				
				String insert = "insert into userbookinginfo values(?,?,?,?,?,?)";
				
				try {
					
					PreparedStatement ps1 = connection.prepareStatement(insert);
					ps1.setInt(1, bookingId);
					ps1.setString(2, details.getFrom());
					ps1.setString(3, details.getTo());
					ps1.setDate(4, Date.valueOf(dateOfBooking));
					ps1.setDouble(5, details.getPrice());
					ps1.setInt(6, userInfo.getUserId());
					
					int result = ps1.executeUpdate();
					
					if (result != 0) {
						
						System.out.println("BookingId\tUserName\tUserMobileNumber\tBookingDate\tUserEmailId");
						System.out.println("*************************************************************************************");
						System.out.println(bookingId+"       "+"\t"+userInfo.getUserName()+"     "+"\t"+userInfo.getUserMobileNumber()+"        "+"\t"+dateOfBooking+"\t"+userInfo.getUserEmailid());
						
						System.out.println("Booking Successfull...");
						
					} else {
						
						System.out.println("Invalid Booking Details..!");
						
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
						
				
			} else {
				
				System.out.println("Invalid Amount..!");
				
			}
			
			
		} else {
			
			System.out.println("Invalid Date");
			
		}
		
	}

	public void bookingStatus(int tcId) {
		
		System.out.println("Enter Your Booking Id : ");
		int bookingId = scanner.nextInt();
		
		String select = "select * from userbookinginfo where Booking_Id=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setInt(1, bookingId);
			ResultSet resultset = ps.executeQuery();
			
			if (resultset.next()) {
				
				System.out.println("Your Booking Status...");
				System.out.println(" ");
				System.out.println("BookingId\tBookingFrom\tBookingTo\tBookingDate\tBookingPrice");
				System.out.println("*****************************************************************************");
				System.out.println(resultset.getInt(1)+"        "+"\t"+resultset.getString(2)+"     "+"\t"+resultset.getString(3)+"\t"+resultset.getDate(4)+"     "+"\t"+resultset.getDouble(5));
				
			} else {
				
				System.out.println("Invalid Booking Id..!");
				System.out.println("Please Enter a Valid Booking Id...");
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void busAvailability() {
		
		System.out.println("Enter From : ");
		String fromAddress = scanner.next();
		System.out.println("Enter To : ");
		String toAddress = scanner.next();
		
		String select = "select * from busdetails where `from`=? and `to`=?";
		
		try {
			
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1, fromAddress);
			ps.setString(2, toAddress);
			ResultSet resultset =  ps.executeQuery();
			
			if (resultset.isBeforeFirst()) {
				
				while (resultset.next()) {
				
					System.out.println("Available Buses Are : ");
					System.out.println(" ");
					System.out.println("BusNumber\tFrom\t	To\t	Time\t	Price");
					System.out.println("***********************************************************************");
					System.out.println(resultset.getInt(1)+"         "+"\t"+resultset.getString(2)+"\t"+resultset.getString(3)+"\t"+resultset.getTime(4)+"\t"+resultset.getDouble(5));
				
//					BusDetails busDetails = new BusDetails();
//					busDetails.setBusnumber(resultSet1.getInt(1));
//					busDetails.setFrom(resultSet.getString(2));
//					busDetails.setTo(resultSet.getString(3));
//					busDetails.setTime(resultSet.getTime(4).toLocalTime());
//					busDetails.setPrice(resultSet.getDouble(5));
//					list.add(busDetails);
////					
//					
//					System.out.println(count++ +"\t"+resultSet.getInt(1)+"      "
//					+"\t"+resultSet.getString(2)
//					+"\t"+resultSet.getString(3)
//					+"\t"+resultSet.getTime(4)
//					+"\t"+resultSet.getDouble(5)); 
					
				}
				
			} else {
				
				System.out.println("No Buses are Available..!  From"+" "+fromAddress+" to "+toAddress);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void ticketcancel(int tcId) {
		
		System.out.println("Enter Your Booking Id : ");
		int bookingId = scanner.nextInt();
		
		String select = "select * from userbookinginfo where Booking_Id=?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setInt(1, bookingId);
			ResultSet resultset = ps.executeQuery();
			
			if (resultset.next()) {
				
				System.out.println("Your Ticket Details...");
				System.out.println(" ");
				System.out.println("BookingId\tBookingFrom\tBookingTo\tBookingDate\tBookingPrice");
				System.out.println("*****************************************************************************");
				System.out.println(resultset.getInt(1)+"        "+"\t"+resultset.getString(2)+"     "+"\t"+resultset.getString(3)+"\t"+resultset.getDate(4)+"     "+"\t"+resultset.getDouble(5));
				System.out.println(" ");
				
				
				String delete = "delete from userbookinginfo where Booking_Id=?";
				PreparedStatement ps1 = connection.prepareStatement(delete);
				ps1.setInt(1, bookingId);
				int result =  ps1.executeUpdate();
				
				if (result != 0) {
					
					System.out.println("Your Ticket Cancelled...");
					System.out.println(" ");
					System.out.println("Your Amount Refunded To Your Account...");
					System.out.println(" ");
					
				} else {
					
					System.out.println("Invalid Ticket Details..!");
					
				}
				
			} else {
				
				System.out.println("Invalid Booking Id..!");
				System.out.println("Please Enter a Valid Booking Id...");
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
