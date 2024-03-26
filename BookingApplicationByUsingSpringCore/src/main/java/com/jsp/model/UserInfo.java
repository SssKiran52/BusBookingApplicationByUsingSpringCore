package com.jsp.model;

public class UserInfo {

	private int UserId;
	private String UserName;
	private String UserEmailid;
	private String UserPassword;
	private String UserAddress;
	private String UserMobileNumber;
	
	public UserInfo() {
		super();
	}

	public UserInfo(int userId, String userName, String userEmailid, String userPassword, String userAddress,
			String userMobileNumber) {
		super();
		UserId = userId;
		UserName = userName;
		UserEmailid = userEmailid;
		UserPassword = userPassword;
		UserAddress = userAddress;
		UserMobileNumber = userMobileNumber;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserEmailid() {
		return UserEmailid;
	}

	public void setUserEmailid(String userEmailid) {
		UserEmailid = userEmailid;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public String getUserAddress() {
		return UserAddress;
	}

	public void setUserAddress(String userAddress) {
		UserAddress = userAddress;
	}

	public String getUserMobileNumber() {
		return UserMobileNumber;
	}

	public void setUserMobileNumber(String userMobileNumber) {
		UserMobileNumber = userMobileNumber;
	}

	@Override
	public String toString() {
		return "UserInfo [UserId=" + UserId + ", UserName=" + UserName + ", UserEmailid=" + UserEmailid
				+ ", UserPassword=" + UserPassword + ", UserAddress=" + UserAddress + ", UserMobileNumber="
				+ UserMobileNumber + "]";
	}
	
	
	
}
