package com.haigest.core.klass;

import java.util.ArrayList;
import java.util.Set;

import com.haigest.common.klass.PackgeScanner;
import com.haigest.core.constant.MvcProperties;
import com.haigest.core.mvc.URLMapping;

//包扫描器测试类
public class Test {
	public static void main(String[] args) throws Exception {
		ArrayList<String> controllerList=new ArrayList<String>();
    	Set<Class<?>> set = PackgeScanner.getAnnotationClasses(MvcProperties.MVC_CONTROLLERS_PACKAGE.split(","), URLMapping.class);
    	for (Class<?> c : set) {
    		controllerList.add(c.getName());
    	}
	}
}
