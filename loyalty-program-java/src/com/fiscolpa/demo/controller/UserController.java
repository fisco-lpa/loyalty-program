package com.fiscolpa.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.PointsUserService;

@RestController
public class UserController {
	
	@Autowired
	PointsUserService ps;
	@Autowired
	LoginController lc;
	
	@RequestMapping(value="/registerUser")
	public @ResponseBody Map<String, Object> registerUser(HttpSession session,HttpServletRequest request,@ModelAttribute("pu") PointsUser pu){
		int val = ps.insertPointsUser(pu);
		if(val==1){
			lc.doLogin(pu.getUserName(), pu.getUserPassword(), session);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("state", val);
		return map;
	}
}
