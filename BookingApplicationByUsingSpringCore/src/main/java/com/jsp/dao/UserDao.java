package com.jsp.dao;

public interface UserDao {
	
	boolean userLogIn(String emailid, String password);
	
	void busBooking();
	void bookingStatus(int tcId);
	
	void busAvailability();
	void ticketcancel(int tcId);

}
