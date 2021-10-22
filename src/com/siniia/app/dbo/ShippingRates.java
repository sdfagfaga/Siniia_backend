package com.siniia.app.dbo;

public class ShippingRates {

	private double amount;
	private String currency;
	private String provider;
	private String estimated_days;
	private String duration_terms;
	private String object_id;
	private String carrier_account;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getEstimated_days() {
		return estimated_days;
	}
	public void setEstimated_days(String estimated_days) {
		this.estimated_days = estimated_days;
	}
	public String getDuration_terms() {
		return duration_terms;
	}
	public void setDuration_terms(String duration_terms) {
		this.duration_terms = duration_terms;
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getCarrier_account() {
		return carrier_account;
	}
	public void setCarrier_account(String carrier_account) {
		this.carrier_account = carrier_account;
	}
	
}