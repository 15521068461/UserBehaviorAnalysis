package com.haigest.xingwei.controller;

import com.haigest.common.exception.BaseException;
import com.haigest.core.mvc.URLMapping;
import com.haigest.core.ret.RetResponse;
import com.haigest.core.ret.RetResult;

@URLMapping(url = "/demo2")
public class DemoController2 {
	/*
	 * 通过注解，我们可以看出我们拟定将来用MvcDemoController这个类来相应/mvcdemo/***的URL。
	 * 其中SayHello方法相应的URL是/mvcdemo/hello，SayHi方法相应的URL是/mvcdemo/hi
	 */
	@URLMapping(url = "/hello")
	public RetResult<String> sayHello() {
		System.out.println("Hello");
		return RetResponse.makeOKRsp("Hello");
	}

	@URLMapping(url = "/hi")
	public RetResult<String> sayHi() {
		System.out.println("Hi");
		if("hi".length()==2) {
			throw new BaseException("error", null, "错误了");
		}
		return RetResponse.makeOKRsp("Hi");
	}

}