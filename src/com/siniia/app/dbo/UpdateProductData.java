package com.siniia.app.dbo;

public class UpdateProductData {

	private int userId;
	private int productId;
	private double pricePerUnit;
	private int minQuantity;
	private int quantityAvailable;
	private String radius;
	private int availableAddressId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
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
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	public int getAvailableAddressId() {
		return availableAddressId;
	}
	public void setAvailableAddressId(int availableAddressId) {
		this.availableAddressId = availableAddressId;
	}
}
