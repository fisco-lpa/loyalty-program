package com.fiscolpa.demo.pointsInterface;

import java.io.Serializable;

public class CtorMsg implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String function = "query";
	private String[] args;

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

}
