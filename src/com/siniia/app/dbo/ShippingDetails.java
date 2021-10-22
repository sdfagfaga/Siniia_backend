package com.siniia.app.dbo;

import java.sql.Timestamp;

public class ShippingDetails {

	private int id;
	private int shipperId;
	private String shipperName;
	private String ShipperDetails;
	private String shipperContact;
	private Timestamp createdDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getShipperId() {
		return shipperId;
	}
	public void setShipperId(int shipperId) {
		this.shipperId = shipperId;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public String getShipperDetails() {
		return ShipperDetails;
	}
	public void setShipperDetails(String shipperDetails) {
		ShipperDetails = shipperDetails;
	}
	public String getShipperContact() {
		return shipperContact;
	}
	public void setShipperContact(String shipperContact) {
		this.shipperContact = shipperContact;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
}
