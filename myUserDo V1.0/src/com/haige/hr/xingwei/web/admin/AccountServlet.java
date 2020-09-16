package com.haige.hr.xingwei.web.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haige.hr.common.exception.BaseException;
import com.haige.hr.core.ajax.AjaxJsonUtil;
import com.haige.hr.core.ret.RetResponse;
import com.haige.hr.core.ret.RetResult;
import com.haige.hr.xingwei.service.AccountService;
import com.haige.hr.xingwei.service.bean.VO4Login;
import com.haige.hr.xingwei.service.bean.VO4ShowUserInfo;
import com.haige.hr.xingwei.web.admin.param.Mobile1param;
import com.haige.hr.xingwei.web.admin.param.Mobile2param;
import com.haige.hr.xingwei.web.admin.param.PasswordParam;
import com.haige.hr.xingwei.web.admin.param.UserInforParam;
/**
 * 用户管理Servlet 
 */
@WebServlet("/auth/personal/account/*")
public class AccountServlet extends HttpServlet {

	private AccountService accountService = new AccountService();

	
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
		String action = getAction(request,"/auth/personal/account/");


		if ("list".equals(action)) {
			this.doShow(request, response);
		} else if ("base".equals(action)) {
			this.doBase(request, response);
		} else if ("security/password".equals(action)) {
			this.doPassword(request, response);
		} else if ("security/mobile1".equals(action)) {
			this.doMobile1(request, response);
		} else if ("security/mobile2".equals(action)) {
			this.doMobile2(request, response);
		}

		else {
			// 找不到返回404
			RetResult<String> retResult = RetResponse.makeNotfoundRsp("不知道你想要干嘛");
            AjaxJsonUtil.doJsonResponse(retResult, response);
		}
	}
     
	
	// 显示用户基本信息业务函数
	protected void doShow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.获取参数
			String tokenValue = request.getHeader("token");

			// 2.参数校验

			// 3.业务调用(业务校验，业务处理)
		VO4ShowUserInfo rtn = accountService.Show(Long.valueOf(tokenValue)); //VO
		RetResult<VO4ShowUserInfo> retResult = RetResponse.makeOKRspWithMsg("业务处理成功啦",rtn);
		 AjaxJsonUtil.doJsonResponse(retResult, response);return ;	

		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回500
			RetResult<String> retResult = RetResponse.makeOKRspWithMsg("系统发生异");
			 AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		}
	}

	// 修改用户基本信息业务函数
	protected void doBase(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.获取参数
		   UserInforParam userInforParam = AjaxJsonUtil.parsePayload2Object(request, UserInforParam.class);
			
			String userName = userInforParam.getUserName();
			int userSex = userInforParam.getUserSex();
			String userIntro = userInforParam.getUserIntro();
			String userLogo = userInforParam.getUserLogo();

			String tokenValue = request.getHeader("token");
			// 2.参数校验
		if (userName.isEmpty() || userName == null || userSex== 0|| userIntro.isEmpty()
				|| userIntro == null || userLogo.isEmpty() || userLogo == null) {
			RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：信息填写不完整");
			AjaxJsonUtil.doJsonResponse(retResult , response);return ;
	
		}
			// 3.业务调用(业务校验，业务处理)
			if (accountService.UpdateBase(Long.valueOf(tokenValue), userInforParam)) {
 	RetResult<VO4ShowUserInfo> retResult = RetResponse.makeOKRspWithMsg("修改基本信息成功");
 	 AjaxJsonUtil.doJsonResponse(retResult, response);return ;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}
	}

	// 修改用户密码业务函数
	protected void doPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.获取参数
			PasswordParam passwordParam = AjaxJsonUtil.parsePayload2Object(request,PasswordParam.class);

			String oldPassword = passwordParam.getOldPassword();
			String newPassword = passwordParam.getNewPassword();
			String tokenValue = request.getHeader("token");
			// 2.参数校验
			if (oldPassword == null || oldPassword.isEmpty()) {
				// 失败返回201
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：旧密码必填");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			if (newPassword == null || newPassword.isEmpty()) {
				// 失败返回201
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：新密码必填");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			if (newPassword.equals(oldPassword)) {
				// 失败返回201
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：旧密码和新密码重复");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			// 3.业务调用(业务校验，业务处理)
			if (accountService.updatePassword(oldPassword,newPassword, Long.valueOf(tokenValue))) {
				RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("密码修改成功啦");	
                AjaxJsonUtil.doJsonResponse(retResult, response);return ;
			} else {
				RetResult<String> retResult = RetResponse.makeFailRsp("密码修改失败");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;	
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 5.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}

	}

	// 修改手机号第一步业务函数
	protected void doMobile1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        try {
		// 1.获取参数
		Mobile1param Mobile1param = AjaxJsonUtil.parsePayload2Object(request, Mobile1param.class);
		
		String userMobile = Mobile1param.getUserMobile();
		String smsCode = Mobile1param.getSmsCode();
		String tokenValue = request.getHeader("token");

		// 2.参数校验
		if (userMobile == null || userMobile.isEmpty()) {
			// 失败返回201
			RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：旧手机号必填");
			AjaxJsonUtil.doJsonResponse(retResult , response);return ;
		}
		if (smsCode == null || smsCode.isEmpty()) {
			// 失败返回201
			RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：验证码必填");
			AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		}

		// 3.业务调用(业务校验，业务处理)
		VO4Login rtn = accountService.changeMobile1(userMobile,smsCode,Long.valueOf(tokenValue));
		if (rtn == null){
			// 失败返回203
			RetResult<String> retResult = RetResponse.makeFailRsp("业务处理失败：手机号或验证码错误");
			 AjaxJsonUtil.doJsonResponse(retResult, response);return ;	
		} else {
			RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("业务处理成功啦",rtn);	
            AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		  }
		}catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}
	}

	// 修改手机号第二步业务函数
	protected void doMobile2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.获取参数
		Mobile2param mobile2param = AjaxJsonUtil.parsePayload2Object(request, Mobile2param.class);
			String userMobile = mobile2param.getUserMobile();
			String smsCode = mobile2param.getSmsCode();
			long mobile1TokenValue = mobile2param.getMobile1Token();
			
			
			
			String tokenValue = request.getHeader("token");

			// 2.参数校验
			if (userMobile == null || userMobile.isEmpty()) {
				// 失败返回201

           RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：手机号必填");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			if (smsCode == null || smsCode.isEmpty()) {
				// 失败返回201
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：验证码必填");
				AjaxJsonUtil.doJsonResponse(retResult, response);return ;
			}

			// 3.业务调用(业务校验，业务处理)
			if (accountService.changeMobile2(Long.valueOf(tokenValue),mobile1TokenValue, userMobile,smsCode)) {
				RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("业务处理成功啦");	
	            AjaxJsonUtil.doJsonResponse(retResult, response);return ;
			} else {
				RetResult<String> retResult = RetResponse.makeFailRsp("业务处理失败，请确认第一步操作");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;	
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}
	}
}



