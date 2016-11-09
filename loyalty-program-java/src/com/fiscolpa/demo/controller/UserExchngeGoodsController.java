
package com.fiscolpa.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsTransationDetail;
import com.fiscolpa.demo.service.PointsExchangeTransationService;
import com.fiscolpa.demo.util.IDCreator;

@Controller
public class UserExchngeGoodsController {
	@Autowired
	private PointsExchangeTransationService pointsExchangeTransationService;

	
	@RequestMapping(value = "/goods",  method = RequestMethod.GET)
	public ModelAndView goods() {
		ModelAndView result = new ModelAndView("goodsShow");
		return result;
	}


	@RequestMapping("/buyGoods")
	@ResponseBody
	public String buyGoods(@RequestParam String goodsName, @RequestParam String pointsNum, HttpSession session) throws Exception{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString1 = formatter.format(currentTime);
		Date date;
		date = formatter.parse(dateString1);
		PointsTransation pointsTransation=new PointsTransation();
		//ID
		String TransId = IDCreator.getInstance().getID("JF_EXCHANGE", "");
		pointsTransation.setTransId(TransId);
		pointsTransation.setCreateTime(date);
		pointsTransation.setCreateUser(session.getAttribute("user").toString());
		pointsTransation.setDescribe("兑换"+pointsNum);
		pointsTransation.setRollInAccount("1");
		pointsTransation.setRollOutAccount("2");
		pointsTransation.setTransAmount(Integer.valueOf(pointsNum));
		pointsTransation.setTransferTime(date);
		pointsTransation.setUpdateTime(date);
		pointsTransation.setUpdateUser("user");
		pointsTransation.setTransferType("3");
		int i=pointsExchangeTransationService.insertExchange(pointsTransation);
		System.out.println(i);
		
		
		PointsTransationDetail pointsTransationDetail=new PointsTransationDetail();
		String detailId = IDCreator.getInstance().getID("EXCHANGE_DETIAL", "");
		pointsTransationDetail.setDetailId(detailId);
		pointsTransationDetail.setCreateTime(date);
		pointsTransationDetail.setCreateUser("3");
		pointsTransationDetail.setCreditParty("2");
		pointsTransationDetail.setCurBalance(Integer.valueOf(pointsNum));
		pointsTransationDetail.setExpireTime(date);
		pointsTransationDetail.setExtRef("999");
		pointsTransationDetail.setMerchant("2");
		pointsTransationDetail.setRollInAccount("1");
		pointsTransationDetail.setRollOutAccount("2");
		pointsTransationDetail.setSourceDetailId("daiding");
		pointsTransationDetail.setStatus("1");
		pointsTransationDetail.setTransAmount(Integer.valueOf(pointsNum));
		pointsTransationDetail.setTransferTime(date);
		pointsTransationDetail.setTransId(TransId);
		pointsTransationDetail.setUpdateTime(date);
		pointsTransationDetail.setUpdateUser("user");
		pointsTransationDetail.setCreditCreateTime(date);
		
		int j=pointsExchangeTransationService.insertDetail(pointsTransationDetail);
		if (i== 1&& j==1) {

			return "1111";
		} 

		return "2222";
		
	}
}
