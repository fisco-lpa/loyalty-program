package com.fiscolpa.demo.model;

import java.util.Date;

public class PointsTransation {
    private String transId;

    private String rollOutAccount;

    private String rollInAccount;

    private Integer transAmount;

    private String describe;

    private Date transferTime;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
    
    private String transferType;
    
    private String imgBase;

    public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
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

	public String getImgBase() {
		return imgBase;
	}

	public void setImgBase(String imgBase) {
		this.imgBase = imgBase;
	}
    
    
}