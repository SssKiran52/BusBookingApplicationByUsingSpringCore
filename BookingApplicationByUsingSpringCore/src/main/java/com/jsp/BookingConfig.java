package com.jsp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jsp.model.BusDetails;
import com.jsp.model.UserInfo;

@Configuration
//@Component
@ComponentScan(basePackages = "com.jsp")
@PropertySource("com/jsp/file/BookingApplication.properties")
public class BookingConfig {
	
//	@Autowired
//	UserDaoImpl userDaoImpl = new UserDaoImpl("jdbc:mysql://localhost:3306/bookingapplication?", "root", "12345", "com.mysql.cj.jdbc.Driver");
	
//	UserDaoImpl userDaoImpl = new UserDaoImpl();
	
	@Bean
	public Scanner scannerClassBean() {
		
		Scanner scanner = new Scanner(System.in);
		return scanner;
		
	}
	
	@Bean
	public List<BusDetails> listbean() {
		
		List<BusDetails> list = new ArrayList<BusDetails>();
		return list;
		
	}
	
	@Bean
	public UserInfo userinfobean() {
		
		UserInfo userInfo = new UserInfo();
		return userInfo;
		
	}
	
	@Bean
	public Random randomBean() {
		
		Random random = new Random();
		return random;
	}
	
}