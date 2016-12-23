package com.fiscolpa.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.AccountService;
import com.fiscolpa.demo.service.PointsUserService;
import com.fiscolpa.demo.util.MenusUtil;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private PointsUserService pointsUserService;
	
	@Autowired
	private AccountService accountService;

    @RequestMapping("/toLogin")
    public String toLogin() {
    	return "login";
    }

    @RequestMapping("/doLogin")
    public @ResponseBody Map<String, String> doLogin(@RequestParam String userName, @RequestParam String password, HttpSession session) {
    	Map<String, String> map =  new HashMap<String, String>();
    	PointsUser pointsUser = new PointsUser();
    	pointsUser.setUserName(userName);
    	pointsUser.setUserPassword(password);
    	List<PointsUser> userList = pointsUserService.selectUserAndUserType(pointsUser);
    	if (userList.size() == 1) {
    		PointsUser currentUser = userList.get(0);
    		logger.info("Login success! User Type:" + currentUser.getUserType());
    		// 查询用户的账户余额
    		Account account = new Account();
    		account.setUserId(currentUser.getUserId());
    		account.setAccountTypeId(currentUser.getUserType());
    		Account currentAccount = accountService.selectOne(account);
    		currentUser.setAccountId(currentAccount.getAccountId());
    		
    		session.setAttribute("userimg", currentUser.getImg());
    		session.setAttribute("user", currentUser);
    		session.setAttribute("menus", MenusUtil.getMenus(String.valueOf(currentUser.getUserType())));
    		map.put("userType", currentUser.getUserType()); 
    		return map;
		} else {
    		logger.error("Login fail!");
    		map.put("userType", null);
    		return map;		
		}
    }
    
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
    	session.removeAttribute("user");
    	return "redirect:/tologin";
    }
    
}
