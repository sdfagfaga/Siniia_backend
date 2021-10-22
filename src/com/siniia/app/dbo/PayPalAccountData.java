package com.siniia.app.dbo;

import java.sql.Timestamp;

public class PayPalAccountData {

	private int id;
	private int userId;
	private String payPalAccountId;
	private Timestamp createdDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPayPalAccountId() {
		return payPalAccountId;
	}
	public void setPayPalAccountId(String payPalAccountId) {
		this.payPalAccountId = payPalAccountId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
}
