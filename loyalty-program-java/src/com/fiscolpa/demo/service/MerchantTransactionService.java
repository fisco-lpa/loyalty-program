package com.fiscolpa.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fiscolpa.demo.model.PointsTransationExtends;
import com.fiscolpa.demo.model.PointsTransationDetailExtends;

@Service
public interface MerchantTransactionService{
	
	//授信查询--------------------------------------------------------------------------
	/**
	 * 更具类型查询积分 待发积分、已发送、已承兑
	 * @param roll_in_account, transfer_type
	 * @return
	 */
	public String queryPoints(PointsTransationExtends pt);
	
	/**
	 * 查询交易信息 
	 * @param roll_out_account,createTime,updateTime,需要带出流水号
	 * @return
	 */
	public List<PointsTransationExtends> queryTransationList(PointsTransationExtends pt);
	
	/**
	 * 授信已发放详细
	 * 积分发放查询
	 * 会员购买的积分
	 * @param ptd。credit_party、merchant、credit_create_time
	 * @return
	 */
	public List<PointsTransationDetailExtends> queryTransationDetailList(PointsTransationDetailExtends ptd);
	
	//积分发放--------------------------------------------------------------------------
	/**
	 * 
	 * 获取账户积分
	 * @param userId
	 * @return
		public List<PointsTransationDetail> queryAccountPoints(String userId);
	 */

	/**
	 * 插入积分
	 * （写入points_transation、points_transation_detail表，修改使用那条积分的余额）
	 * @param pt
	 */
	public void sevePoints(PointsTransationExtends pt);
	
	/**
	 * 修改当前积分记录
	 * @param ptd
	
	public void updateCurrentPoints(PointsTransationDetail ptd);
	 */
	
	/**
	 * 商户发起承兑
	 * @param pt
	 */
	public void seveAccept(PointsTransationDetailExtends ptd);
	
}
