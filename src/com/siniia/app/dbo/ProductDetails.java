package com.siniia.app.dbo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProductDetails {

	private int id;
	private String categoryName;
	private String productName;
	private String productSubName;
	private String productType;
	private String productGrade;
	private String quantityType;
	private int quantityPerUnit;
	private double pricePerUnit;
	private int minQuantity;
	private int quantityAvailable;
	private String highlight;
	private String productDescription;
	private int availableAddressId;
	private int productOwenerID;
	private String productOwnerName;
	private Timestamp createdDate;
	private String thumbImageURL;
	private String radius;
	private String productOwnerContact;
	private List<MultipartFile> uploadedImage;
	private int productStatus;
	private int productId;
	private UserAddress address;
	
	private List<MultipartFile> advertisementVideo;
	
	private double height;
	private double width;
	private double weight;
	private double length;
	
	private String addressLat;	
	private String addressLong;
	
	private String pinCode;
	private String address1;	
	private String address2;
	private String landmark;
	private String city;	
	private String state;	
	private String country;
	
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
	public UserAddress getAddress() {
		return address;
	}
	public void setAddress(UserAddress address) {
		this.address = address;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSubName() {
		return productSubName;
	}
	public void setProductSubName(String productSubName) {
		this.productSubName = productSubName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductGrade() {
		return productGrade;
	}
	public void setProductGrade(String productGrade) {
		this.productGrade = productGrade;
	}
	public String getQuantityType() {
		return quantityType;
	}
	public void setQuantityType(String quantityType) {
		this.quantityType = quantityType;
	}
	public int getQuantityPerUnit() {
		return quantityPerUnit;
	}
	public void setQuantityPerUnit(int quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}
	public double getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public int getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
	}
	public int getQuantityAvailable() {
		return quantityAvailable;
	}
	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	public String getHighlight() {
		return highlight;
	}
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public int getAvailableAddressId() {
		return availableAddressId;
	}
	public void setAvailableAddressId(int availableAddressId) {
		this.availableAddressId = availableAddressId;
	}
	public int getProductOwenerID() {
		return productOwenerID;
	}
	public void setProductOwenerID(int productOwenerID) {
		this.productOwenerID = productOwenerID;
	}
	public String getProductOwnerName() {
		return productOwnerName;
	}
	public void setProductOwnerName(String productOwnerName) {
		this.productOwnerName = productOwnerName;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getThumbImageURL() {
		return thumbImageURL;
	}
	public void setThumbImageURL(String thumbImageURL) {
		this.thumbImageURL = thumbImageURL;
	}
	public List<MultipartFile> getUploadedImage() {
		return uploadedImage;
	}
	public void setUploadedImage(List<MultipartFile> uploadedImage) {
		this.uploadedImage = uploadedImage;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	public String getProductOwnerContact() {
		return productOwnerContact;
	}
	public void setProductOwnerContact(String productOwnerContact) {
		this.productOwnerContact = productOwnerContact;
	}
	public int getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
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
	
	public List<MultipartFile> getAdvertisementVideo() {
		return advertisementVideo;
	}
	public void setAdvertisementVideo(List<MultipartFile> advertisementVideo) {
		this.advertisementVideo = advertisementVideo;
	}
	@Override
	public String toString() {
		return "ProductDetails [id=" + id + ", categoryName=" + categoryName + ", productName=" + productName
				+ ", productSubName=" + productSubName + ", productType=" + productType + ", productGrade="
				+ productGrade + ", quantityType=" + quantityType + ", quantityPerUnit=" + quantityPerUnit
				+ ", pricePerUnit=" + pricePerUnit + ", minQuantity=" + minQuantity + ", quantityAvailable="
				+ quantityAvailable + ", highlight=" + highlight + ", productDescription=" + productDescription
				+ ", availableAddressId=" + availableAddressId + ", productOwenerID=" + productOwenerID
				+ ", productOwnerName=" + productOwnerName + ", createdDate=" + createdDate + ", thumbImageURL="
				+ thumbImageURL + ", radius=" + radius + ", productOwnerContact=" + productOwnerContact
				+ ", uploadedImage=" + uploadedImage + ", productStatus=" + productStatus + ", productId=" + productId
				+ ", address=" + address + ", height=" + height + ", width=" + width + ", weight=" + weight
				+ ", length=" + length + ", addressLat=" + addressLat + ", addressLong=" + addressLong + ", pinCode="
				+ pinCode + ", address1=" + address1 + ", address2=" + address2 + ", landmark=" + landmark + ", city="
				+ city + ", state=" + state + ", country=" + country + "]";
	}	
}