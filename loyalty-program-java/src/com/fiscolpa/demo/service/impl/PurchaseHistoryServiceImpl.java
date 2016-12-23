package com.fiscolpa.demo.service.impl;

import com.fiscolpa.demo.model.PurchaseHistory;
import com.fiscolpa.demo.service.PurchaseHistoryService;
import com.github.pagehelper.PageHelper;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service("purchaseHistoryService")
public class PurchaseHistoryServiceImpl extends BaseService<PurchaseHistory> implements PurchaseHistoryService {

    @Override
    public List<PurchaseHistory> selectByPurchaseHistory(PurchaseHistory ch, int page, int rows) {
        Example example = new Example(PurchaseHistory.class);
        Example.Criteria criteria = example.createCriteria();
        
        if (StringUtil.isNotEmpty(ch.getPurchaseId())) {
            criteria.andEqualTo("purchaseId", ch.getPurchaseId());
        }
        if (StringUtil.isNotEmpty(ch.getUserId())) {
            criteria.andEqualTo("userId", ch.getUserId());
        }
        if (StringUtil.isNotEmpty(ch.getCommodityId())) {
            criteria.andEqualTo("commodityId", ch.getCommodityId());
        }
        if (StringUtil.isNotEmpty(ch.getMerchantId())) {
            criteria.andEqualTo("merchantId", ch.getMerchantId());
        }
        //分页查询
        PageHelper.startPage(page, rows);
        return selectByExample(example);
    }

}
