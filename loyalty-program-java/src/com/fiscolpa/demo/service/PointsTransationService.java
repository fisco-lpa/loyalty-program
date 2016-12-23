package com.fiscolpa.demo.service;

import java.util.List;

import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.vo.PointsTransationDetailVo;
import com.fiscolpa.demo.vo.PointsTransationVo;


public interface PointsTransationService  {

	/**
	 * 获取授信方授信积分列表信息
	 * @param accountId
	 * @return
	 */
	List<PointsTransationDetailVo> getCreditPartyCreditDetailList (PointsTransationVo pointsTransationVo);
	
	/**
	 * 
	 * 通过账户获取账户转入数据列表
	 * @param accountId
	 * @return
	 */
	List<PointsTransationDetailVo> queryPointsTransationDetail(PointsTransationDetailVo pointsTransationDetailVo);
	
	/**
	 * 授信
	 * @param pointsTransation
	 * @return
	 */
	int addCredit(PointsTransation pointsTransation);
}
