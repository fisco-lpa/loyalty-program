package com.fiscolpa.demo.vo;

import java.io.Serializable;

public class ItemVo implements Serializable {

	private static final long serialVersionUID = -7720685114048366256L;
	
	private String name;
	private String value;
	
	public ItemVo() {
		
	}
	
	public ItemVo(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
