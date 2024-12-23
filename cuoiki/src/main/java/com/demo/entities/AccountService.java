package com.demo.entities;

import java.util.Date;

public class AccountService {
	private int id;
	private int accountID;
	private int serviceID;
	private int durationID;
	private String description;
	private Date created;
	private Date endService;
	private boolean status;
	private int saleID;
	private String key;
	public AccountService() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public int getServiceID() {
		return serviceID;
	}
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
	public int getDurationID() {
		return durationID;
	}
	public void setDurationID(int durationID) {
		this.durationID = durationID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getEndService() {
		return endService;
	}
	public void setEndService(Date endService) {
		this.endService = endService;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getSaleID() {
		return saleID;
	}
	public void setSaleID(int saleID) {
		this.saleID = saleID;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public AccountService(int id, int accountID, int serviceID, int durationID, String description, Date created,
			Date endService, boolean status, int saleID, String key) {
		super();
		this.id = id;
		this.accountID = accountID;
		this.serviceID = serviceID;
		this.durationID = durationID;
		this.description = description;
		this.created = created;
		this.endService = endService;
		this.status = status;
		this.saleID = saleID;
		this.key = key;
	}
	@Override
	public String toString() {
		return "AccountService [id=" + id + ", accountID=" + accountID + ", serviceID=" + serviceID + ", durationID="
				+ durationID + ", description=" + description + ", created=" + created + ", endService=" + endService
				+ ", status=" + status + ", saleID=" + saleID + ", key=" + key + "]";
	}

	
	
}
