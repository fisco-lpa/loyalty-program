package com.fiscolpa.demo.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fiscolpa.demo.model.Account;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BeanToMap<K, V> {
	private BeanToMap() {  
    }
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> Bean2Map(Object javaBean) {  
        Map<K, V> ret = new HashMap<K, V>();  
        try {  
            Method[] methods = javaBean.getClass().getDeclaredMethods();  
            for (Method method : methods) {  
                if (method.getName().startsWith("get")) {  
                    String field = method.getName();  
                    field = field.substring(field.indexOf("get") + 3);  
                    field = field.toLowerCase().charAt(0) + field.substring(1);  
                    Object value = method.invoke(javaBean, (Object[]) null); 
                    if(value instanceof Integer){
                    	value = String.valueOf(value);
                    }
                    if(value instanceof Date){
                    	value = DateUtil.getDate((Date)value);
                    }
                    ret.put((K) field, (V) (null == value ? "" : value));  
                }  
            }  
        } catch (Exception e) {  
        }  
        return ret;  
    }
	
	@SuppressWarnings("unchecked")
	public static <K, V> List<Map<K, V>> Bean2MapList(Object beanList){
		List<Object> in = (List<Object>) beanList;
		List<Map<K, V>> out = new ArrayList<>();
		for (int i = 0; i < in.size(); i++) {
			out.add(Bean2Map(in.get(i)));
		}
		return out;
	}
	
	
	public static void main(String[] args) {  
		Account a = new Account();
		a.setAccountId("GM_5df53c5ea5be4c1b88cb3f79c6936cbe");
		a.setAccountBalance(888);
		a.setCreateTime(new Date());
		Account a1 = new Account();
		a1.setAccountId("GM_5df53c5ea5be4c1b88cb3f79c6936cbc");
		a1.setAccountBalance(8888);
		a1.setCreateTime(new Date());
		List<Account> l = new ArrayList<>();
		l.add(a);
		l.add(a1);
        System.out.println(JSONArray.fromObject(Bean2MapList(l)));  
    }  
}
