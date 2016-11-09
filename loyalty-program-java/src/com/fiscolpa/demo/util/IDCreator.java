package com.fiscolpa.demo.util;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : BRS <br>
 * JDK version used : jdk1.6.0_22 <br>
 * Description : 编号生成工具类 <br>
 * Comments Name : IDCreator <br>
 * author : YY <br>
 * date : 2010-12-16<br>
 * Version : 1.00 <br>
 * editor : <br>
 * editorDate : <br>
 */
public class IDCreator {
	/**
	 * 私有化构造方法
	 */
	private IDCreator() {

	}

	/**
	 * 配置属性
	 */
	private static Properties props;

	/**
	 * ID 配置属性文件名
	 */
	private static final String ID_FILE_NAME = "id-rules";

	// 实例
	private static IDCreator instance = null;

	/**
	 * 获取实例
	 * 
	 * @return
	 * @throws Exception
	 */
	public static IDCreator getInstance() {
		if (null == instance) {
			instance = new IDCreator();
			// 初始化属性配置文件
			ResourceBundle resource = ResourceBundle.getBundle(ID_FILE_NAME,
					Locale.getDefault());
			Enumeration<String> e = resource.getKeys();
			props = new Properties();
			while (e.hasMoreElements()) {
				String s = e.nextElement();
				props.setProperty(s, resource.getString(s));
			}
		}
		return instance;
	}

	/**
	 * 上一次获取时间的毫秒数
	 */
	private static long lastID = 0;

	/**
	 * 按照系统时间毫秒数生成的唯一编号
	 * 
	 * @return 按当前系统时间生成的唯一编号字符串
	 */
	public synchronized static String createTimeTokenID() {
		long time = System.currentTimeMillis();
		time = time <= lastID ? lastID + 1 : time;
		lastID = time;
		return String.valueOf(time);
	}

	/**
	 * 构建所需规则的ID序号 <br>
	 * 
	 * @param key
	 * <br>
	 * @param value
	 * <br>
	 * @return <br>
	 * @throws Exception
	 * <br>
	 */
	public synchronized String getID(String key, String value) throws Exception {
		if (null == key) {
			return "";
		}
		if (null == value) {
			value = "";
		}

		String[] values = value.split(",");
		String pattern = props.getProperty(key);
		pattern = MessageFormat.format(pattern, values);
		// ==================== 处理 [DATE] 格式 =========================
		pattern = formatDate(key, pattern);
		String cache = pattern;
		// ====================== 解析 [NUM] 格式 ======================
		pattern = formatNum(key, pattern, pattern.equals(dateCache.get(key)));
		dateCache.put(key, cache);
		pattern = pattern.trim();
		return pattern;
	}

	/**
	 * 处理ID配置中的日期部分
	 * 
	 * @param key
	 * @param pattern
	 * @return 返回当前日期与上次生成同一种的KEY的ID时的日期是否相同
	 * @throws Exception
	 */
	private String formatDate(String key, String pattern) throws Exception {
		if (pattern == null)
			return null;
		// ==================== 处理 [DATE] 格式 =========================
		int startDATE = pattern.indexOf("[DATE");
		int endDATE = pattern.indexOf("]", startDATE);
		if (startDATE > -1 && endDATE > -1) {
			String temp = pattern.substring(startDATE + 1, endDATE);
			String date = temp.replaceAll("DATE", "");
			String[] dates = date.split("-");
			if (dates.length != 2) {
				// 日期时间格式不对
				throw new Exception("[ IDCreator buildMaxID ] 日期时间按格式不对 ");
			} else {
				// 格式化时间
				date = this.formatDateTime(dates[0], dates[1]);
			}
			// 替换日期时间部分
			pattern = pattern.replaceAll("\\[" + temp + "\\]", date);
		}
		return pattern;
	}

	/**
	 * 处理ID配置中的数字部分
	 * 
	 * @param key
	 * @param pattern
	 * @param flag
	 *            此次生成ID的时间与上次是否相同, flag为false时与上次不同，则数字部分重新从1开始累计
	 * @throws Exception
	 */
	private String formatNum(String key, String pattern, boolean flag)
			throws Exception {
		if (pattern == null)
			return null;

		int startNUM = pattern.indexOf("[NUM");
		int endNUM = pattern.indexOf("]", startNUM);
		if (startNUM > -1 && endNUM > -1) {
			String temp = pattern.substring(startNUM + 1, endNUM);
			int length = Integer.parseInt(temp.replaceAll("NUM", "")); // 数字部分的长度
			String num;

			num = numCache.get(key);
			if (num == null || !flag)
				num = "1";
			else
				num = String.valueOf(Integer.parseInt(num) + 1);
			if (num.length() > length)
				throw new Exception("编号超出最大范围，请等待一秒钟后再次操作！");
			numCache.put(key, num);
			num = fill("Left", num, "0", length);

			// 替换数字序号部分
			pattern = pattern.replaceAll("\\[" + temp + "\\]", num);
		}
		return pattern;
	}

	public static String fill(String fillType, String src, String fillString,
			int length) throws Exception {
		if ((fillType == null) || (fillString == null) || (src == null)) {
			throw new Exception(" [ At least one of parameters is null! ] ");
		}
		if (("Left".equalsIgnoreCase(fillType))
				&& ("Right".equalsIgnoreCase(fillType))) {
			throw new Exception(" [ The fillType must be Right or Left! ] ");
		}
		int fillLength = length - src.length();
		if (fillLength == 0) {
			return src;
		}
		if (fillLength < 0) {
			throw new Exception(
					" [ The src' length is longer than expected ! ] ");
		}
		StringBuffer sb = new StringBuffer();
		while (sb.length() < fillLength) {
			sb.append(fillString);
		}
		sb.delete(fillLength, sb.length());
		if ("Left".equalsIgnoreCase(fillType)) {
			return sb + src;
		}
		if ("Right".equalsIgnoreCase(fillType)) {
			return src + sb;
		}
		return src;
	}

	/**
	 * 格式日期时间
	 * 
	 * @return
	 */
	private String formatDateTime(String start, String end) {
		if (null == start || null == end) {
			return "";
		}
		int beginIndex = Integer.parseInt(start);
		int endIndex = Integer.parseInt(end);

		if (beginIndex > 14 || endIndex > 14) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentDate = new Date();
		String date1 = sdf.format(currentDate);

		return date1.substring(beginIndex, endIndex);
	}

	private static Map<String, String> numCache = new HashMap<String, String>();

	private static Map<String, String> dateCache = new HashMap<String, String>();

	public static void main(String[] s) {
		try {
			System.out.println(getInstance().getID("FLOW_ID", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
