package com.fiscolpa.demo.util;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fiscolpa.demo.pointsInterface.InvokeResponseBean;
import com.fiscolpa.demo.pointsInterface.RegistBean;
import com.fiscolpa.demo.pointsInterface.ResponseBean;


public class HttpTool {
	
	private static Logger logger = LoggerFactory.getLogger(HttpTool.class);
	
	/**	
	 * 组装链接post到底层
	 * @param rest
	 * @param data
	 * @return
	 */
	public static String post(String rest,String data)  {
        String response = null; 
		try {
			HttpPost method = new HttpPost(rest);  
	        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	        if(data!=null&&data!=""){
	        	StringEntity entity = new StringEntity(data);
		        entity.setContentType("application/x-www-form-urlencoded"); 
		        method.setEntity(entity);  
	        }
	        HttpResponse httpresponse = httpClient.execute(method);
            response = EntityUtils.toString(httpresponse.getEntity());   

		} catch (Exception e) {
			e.printStackTrace();
		}
        return response;  
    } 
		
	public static String get(String rest)  { 
        String response = null; 
		try {
			HttpGet method = new HttpGet(rest);  
	        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	        HttpResponse httpresponse = httpClient.execute(method);
            response = EntityUtils.toString(httpresponse.getEntity());   
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("get response"+response);
        return response;  
    } 
	
	/**
	 * 区块链账户登录注册
	 * @param enrollId 
	 * @param enrollSecret
	 * @return
	 * @throws IOException 
	 */
	public static boolean register(String enrollId,String enrollSecret) throws IOException{
		//登陆用户
		RegistBean rb = new RegistBean();
		enrollId="jim";
		enrollSecret="6avZQLwcUe9b";
		rb.setEnrollId(enrollId);
		rb.setEnrollSecret(enrollSecret);
		String requestJsonData = JsonUtil.beanToJson(rb);
		String response = HttpTool.post(PropertiesUtil.readValue("BLOCK_CHAIN_URL")+"/registrar", requestJsonData);
		if(response != null && !"".equals(response)){
			System.out.println("true");
			if(response.contains("OK") && response.contains("successful") || response.contains("already logged in")){
				System.out.println("true1");
				return true;
			}else{
				System.out.println("false1");
				return false;
			}
		}
		System.out.println("false");
		return false; 
	}
	/**
	 * 交易
	 * @param jsonData
	 * @param beanCalss
	 * @return
	 * @throws IOException 
	 */
	public static <T> T send(String jsonData,Class<T> beanCalss) throws IOException{
		String response = HttpTool.post(PropertiesUtil.readValue("BLOCK_CHAIN_URL")+"/chaincode", jsonData);
		System.out.println("response:"+response);
		logger.info("Fabric{send}返回数据："+response);
		if(response != null && !"".equals(response)){
			System.out.println("response:true");
			if(!response.contains("error")){
				return JsonUtil.jsonToBean(response, beanCalss);
			}
		}
		System.out.println("response:false");
		return null; 
	}
	
	/**
	 * 获取区块交易详情
	 * @param transationId
	 * @param beanCalss
	 * @return
	 * @throws IOException 
	 */
	public static <T> T get(String transationId,Class<T> beanCalss) throws IOException{
		System.out.println("to get");
		String response = HttpTool.get(PropertiesUtil.readValue("BLOCK_CHAIN_URL")+"/transactions"+"/"+transationId);
		System.out.println("get response"+response);
		logger.info("Fabric{get}返回数据："+response);
		if(response != null && !"".equals(response)){
			return JsonUtil.jsonToBean(response, beanCalss);
		}
		return null; 
	}
	
	/**
	 * 获取当前区块高度
	 * @param beanCalss
	 * @return
	 * @throws IOException 
	 */
	public static <T> T getHeight(Class<T> beanCalss) throws IOException{
		String response = HttpTool.get(PropertiesUtil.readValue("BLOCK_CHAIN_URL")+"/chain");
		logger.info("Fabric{getHeight}返回数据："+response);
		if(response != null && !"".equals(response)){
			return JsonUtil.jsonToBean(response, beanCalss);
		}
		return null; 
	}
	
	/**
	 * 获取指定区块的交易详情
	 * @param beanCalss
	 * @param height
	 * @return
	 * @throws IOException 
	 */
	public static <T> T getBlockInfo4Height(Class<T> beanCalss, int height) throws IOException{
		String response = HttpTool.get(PropertiesUtil.readValue("BLOCK_CHAIN_URL")+"/chain/blocks/" + height);
		logger.debug("Fabric{getHeight}返回数据："+response);
		if(response != null && !"".equals(response) && !response.contains("Error")){
			return JsonUtil.jsonToBean(response, beanCalss);
		}
		return null; 
	}
	/**
	 * 接口
	 * @param obj  传递的参数
	 * @param method  交易的类型（query or invoke）
	 * @param function  方法名
	 * @return
	 * @throws IOException 
	 */
	public static boolean sendToFabric(String obj,String method,String function) throws IOException{
		//登陆注册区块链
		boolean isSucc = HttpTool.register("jim", "6avZQLwcUe9b");
		if(isSucc){
			//获取chaincodeId 
			String chaincodeId = PropertiesUtil.readValue("Chaincode_Id");
			//获取区块链用户名称
			String blockName = "jim";
			String sendJson = CommonUtils.createJson(method, chaincodeId, function, obj, blockName);
			ResponseBean responseBean = HttpTool.send(sendJson, ResponseBean.class);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			InvokeResponseBean invokeBean = HttpTool.get(responseBean.getResult().getMessage(), InvokeResponseBean.class);
			
			long beginTime = System.currentTimeMillis();//开始系统时间
			String txId = invokeBean.getTxid();
			
			logger.info("-----------------------------------------------------------------------------");
			logger.info("----------Message："+responseBean.getResult().getMessage());
			logger.info("----------TxId："+invokeBean.getTxid());
			logger.info("-----------------------------------------------------------------------------");
			
			while(true){
				if(txId == null || "".equals(txId)){
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					long checkTime = System.currentTimeMillis();
					if((checkTime-beginTime) <= (300*1000)){
						invokeBean = HttpTool.get(responseBean.getResult().getMessage(), InvokeResponseBean.class);
						if(invokeBean.getTxid() != null && !"".equals(invokeBean.getTxid())){
							break;
						}
					}
				}else{
					break;
				}
			}
			
			logger.info("-----------------------------------------------------------------------------");
			logger.info("----------Message："+responseBean.getResult().getMessage());
			logger.info("----------TxId："+invokeBean.getTxid());
			logger.info("-----------------------------------------------------------------------------");
			
			if (responseBean.getResult().getMessage().equals(invokeBean.getTxid())) {
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}

}

