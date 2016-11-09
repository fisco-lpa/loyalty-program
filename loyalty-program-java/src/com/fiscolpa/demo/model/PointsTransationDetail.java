package com.fiscolpa.demo.model;

import java.util.Date;

public class PointsTransationDetail {
    private String detailId;

    private String sourceDetailId;

    private String transId;

    private Date expireTime;

    private String extRef;

    private String status;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String rollOutAccount;

    private String rollInAccount;

    private Integer transAmount;

    private Integer curBalance;

    private String creditParty;

    private String merchant;

    private Date transferTime;
    
    private Date creditCreateTime;

    public Date getCreditCreateTime() {
		return creditCreateTime;
	}

	public void setCreditCreateTime(Date creditCreateTime) {
		this.creditCreateTime = creditCreateTime;
	}

	public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId == null ? null : detailId.trim();
    }

    public String getSourceDetailId() {
        return sourceDetailId;
    }

    public void setSourceDetailId(String sourceDetailId) {
        this.sourceDetailId = sourceDetailId == null ? null : sourceDetailId.trim();
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getExtRef() {
        return extRef;
    }

    public void setExtRef(String extRef) {
        this.extRef = extRef == null ? null : extRef.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getRollOutAccount() {
        return rollOutAccount;
    }

    public void setRollOutAccount(String rollOutAccount) {
        this.rollOutAccount = rollOutAccount == null ? null : rollOutAccount.trim();
    }

    public String getRollInAccount() {
        return rollInAccount;
    }

    public void setRollInAccount(String rollInAccount) {
        this.rollInAccount = rollInAccount == null ? null : rollInAccount.trim();
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
        this.creditParty = creditParty == null ? null : creditParty.trim();
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant == null ? null : merchant.trim();
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }
}