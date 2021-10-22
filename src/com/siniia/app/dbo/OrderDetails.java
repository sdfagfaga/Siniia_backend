package com.siniia.app.dbo;

import java.sql.Timestamp;

import javax.persistence.Transient;

import org.json.JSONObject;

public class OrderDetails {

	private int id;
	private int  productId;
	private int productStatus;
	private long userId;
	private int availableAddressId;
	private int deliveryAddressID;
	private int shipmentId;
	private int quantity;
	private int quantityTypeId;
	private double quantityPrice;
	private String deliveryStatus;
	private Timestamp createdDate;
	private String paymentDetails;
	private double paymentAmount;
	private String paymentType;
	
	private UserAddress address;
	
	private String addressLat;	
	private String addressLong;
	
	private String pinCode;
	private String address1;	
	private String address2;
	private String landmark;
	
	private String shipmentObjectId;
	private String shipmentTrackingId;
	private String provider;
	
	private String shipmentStatus;
	
	private String isPayoutDone;
	
	public String getIsPayoutDone() {
		return isPayoutDone;
	}
	public void setIsPayoutDone(String isPayoutDone) {
		this.isPayoutDone = isPayoutDone;
	}
	@Transient
	private String productName;
	
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getShipmentStatus() {
		return shipmentStatus;
	}
	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getShipmentTrackingId() {
		return shipmentTrackingId;
	}
	public void setShipmentTrackingId(String shipmentTrackingId) {
		this.shipmentTrackingId = shipmentTrackingId;
	}
	public String getShipmentObjectId() {
		return shipmentObjectId;
	}
	public void setShipmentObjectId(String shipmentObjectId) {
		this.shipmentObjectId = shipmentObjectId;
	}
	public UserAddress getAddress() {
		return address;
	}
	public void setAddress(UserAddress address) {
		this.address = address;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getAvailableAddressId() {
		return availableAddressId;
	}
	public void setAvailableAddressId(int availableAddressId) {
		this.availableAddressId = availableAddressId;
	}
	public int getDeliveryAddressID() {
		return deliveryAddressID;
	}
	public void setDeliveryAddressID(int deliveryAddressID) {
		this.deliveryAddressID = deliveryAddressID;
	}
	public int getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(int shipmentId) {
		this.shipmentId = shipmentId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getQuantityTypeId() {
		return quantityTypeId;
	}
	public void setQuantityTypeId(int quantityTypeId) {
		this.quantityTypeId = quantityTypeId;
	}
	public double getQuantityPrice() {
		return quantityPrice;
	}
	public void setQuantityPrice(double quantityPrice) {
		this.quantityPrice = quantityPrice;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
}