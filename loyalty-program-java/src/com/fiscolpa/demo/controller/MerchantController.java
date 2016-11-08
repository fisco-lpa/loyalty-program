package com.fiscolpa.demo.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fiscolpa.demo.model.PointsTransationExtends;
import com.fiscolpa.demo.model.PointsTransationDetailExtends;
import com.fiscolpa.demo.service.MerchantTransactionService;
import com.mysql.jdbc.StringUtils;

@Controller
public class MerchantController {

	private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);

	@Autowired
	private MerchantTransactionService mts;
	
	@RequestMapping("/merchantTransaction")
    public String merchantTransaction() {
    	return "merchant";
    }
	
	@RequestMapping(value="/queryPoints",method = RequestMethod.POST)
	public ModelAndView queryPoints(HttpServletRequest request,HttpSession session){
		//PointsUser currentUser = (PointsUser) session.getAttribute("user");
		ModelAndView result = new ModelAndView("accept");
		PointsTransationExtends pt = new PointsTransationExtends();
		pt.setRollInAccount("2");
		pt.setTransferType("1");
        result.addObject("dff", mts.queryPoints(pt));
        PointsTransationExtends pt1 = new PointsTransationExtends();
        pt1.setRollOutAccount("2");
		pt1.setTransferType("2");
        result.addObject("yff", mts.queryPoints(pt1));
        PointsTransationExtends pt2 = new PointsTransationExtends();
        pt2.setRollOutAccount("2");
		pt2.setTransferType("3");
        result.addObject("ycd", mts.queryPoints(pt2));
        return result;
	}
	
	
	@RequestMapping(value="/queryTransationList",method = RequestMethod.POST)
	public @ResponseBody List<PointsTransationExtends> queryTransationList(HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		if(StringUtils.isNullOrEmpty(pt.getRollOutAccount())){
			pt.setRollOutAccount(null);
		}
		pt.setRollInAccount("2");
		pt.setTransferType("1");
		pt.setQueryColumn("use");
		return mts.queryTransationList(pt);
	}
	
	@RequestMapping(value="/queryTransationDetailList",method = RequestMethod.POST)
	public @ResponseBody List<PointsTransationDetailExtends> queryTransationDetailList(HttpServletRequest request,@ModelAttribute("ptd") PointsTransationDetailExtends ptd){
		ptd.setTransferType("2");
		return mts.queryTransationDetailList(ptd);
	}
	
	
	@RequestMapping(value="/reqSevePoints",method = RequestMethod.POST)
    public String reqSevePoints() {
    	return "sentOut";
    }
	
	
	@RequestMapping(value="/sevePoints",method = RequestMethod.POST)
	public String sevePoints(HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		pt.setRollOutAccount("2");
		mts.sevePoints(pt);
		return "sentOut";
	}
	
	@RequestMapping(value="/querySentOutQuery",method = RequestMethod.POST)
	public ModelAndView querySentOutQuery(HttpServletRequest request,HttpSession session){
		//PointsUser currentUser = (PointsUser) session.getAttribute("user");
		ModelAndView result = new ModelAndView("sentOutQuery");
		PointsTransationExtends pt = new PointsTransationExtends();
		pt.setRollInAccount("2");
		pt.setTransferType("1");
        result.addObject("dff", mts.queryPoints(pt));
        result.addObject("yff", "345678");
        result.addObject("ycd", "56789");
        return result;
	}
	
	@RequestMapping(value="/querySentOutQueryList",method = RequestMethod.POST)
	public @ResponseBody List<PointsTransationExtends> querySentOutQueryList(HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		pt.setRollOutAccount("2");
		pt.setTransferType("2");
		return mts.queryTransationList(pt);
	}
	
	@RequestMapping(value="/queryCredit",method = RequestMethod.POST)
	public ModelAndView queryCredit(HttpServletRequest request,HttpSession session){
		//PointsUser currentUser = (PointsUser) session.getAttribute("user");
		ModelAndView result = new ModelAndView("credit");
		PointsTransationExtends pt = new PointsTransationExtends();
		pt.setRollInAccount("2");
		pt.setTransferType("1");
        result.addObject("dff", mts.queryPoints(pt));
        result.addObject("yff", "345678");
        result.addObject("ycd", "56789");
        return result;
	}
	
	
	@RequestMapping(value="/queryCreditList",method = RequestMethod.POST)
	public @ResponseBody List<PointsTransationExtends> queryCreditList(HttpServletRequest request,@ModelAttribute("pt") PointsTransationExtends pt){
		if(StringUtils.isNullOrEmpty(pt.getCreditParty())){
			pt.setCreditParty(null);
		}
		pt.setRollInAccount("2");
		pt.setTransferType("3");
		return mts.queryTransationList(pt);
	}
	
	@RequestMapping(value="/reqAccept",method = RequestMethod.POST)
	public String reqAccept(HttpServletRequest request,@ModelAttribute("ptd") PointsTransationDetailExtends ptd){
		ptd.setRollInAccount("2");
		ptd.setTransferType("3");
		mts.seveAccept(ptd);
		return "credit";
	}
	
}
