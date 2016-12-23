package com.fiscolpa.demo.model;

public class PointsTransationDetailExtends extends PointsModel {
	private String detailId;

	private String sourceDetailId;

	private String transId;

	private String expireTime;

	private String extRef;

	private String status;


	private String rollOutAccount;

	private String rollInAccount;
	
	private String rollInAccountName;

	private Integer transAmount;

	private Integer curBalance;

	private String creditParty;

	private String merchant;

	private String transferTime;

	private String transferType;

	private String creditCreateTime;

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getSourceDetailId() {
		return sourceDetailId;
	}

	public void setSourceDetailId(String sourceDetailId) {
		this.sourceDetailId = sourceDetailId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getExtRef() {
		return extRef;
	}

	public void setExtRef(String extRef) {
		this.extRef = extRef;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRollOutAccount() {
		return rollOutAccount;
	}

	public void setRollOutAccount(String rollOutAccount) {
		this.rollOutAccount = rollOutAccount;
	}

	public String getRollInAccount() {
		return rollInAccount;
	}

	public void setRollInAccount(String rollInAccount) {
		this.rollInAccount = rollInAccount;
	}

	public Integer getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Integer transAmount) {
		this.transAmount = transAmount;
	}

	public Integer getCurBalance() {
		return curBalance;
	}

	public void setCurBalance(Integer curBalance) {
		this.curBalance = curBalance;
	}

	public String getCreditParty() {
		return creditParty;
	}

	public void setCreditParty(String creditParty) {
		this.creditParty = creditParty;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getCreditCreateTime() {
		return creditCreateTime;
	}

	public void setCreditCreateTime(String creditCreateTime) {
		this.creditCreateTime = creditCreateTime;
	}

	public String getRollInAccountName() {
		return rollInAccountName;
	}

	public void setRollInAccountName(String rollInAccountName) {
		this.rollInAccountName = rollInAccountName;
	}

}