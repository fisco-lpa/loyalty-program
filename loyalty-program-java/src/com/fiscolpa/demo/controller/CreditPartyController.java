package com.fiscolpa.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fiscolpa.demo.model.Account;
import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.AccountService;
import com.fiscolpa.demo.service.PointsTransationService;
import com.fiscolpa.demo.vo.AccountVo;
import com.fiscolpa.demo.vo.PointsTransationDetailVo;
import com.fiscolpa.demo.vo.PointsTransationVo;
import com.github.pagehelper.PageInfo;

/**
 * 授信方
 * @author Administrator
 *
 */
@Controller
public class CreditPartyController {
	
	@Autowired
	private PointsTransationService pointsTransationService;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 商家账户类型
	 */
	public static final String MERCHANT_ACCOUNT_TYPE = "2";
	
	private String redirect_list = "redirect:goCreditQueryPage";
	
    /**
     * 授信
     * @param pointsTransation
     */
    @RequestMapping(value = "/creditParty/credit", method = RequestMethod.POST)
    public ModelAndView credit(PointsTransation pointsTransation,HttpSession session) {
    	//授信
    	PointsUser user = (PointsUser)session.getAttribute("user");
    	
    	pointsTransation.setCreateUser(user.getUserId());
//    	pointsTransation.setCreateUser("3");
//    	pointsTransation.setRollInAccount("2");
//    	pointsTransation.setRollOutAccount("3");
    	pointsTransationService.addCredit(pointsTransation);
    	//返回到授信查询页面
        ModelAndView result = new ModelAndView(redirect_list);//TODO 
//        result.addObject("user",user);//TODO userId
        return result;
    }
    /**
     * 授信页面
     * @param pointsTransation
     */
    @RequestMapping(value = "/creditParty/goCreditPage", method = RequestMethod.GET)
    public ModelAndView goCreditPage(String accountId) {
    	ModelAndView result = new ModelAndView("credit_party_credit");
    	result.addObject("rollOutAccount", "2");
    	List<AccountVo> merchants = accountService.getAllMerchant(MERCHANT_ACCOUNT_TYPE);
    	result.addObject("merchants", merchants);
    	return result;
    }
    
    /**
     * 授信方 授信查询页面
     * @return
     */
    @RequestMapping(value = "/creditParty/goCreditQueryPage", method = RequestMethod.GET)
    public ModelAndView goCreditQueryPage(HttpSession session,PointsTransationVo pointsTransationVo, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	//TODO  获取userId
    	PointsUser user = (PointsUser)session.getAttribute("user");
//    	String userId = user.getUserId();
    	//获取账户信息
    	Account account = accountService.getAccount("2");//TODO 
    	ModelAndView result = new ModelAndView("credit_party_index2");
    	result.addObject("account",account);
    	result.addObject("user",user);
    	pointsTransationVo.setRollOutAccount("2");
        List<PointsTransationDetailVo> list = pointsTransationService.getCreditPartyCreditDetailList(pointsTransationVo, page, rows);
        result.addObject("pageInfo", new PageInfo<PointsTransationDetailVo>(list));
        result.addObject("queryParam", pointsTransationVo);
        result.addObject("page", page);
        result.addObject("rows", rows);
        return result;
    }
    
    /**
     * 授信方 承兑查询页面
     * @return
     */
    @RequestMapping(value = "/creditParty/goAcceptQueryPage", method = RequestMethod.GET)
    public ModelAndView goAcceptQueryPage(HttpSession session,PointsTransationVo pointsTransationVo, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	//TODO  获取userId
    	PointsUser user = (PointsUser)session.getAttribute("user");
//    	String userId = user.getUserId();
    	//获取账户信息
    	Account account = accountService.getAccount("2");
    	ModelAndView result = new ModelAndView("credit_party_accept2");
    	result.addObject("account",account);
    	result.addObject("user",user);
    	
    	pointsTransationVo.setRollInAccount(account.getAccountId());
        List<PointsTransationDetailVo> list = pointsTransationService.queryPointsTransationDetail(pointsTransationVo, page, rows);
        result.addObject("pageInfo", new PageInfo<PointsTransationDetailVo>(list));
        result.addObject("queryParam", pointsTransationVo);
        result.addObject("page", page);
        result.addObject("rows", rows);
        return result;
    }
    
//    /**
//     * 授信方 授信查询数据列表
//     * @return
//     */
//    @RequestMapping(value = "/creditParty/getCreditPartyCreditDetailList", method = RequestMethod.POST)
//    @ResponseBody
//    public List<PointsTransationDetailVo> getCreditPartyCreditDetailList(String accountId,PointsTransationVo pointsTransationVo) {
//    	
//    	List<PointsTransationDetailVo> list = pointsTransationService.getCreditPartyCreditDetailList(pointsTransationVo);
//    	return list;
//    }
    
//    /**
//     * 授信方 		授信查询数据列表
//     * @param pointsTransationVo
//     * @param page
//     * @param rows
//     * @return
//     */
//    @RequestMapping(value = "/creditParty/getCreditPartyCreditDetailList", method = RequestMethod.POST)
//    public ModelAndView getCreditPartyCreditDetailList(PointsTransationVo pointsTransationVo, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
//        ModelAndView result = new ModelAndView("credit_party_accept2");
//        List<PointsTransationDetailVo> list = pointsTransationService.getCreditPartyCreditDetailList(pointsTransationVo, page, rows);
//        result.addObject("pageInfo", new PageInfo<PointsTransationDetailVo>(list));
//        result.addObject("queryParam", pointsTransationVo);
//        result.addObject("page", page);
//        result.addObject("rows", rows);
//        return result;
//    }
    
//    /**
//     * 授信方 承兑查询数据列表
//     * @return
//     */
//    @RequestMapping(value = "/creditParty/getCreditPartyAcceptDetailList", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView getCreditPartyAcceptDetailList(PointsTransationVo pointsTransationVo, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
//    	ModelAndView result = new ModelAndView("credit_party_accept2");
//    	List<PointsTransationDetailVo> list = pointsTransationService.queryPointsTransationDetail(pointsTransationVo, page, rows);
//    	result.addObject("pageInfo", new PageInfo<PointsTransationDetailVo>(list));
//        result.addObject("queryParam", pointsTransationVo);
//        result.addObject("page", page);
//        result.addObject("rows", rows);
//        return result;
//    }
    
//    /**
//     * 获取商家列表
//     * @param accountType
//     * @return
//     */
//    @RequestMapping(value = "/creditParty/getMerchants", method = RequestMethod.POST)
//    @ResponseBody
//    public List<AccountVo> getMerchants(String accountType) {
//    	List<AccountVo> list = accountService.getAllMerchant(accountType);
//    	return list;
//    }


}
