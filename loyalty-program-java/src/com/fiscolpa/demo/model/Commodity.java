package com.fiscolpa.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

public class Commodity {
	
    @Id
    @Column(name = "commodity_id")
    private String commodityId;

    private String commodityName;

    private Integer price;
    
    private Integer stockAmount;
    
    private String accountId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
    
    @Transient
    private String imgId;
    @Transient
    private String imgBase;
    @Transient
    private Integer salesCount;
    
    
	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
		this.createUser = createUser;
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
		this.updateUser = updateUser;
	}

	public String getImgBase() {
		return imgBase;
	}

	public void setImgBase(String imgBase) {
		this.imgBase = imgBase;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public Integer getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(Integer salesCount) {
		this.salesCount = salesCount;
	}

	public Commodity() { }
	
	public Commodity(String commodityId) {
		this.commodityId = commodityId;
	}
	
	public Commodity(String commodityId,Integer stockAmount) {
		this.commodityId = commodityId;
		this.stockAmount = stockAmount;
	}
	
}