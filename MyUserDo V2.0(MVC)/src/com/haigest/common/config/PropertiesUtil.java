package com.haigest.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.haigest.common.exception.BaseException;


/**
 * 	配置文件读写工具类
 * @author leeyn
 *
 */
public class PropertiesUtil {
	/**
	 * 将配置文件读取为map集合
	 * @param filePath 相对于src或classes根目录的文件路径，譬如  /sys.properties（...src/sys.properties）
	 * @return 
	 */
	public static Map<String, String> getProperties(String filePath) {
		Properties props = new Properties();
		InputStream inStream = new PropertiesUtil().getClass().getResourceAsStream(filePath);
		Map<String, String> map=null;
		if (inStream == null) {
			throw new BaseException("fatal","系统错误：PropertiesUtil  "+filePath+"文件不存在",
					"系统错误");
		}
		try {
			props.load(inStream);
			map=new HashMap<String, String>();
			Enumeration<?> en=props.propertyNames();
			while (en.hasMoreElements()) {
				String key=(String) en.nextElement();
				String property=props.getProperty(key);
				map.put(key, property);
			}
			inStream.close();
			return map;
		} catch (IOException e) {
			throw new BaseException("fatal","系统错误：PropertiesUtil  "+filePath+"解析失败",
					"系统错误",e);
		}
	}
}
