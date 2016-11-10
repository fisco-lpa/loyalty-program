package com.fiscolpa.demo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiscolpa.demo.mapper.AccountMapper;
import com.fiscolpa.demo.mapper.PointsTransationDetailMapper;
import com.fiscolpa.demo.mapper.PointsTransationMapper;
import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsTransationDetail;
import com.fiscolpa.demo.service.PointsTransationService;
import com.fiscolpa.demo.util.UUIDGenerator;
import com.fiscolpa.demo.vo.PointsTransationDetailVo;
import com.fiscolpa.demo.vo.PointsTransationVo;
import com.github.pagehelper.PageHelper;

@Service("pointsTransationService")
public class PointsTransationServiceImpl implements PointsTransationService {
	@Autowired
	private PointsTransationMapper pointsTransationMapper;
	@Autowired
	private PointsTransationDetailMapper pointsTransationDetailMapper;
	@Autowired
	private AccountMapper accountMapper;

	public List<PointsTransationDetailVo> getCreditPartyCreditDetailList(PointsTransationVo pointsTransationVo, int page, int rows) {
        PageHelper.startPage(page, rows);
        return pointsTransationDetailMapper.getCreditPartyCreditDetailList(pointsTransationVo);
    }

	@Override
	public List<PointsTransationDetailVo> queryPointsTransationDetail(PointsTransationVo pointsTransationVo, int page, int rows) {
		PageHelper.startPage(page, rows);
		return pointsTransationDetailMapper.getCreditPartyAcceptDetailList(pointsTransationVo);
	}

	@Override
	public int addCredit(PointsTransation pointsTransation) {
		//insert交易表
		String transId = new StringBuffer("PT").append(UUIDGenerator.getUUID()).toString();
		pointsTransation.setTransId(transId);
		Date time=new Date();
//		String time = dateFormater.format(date);
//		System.out.print(dateFormater.format(date));
		pointsTransation.setCreateTime(time);
		pointsTransation.setUpdateTime(time);
		pointsTransation.setTransferTime(time);
		pointsTransation.setTransferType("1");
		pointsTransation.setUpdateUser(pointsTransation.getCreateUser());
		pointsTransation.setTransferTime(time);
		pointsTransation.setDescribe("授信");
		pointsTransationMapper.insert(pointsTransation);
		//insert交易明细表
		PointsTransationDetail pointsTransationDetail = new PointsTransationDetail();
		String detailId = new StringBuffer("PTD").append(UUIDGenerator.getUUID()).toString();
		pointsTransationDetail.setDetailId(detailId);
		pointsTransationDetail.setTransId(pointsTransation.getTransId());//积分交易ID
		pointsTransationDetail.setCreateUser(pointsTransation.getCreateUser());//
		pointsTransationDetail.setUpdateUser(pointsTransation.getUpdateUser());//
		pointsTransationDetail.setCreateTime(time);
		pointsTransationDetail.setUpdateTime(time);
		pointsTransationDetail.setTransferTime(time);
		pointsTransationDetail.setExtRef("1111");
		SimpleDateFormat dateFormater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateFormater.parse("9999-12-31 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		pointsTransationDetail.setExpireTime(date);
		//TODO Q:授信创建时间，是在mysql写还是在java代码里写？若在sql里，需要单独的insert语句（或者insert加个判断）
		//TODO 主键--流水号，创建时间，修改时间，创建人，修改人，交易时间，待定
		pointsTransationDetail.setRollInAccount(pointsTransation.getRollInAccount());//转入账户
		pointsTransationDetail.setRollOutAccount(pointsTransation.getRollOutAccount());//转出账户
		pointsTransationDetail.setTransAmount(pointsTransation.getTransAmount());//交易数量
		pointsTransationDetail.setCurBalance(pointsTransation.getTransAmount());//剩余数量---授信即交易数量
		pointsTransationDetail.setCreditParty(pointsTransation.getRollOutAccount());//授信方---授信即转出账户
		pointsTransationDetail.setMerchant(pointsTransation.getRollInAccount());//商家----授信即转入账户
		pointsTransationDetail.setCreditCreateTime(time);
		pointsTransationDetail.setStatus("1");
		
		pointsTransationDetailMapper.insert(pointsTransationDetail);
		
		//更新账户表
		//先计算余额再更新
		int balance = accountMapper.selectByPrimaryKey(pointsTransation.getRollInAccount()).getAccountBalance()+pointsTransation.getTransAmount();
		Account account = new Account();
		account.setAccountId(pointsTransation.getRollInAccount());
		account.setAccountBalance(balance);
		account.setUpdateUser(pointsTransation.getCreateUser());
		accountMapper.updateByPrimaryKeySelective(account);
		
		return 0;
	}

	

}
