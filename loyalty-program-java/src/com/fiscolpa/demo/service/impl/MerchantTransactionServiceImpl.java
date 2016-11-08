package com.fiscolpa.demo.service.impl;

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
import com.fiscolpa.demo.model.PointsTransationDetailExtends;
import com.fiscolpa.demo.service.MerchantTransactionService;
import com.fiscolpa.demo.util.UUIDGenerator;
import com.mysql.jdbc.StringUtils;

@Service("merchantTransactionService")
public class MerchantTransactionServiceImpl implements MerchantTransactionService {

	@Autowired
	private MerchantTransactionMapper mtm;
	
	@Override
	public String queryPoints(PointsTransationExtends pt) {
		return mtm.queryPoints(pt);
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
	public void sevePoints(PointsTransationExtends pt) {
		List<PointsTransationExtends> ptList = new ArrayList<>();
		String transId = UUIDGenerator.getUUID();
		//交易ID
		pt.setTransId(transId);
		pt.setTransferTime("2016-11-07 18:34:00");
		pt.setTransferType("2");
		pt.setCreateUser("admin");
		pt.setUpdateUser("admin");
		//交易总表
		ptList.add(pt);
		//交易积分
		Integer transAmount = pt.getTransAmount();
		PointsTransationDetailExtends ptd = new PointsTransationDetailExtends();
		ptd.setRollInAccount(pt.getRollOutAccount());
		ptd.setExpireTime("1");
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
			save.setDetailId(UUIDGenerator.getUUID());
			save.setTransId(transId);
			save.setSourceDetailId(pd.getDetailId());
			save.setRollOutAccount(pt.getRollOutAccount());
			save.setRollInAccount(pt.getRollInAccount());
			if(StringUtils.isNullOrEmpty(pt.getExpireTime())){
				//save.setExpireTime(pt.getExpireTime());
				save.setExpireTime("2017-11-07 18:34:00");
			}
			//修改当前剩余
			PointsTransationDetailExtends up = new PointsTransationDetailExtends();
			up.setDetailId(pd.getDetailId());
			
			if(pd.getCurBalance()>=transAmount){
				save.setTransAmount(transAmount);
				save.setCurBalance(transAmount);
				Integer surplus = pd.getCurBalance()-transAmount;
				up.setCurBalance(surplus);
			}else{
				save.setTransAmount(pd.getCurBalance());
				save.setCurBalance(pd.getCurBalance());
				up.setCurBalance(0);
			}
			salist.add(save);
			upList.add(up);
		}
		
		if(salist.size()>0){
			mtm.insertTransation(ptList);
			mtm.insertTransationDetail(salist);
			mtm.updateCurBalance(upList);
		}
	}

	@Override
	public void seveAccept(PointsTransationDetailExtends ptd) {
		//新增
		List<PointsTransationDetailExtends> salist = new ArrayList<PointsTransationDetailExtends>();
		//修改
		List<PointsTransationDetailExtends> upList = new ArrayList<PointsTransationDetailExtends>();
		//合并同一个承兑商的积分
    	Map<String, PointsTransationExtends> map = new HashMap<String, PointsTransationExtends>();
		//获取所以未过期的积分
		List<PointsTransationDetailExtends> ptdl = queryTransationDetailList(ptd);
		for (int i = 0; i < ptdl.size(); i++) {
			PointsTransationDetailExtends detail = ptdl.get(i);
			String accept = detail.getCreditParty();
			String transId = UUIDGenerator.getUUID();
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
				pt.setTransferTime("2016-11-07 18:34:00");
				pt.setTransferType("3");
				pt.setCreateUser("admin");
				pt.setUpdateUser("admin");
				map.put(accept, pt);
			}
			//新增流水
			PointsTransationDetailExtends save = new PointsTransationDetailExtends();
			BeanUtils.copyProperties(detail,save);
			save.setDetailId(UUIDGenerator.getUUID());
			save.setTransId(transId);
			save.setSourceDetailId(detail.getDetailId());
			save.setRollOutAccount(ptd.getRollInAccount());
			save.setRollInAccount(accept);
			save.setTransAmount(detail.getTransAmount());
			save.setCurBalance(detail.getTransAmount());
			//修改当前剩余
			PointsTransationDetailExtends up = new PointsTransationDetailExtends();
			up.setDetailId(detail.getDetailId());
			up.setCurBalance(0);
			
			salist.add(save);
			upList.add(up);
		}
		//交易表
		List<PointsTransationExtends> ptList = new ArrayList<>(); 
		for (Map.Entry<String, PointsTransationExtends> entry : map.entrySet()) {
			ptList.add(entry.getValue());
		}
		if(salist.size()>0){
			mtm.insertTransation(ptList);
			mtm.insertTransationDetail(salist);
			mtm.updateCurBalance(upList);
		}
	}
	
	

}
