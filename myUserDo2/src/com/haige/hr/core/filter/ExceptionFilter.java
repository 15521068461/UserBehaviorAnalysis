package com.haige.hr.core.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.haige.hr.common.exception.BaseError;
import com.haige.hr.common.exception.BaseException;
import com.haige.hr.core.ajax.AjaxJsonUtil;
import com.haige.hr.core.ret.RetResponse;
import com.haige.hr.core.filter.ExceptionFilter;

/**
 * Exception统一捕捉处理过滤器
 * 
 * @author leeyn
 *
 */
@WebFilter("/*")
public class ExceptionFilter implements javax.servlet.Filter {
	private final static Log logger = LogFactory.getLog(ExceptionFilter.class);

	/**
	 * 当请求到达时，会首先被此拦截器拦截，当数据经过获取并在V层显示完毕(响应完毕)后，
	 * 又回到此Filter内部，途中如果下层有异常抛出，在这里进行拦截捕捉，并统一处理
	 */
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String error = null;
		try {
			chain.doFilter(request, response);
		} catch (BaseError err) {
			if (err.getAdminMessage() != null) {
				logger.error(err.getAdminMessage(), err);
			}
			if (err.getUserMessage() != null) {
				error = err.getUserMessage();
			}
		} catch (BaseException err) {
			System.out.println(err.getLogLevel());
			if (err.getAdminMessage() != null) {
				if ("fatal".equals(err.getLogLevel()))
					logger.fatal(err.getAdminMessage(), err);
				if ("error".equals(err.getLogLevel()))
					logger.error(err.getAdminMessage(), err);
				if ("warn".equals(err.getLogLevel())) {
					System.out.println("这是一个warn类型的错误");
					logger.warn(err.getAdminMessage(), err);
					}
				if ("info".equals(err.getLogLevel()))
					logger.info(err.getAdminMessage(), err);
				if ("debug".equals(err.getLogLevel()))
					logger.debug(err.getAdminMessage(), err);
			}
			if (err.getUserMessage() != null) {
				error = err.getUserMessage();
			}
		} catch (Exception err) { // 某些还未能考虑到的异常
			// 开发时未考虑到的 情况 ，要重点记录， 升级系统
			logger.error(err.getMessage(), err);
			error = "系统异常";
		} catch (Error err) { // 某些还未能考虑到的错误
			logger.error(err.getMessage(), err);
			error = "系统错误";
		}

		if (error != null) {
			
			// MVC模式 带上error信息跳转到error.jsp
			// request.setAttribute("error", error);
			// request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,
			// response);
			
			// JSON交互            (感觉异常全都是报500)
			AjaxJsonUtil.doJsonResponse(RetResponse.makeErrorRsp(error), (HttpServletResponse) response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public void destroy() {

	}

}