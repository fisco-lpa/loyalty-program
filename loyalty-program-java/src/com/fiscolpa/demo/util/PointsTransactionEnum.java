package com.fiscolpa.demo.util;

public enum PointsTransactionEnum {
	/**
	 * 授信  ("1","SX_","授信UUID开头")
	 */
	CREDIT("1","SX_","UUID开头"),
	/**
	 * 发放  ("2","FF_","发放UUID开头")
	 */
	GRANT("2","FF_","发放UUID开头"),
	/**
	 * 购买 ("3","GM_","购买UUID开头")
	 */
	BUY("3","GM_","购买UUID开头"),
	/**
	 * 承兑 ("4","GM_","承兑UUID开头")
	 */
	ACCEPT("4","CD_","承兑UUID开头"),
	/**
	 * 新增 ("0","0","新增")区块连标示
	 */
	INSERT("0","0","新增"),
	/**
	 * 修改 ("1","1","修改")区块连标示
	 */
	UPDATE("1","1","修改"),
	/**
	 * 删除("2","2","删除")区块连标示
	 */
	DELETE("2","2","删除"),
	
	
	/**
	 * 商品("0","SP_","商品")
	 */
	GOODS("0","SP_","商品UUID开头"),
	
	
	
	/**
	 * 商品图片("0","SP_","商品")
	 */
	IMG_COMMODITY("0","SPIMG_","商品图片UUID开头"),
	/**
	 * 商品详情图片("1","SPXQIMG_","商品详情图片UUID开头")
	 */
	IMG_COMMODITY_DETAILS("1","SPXQIMG_","商品详情图片UUID开头"),
	/**
	 * 用户("2","YHIMG_","用户图片UUID开头")
	 */
	IMG_USER("2","YHIMG_","用户图片UUID开头")
	;

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
