package com.siniia.app.dbo;

import java.sql.Timestamp;

public class SearchedData {

	private int id;	
	private String userId;
	private String searchedString;
	private Timestamp createdDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSearchedString() {
		return searchedString;
	}
	public void setSearchedString(String searchedString) {
		this.searchedString = searchedString;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
}
