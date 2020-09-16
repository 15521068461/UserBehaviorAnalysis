package com.haigest.core.mvc;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.haigest.common.exception.BaseError;
import com.haigest.common.exception.BaseException;
import com.haigest.core.ajax.AjaxJsonUtil;
import com.haigest.core.ret.RetResponse;
/**
 * 所有的URL请求都要跳转到这个Servlet中
 * @author 灯芯科技 李远念
 *
 */
public class ServletCenter extends HttpServlet {
    private static final long serialVersionUID = -1163369749078348562L;

	private final static Log logger = LogFactory.getLog(ServletCenter.class);
	
	private void doTransfer(HttpServletRequest request, HttpServletResponse response) {
		
		String error = null;
		try {
			request.setCharacterEncoding("UTF-8");    // 这是需要加到这里吗？
			response.setCharacterEncoding("UTF-8"); 
			// 资源是否存在
			boolean isExits = false;
			for (MVCBase mvcBase : URLMappingCollection.getMvcBases()) { // 遍历这个从URL到JAVA后台方法对应关系的集合
				if (request.getRequestURI().equals(request.getServletContext().getContextPath() + mvcBase.getUrl())) { // url路径
																														// =
																														// 公共路径+方法url
					Class<?> clazz = Class.forName(mvcBase.getController()); // 根据类名生成类(反射) ;实例化一个类(对应的controller)
					Object object = clazz.newInstance(); // 等到一个类对象 ; 实例化这个类的一个对象
					// 注入 请求、响应 对象
					Method method = clazz.getMethod("setRequest", HttpServletRequest.class); // 获取类里面的方法
					method.invoke(object, request); // 执行该类的方法(传入相应的参数)
					method = clazz.getMethod("setResponse", HttpServletResponse.class);
					method.invoke(object, response);
					// 获取 请求对应的方法
					method = clazz.getMethod(mvcBase.getMethod());
					Object retResult = method.invoke(object); // 执行该方法，并接受返回值
					// 返回 响应结果
					if(retResult!=null)
					AjaxJsonUtil.doJsonResponse(retResult, response);
					isExits = true;
					break;
				}
			}
			if (!isExits) {
				AjaxJsonUtil.doJsonResponse(RetResponse.makeNotfoundRsp(), response);
			}
		} catch (InvocationTargetException e) {
			// 反射调用方法时，方法内部抛出了自定义异常，但是无法在反射调用的时候捕获到抛出的自定义异常
			// https://www.jianshu.com/p/f60a19ceb9bf
			// InvocationTargetException是用来处理内部方法抛出异常的
			Throwable cause = e.getTargetException();// 获取内部异常
			if (cause instanceof BaseError) {
				BaseError err = (BaseError) cause;
				if (err.getAdminMessage() != null) {
					logger.error(err.getAdminMessage(), err);
				}
				if (err.getUserMessage() != null) {
					error = "接口错误：" + err.getUserMessage();
				}
			} else if (cause instanceof BaseException) {
				BaseException err = (BaseException) cause;
				if (err.getAdminMessage() != null) {
					if ("fatal".equals(err.getLogLevel()))
						logger.fatal(err.getAdminMessage(), err);
					if ("error".equals(err.getLogLevel()))
						logger.error(err.getAdminMessage(), err);
					if ("warn".equals(err.getLogLevel()))
						logger.warn(err.getAdminMessage(), err);
					if ("info".equals(err.getLogLevel()))
						logger.info(err.getAdminMessage(), err);
					if ("debug".equals(err.getLogLevel()))
						logger.debug(err.getAdminMessage(), err);
				}
				if (err.getUserMessage() != null) {
					error = "接口异常：" + err.getUserMessage();
				}
			} else {
				// 某些还未能考虑到的异常
				// 开发时未考虑到的 情况 ，要重点记录， 升级系统
				logger.error(cause.getMessage(), cause);
				error = "接口异常或错误：" + cause.getMessage();
			}

		} catch (Exception err) { // 反射本身产生的其它异常
			logger.error(err.getMessage(), err);
			error = "反射异常：" + err.getMessage();
		} catch (Error err) { // 反射本身产生的其它错误
			logger.error(err.getMessage(), err);
			error = "反射错误：" + err.getMessage();
		}

		if (error != null) {
			// 这里可能产生 上述 接口、反射 两类异常以外的异常【响应异常】，得在Filter中处理
			AjaxJsonUtil.doJsonResponse(RetResponse.makeFailRsp(error), response);
		}
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
            doTransfer(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
            doTransfer(request, response);        
    }
}