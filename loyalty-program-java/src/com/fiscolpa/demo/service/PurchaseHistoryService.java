package com.fiscolpa.demo.service;

import java.util.List;

import com.fiscolpa.demo.model.PurchaseHistory;

public interface PurchaseHistoryService extends IService<PurchaseHistory> {

    /**
     * 根据条件分页查询
     *
     * @param country
     * @param page
     * @param rows
     * @return
     */
    List<PurchaseHistory> selectByPurchaseHistory(PurchaseHistory ch, int page, int rows);

}
