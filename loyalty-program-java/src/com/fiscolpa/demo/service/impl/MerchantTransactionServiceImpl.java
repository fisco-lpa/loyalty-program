package com.fiscolpa.demo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiscolpa.demo.mapper.MerchantTransactionMapper;
import com.fiscolpa.demo.model.PointsTransationExtends;
import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.model.PointsTransationDetailExtends;
import com.fiscolpa.demo.service.MerchantTransactionService;
import com.fiscolpa.demo.service.UserAccountService;
import com.fiscolpa.demo.util.BeanToMap;
import com.fiscolpa.demo.util.DateUtil;
import com.fiscolpa.demo.util.HttpTool;
import com.fiscolpa.demo.util.PointsTransactionEnum;
import com.fiscolpa.demo.util.UUIDGenerator;
import com.mysql.jdbc.StringUtils;

import net.sf.json.JSONObject;

@Service("merchantTransactionService")
public class MerchantTransactionServiceImpl implements MerchantTransactionService {

	@Autowired
	private MerchantTransactionMapper mtm;
	
	@Autowired
	private UserAccountService ua;
	
	@Override
	public String queryPoints(PointsTransationExtends pt) {
		String points = mtm.queryPoints(pt);
		return StringUtils.isNullOrEmpty(points)?"0":points;
	}

	@Override
	public List<PointsTransationExtends> queryTransationList(PointsTransationExtends pt) {
		return mtm.queryTransationList(pt);
	}

	@Override
	public List<PointsTransationDetailExtends> queryTransationDetailList(PointsTransationDetailExtends ptd) {
		return mtm.queryTransationDetailList(ptd);
	}
	
	@Transactional
	@Override
	public String sevePoints(PointsTransationExtends pt){
		//根据用户手机号查询查询账户ID
		String accountId = ua.getUserByAccountId(pt.getRollInAccount());
		if(StringUtils.isNullOrEmpty(accountId)) return "00003";//没有配置账户
		pt.setRollInAccount(accountId);
		//获取账户余额
		int balance = ua.sumByPrimaryKey(pt.getRollOutAccount());
		if(balance<pt.getTransAmount())return "00001";//余额不足
		//修改商户的
		Account out = new Account();
		out.setAccountId(pt.getRollOutAccount());
		out.setAccountBalance(balance-pt.getTransAmount());
		
		//修改会员的
		int  userBalance = ua.sumByPrimaryKey(pt.getRollInAccount());
		Account in = new Account();
		in.setAccountId(pt.getRollInAccount());
		in.setAccountBalance(userBalance+pt.getTransAmount());
		
		List<PointsTransationExtends> ptList = new ArrayList<>();
		String transId = PointsTransactionEnum.GRANT.getBeginning()+UUIDGenerator.getUUID();
		//交易ID
		pt.setTransId(transId);
		pt.setTransferTime(DateUtil.getDateTime());
		pt.setTransferType(PointsTransactionEnum.GRANT.getSign());
		pt.setCreateUser(pt.getRollOutAccount());
		pt.setUpdateUser(pt.getRollOutAccount());
		pt.setOperFlag(PointsTransactionEnum.INSERT.getSign());
		pt.setDescribe("GRANT");
		//交易总表
		ptList.add(pt);
		//交易积分
		Integer transAmount = pt.getTransAmount();
		PointsTransationDetailExtends ptd = new PointsTransationDetailExtends();
		ptd.setRollInAccount(pt.getRollOutAccount());
		ptd.setExpireTime("1");
		ptd.setCurBalance(0);
		//获取所以未过期的积分
		List<PointsTransationDetailExtends> ptdl = queryTransationDetailList(ptd);
		//新增
		List<PointsTransationDetailExtends> salist = new ArrayList<PointsTransationDetailExtends>();
		//修改
		List<PointsTransationDetailExtends> upList = new ArrayList<PointsTransationDetailExtends>();
		
		
		for (int i = 0; i < ptdl.size(); i++) {
			PointsTransationDetailExtends pd = ptdl.get(i);
			//新增流水
			PointsTransationDetailExtends save = new PointsTransationDetailExtends();
			BeanUtils.copyProperties(pd,save);
			save.setDetailId(PointsTransactionEnum.GRANT.getBeginning()+UUIDGenerator.getUUID());
			save.setTransId(transId);
			save.setSourceDetailId(pd.getDetailId());
			save.setRollOutAccount(pt.getRollOutAccount());
			save.setRollInAccount(pt.getRollInAccount());
			save.setOperFlag(PointsTransactionEnum.INSERT.getSign());
			if(StringUtils.isNullOrEmpty(pt.getExpireTime())){
				//save.setExpireTime(pt.getExpireTime());
				save.setExpireTime(DateUtil.addYear(1));
			}
			//修改当前剩余
			PointsTransationDetailExtends up = new PointsTransationDetailExtends();
			up.setDetailId(pd.getDetailId());
			up.setOperFlag(PointsTransactionEnum.UPDATE.getSign());
			if(pd.getCurBalance()>=transAmount){
				save.setTransAmount(transAmount);
				save.setCurBalance(transAmount);
				Integer surplus = pd.getCurBalance()-transAmount;
				up.setCurBalance(surplus);
			}else{
				transAmount-=pd.getCurBalance();
				save.setTransAmount(pd.getCurBalance());
				save.setCurBalance(pd.getCurBalance());
				up.setCurBalance(0);
			}
			salist.add(save);
			upList.add(up);
		}
		
		if(salist.size()<=0) return "00002";//交易失败
		
		//组装区块连账户
		Map<Object, Object> aOut = BeanToMap.Bean2Map(out);
		aOut.put("operFlag", PointsTransactionEnum.UPDATE.getSign());
		Map<Object, Object> iOut = BeanToMap.Bean2Map(in);
		iOut.put("operFlag", PointsTransactionEnum.UPDATE.getSign());
		
		List<Map<Object, Object>> aList = new ArrayList<Map<Object, Object>>();
		aList.add(aOut);
		aList.add(iOut);
		
		List<PointsTransationDetailExtends> pList = new ArrayList<PointsTransationDetailExtends>(); 
		pList.addAll(salist);
		pList.addAll(upList);
		
		Map<String, Object> map = new HashMap<>();
		map.put("accountList", aList);
		map.put("pointsTransaction", BeanToMap.Bean2Map(pt));
		map.put("pointsTransactionDetailList", BeanToMap.Bean2MapList(pList));
		
		String json = JSONObject.fromObject(map).toString();
		Boolean result = false;
		try {
			result = HttpTool.sendToFabric(json, "invoke", "ConsumePoints");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!result) return "00003";//区块连交易失败
		
		mtm.insertTransation(ptList);
		mtm.insertTransationDetail(salist);
		mtm.updateCurBalance(upList);
		ua.updateAccountByBalance(out);
		ua.updateAccountByBalance(in);
		
		return "00000";//交易正常
	}
	
