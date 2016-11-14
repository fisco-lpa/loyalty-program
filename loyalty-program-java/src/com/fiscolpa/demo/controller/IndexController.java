package com.fiscolpa.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.AccountService;
import com.fiscolpa.demo.service.PointsUserService;
import com.fiscolpa.demo.util.BeanToMap;
import com.fiscolpa.demo.util.HttpTool;

import net.sf.json.JSONObject;

@Controller
public class IndexController {
	@Autowired
	AccountService accountService;
	@Autowired
	PointsUserService pointsUserService;

    @RequestMapping(value={"", "/"})
    public String toIndex() {
    	return "index";
    }

    @RequestMapping(value="/initialize")
    public ModelAndView initialize() {
    	ModelAndView mv = new ModelAndView("initInfo");
    	
    	List<Account> accountList = accountService.getAllAccount();
    	List<PointsUser> pointsUserList = pointsUserService.getAllPointsUser();
    	
    	//调用区块链接口
		Map<String, Object> map = new HashMap<>();
		map.put("account", BeanToMap.ListToMapForInsert(accountList,"0"));
		map.put("pointsUser", BeanToMap.ListToMapForInsert(pointsUserList,"0"));
		String json = JSONObject.fromObject(map).toString();
		Boolean result = false;
		try {
			result = HttpTool.sendToFabric(json, "invoke", "InitData");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result){
			mv.addObject("msg", "初始化成功！");
		}else{
			mv.addObject("msg", "初始化失败，请重新初始化！");
		}
		return mv;
    }
}
