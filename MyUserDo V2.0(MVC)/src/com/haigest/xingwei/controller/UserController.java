package com.haigest.xingwei.controller;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.haigest.common.exception.BaseException;
import com.haigest.core.ajax.AjaxJsonUtil;
import com.haigest.core.mvc.Controller;
import com.haigest.core.mvc.URLMapping;
import com.haigest.core.ret.RetResponse;
import com.haigest.core.ret.RetResult;
import com.haigest.xingwei.controller.param.LoginParam;
import com.haigest.xingwei.service.UserService;
import com.haigest.xingwei.service.bean.VO4Login;

@URLMapping(url = "/open/user")
public class UserController extends Controller {

	private UserService userService = new UserService();
	
	// 功能：用户登录
	@URLMapping(url = "/login")
	public RetResult<VO4Login> doLogin() {
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
				return  RetResponse.makeUnvalidateRsp("参数校验失败：卡号必填");
				
			}
			if (pass == null || pass.isEmpty()) {
				// 失败返回402
				return	 RetResponse.makeUnvalidateRsp("参数校验失败：密码必填");
			}
			if (yzm == null || yzm.isEmpty()) {
				// 失败返回402
			
				return RetResponse.makeUnvalidateRsp("参数校验失败：图形验证码必填");
			}
			if (!yzm.equals(tu2) || !imgCodeToken.equals(shijianchuo2)) {
				return RetResponse.makeUnvalidateRsp("参数校验失败：图形验证码错误");
			}
		 
			// 3业务调用
			VO4Login rtn = userService.login(phone, pass); // 调用登录业务函数
			
			if (rtn == null) {
				// 失败返回400
				return RetResponse.makeFailRsp("账号密码错误，登录失败");
			} else {
				// 成功返回200

				return RetResponse.makeOKRspWithMsg("业务处理成功啦",rtn);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回500
		
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
			
		}
	}
	
	// 功能：自动登录
	@URLMapping(url = "/autologin")
	public RetResult<VO4Login>  doautoLogin(){
		try {
			// 1获取参数
			JSONObject jreq = AjaxJsonUtil.readAsText(request);

			String oldtoken = jreq.getString("token"); // 获取旧的token

			// 2参数校验

			// 3业务调用(业务校验，业务处理)
			VO4Login rtn = userService.doautologin(Long.valueOf(oldtoken));

			if (rtn == null) {
				// 失败返回401
				return  RetResponse.makeUnauthorizedRsp("业务处理失败：获取权限失败");
			} else {
				// 成功返回200
				return  RetResponse.makeOKRspWithMsg("业务处理成功啦",rtn);
			}
         
		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}
	}
	
	// 功能：退出登录
	@URLMapping(url = "/logout")
	public RetResult<String> dologout(){
		try {
			// 1.获取参数
			String tokenValue = request.getHeader("token");
			// 2.参数校验

			

			// 3.业务调用(业务校验，业务处理)
			userService.logout(Long.valueOf(tokenValue));
			return  RetResponse.makeOKRsp("已退出登录");
				
		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}
	}
	
	
	
}
