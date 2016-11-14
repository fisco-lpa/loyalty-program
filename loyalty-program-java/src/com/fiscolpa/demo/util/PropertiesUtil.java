package com.fiscolpa.demo.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	
    
    /**
     * 配置文件对象
     */
    private static Properties props=null;
    
    /**
     * 默认构造函数，用于sh运行，自动找到classpath下的config.properties。
     */
    public PropertiesUtil() throws IOException{
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
        props = new Properties();
        props.load(in);
        //关闭资源
        in.close();
    }
    
    /**
     * 根据key值读取配置的值
     * @param key key值
     * @return key 键对应的值 
     * @throws IOException 
     */
    public static String readValue(String key) throws IOException {
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
        props = new Properties();
        props.load(in);
        //关闭资源
        in.close();
        return  props.getProperty(key);
    }
    
    /**
     * 读取properties的全部信息
     * @throws FileNotFoundException 配置文件没有找到
     * @throws IOException 关闭资源文件，或者加载配置文件错误
     * 
     */
    public Map<String,String> readAllProperties() throws FileNotFoundException,IOException  {
        //保存所有的键值
        Map<String,String> map=new HashMap<String,String>();
        Enumeration en = props.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String Property = props.getProperty(key);
            map.put(key, Property);
        }
        return map;
    }

  
//    public static void main(String[] args) {
//        PropertiesUtil p;
//        try {
//            p = new PropertiesUtil();
//            System.out.println(p.readValue("BLOCK_CHAIN_URL"));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}
