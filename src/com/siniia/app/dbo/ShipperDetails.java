package com.siniia.app.dbo;

import java.sql.Timestamp;

public class ShipperDetails {

	private int id;
	private int userId;
	private String country;
	private String shipperName;
	private String link;
	private String shipperContact;
	private String shipperLocation;
	private Timestamp createdDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getShipperContact() {
		return shipperContact;
	}
	public void setShipperContact(String shipperContact) {
		this.shipperContact = shipperContact;
	}
	public String getShipperLocation() {
		return shipperLocation;
	}
	public void setShipperLocation(String shipperLocation) {
		this.shipperLocation = shipperLocation;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}	
}