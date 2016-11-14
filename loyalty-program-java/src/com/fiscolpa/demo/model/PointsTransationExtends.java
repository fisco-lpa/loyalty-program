package com.fiscolpa.demo.model;

public class PointsTransationExtends extends PointsModel{
	
	private String transId;

    private String rollOutAccount;

    private String rollInAccount;

    private Integer transAmount;

    private String describe;

    private String transferTime;
    
    private String transferType;

    private String createTime;

    private String createUser;

    private String updateTime;

    private String updateUser;
	
    //流水ID
    private String detailId;
    //发放额度
    private String usePoints;
    //过期时间
    private String expireTime;
    //授信方
    private String creditParty;
    //查询判断列
    private String queryColumn;
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getUsePoints() {
		return usePoints;
	}
	public void setUsePoints(String usePoints) {
		this.usePoints = usePoints;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getCreditParty() {
		return creditParty;
	}
	public void setCreditParty(String creditParty) {
		this.creditParty = creditParty;
	}
	public String getQueryColumn() {
		return queryColumn;
	}
	public void setQueryColumn(String queryColumn) {
		this.queryColumn = queryColumn;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
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
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
    
}