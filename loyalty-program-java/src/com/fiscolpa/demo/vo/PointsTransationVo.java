package com.fiscolpa.demo.vo;

import com.fiscolpa.demo.model.PointsTransation;

public class PointsTransationVo extends PointsTransation{
    
    /**
     * 转出账户名称
     */
    private String rollOutAccountName;
    
    /**
     * 转入账户名称
     */
    private String rollInAccountName;
    /**
     * 起始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}