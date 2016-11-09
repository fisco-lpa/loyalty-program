package com.fiscolpa.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fiscolpa.demo.vo.ItemVo;

public class MenusUtil {

	private static Map<String, List<ItemVo>> menus;
	
	static {
		menus = new HashMap<String, List<ItemVo>>();
		
		//授信方菜单
		List<ItemVo> bankMenus = new ArrayList<ItemVo>();
		bankMenus.add(new ItemVo("授信", "/creditParty/goCreditPage"));
		bankMenus.add(new ItemVo("授信查询", "/creditParty/goCreditQueryPage"));
		bankMenus.add(new ItemVo("承兑查询", "/creditParty/goAcceptQueryPage"));
		menus.put("1", bankMenus);
		
		//商户菜单
		List<ItemVo> storeMenus = new ArrayList<ItemVo>();
		storeMenus.add(new ItemVo("授信查询", "merchant/merchantTransaction"));
		storeMenus.add(new ItemVo("积分发放", "merchant/reqSevePoints"));
		storeMenus.add(new ItemVo("积分发放查询", "merchant/querySentOutQuery"));
		storeMenus.add(new ItemVo("积分承兑查询", "merchant/queryCredit"));
		menus.put("2", storeMenus);
		
		//用户菜单
		List<ItemVo> userMenus = new ArrayList<ItemVo>();
		userMenus.add(new ItemVo("进账积分查询", "userIn"));
		userMenus.add(new ItemVo("积分消费查询", "userOut"));
		userMenus.add(new ItemVo("商品页", "goods"));
		menus.put("3", userMenus);
	}
	
	public static List<ItemVo> getMenus(String role) {
		return menus.get(role);
	}
	
 }
