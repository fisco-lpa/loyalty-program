package com.fiscolpa.demo.pointsInterface;


public class RequestBean {
	

	private String jsonrpc = "2.0";
	private String method = "query";
	private Params params;
	private int id = 5;

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Params getParams() {
		return params;
	}

	public void setParams(Params params) {
		this.params = params;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
