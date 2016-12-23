package com.fiscolpa.demo.vo;

import com.fiscolpa.demo.model.PointsTransationDetail;

public class PointsTransationDetailVo extends PointsTransationDetail{
    
	/**
	 * 转出账户名称
	 */
    private String rollOutAccountName;

    /**
     * 转入账户名称
     */
    private String rollInAccountName;
    
    /**
     * 已承兑积分数量
     */
    private int acceptedPointsNum;
    
    /**
     * 商户头像
     */
    private String imgBase;
    
    

	public String getRollOutAccountName() {
		return rollOutAccountName;
	}

	public void setRollOutAccountName(String rollOutAccountName) {
		this.rollOutAccountName = rollOutAccountName;
	}

	public String getRollInAccountName() {
		return rollInAccountName;
	}

	public void setRollInAccountName(String rollInAccountName) {
		this.rollInAccountName = rollInAccountName;
	}

	public int getAcceptedPointsNum() {
		return acceptedPointsNum;
	}

	public void setAcceptedPointsNum(int acceptedPointsNum) {
		this.acceptedPointsNum = acceptedPointsNum;
	}

	public String getImgBase() {
		return imgBase;
	}

	public void setImgBase(String imgBase) {
		this.imgBase = imgBase;
	}
	
}