	@Transactional
	@Override
	public String seveAccept(PointsTransationDetailExtends ptd){
		//获取所以未过期的积分
		List<PointsTransationDetailExtends> ptdl = queryTransationDetailList(ptd);
		if(ptdl.size()<=0) return "00001"; //没有可兑换的积分
		
		//新增
		List<PointsTransationDetailExtends> salist = new ArrayList<PointsTransationDetailExtends>();
		//修改
		List<PointsTransationDetailExtends> upList = new ArrayList<PointsTransationDetailExtends>();
		//合并同一个承兑商的积分
    	Map<String, PointsTransationExtends> map = new HashMap<String, PointsTransationExtends>();
		for (int i = 0; i < ptdl.size(); i++) {
			PointsTransationDetailExtends detail = ptdl.get(i);
			String accept = detail.getCreditParty();
			String transId = PointsTransactionEnum.ACCEPT.getBeginning()+UUIDGenerator.getUUID();
			if(map.containsKey(accept)){
				Integer amount = map.get(accept).getTransAmount();
				amount+=detail.getTransAmount();
				map.get(accept).setTransAmount(amount);
			}else{
				PointsTransationExtends pt = new PointsTransationExtends();
				pt.setRollOutAccount(ptd.getRollInAccount());
				pt.setRollInAccount(accept);
				//交易ID
				pt.setTransAmount(detail.getTransAmount());
				pt.setTransId(transId);
				pt.setTransferTime(DateUtil.getDateTime());
				pt.setTransferType(PointsTransactionEnum.ACCEPT.getSign());
				pt.setCreateUser(ptd.getRollInAccount());
				pt.setUpdateUser(ptd.getRollInAccount());
				pt.setDescribe("ACCEPT");
				pt.setOperFlag(PointsTransactionEnum.INSERT.getSign());
				map.put(accept, pt);
			}
			//新增流水
			PointsTransationDetailExtends save = new PointsTransationDetailExtends();
			BeanUtils.copyProperties(detail,save);
			save.setDetailId(PointsTransactionEnum.ACCEPT.getBeginning()+UUIDGenerator.getUUID());
			save.setTransId(transId);
			save.setSourceDetailId(detail.getDetailId());
			save.setRollOutAccount(ptd.getRollInAccount());
			save.setRollInAccount(accept);
			save.setTransAmount(detail.getTransAmount());
			save.setCurBalance(detail.getTransAmount());
			save.setOperFlag(PointsTransactionEnum.INSERT.getSign());
			//修改当前剩余
			PointsTransationDetailExtends up = new PointsTransationDetailExtends();
			up.setDetailId(detail.getDetailId());
			up.setCurBalance(0);
			up.setOperFlag(PointsTransactionEnum.UPDATE.getSign());
			
			salist.add(save);
			upList.add(up);
		}
		//交易表
		List<PointsTransationExtends> ptList = new ArrayList<>(); 
		for (Map.Entry<String, PointsTransationExtends> entry : map.entrySet()) {
			ptList.add(entry.getValue());
		}
		if(salist.size()<=0) return "00002";//交易失败
		
		List<PointsTransationDetailExtends> pList = new ArrayList<PointsTransationDetailExtends>(); 
		pList.addAll(salist);
		pList.addAll(upList);
		
		List<Map<Object, Object>> it = BeanToMap.Bean2MapList(ptList);
		List<Map<Object, Object>> itd = BeanToMap.Bean2MapList(pList);
		
		Map<String, Object> map1 = new HashMap<>();
		map1.put("pointsTransaction", it);
		map1.put("pointsTransactionDetailList", itd);
		
		String json = JSONObject.fromObject(map1).toString();
		Boolean result = false;
		try {
			result = HttpTool.sendToFabric(json, "invoke", "ConsumePoints");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!result) return "00003";//区块连交易失败

		mtm.insertTransation(ptList);
		mtm.insertTransationDetail(salist);
		mtm.updateCurBalance(upList);
		
		return "00000"; //交易正常
	}
	
	

}
