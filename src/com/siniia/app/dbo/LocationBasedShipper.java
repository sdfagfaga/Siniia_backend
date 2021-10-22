package com.siniia.app.dbo;

import java.sql.Timestamp;
import java.util.List;

public class LocationBasedShipper {

	private int id;	
	private String country;
	private String shippers;
	private String shipperLink;
	private Timestamp createdDate;
	private List<ShippingDetails> shippingDetails;
	
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
	public String getShippers() {
		return shippers;
	}
	public void setShippers(String shippers) {
		this.shippers = shippers;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getShipperLink() {
		return shipperLink;
	}
	public void setShipperLink(String shipperLink) {
		this.shipperLink = shipperLink;
	}
	public List<ShippingDetails> getShippingDetails() {
		return shippingDetails;
	}
	public void setShippingDetails(List<ShippingDetails> shippingDetails) {
		this.shippingDetails = shippingDetails;
	}
}