package com.fiscolpa.demo.controller;

import com.fiscolpa.demo.model.Commodity;
import com.fiscolpa.demo.model.CommonImg;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.model.PurchaseHistory;
import com.fiscolpa.demo.service.CommodityService;
import com.fiscolpa.demo.service.CommonImgService;
import com.fiscolpa.demo.service.PurchaseHistoryService;
import com.fiscolpa.demo.util.PointsTransactionEnum;
import com.fiscolpa.demo.util.UUIDGenerator;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Controller
public class CommodityController {

    @Autowired
    private CommodityService cs;
    
    @Autowired
    private CommonImgService cis;
    
    @Autowired
    private PurchaseHistoryService phs;

    @RequestMapping(value="/getCommodityList")
    public @ResponseBody Map<String, Object> getCommodityList(HttpSession session,Commodity commodity, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	Map<String,Object> map = new HashMap<>();
    	PointsUser currentUser = (PointsUser) session.getAttribute("user");
    	commodity.setAccountId(currentUser.getAccountId());
        List<Commodity> countryList = cs.selectByCommodity(commodity, page, rows);
        if(StringUtil.isNotEmpty(commodity.getCommodityId())){
        	CommonImg ci = new CommonImg();
        	ci.setAssociateId(commodity.getCommodityId());
        	ci.setImgType(PointsTransactionEnum.IMG_COMMODITY.getSign());
        	ci = cis.selectOne(ci);
        	if(ci!=null){
        		countryList.get(0).setImgId(ci.getImgId());
            	countryList.get(0).setImgBase(ci.getImgBase());
        	}
        }else{
        	for (int i = 0; i < countryList.size(); i++) {
        		Commodity cd = countryList.get(i);
            	CommonImg ci = new CommonImg();
            	ci.setAssociateId(cd.getCommodityId());
            	ci.setImgType(PointsTransactionEnum.IMG_COMMODITY.getSign());
            	ci = cis.selectOne(ci);
            	if(ci!=null){
	    	        cd.setImgId(ci.getImgId());
	    	        cd.setImgBase(ci.getImgBase());
            	}
            	PurchaseHistory ph = new PurchaseHistory();
            	ph.setCommodityId(cd.getCommodityId());
            	cd.setSalesCount(phs.selectCount(ph));
    		}
        }
        map.put("pageInfo", new PageInfo<Commodity>(countryList));
        map.put("queryParam", commodity);
        map.put("page", page);
        map.put("rows", rows);
        return map;
    }
    
    
    @RequestMapping(value="/getCommodityAll")
    public @ResponseBody Map<String, Object> getCommodityAll(HttpSession session,Commodity commodity, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	Map<String,Object> map = new HashMap<>();
        List<Commodity> countryList = cs.selectByCommodity(commodity, page, rows);
        for (int i = 0; i < countryList.size(); i++) {
        		Commodity cd = countryList.get(i);
            	CommonImg ci = new CommonImg();
            	ci.setAssociateId(cd.getCommodityId());
            	ci.setImgType(PointsTransactionEnum.IMG_COMMODITY.getSign());
            	ci = cis.selectOne(ci);
            	if(ci!=null){
	    	        cd.setImgId(ci.getImgId());
	    	        cd.setImgBase(ci.getImgBase());
            	}
        }
        map.put("pageInfo", new PageInfo<Commodity>(countryList));
        map.put("queryParam", commodity);
        map.put("page", page);
        map.put("rows", rows);
        return map;
    }
    
    
    @Transactional
    @RequestMapping(value = "/saveCommodity", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object>  save(HttpSession session,@ModelAttribute("commodity") Commodity commodity) {
    	PointsUser currentUser = (PointsUser) session.getAttribute("user");
    	Map<String,Object> map = new HashMap<>();
    	int state = 0;
        if (StringUtil.isNotEmpty(commodity.getCommodityId())) {
        	state = cs.updateNotNull(commodity);
        	
        	CommonImg ci = new CommonImg();
        	ci.setImgId(commodity.getImgId());
        	ci.setImgBase(commodity.getImgBase());
        	cis.updateNotNull(ci);
        } else {
        	String uuid = PointsTransactionEnum.GOODS.getBeginning()+UUIDGenerator.getUUID();
        	commodity.setCommodityId(uuid);
        	commodity.setCreateTime(new Date());
        	commodity.setAccountId(currentUser.getAccountId());
        	state = cs.save(commodity);
        	
        	CommonImg ci = new CommonImg();
        	ci.setImgId(PointsTransactionEnum.IMG_COMMODITY.getBeginning()+UUIDGenerator.getUUID());
        	ci.setAssociateId(uuid);
        	ci.setImgType(PointsTransactionEnum.IMG_COMMODITY.getSign());
        	ci.setImgBase(commodity.getImgBase());
        	cis.save(ci);
        }
        map.put("state", state);
        return map;
    }

    @RequestMapping("/deleteCommodity")
    public @ResponseBody Map<String, Object>  delete(Integer id) {
    	Map<String,Object> map = new HashMap<>();
    	map.put("state",  cs.delete(id));
        return map;
    }
    
    @RequestMapping(value="/getPurchaseHistory")
    public @ResponseBody Map<String, Object> getPurchaseHistory(HttpSession session,PurchaseHistory purchaseHistory, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	Map<String,Object> map = new HashMap<>();
    	PointsUser currentUser = (PointsUser) session.getAttribute("user");
    	
    	if(StringUtil.isEmpty(purchaseHistory.getType())){
    		return null;
    	}else if("0".equals(purchaseHistory.getType())){
    		purchaseHistory.setUserId(currentUser.getAccountId());
    	}else if("1".equals(purchaseHistory.getType())){
    		purchaseHistory.setMerchantId(currentUser.getAccountId());
    	}
    	List<PurchaseHistory> phList = phs.selectByPurchaseHistory(purchaseHistory, page, rows);
    	for (int i = 0; i < phList.size(); i++) {
    		PurchaseHistory ph = phList.get(0);
        	CommonImg ci = new CommonImg();
        	ci.setAssociateId(ph.getCommodityId());
        	ci.setImgType(PointsTransactionEnum.IMG_COMMODITY.getSign());
        	ci = cis.selectOne(ci);
        	if(ci!=null){
        		ph.setImgBase(ci.getImgBase());
        	}
		}
    	map.put("pageInfo", new PageInfo<PurchaseHistory>(phList));
        map.put("queryParam", purchaseHistory);
        map.put("page", page);
        map.put("rows", rows);
        return map;
    }

}
