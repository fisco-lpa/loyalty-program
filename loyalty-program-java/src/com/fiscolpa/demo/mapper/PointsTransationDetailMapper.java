package com.fiscolpa.demo.mapper;

import java.util.List;

import com.fiscolpa.demo.model.PointsTransationDetail;
import com.fiscolpa.demo.vo.PointsTransationDetailVo;
import com.fiscolpa.demo.vo.PointsTransationVo;

public interface PointsTransationDetailMapper {
    int deleteByPrimaryKey(String detailId);

    int insert(PointsTransationDetail record);

    int insertSelective(PointsTransationDetail record);

    PointsTransationDetail selectByPrimaryKey(String detailId);

    int updateByPrimaryKeySelective(PointsTransationDetail record);

    int updateByPrimaryKey(PointsTransationDetail record);
    
    List<PointsTransationDetailVo> getCreditPartyCreditDetailList(PointsTransationVo record);
    
    List<PointsTransationDetailVo> getCreditPartyAcceptDetailList(PointsTransationVo record);
}