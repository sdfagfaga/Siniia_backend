package com.siniia.app.dbo;

public class UserAddress {

	private int addressId;
	private int userId;
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
	
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	@Override
	public String toString() {
		return "UserAddress [addressId=" + addressId + ", userId=" + userId + ", nickName=" + nickName + ", pinCode="
				+ pinCode + ", address1=" + address1 + ", address2=" + address2 + ", landmark=" + landmark + ", city="
				+ city + ", state=" + state + ", country=" + country + ", addressLat=" + addressLat + ", addressLong="
				+ addressLong + "]";
	}
	
	
}