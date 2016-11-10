package com.fiscolpa.demo.util;

public enum PointsTransactionEnum {
	/**
	 * 授信  ("1","SX_","发放UUID开头")
	 */
	CREDIT("1","SX_","发放UUID开头"),
	/**
	 * 发放  ("2","FF_","发放UUID开头")
	 */
	GRANT("2","FF_","发放UUID开头"),
	/**
	 * 购买 ("3","GM_","发放UUID开头")
	 */
	BUY("3","GM_","发放UUID开头"),
	/**
	 * 承兑 ("4","GM_","发放UUID开头")
	 */
	ACCEPT("4","GM_","发放UUID开头");

	private String sign;//标示
	private String beginning;//开头
	@SuppressWarnings("unused")
	private String describe;//描述

	private PointsTransactionEnum(String sign,String beginning,String describe) {
		this.sign = sign;
		this.beginning = beginning;
		this.describe = describe;
	}
	
	/**
	 * 获取标示
	 * @return
	 */
	public String getSign() {
		return sign;
	}
	
	/**
	 * 获取UUID开头
	 * @return
	 */
	public String getBeginning() {
		return beginning;
	}
}
