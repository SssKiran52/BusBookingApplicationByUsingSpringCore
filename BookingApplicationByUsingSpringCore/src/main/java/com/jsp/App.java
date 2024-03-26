package com.jsp;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jsp.dao.UserDao;
import com.jsp.dao.UserDaoImpl;

public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BookingConfig.class);
//        UserDao userDao = context.getBean("userDaoImpl",UserDao.class);
//        System.out.println(userDao);
        UserDao userDao = context.getBean("userDaoImpl",UserDao.class);
//        System.out.println(userDao);
//        userDao.userLogIn("siva@gmail.com", "12345");
        
        Scanner scanner = context.getBean("scannerClassBean",Scanner.class);
        System.out.println("Enter your Email Id : ");
        String emailid = scanner.next();
        System.out.println("Enter your Password : ");
        String password = scanner.next();
//        userDao.userLogIn(emailid, password);
        if (userDao.userLogIn(emailid, password)) {
				
        	System.out.println("Enter \n 1. For Bus Booking" + "\n 2. For Ticket Cancelling" + "\n 3. For Booking Status" + "\n 4. For Bus Availability");
        	int userOptions = scanner.nextInt();
        	
        	switch (userOptions) {
			case 1: userDao.busBooking();
				
				break;
				
			case 2 : userDao.ticketcancel(userOptions);
			
				break;
				
			case 3 : userDao.bookingStatus(userOptions);
				
				break;
				
			case 4 : userDao.busAvailability();
			
				break;

			default:
				System.out.println("Invalid Option..!");
				break;
			}
        	
		} else {
			
			System.out.println("Invalid Details");
			
		}
        
//        String[] BeanDefinitionNames = context.getBeanDefinitionNames();
//        
//        for (String string : BeanDefinitionNames) {
//			System.out.println(string);
//		}
        
        context.close();
    }
}
