package com.siniia.app.dbo;

import java.sql.Timestamp;

public class ProductImages {

	private int id;
	private int productId;
	private String imageURLSmall;
	private String imageURLMedium;
	private String imageURLLarge;	
	private Timestamp createdDate;
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
	public String getImageURLSmall() {
		return imageURLSmall;
	}
	public void setImageURLSmall(String imageURLSmall) {
		this.imageURLSmall = imageURLSmall;
	}
	public String getImageURLMedium() {
		return imageURLMedium;
	}
	public void setImageURLMedium(String imageURLMedium) {
		this.imageURLMedium = imageURLMedium;
	}
	public String getImageURLLarge() {
		return imageURLLarge;
	}
	public void setImageURLLarge(String imageURLLarge) {
		this.imageURLLarge = imageURLLarge;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
}
