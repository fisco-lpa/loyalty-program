package com.fiscolpa.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiscolpa.demo.mapper.UserPointsTransationDetailMapper;
import com.fiscolpa.demo.model.PointsTransationDetail;
import com.fiscolpa.demo.service.PointsTDService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;

import tk.mybatis.mapper.entity.Example;

@Service("pointsTransationDetailService")
public class PointsTDServiceImpl extends BaseService<PointsTransationDetail> implements PointsTDService {

	private static final Logger logger = LoggerFactory.getLogger(PointsTDServiceImpl.class);
	
	@Override
	public List<PointsTransationDetail> selectByPointsTransationDetail(PointsTransationDetail pointsTransationDetail,int page, int rows) {
		 	Example example = new Example(PointsTransationDetail.class);
	        Example.Criteria criteria = example.createCriteria();
	        if (StringUtil.isNotEmpty(pointsTransationDetail.getRollInAccount())) {
	            criteria.andLike("rollInAccount", "%" + pointsTransationDetail.getRollInAccount() + "%");
	        }
	        if (StringUtil.isNotEmpty(pointsTransationDetail.getRollOutAccount())) {
	            criteria.andLike("rollOutAccount", "%" + pointsTransationDetail.getRollOutAccount() + "%");
	        }
	        /*if (StringUtil.isNotEmpty(pointsTransationDetail.getCreateTime().toString())) {
	            criteria.andBetween("createTime", pointsTransationDetail.getCreateTime(), pointsTransationDetail.getCreateTime());
	        }*/

	        //分页查询
	        PageHelper.startPage(page, rows);
	        //List<PointsTransationDetail> ptd = selectByExample(example);
	        //logger.info("xxxxxxxxxxxxxxxxxxxxxxx"+ptd.get(0).toString());
	        return selectByExample(example);
	}

	@Autowired  
    private UserPointsTransationDetailMapper userPointsTransationDetailMapper;  
	
	@Override
	public int sumByRollInAccount(String rollInAccount) {
		return userPointsTransationDetailMapper.sumByRollInAccount(rollInAccount);
	}

	@Override
	public int sumByRollOutAccount(String rollOutAccount) {
		return userPointsTransationDetailMapper.sumByRollOutAccount(rollOutAccount);
	}

	@Override
	public List<PointsTransationDetail> selectByAccount(PointsTransationDetail record, int page, int rows) {
		PageHelper.startPage(page, rows);
		List<PointsTransationDetail> ptd = userPointsTransationDetailMapper.selectByAccount(record);
		return ptd;
	}
	
}
