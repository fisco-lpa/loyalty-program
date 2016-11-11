package com.fiscolpa.demo.pointsInterface;

import com.fiscolpa.demo.model.Account;

public class QueryMemBean {
	
	private String status; //OK-成功，FAIL-失败
	private String errMsg; //失败原因
	private Account account;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	

}
