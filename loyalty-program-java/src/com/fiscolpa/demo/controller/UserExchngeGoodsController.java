
package com.fiscolpa.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBodyReturnValueHandler;

import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsTransationDetail;
import com.fiscolpa.demo.model.PointsTransationDetailExtends;
import com.fiscolpa.demo.model.PointsTransationExtends;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.MerchantTransactionService;
import com.fiscolpa.demo.service.PointsExchangeTransationService;
import com.fiscolpa.demo.service.UserAccountService;
import com.fiscolpa.demo.util.IDCreator;
import com.fiscolpa.demo.util.PointsTransactionEnum;
import com.fiscolpa.demo.util.UUIDGenerator;
import com.mysql.jdbc.StringUtils;

@Controller
public class UserExchngeGoodsController {
	@Autowired
	private PointsExchangeTransationService pointsExchangeTransationService;
	@Autowired
	private MerchantTransactionService merchantTransactionService;
	@Autowired
	private UserAccountService account;
	
	@RequestMapping(value = "/goods",  method = RequestMethod.GET)
	public ModelAndView goods() {
		ModelAndView result = new ModelAndView("goodsShow");
		return result;
	}


	@RequestMapping("/buyGoods")
	@ResponseBody
	public String buyGoods(@RequestParam String goodsName, @RequestParam String pointsNum,@RequestParam String accountMallId, HttpSession session) throws Exception{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString1 = formatter.format(currentTime);
		Date date;
		date = formatter.parse(dateString1);
		Integer NeedJiFen=Integer.valueOf(pointsNum);
		//获取session的值
		PointsUser currentUser=(PointsUser) session.getAttribute("user");

		PointsTransation pointsTransation=new PointsTransation();
		//交易ID
		String TransId = PointsTransactionEnum.BUY.getBeginning()+UUIDGenerator.getUUID();
		//String TransId = IDCreator.getInstance().getID("JF_EXCHANGE", "");
		pointsTransation.setTransId(TransId);
		pointsTransation.setCreateTime(date);
		pointsTransation.setCreateUser(currentUser.getCreateUser());
		pointsTransation.setDescribe("兑换"+pointsNum);
		pointsTransation.setRollInAccount(accountMallId);
		pointsTransation.setRollOutAccount(currentUser.getAccountId());
		pointsTransation.setTransAmount(Integer.valueOf(pointsNum));
		pointsTransation.setTransferTime(date);
		pointsTransation.setUpdateTime(date);
		pointsTransation.setUpdateUser(currentUser.getCreateUser());
		pointsTransation.setTransferType("2");
		
		int account_balance	=pointsExchangeTransationService.selectAccountBalance(currentUser.getAccountId());
		if(account_balance <Integer.valueOf(pointsNum)){
			return "2222";
		}

		
		//修改用户的余额
		Account out = new Account();
		out.setAccountId(currentUser.getAccountId());
		out.setAccountBalance(account_balance-NeedJiFen);
		account.updateAccountByBalance(out);
		//修改商户的
		int account_balance2	=pointsExchangeTransationService.selectAccountBalance("2");
		Account in = new Account();
		in.setAccountId(accountMallId);
		in.setAccountBalance(account_balance2+NeedJiFen);
		account.updateAccountByBalance(in);
		
		
		PointsTransationDetail pointsTransationDetail=new PointsTransationDetail();
		String detailId = IDCreator.getInstance().getID("EXCHANGE_DETIAL", "");
		pointsTransationDetail.setDetailId(detailId);
//		pointsTransationDetail.setCreateTime(date);
//		pointsTransationDetail.setCreateUser(currentUser.getCreateUser());
		//pointsTransationDetail.setCreditParty("2");
//		pointsTransationDetail.setCurBalance(Integer.valueOf(pointsNum));
//		pointsTransationDetail.setExpireTime(formatter.parse("2017-11-07 18:34:00"));
//		pointsTransationDetail.setExtRef("111");
//		pointsTransationDetail.setMerchant("2");
		pointsTransationDetail.setRollInAccount(currentUser.getAccountId());
		pointsTransationDetail.setRollOutAccount(accountMallId);
//		pointsTransationDetail.setSourceDetailId("daiding");
//		pointsTransationDetail.setStatus("1");
//		pointsTransationDetail.setTransAmount(Integer.valueOf(pointsNum));
//		pointsTransationDetail.setTransferTime(date);
//		pointsTransationDetail.setTransId(TransId);
//		pointsTransationDetail.setUpdateTime(date);
//		pointsTransationDetail.setUpdateUser("user");
//		pointsTransationDetail.setCreditCreateTime(date);

		List<PointsTransationDetail> JFList = pointsExchangeTransationService.queryTransationDetailList(pointsTransationDetail);
		
		for (int j = 0; j < JFList.size(); j++) {
			PointsTransationDetail pd = JFList.get(j);
			
			//新增流水
			PointsTransationDetail save = new PointsTransationDetail();
			BeanUtils.copyProperties(pd,save);
			String detailIdIncrese = PointsTransactionEnum.BUY.getBeginning()+UUIDGenerator.getUUID();
		//	String detailIdIncrese = IDCreator.getInstance().getID("EXCHANGE_DETIAL", "");
			save.setDetailId(detailIdIncrese);
			save.setSourceDetailId(pd.getDetailId());
			save.setTransId(TransId);
			save.setRollOutAccount(currentUser.getAccountId());
			save.setRollInAccount(accountMallId);
			save.setCreditCreateTime(date);
			save.setUpdateTime(date);
			save.setTransferTime(date);
			save.setCreateTime(date);
			//修改当前剩余
			
			pd.setUpdateTime(date);
			if(pd.getCurBalance()>=NeedJiFen){
				save.setTransAmount(NeedJiFen);
				save.setCurBalance(NeedJiFen);
				Integer surplus = pd.getCurBalance()-NeedJiFen;
				pd.setCurBalance(surplus);
				//新增交易明细
				pointsExchangeTransationService.insertDetail(save);
				//修改交易明细
				pointsExchangeTransationService.updateByPrimaryKeySelective(pd);
				break;
			}else{
				save.setTransAmount(pd.getCurBalance());
				save.setCurBalance(pd.getCurBalance());
				NeedJiFen-=pd.getCurBalance();
				pd.setCurBalance(0);
				//新增交易明细
				pointsExchangeTransationService.insertDetail(save);
				pointsExchangeTransationService.updateByPrimaryKeySelective(pd);
			}
			
		
		}
	
		//插入交易表
		int i=pointsExchangeTransationService.insertExchange(pointsTransation);
		
		if (i== 1) {

			return "1111";
		} 

		return "2222";
		
	}
}
