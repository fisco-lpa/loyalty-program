package com.fiscolpa.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.AccountService;
import com.fiscolpa.demo.service.PointsTransationService;
import com.fiscolpa.demo.util.PointsTransactionEnum;
import com.fiscolpa.demo.vo.AccountVo;
import com.fiscolpa.demo.vo.PointsTransationDetailVo;
import com.fiscolpa.demo.vo.PointsTransationVo;

/**
 * 授信方
 * @author Administrator
 *
 */
@RestController
public class CreditPartyController {
	
	@Autowired
	private PointsTransationService pointsTransationService;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 商家账户类型
	 */
	public static final String MERCHANT_ACCOUNT_TYPE = "2";
	
	/**
	 * 授信积分操作
	 * @param pointsTransation
	 * @param session
	 * @return
	 */
    @RequestMapping(value = "/creditParty/credit", method = RequestMethod.POST)
    public String credit(PointsTransation pointsTransation,HttpSession session) {
    	PointsUser user = (PointsUser)session.getAttribute("user");
    	pointsTransation.setCreateUser(user.getUserId());
    	pointsTransation.setRollOutAccount(user.getAccountId());
    	pointsTransationService.addCredit(pointsTransation,(String) session.getAttribute(PointsTransactionEnum.FABRIC_SWITCH.getBeginning()));
    	return "ok";
    }
    /**
     * 授信中心页面获取用户信息
     */
    @RequestMapping(value = "/creditParty/index/getData")
    public Map<String,Object> getData(HttpSession session){
    	PointsUser user = (PointsUser)session.getAttribute("user");
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("user", user);
    	map.put("img",String.valueOf(session.getAttribute("userimg")));
    	return map;
    }
    /**
     * 获取商户列表
     * @return
     */
    @RequestMapping(value = "/creditParty/getMerchants", method = RequestMethod.GET)
    public List<AccountVo> getMerchants(){
    	List<AccountVo> merchants = accountService.getAllMerchant(MERCHANT_ACCOUNT_TYPE);
    	return merchants;
    }
    
    /**
     * 获取授信列表数据
     * @param session
     * @param pointsTransationVo
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/creditParty/getCreditList", method = RequestMethod.GET)
    public @ResponseBody List<PointsTransationDetailVo> getCreditList(HttpSession session){
    	PointsUser user = (PointsUser)session.getAttribute("user");
    	PointsTransationVo pointsTransationVo = new PointsTransationVo();
    	pointsTransationVo.setRollOutAccount(user.getAccountId());
        List<PointsTransationDetailVo> list = pointsTransationService.getCreditPartyCreditDetailList(pointsTransationVo);
        return list;
    }
    
    /**
     * 授信方 承兑查询页面
     * @return
     */
    @RequestMapping(value = "/creditParty/getAcceptList", method = RequestMethod.GET)
    public List<PointsTransationDetailVo> getAcceptList(HttpSession session,PointsTransationDetailVo pointsTransationDetailVo,Long time) {
    	//  获取userId
    	PointsUser user = (PointsUser)session.getAttribute("user");
    	//获取账户信息
    	pointsTransationDetailVo.setRollInAccount(user.getAccountId());
    	pointsTransationDetailVo.setCreditParty(user.getAccountId());
    	if(null != time){
    		pointsTransationDetailVo.setCreditCreateTime(new Date(time));
    	}
        List<PointsTransationDetailVo> list = pointsTransationService.queryPointsTransationDetail(pointsTransationDetailVo);
        return list;
    }
    
}
