package com.siniia.app.dbo;

import java.sql.Timestamp;

public class UserProfile {

	private int id;
	private String email;
	private String name;
	private String mobileCountry;
	private String mobileNumber;
	private String profilePicUrl;
	private Timestamp created_date;
	private String LastLocationLat;
	private String LastLocationLong;
	private String notificationsCount;
	private String basketOrdersCount;
	private String userType;
	private int isProfileComplete;
	private int otp;
	private int isOTPVerified;
	private String gcmId;
	private String deviceType;
	
	private String nickName;	
	private String pinCode;
	private String address1;	
	private String address2;
	private String landmark;	
	private String city;	
	private String state;	
	private String country;	
	private String addressLat;	
	private String addressLong;
	
	private String password;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileCountry() {
		return mobileCountry;
	}
	public void setMobileCountry(String mobileCountry) {
		this.mobileCountry = mobileCountry;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getProfilePicUrl() {
		return profilePicUrl;
	}
	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}
	public Timestamp getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
	public String getLastLocationLat() {
		return LastLocationLat;
	}
	public void setLastLocationLat(String lastLocationLat) {
		LastLocationLat = lastLocationLat;
	}
	public String getLastLocationLong() {
		return LastLocationLong;
	}
	public void setLastLocationLong(String lastLocationLong) {
		LastLocationLong = lastLocationLong;
	}
	public String getNotificationsCount() {
		return notificationsCount;
	}
	public void setNotificationsCount(String notificationsCount) {
		this.notificationsCount = notificationsCount;
	}
	public String getBasketOrdersCount() {
		return basketOrdersCount;
	}
	public void setBasketOrdersCount(String basketOrdersCount) {
		this.basketOrdersCount = basketOrdersCount;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public int getIsProfileComplete() {
		return isProfileComplete;
	}
	public void setIsProfileComplete(int isProfileComplete) {
		this.isProfileComplete = isProfileComplete;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	public int getIsOTPVerified() {
		return isOTPVerified;
	}
	public void setIsOTPVerified(int isOTPVerified) {
		this.isOTPVerified = isOTPVerified;
	}
	public String getGcmId() {
		return gcmId;
	}
	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddressLat() {
		return addressLat;
	}
	public void setAddressLat(String addressLat) {
		this.addressLat = addressLat;
	}
	public String getAddressLong() {
		return addressLong;
	}
	public void setAddressLong(String addressLong) {
		this.addressLong = addressLong;
	}	
}