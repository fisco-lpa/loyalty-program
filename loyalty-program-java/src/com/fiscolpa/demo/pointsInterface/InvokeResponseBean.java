package com.fiscolpa.demo.pointsInterface;

public class InvokeResponseBean {
	private String type;
	private String chaincodeID;
	private String payload;
	private String uuid;
	private String txId;
	private Timestamp timestamp;
	private String confidentialityLevel;
	private String confidentialityProtocolVersion;
	private String nonce;
	private String toValidators;
	private String cert;
	private String signature;
	

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChaincodeID() {
		return chaincodeID;
	}

	public void setChaincodeID(String chaincodeID) {
		this.chaincodeID = chaincodeID;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(String confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	public String getConfidentialityProtocolVersion() {
		return confidentialityProtocolVersion;
	}

	public void setConfidentialityProtocolVersion(String confidentialityProtocolVersion) {
		this.confidentialityProtocolVersion = confidentialityProtocolVersion;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getToValidators() {
		return toValidators;
	}

	public void setToValidators(String toValidators) {
		this.toValidators = toValidators;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
