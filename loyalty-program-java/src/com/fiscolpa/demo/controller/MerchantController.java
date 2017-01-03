package com.fiscolpa.demo.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fiscolpa.demo.model.PointsTransationExtends;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.model.PointsTransationDetailExtends;
import com.fiscolpa.demo.service.MerchantTransactionService;
import com.fiscolpa.demo.util.PointsTransactionEnum;
import com.mysql.jdbc.StringUtils;


@Controller
@RequestMapping ("/merchant")  
public class MerchantController {

	@Autowired
	private MerchantTransactionService mts;
	
	@RequestMapping("/merchantTransaction")
    public String merchantTransaction() {
    	return "merchant";
    }
	
	public ModelAndView queryMerchantInfo(HttpSession session,String viewName){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		ModelAndView result = new ModelAndView(viewName);
		result.addObject("name", currentUser.getUserName());
		result.addObject("id", currentUser.getUserId());
		PointsTransationExtends pt = new PointsTransationExtends();
		pt.setRollInAccount(currentUser.getAccountId());
		pt.setTransferType(PointsTransactionEnum.CREDIT.getSign());
        result.addObject("dff", mts.queryPoints(pt));
        PointsTransationExtends pt1 = new PointsTransationExtends();
        pt1.setRollOutAccount(currentUser.getAccountId());
		pt1.setTransferType(PointsTransactionEnum.GRANT.getSign());
        result.addObject("yff", mts.queryPoints(pt1));
        PointsTransationExtends pt2 = new PointsTransationExtends();
        pt2.setRollOutAccount(currentUser.getAccountId());
		pt2.setTransferType(PointsTransactionEnum.ACCEPT.getSign());
        result.addObject("ycd", mts.queryPoints(pt2));
        return result;
	}
	
	@RequestMapping(value="/queryPoints")
	public ModelAndView queryPoints(HttpServletRequest request,HttpSession session){
		return queryMerchantInfo(session, "accept");
	}                                         
	
	@RequestMapping(value="/queryPointsHtml")
	public @ResponseBody Map<String, String> queryPointsHtml(HttpServletRequest request,HttpSession session){
		Map<String,String> map = new HashMap<>();
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		map.put("userName", currentUser.getUserName());
		map.put("userId", currentUser.getUserId());
		PointsTransationExtends pt = new PointsTransationExtends();
		pt.setRollInAccount(currentUser.getAccountId());
		pt.setTransferType(PointsTransactionEnum.CREDIT.getSign());
		map.put("dff", mts.queryPoints(pt));
        PointsTransationExtends pt1 = new PointsTransationExtends();
        pt1.setRollOutAccount(currentUser.getAccountId());
		pt1.setTransferType(PointsTransactionEnum.GRANT.getSign());
		map.put("yff", mts.queryPoints(pt1));
        PointsTransationExtends pt2 = new PointsTransationExtends();
        pt2.setRollOutAccount(currentUser.getAccountId());
		pt2.setTransferType(PointsTransactionEnum.ACCEPT.getSign());
		map.put("ycd", mts.queryPoints(pt2));
		map.put("img",String.valueOf(session.getAttribute("userimg")));
		return map;
	}
	
	                 
	@RequestMapping(value="/queryTransationList")
	public @ResponseBody List<PointsTransationExtends> queryTransationList(HttpSession session,HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		pt.setRollInAccount(currentUser.getAccountId());
		pt.setTransferType(PointsTransactionEnum.CREDIT.getSign());
		return mts.queryTransationList(pt);
	}
	
	@RequestMapping(value="/queryTransationDetailList")
	public @ResponseBody List<PointsTransationDetailExtends> queryTransationDetailList(HttpSession session,HttpServletRequest request,@ModelAttribute("ptd") PointsTransationDetailExtends ptd){
		ptd.setTransferType(PointsTransactionEnum.GRANT.getSign());
		return mts.queryTransationDetailList(ptd);
	}
	
	
	@RequestMapping(value="/reqSevePoints")
    public String reqSevePoints() {
    	return "sentOut";
    }
	
	
	@RequestMapping(value="/sevePoints")
	public String sevePoints(HttpSession session,HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		pt.setRollOutAccount(currentUser.getAccountId());
		mts.sevePoints(pt);
		return "sentOut";
	}
	
	@RequestMapping(value="/sevePointsHtml")
	public @ResponseBody Map<String,String> sevePointsHtml(HttpSession session,HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		Map<String,String> map = new HashMap<String, String>();
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		pt.setRollOutAccount(currentUser.getAccountId());
		pt.setFabricSwitch((String) session.getAttribute(PointsTransactionEnum.FABRIC_SWITCH.getBeginning()));
		map.put("state", mts.sevePoints(pt));
		return map;
	}
	
	@RequestMapping(value="/querySentOutQuery")
	public ModelAndView querySentOutQuery(HttpServletRequest request,HttpSession session){
		return queryMerchantInfo(session, "sentOutQuery");
	}
	
	@RequestMapping(value="/querySentOutQueryList")
	public @ResponseBody List<PointsTransationExtends> querySentOutQueryList(HttpSession session,HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		pt.setRollOutAccount(currentUser.getAccountId());
		pt.setTransferType(PointsTransactionEnum.GRANT.getSign());
		return mts.queryTransationList(pt);
	}
	
	@RequestMapping(value="/queryCredit")
	public ModelAndView queryCredit(HttpServletRequest request,HttpSession session){
		return queryMerchantInfo(session, "credit");
	}
	
	
	@RequestMapping(value="/queryCreditList")
	public @ResponseBody List<PointsTransationExtends> queryCreditList(HttpSession session,HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		if(StringUtils.isNullOrEmpty(pt.getCreditParty())){
			pt.setCreditParty(null);
		}
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		pt.setRollInAccount(currentUser.getAccountId());
		pt.setTransferType(PointsTransactionEnum.BUY.getSign());
		return mts.queryTransationList(pt);
	}
	
	@RequestMapping(value="/reqAccept")
	public String reqAccept(HttpSession session,HttpServletRequest request,@ModelAttribute("ptd") PointsTransationDetailExtends ptd){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		ptd.setRollInAccount(currentUser.getAccountId());
		ptd.setTransferType(PointsTransactionEnum.BUY.getSign());
		ptd.setCurBalance(0);
		try {
			mts.seveAccept(ptd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "credit";
	}
	
	@RequestMapping(value="/reqAcceptHtml")
	public @ResponseBody Map<String,String> reqAcceptHtml(HttpSession session,HttpServletRequest request,@ModelAttribute("ptd") PointsTransationDetailExtends ptd){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		ptd.setRollInAccount(currentUser.getAccountId());
		ptd.setTransferType(PointsTransactionEnum.BUY.getSign());
		ptd.setCurBalance(0);
		ptd.setFabricSwitch((String) session.getAttribute(PointsTransactionEnum.FABRIC_SWITCH.getBeginning()));
		String state = null;
		try {
			state = mts.seveAccept(ptd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("state", state);
		return map;
	}
}
