package com.demo.entities;

import java.util.Date;

public class Key {
	private int id;
	private int userID;
	private String publicKey;
	private Date startTime;
	private Date endTime;
	@Override
	public String toString() {
		return "Key [id=" + id + ", userID=" + userID + ", publicKey=" + publicKey + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Key(int userID, String publicKey, Date startTime, Date endTime) {
		super();
		this.userID = userID;
		this.publicKey = publicKey;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public Key() {
		super();
	}
	
}
