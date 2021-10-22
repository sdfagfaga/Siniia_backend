package com.siniia.app.dbo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BannersDetails {

	private int id;
	private String URL;
	private String UserType;
	private Timestamp StartTime;
	private Timestamp endTime;
	private String productId;
	
	private List<MultipartFile> uploadedImage;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getUserType() {
		return UserType;
	}
	public void setUserType(String userType) {
		UserType = userType;
	}
	public Timestamp getStartTime() {
		return StartTime;
	}
	public void setStartTime(Timestamp startTime) {
		StartTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<MultipartFile> getUploadedImage() {
		return uploadedImage;
	}
	public void setUploadedImage(List<MultipartFile> uploadedImage) {
		this.uploadedImage = uploadedImage;
	}
	
	@Override
	public String toString() {
		return "BannersDetails [id=" + id + ", URL=" + URL + ", UserType=" + UserType + ", StartTime=" + StartTime
				+ ", endTime=" + endTime + ", productId=" + productId + ", uploadedImage=" + uploadedImage + "]";
	}
		
}
