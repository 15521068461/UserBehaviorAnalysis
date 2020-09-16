package com.haigest.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 	公共过滤器
 * @author leeyn
 *
 */
public class PubFilter implements javax.servlet.Filter {
	private final static Log logger = LogFactory.getLog(PubFilter.class);
	/**
	 *当请求到达时，会首先被此拦截器拦截，当数据经过获取并在V层显示完毕(响应完毕)后，
	 *又回到此Filter内部，途中如果下层有异常抛出，在这里进行拦截捕捉，并统一处理
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		/* 字符编码设为utf-8 */
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");

		HttpServletResponse response=(HttpServletResponse)res;
		
		/* 允许跨域的主机地址 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 允许跨域的请求方法GET, POST, HEAD 等 */
		response.setHeader("Access-Control-Allow-Methods", "*");
		/* 重新预检验跨域的缓存时间 (s) */
		response.setHeader("Access-Control-Max-Age", "3600");
		/* 允许跨域的请求头 */
		response.setHeader("Access-Control-Allow-Headers", "*");
		/* 是否携带cookie */
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		/* 下层ServletCenter已经处理了能处理的异常（接口异常、反射异常），这里捕捉【响应异常】（无法给用户响应），只能logger输出 */
		try {
			chain.doFilter(req, res);	
		}catch (Throwable err) {
			logger.error(err.getMessage(), err);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	
	}

	public void destroy() {
	
	}
}