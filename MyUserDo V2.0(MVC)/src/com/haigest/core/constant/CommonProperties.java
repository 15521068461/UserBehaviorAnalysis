package com.haigest.core.constant;

import java.util.Map;
import com.haigest.common.config.PropertiesUtil;

/** 
 * common.properties配置文件-常量对象
 * @author 灯芯科技 李远念
 */  
public final class CommonProperties {

	private static Map<String, String> map=PropertiesUtil.getProperties("/common.properties");

	public static final String COMMON_UTIL_SNID_DATACENTERID;
	public static final String COMMON_UTIL_SNID_WORKERID;

	static {
		COMMON_UTIL_SNID_DATACENTERID = map.get("common.util.snid.datacenterId");
		COMMON_UTIL_SNID_WORKERID = map.get("common.util.snid.workerId");
	}

}
