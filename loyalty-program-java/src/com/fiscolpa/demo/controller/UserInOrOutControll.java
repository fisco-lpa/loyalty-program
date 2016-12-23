package com.fiscolpa.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fiscolpa.demo.model.PointsTransation;
import com.fiscolpa.demo.model.PointsTransationDetail;
import com.fiscolpa.demo.model.PointsUser;
import com.fiscolpa.demo.service.PointsTDService;
import com.fiscolpa.demo.service.PointsTService;
import com.fiscolpa.demo.service.UserAccountService;
import com.github.pagehelper.PageInfo;


//import com.foresealife.tpis.model.FieldConfig;


/**
 * 接口字段转换服务配置Controller
 * @author Administrator
 *
 */
@Controller
public class UserInOrOutControll {
	
	//private static final Logger logger = LoggerFactory.getLogger(UserInOrOutControll.class);
	
	/*用户进账*/
	@Autowired  
    private PointsTDService pointsTransationDetailService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	/*用户消费*/
	@Autowired
	private PointsTService pointsTService;
	
	
	/**
	 * 用户进账
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping(value = {"/userIn"})
    public ModelAndView getList(HttpServletRequest request,PointsTransationDetail pointsTransationDetail, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	PointsUser currentUser = (PointsUser)request.getSession().getAttribute("user");
    	String userName = currentUser.getUserName();
    	String phoneNumber = currentUser.getPhoneNumber();//手机号
    	ModelAndView mv = new ModelAndView("userIn");
    	
		//获取用户账户ID
    	String accuntId = userAccountService.getAccountByUserName(userName);
    	
    	String sel = request.getParameter("sel"); //过期标记
    	if ("wgq".equals(sel)){
    		pointsTransationDetail.setStatus("0");//未过期
    	}else if ("gq".equals(sel)){
    		pointsTransationDetail.setCreateUser("1");//过期
    	}

    	String startTime = request.getParameter("starttime");
    	pointsTransationDetail.setUpdateUser(startTime);//发放起始日期
    	String endTime = request.getParameter("endtime");
    	pointsTransationDetail.setMerchant(endTime);//发放起始日期
    	
        pointsTransationDetail.setRollInAccount(accuntId);
        //List<PointsTransationDetail> pointsTransationDetailList = pointsTransationDetailService.selectByPointsTransationDetail(pointsTransationDetail, page, rows);
        List<PointsTransationDetail> pointsTransationDetailList = pointsTransationDetailService.selectByAccount(pointsTransationDetail, page, rows);
        mv.addObject("pageInfo", new PageInfo<PointsTransationDetail>(pointsTransationDetailList));
        mv.addObject("queryParam", pointsTransationDetail);
        mv.addObject("page", page);
        mv.addObject("rows", rows);
        
		mv.addObject("userName",userName); 
		mv.addObject("phoneNumber",phoneNumber);//手机号
		
		
		//总进账积分
		Integer in = pointsTransationDetailService.sumByRollInAccount(accuntId);
		mv.addObject("rollInAcount",in);
		//总消费积分
		Integer out = pointsTransationDetailService.sumByRollOutAccount(accuntId);
		mv.addObject("rollOutAccount", out);
		//积分余额
		Integer accountBalance= userAccountService.sumByPrimaryKey(accuntId);
		mv.addObject("accountBalance", accountBalance);
		
        return mv;
    }
    
    
    
    
    @RequestMapping(value = {"/userHtml"})
    public @ResponseBody Map<String,Object> userHtml(HttpServletRequest request,PointsTransationDetail pointsTransationDetail, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	Map<String,Object> map = new HashMap<>();
    	PointsUser currentUser = (PointsUser)request.getSession().getAttribute("user");
    	
    	map.put("userName",currentUser.getUserName()); 
		map.put("phoneNumber",currentUser.getPhoneNumber());//手机号

		//总进账积分
		Integer in = pointsTransationDetailService.sumByRollInAccount(currentUser.getAccountId());
		map.put("rollInAcount",in);
		//总消费积分
		Integer out = pointsTransationDetailService.sumByRollOutAccount(currentUser.getAccountId());
		map.put("rollOutAccount", out);
		//积分余额
		Integer accountBalance= userAccountService.sumByPrimaryKey(currentUser.getAccountId());
		map.put("accountBalance", accountBalance);
		map.put("img",String.valueOf(request.getSession().getAttribute("userimg")));
    	return map;
    }
    
    
    /**
	 * 用户进账
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping(value = {"/userInHtml"})
    public @ResponseBody Map<String,Object> userInHtml(HttpServletRequest request,PointsTransationDetail pointsTransationDetail, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	Map<String,Object> map = new HashMap<>();
    	PointsUser currentUser = (PointsUser)request.getSession().getAttribute("user");
		//获取用户账户ID
    	String accuntId = currentUser.getAccountId();
    	
    	String sel = request.getParameter("sel"); //过期标记
    	if ("wgq".equals(sel)){
    		pointsTransationDetail.setStatus("0");//未过期
    	}else if ("gq".equals(sel)){
    		pointsTransationDetail.setCreateUser("1");//过期
    	}

    	String startTime = request.getParameter("starttime");
    	pointsTransationDetail.setUpdateUser(startTime);//发放起始日期
    	String endTime = request.getParameter("endtime");
    	pointsTransationDetail.setMerchant(endTime);//发放起始日期
    	
        pointsTransationDetail.setRollInAccount(accuntId);
        List<PointsTransationDetail> pointsTransationDetailList = pointsTransationDetailService.selectByAccount(pointsTransationDetail, page, rows);
        map.put("list", pointsTransationDetailList);
        map.put("queryParam", pointsTransationDetail);
        map.put("page", page);
        map.put("rows", rows);
	
        return map;
    }
	
    /**
	 * 用户消费
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping(value = {"/userOut"})
    public ModelAndView outList(HttpServletRequest request,PointsTransation pointsTransation, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	PointsUser currentUser = (PointsUser)request.getSession().getAttribute("user");
    	String userName = currentUser.getUserName();
    	String phoneNumber = currentUser.getPhoneNumber();//手机号
    	ModelAndView mv = new ModelAndView("userOut");
    	
		//获取用户账户ID
    	String accuntId = userAccountService.getAccountByUserName(userName);
    	
    	String startTime = request.getParameter("starttime");
    	pointsTransation.setUpdateUser(startTime);//发放起始日期
    	String endTime = request.getParameter("endtime");
    	pointsTransation.setCreateUser(endTime);//发放起始日期
    	
        pointsTransation.setRollOutAccount(accuntId);
        List<PointsTransation> pointsTransationList = pointsTService.selectByAccount(pointsTransation, page, rows);
        mv.addObject("pageInfo", new PageInfo<PointsTransation>(pointsTransationList));
        mv.addObject("queryParam", pointsTransationList);
        mv.addObject("page", page);
        mv.addObject("rows", rows);
        
		mv.addObject("userName",userName);
		mv.addObject("phoneNumber",phoneNumber);//手机号
		
		//总进账积分
		Integer in = pointsTransationDetailService.sumByRollInAccount(accuntId);
		mv.addObject("rollInAcount",in);
		//总消费积分
		Integer out = pointsTransationDetailService.sumByRollOutAccount(accuntId);
		mv.addObject("rollOutAccount", out);
		//积分余额
		Integer accountBalance= userAccountService.sumByPrimaryKey(accuntId);
		mv.addObject("accountBalance", accountBalance);
		
        return mv;
    }
    
    
    @RequestMapping(value = {"/userOutHtml"})
    public @ResponseBody Map<String,Object> outListHtml(HttpServletRequest request,PointsTransation pointsTransation, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "10") int rows) {
    	PointsUser currentUser = (PointsUser)request.getSession().getAttribute("user");
    	Map<String,Object> map = new HashMap<>();
    	//获取用户账户ID
    	String accuntId = currentUser.getAccountId();
    	
    	String startTime = request.getParameter("starttime");
    	pointsTransation.setUpdateUser(startTime);//发放起始日期
    	String endTime = request.getParameter("endtime");
    	pointsTransation.setCreateUser(endTime);//发放起始日期
    	
        pointsTransation.setRollOutAccount(accuntId);
        List<PointsTransation> pointsTransationList = pointsTService.selectByAccount(pointsTransation, page, rows);
        map.put("list", pointsTransationList);
        map.put("queryParam", pointsTransationList);
        map.put("page", page);
        map.put("rows", rows);
    	return map;
    }
}
