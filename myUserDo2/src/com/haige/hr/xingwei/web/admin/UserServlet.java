package com.haige.hr.xingwei.web.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import com.haige.hr.common.exception.BaseException;
import com.haige.hr.core.ajax.AjaxJsonUtil;
import com.haige.hr.xingwei.service.UserService;
import com.haige.hr.xingwei.service.bean.VO4Login;
import com.haige.hr.xingwei.web.admin.param.LoginParam;
import com.haige.hr.core.ret.RetResponse;
import com.haige.hr.core.ret.RetResult;

/**
 *  用户模块Servlet 
 */
@WebServlet("/open/user/*")
public class UserServlet extends HttpServlet {

	private UserService userService = new UserService();
	
	
	public String getAction(HttpServletRequest request,String url) {
		String uri = request.getRequestURI(); //获取请求路径，如：/open/user/login
		String action = uri.substring(uri.indexOf(url)+url.length()); //获取具体意图，"login"
		return action ;
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 初始化
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 获取意图
		String action = getAction(request,"/open/user/");
	
		if ("login".equals(action)) {
			this.doLogin(request, response);
		} else if ("logout".equals(action)) {
			this.doLogout(request, response);
		} else if ("autologin".equals(action)) {
			this.doautoLogin(request, response);
		}

		else {
			
			// 找不到返回404
			RetResult<String> retResult = RetResponse.makeNotfoundRsp("不知道你想要干嘛");
            AjaxJsonUtil.doJsonResponse(retResult, response);
		}
	}
	
	
	// 功能：用户登录
	protected void doLogin(HttpServletRequest request, HttpServletResponse response)
		 {
		try {
			// 1获取参数

			LoginParam LoginParam =  AjaxJsonUtil.parsePayload2Object(request, LoginParam.class);
			
			// 用户登录时填入的参数
			String phone = LoginParam.getUserMobile();
			String pass = LoginParam.getUserPassword();
			String yzm = LoginParam.getImgCode();
			String imgCodeToken = LoginParam.getImgCodeToken();
			int autoLogin = LoginParam.getAutoLogin();

			// 获取生成图形验证码时塞入session的参数
			HttpSession session = request.getSession();
			System.out.println(session.getId() + "第二次请求");
			String tu2 = (String) session.getAttribute("yz");
			String shijianchuo2 = (String) session.getAttribute("time");

			System.out.println("用户登录时输入的验证码：" + yzm);
			System.out.println("用户登录带入的时间戳：" + imgCodeToken);
			System.out.println("先前生成的验证码：" + tu2);
			System.out.println("先前生成的时间戳：" + shijianchuo2);
			// 2参数校验
			if (phone == null || phone.isEmpty()) {
				// 失败返回402
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：卡号必填");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			if (pass == null || pass.isEmpty()) {
				// 失败返回402
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：密码必填");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;			
			}
			if (yzm == null || yzm.isEmpty()) {
				// 失败返回402
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：图形验证码必填");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;			
			}
			if (!yzm.equals(tu2) || !imgCodeToken.equals(shijianchuo2)) {
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：图形验证码错误");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;				 
			}
		 
			// 3业务调用
			VO4Login rtn = userService.login(phone, pass); // 调用登录业务函数
			
			if (rtn == null) {
				// 失败返回400
				RetResult<String> retResult = RetResponse.makeFailRsp("账号密码错误，登录失败");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;			
			} else {
				// 成功返回200

				RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("业务处理成功啦",rtn);
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;				
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回500
		
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
			
		}
	}

	// 功能：自动登录
	protected void doautoLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1获取参数
			JSONObject jreq = AjaxJsonUtil.readAsText(request);

			String oldtoken = jreq.getString("token"); // 获取旧的token

			// 2参数校验

			// 3业务调用(业务校验，业务处理)
			VO4Login rtn = userService.doautologin(Long.valueOf(oldtoken));

			if (rtn == null) {
				// 失败返回401
				RetResult<String> retResult = RetResponse.makeUnauthorizedRsp("业务处理失败：获取权限失败");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;				
			} else {
				// 成功返回200
				RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("业务处理成功啦",rtn);
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;				
			}
         
		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}
	}

	// 功能：退出登录
	private void doLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// 1.获取参数
			String tokenValue = request.getHeader("token");
			// 2.参数校验

			

			// 3.业务调用(业务校验，业务处理)
			userService.logout(Long.valueOf(tokenValue));
			RetResult<String> retResult = RetResponse.makeOKRsp();
			 AjaxJsonUtil.doJsonResponse(retResult, response);return ;			
		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}

	}
}


