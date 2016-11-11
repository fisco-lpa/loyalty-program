package com.fiscolpa.demo.pointsInterface;


public class Params {

	private int type = 1;
	private ChainCode chaincodeID;
	private CtorMsg ctorMsg;
	private String secureContext;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ChainCode getChaincodeID() {
		return chaincodeID;
	}

	public void setChaincodeID(ChainCode chaincodeID) {
		this.chaincodeID = chaincodeID;
	}

	public CtorMsg getCtorMsg() {
		return ctorMsg;
	}

	public void setCtorMsg(CtorMsg ctorMsg) {
		this.ctorMsg = ctorMsg;
	}

	public String getSecureContext() {
		return secureContext;
	}

	public void setSecureContext(String secureContext) {
		this.secureContext = secureContext;
	}

}
