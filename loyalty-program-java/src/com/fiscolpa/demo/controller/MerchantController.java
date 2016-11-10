package com.fiscolpa.demo.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(value="/queryPoints",method = RequestMethod.POST)
	public ModelAndView queryPoints(HttpServletRequest request,HttpSession session){
		return queryMerchantInfo(session, "accept");
	}
	
	
	@RequestMapping(value="/queryTransationList",method = RequestMethod.POST)
	public @ResponseBody List<PointsTransationExtends> queryTransationList(HttpSession session,HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		if(StringUtils.isNullOrEmpty(pt.getRollOutAccount())){
			pt.setRollOutAccount(null);
		}
		pt.setRollInAccount(currentUser.getAccountId());
		pt.setTransferType(PointsTransactionEnum.CREDIT.getSign());
		pt.setQueryColumn("use");
		return mts.queryTransationList(pt);
	}
	
	@RequestMapping(value="/queryTransationDetailList",method = RequestMethod.POST)
	public @ResponseBody List<PointsTransationDetailExtends> queryTransationDetailList(HttpSession session,HttpServletRequest request,@ModelAttribute("ptd") PointsTransationDetailExtends ptd){
		ptd.setTransferType(PointsTransactionEnum.GRANT.getSign());
		return mts.queryTransationDetailList(ptd);
	}
	
	
	@RequestMapping(value="/reqSevePoints",method = RequestMethod.POST)
    public String reqSevePoints() {
    	return "sentOut";
    }
	
	
	@RequestMapping(value="/sevePoints",method = RequestMethod.POST)
	public String sevePoints(HttpSession session,HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		pt.setRollOutAccount(currentUser.getAccountId());
		mts.sevePoints(pt);
		return "sentOut";
	}
	
	@RequestMapping(value="/querySentOutQuery",method = RequestMethod.POST)
	public ModelAndView querySentOutQuery(HttpServletRequest request,HttpSession session){
		return queryMerchantInfo(session, "sentOutQuery");
	}
	
	@RequestMapping(value="/querySentOutQueryList",method = RequestMethod.POST)
	public @ResponseBody List<PointsTransationExtends> querySentOutQueryList(HttpSession session,HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		pt.setRollOutAccount(currentUser.getAccountId());
		pt.setTransferType(PointsTransactionEnum.GRANT.getSign());
		return mts.queryTransationList(pt);
	}
	
	@RequestMapping(value="/queryCredit",method = RequestMethod.POST)
	public ModelAndView queryCredit(HttpServletRequest request,HttpSession session){
		return queryMerchantInfo(session, "credit");
	}
	
	
	@RequestMapping(value="/queryCreditList",method = RequestMethod.POST)
	public @ResponseBody List<PointsTransationExtends> queryCreditList(HttpSession session,HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		if(StringUtils.isNullOrEmpty(pt.getCreditParty())){
			pt.setCreditParty(null);
		}
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		pt.setRollInAccount(currentUser.getAccountId());
		pt.setTransferType(PointsTransactionEnum.BUY.getSign());
		return mts.queryTransationList(pt);
	}
	
	@RequestMapping(value="/reqAccept",method = RequestMethod.POST)
	public String reqAccept(HttpSession session,HttpServletRequest request,@ModelAttribute("ptd") PointsTransationDetailExtends ptd){
		PointsUser currentUser = (PointsUser) session.getAttribute("user");
		ptd.setRollInAccount(currentUser.getAccountId());
		ptd.setTransferType(PointsTransactionEnum.BUY.getSign());
		mts.seveAccept(ptd);
		return "credit";
	}
	
}
