package com.fiscolpa.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiscolpa.demo.pointsInterface.ChainCode;
import com.fiscolpa.demo.pointsInterface.CtorMsg;
import com.fiscolpa.demo.pointsInterface.Params;
import com.fiscolpa.demo.pointsInterface.RequestBean;

/**
 * 常用工具类
 * @author heqingping
 *
 */
public class CommonUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
			
	/**
	 * 组装与区块链系统交互信息的JSON字符串
	 * @param method 区块链执行方法(deploy,invoke,query其中一种)
	 * @param chaincodeId chaincodeID
	 * @param function 执行各交易的实际方法
	 * @param paramJsonString 实际参数BEAN转成的JSON串，不BASE64加密
	 * @param secureContext 区块链操作用户
	 * @return 
	 */
	public static String createJson(String method,String chaincodeId,String function,String paramJsonString,String secureContext){
		ChainCode chaincode = new ChainCode();
		chaincode.setName(chaincodeId);//每个资产的chaincodeID
		
		RequestBean bean = null;
		try {
			CtorMsg cm = new CtorMsg();
			cm.setFunction(function);//根据每个接口不同，调用的方法不同
			if(paramJsonString != null && !"".equalsIgnoreCase(paramJsonString)){
				cm.setArgs(new String[]{Base64Util.encode(paramJsonString.getBytes("utf-8"))});//内部参数，需要通过BASE64编码
			}
			Params p = new Params();
			p.setChaincodeID(chaincode); 
			p.setCtorMsg(cm);
			p.setSecureContext(secureContext);//区块链操作用户名称
			
			bean = new RequestBean();
			bean.setMethod(method);//deploy,invoke,query
			bean.setParams(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sendJson = JsonUtil.beanToJson(bean);
		logger.info("-------------------------------------------------------------------------");
		logger.info("-----method："+method);
		logger.info("-----chaincodeID："+chaincodeId);
		logger.info("-----function："+function);
		logger.info("-----secureContext："+secureContext);
		logger.info("-----paramJsonString："+paramJsonString);
		logger.info("-----sendJson："+sendJson);
		logger.info("-------------------------------------------------------------------------");
		return sendJson;
	}
	

}
