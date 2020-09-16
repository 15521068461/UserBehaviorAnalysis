package com.haigest.core.constant;

import java.util.Map;
import com.haigest.common.config.*;

/** 
 * mvc.properties配置文件-常量对象
 * @author 灯芯科技 李远念
 */  
public final class MvcProperties {

	private static Map<String, String> map=PropertiesUtil.getProperties("/mvc.properties");

	public static final String MVC_CONTROLLERS;
	public static final String MVC_CONTROLLERS_PACKAGE;

	static {
		MVC_CONTROLLERS = map.get("mvc.controllers");
		MVC_CONTROLLERS_PACKAGE = map.get("mvc.controllers.package");
	}

}
