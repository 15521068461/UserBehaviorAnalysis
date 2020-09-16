package com.haige.hr.core.ajax;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.haige.hr.common.exception.BaseException;

/**
 * Ajax Json 处理工具
 * @author leeyn
 *
 */
public class AjaxJsonUtil{	
	
	// 返回json结果
	public static void doJsonResponse(Object obj, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(obj));
		out.close();
	}

	
	// 用于解析payload（json/其它）的解析       (接受json格式的参数)
	public static String parsePayload(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = request.getReader();) {
			char[] buff = new char[1024];
			int len;
			while ((len = reader.read(buff)) != -1) {
				sb.append(buff, 0, len);
			}
		} catch (IOException e) {
		 throw new BaseException("debug", "传入payload参数解析有误", "传入payload参数解析有误",e);
		}
		return sb.toString();
	}
	

	
	// 用于解析payload（json/其它）的解析      
	public static <T> T parsePayload2Object(HttpServletRequest request,Class<T> aClass) {
		T obj=null;
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = request.getReader();) {
			char[] buff = new char[1024];
			int len;
			while ((len = reader.read(buff)) != -1) {
				sb.append(buff, 0, len);
			}
			obj=JSON.parseObject(sb.toString(), aClass);		
		} catch (Exception e) {
			throw new BaseException("debug", "传入payload参数解析有误", "传入payload参数解析有误",e);
		}
		return obj;
	}
	
	// 读取输入参数流，并转化成JSON对象
	public static JSONObject readAsText(HttpServletRequest request) throws IOException {
			ByteArrayOutputStream cache = new ByteArrayOutputStream(1024 * 16);
			byte[] data = new byte[1024];

			int numOfWait = 0;
			while (true) {
				int n = request.getInputStream().read(data); // n: 实际读取的字节数
				if (n < 0)
					break; // 连接已经断开
				if (n == 0) {
					if (numOfWait++ >= 3)
						break; // 此种情况不得连续3次
					try {
						Thread.sleep(5);
					} catch (Exception e) {
					}
					continue;// 数据未完 //
				}
				numOfWait = 0;

				// 缓存起来
				cache.write(data, 0, n);
				if (cache.size() > 1024 * 512) // 上限, 最多读取512K
					break;
			}

			JSONObject jreq = null;
			if ( cache.toString("UTF-8").length() > 0)
				jreq = new JSONObject( cache.toString("UTF-8"));
			
		return jreq ;
		}
	
	
}