
package com.fiscolpa.demo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsTransationDetail;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.MerchantTransactionService;
import com.fiscolpa.demo.service.PointsExchangeTransationService;
import com.fiscolpa.demo.service.UserAccountService;
import com.fiscolpa.demo.util.BeanToMap;
import com.fiscolpa.demo.util.HttpTool;
import com.fiscolpa.demo.util.IDCreator;
import com.fiscolpa.demo.util.PointsTransactionEnum;
import com.fiscolpa.demo.util.UUIDGenerator;

import net.sf.json.JSONObject;

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
		//要插入交易表的交易信息
		PointsTransation pointsTransation=new PointsTransation();
		//交易ID
		String TransId = PointsTransactionEnum.BUY.getBeginning()+UUIDGenerator.getUUID();
		
		pointsTransation.setTransId(TransId);
		pointsTransation.setCreateTime(date);
		pointsTransation.setCreateUser(currentUser.getUserId());
		pointsTransation.setDescribe("兑换"+pointsNum);
		pointsTransation.setRollInAccount(accountMallId);
		pointsTransation.setRollOutAccount(currentUser.getAccountId());
		pointsTransation.setTransAmount(Integer.valueOf(pointsNum));
		pointsTransation.setTransferTime(date);
		pointsTransation.setUpdateTime(date);
		pointsTransation.setUpdateUser(currentUser.getUserName());
		pointsTransation.setTransferType(PointsTransactionEnum.BUY.getSign());
		pointsTransation.setDescribe("BUY");
		
		int account_balance	=pointsExchangeTransationService.selectAccountBalance(currentUser.getAccountId());
		if(account_balance <Integer.valueOf(pointsNum)){
			return "2222";
		}

		
		//修改用户的余额
		Account out = new Account();
		out.setAccountId(currentUser.getAccountId());
		out.setAccountBalance(account_balance-NeedJiFen);
		out.setUpdateTime(date);
		out.setUpdateUser(currentUser.getCreateUser());
		account.updateAccountByBalance(out);
		//修改商户的
		int account_balance2	=pointsExchangeTransationService.selectAccountBalance(accountMallId);
		Account in = new Account();
		in.setAccountId(accountMallId);
		in.setAccountBalance(account_balance2+NeedJiFen);
		in.setUpdateTime(date);
		in.setUpdateUser(currentUser.getCreateUser());
		account.updateAccountByBalance(in);
		
		
		PointsTransationDetail pointsTransationDetail=new PointsTransationDetail();
		String detailId = IDCreator.getInstance().getID("EXCHANGE_DETIAL", "");
		pointsTransationDetail.setDetailId(detailId);
		pointsTransationDetail.setRollInAccount(currentUser.getAccountId());
		pointsTransationDetail.setRollOutAccount(accountMallId);

		//获取所有未过期的积分
		List<PointsTransationDetail> JFList = pointsExchangeTransationService.queryTransationDetailList(pointsTransationDetail);
		//新增
		List<PointsTransationDetail> salist = new ArrayList<PointsTransationDetail>();
		//修改主要是插入块中
		List<PointsTransationDetail> upList = new ArrayList<PointsTransationDetail>();
		for (int j = 0; j < JFList.size(); j++) {
			PointsTransationDetail pd = JFList.get(j);
			
			//新增流水
			PointsTransationDetail save = new PointsTransationDetail();
			BeanUtils.copyProperties(pd,save);
			String detailIdIncrese = PointsTransactionEnum.BUY.getBeginning()+UUIDGenerator.getUUID();
			save.setDetailId(detailIdIncrese);
			save.setSourceDetailId(pd.getDetailId());
			save.setTransId(TransId);
			save.setRollOutAccount(currentUser.getAccountId());
			save.setRollInAccount(accountMallId);
			//save.setCreditCreateTime(date);
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
				salist.add(save);
				upList.add(pd);
				break;
				
			}else{
				save.setTransAmount(pd.getCurBalance());
				save.setCurBalance(pd.getCurBalance());
				NeedJiFen-=pd.getCurBalance();
				pd.setCurBalance(0);
				//新增交易明细
				pointsExchangeTransationService.insertDetail(save);
				//修改交易明细
				pointsExchangeTransationService.updateByPrimaryKeySelective(pd);
				salist.add(save);
				upList.add(pd);
			}
			
		
		}
	
		//插入交易表
		int i=pointsExchangeTransationService.insertExchange(pointsTransation);
		
		
		
		//组装区块连账户
		//存储账户
		List<Account> aList = new ArrayList<Account>();
		aList.add(out);
		aList.add(in);
	
		
		Map<String, Object> map = new HashMap<>();
		map.put("accountList",BeanToMap.ListToMapForInsert(aList,"1"));
		
		map.put("pointsTransaction", BeanToMap.BeanToMapForInsert(pointsTransation,"0"));
		List list = BeanToMap.ListToMapForInsert(salist,"0");
		list.addAll(BeanToMap.ListToMapForInsert(upList,"1"));
		map.put("pointsTransactionDetailList",list);
		String json = JSONObject.fromObject(map).toString();
		
		Boolean result = false;
		try {
			result = HttpTool.sendToFabric(json, "invoke", "ConsumePoints");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!result) return "00003";//区块连交易失败
		
		
		if (i== 1) {
			//交易不正常
			return "1111";
		} 

		return "2222";
		
	}
}
