package com.fiscolpa.demo.service.impl;

import com.fiscolpa.demo.model.Commodity;
import com.fiscolpa.demo.service.CommodityService;
import com.github.pagehelper.PageHelper;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service("commodityService")
public class CommodityServiceImpl extends BaseService<Commodity> implements CommodityService {

    @Override
    public List<Commodity> selectByCommodity(Commodity commodity, int page, int rows) {
        Example example = new Example(Commodity.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(commodity.getCommodityName())) {
            criteria.andLike("commodityName", "%" + commodity.getCommodityName() + "%");
        }
        if (StringUtil.isNotEmpty(commodity.getCommodityId())) {
            criteria.andEqualTo("commodityId", commodity.getCommodityId());
        }
        if (StringUtil.isNotEmpty(commodity.getAccountId())) {
            criteria.andEqualTo("accountId", commodity.getAccountId());
        }
        //分页查询
        PageHelper.startPage(page, rows);
        return selectByExample(example);
    }

}
