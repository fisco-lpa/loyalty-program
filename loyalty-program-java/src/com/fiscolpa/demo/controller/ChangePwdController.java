package com.fiscolpa.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.ChangePwdService;
import com.fiscolpa.demo.service.PointsUserService;
@Controller
public class ChangePwdController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChangePwdController.class);
	
	@Autowired
	private PointsUserService pointsUserService;
	
	@Autowired
	private ChangePwdService changePwdService;
	
    @RequestMapping("/toChangePwd")
    public String toLogin() {
    	return "changePwd";
    }
    
    @RequestMapping("/checkPwd")
    @ResponseBody
    public String checkPwd(@RequestParam String userName, @RequestParam String userPassword){
    	PointsUser pointsUser = new PointsUser();
    	pointsUser.setUserName(userName);
    	pointsUser.setUserPassword(userPassword);
    	List<PointsUser> userList = pointsUserService.select(pointsUser);
    	if (userList.size() == 1) {
    		logger.info("密码验证正确");
    		return "111";
    	} else {
    		logger.error("密码验证错误");
    		return "222";
    	}
    }
    
    @RequestMapping("/changePwd")
    public ModelAndView changePwd(@RequestParam String userName, @RequestParam String password){
    	PointsUser pointsUser = new PointsUser();
    	pointsUser.setUserName(userName);
    	pointsUser.setUserPassword(password);
    	int n=changePwdService.updatePwd(pointsUser);
    	if(n==1){
    		ModelAndView result = new ModelAndView("changePwd");
    		return result;
    	}
    	ModelAndView result = new ModelAndView("login");
		return result;
    }
    

}
