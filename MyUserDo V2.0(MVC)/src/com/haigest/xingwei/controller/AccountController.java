package com.haigest.xingwei.controller;

import com.haigest.common.exception.BaseException;
import com.haigest.core.ajax.AjaxJsonUtil;
import com.haigest.core.mvc.Controller;
import com.haigest.core.mvc.URLMapping;
import com.haigest.core.ret.RetResponse;
import com.haigest.core.ret.RetResult;
import com.haigest.xingwei.controller.param.Mobile1param;
import com.haigest.xingwei.controller.param.Mobile2param;
import com.haigest.xingwei.controller.param.PasswordParam;
import com.haigest.xingwei.controller.param.UserInforParam;
import com.haigest.xingwei.service.AccountService;
import com.haigest.xingwei.service.bean.VO4Login;
import com.haigest.xingwei.service.bean.VO4ShowUserInfo;

@URLMapping(url = "/auth/personal/account")
public class AccountController extends Controller {
   
	private AccountService accountService = new AccountService();
	
	
	// 显示用户基本信息业务函数
	@URLMapping(url = "/list")
	public RetResult<?> doShow(){
		try {
			// 1.获取参数
			String tokenValue = request.getHeader("token");

			// 2.参数校验

			// 3.业务调用(业务校验，业务处理)
		VO4ShowUserInfo rtn = accountService.Show(Long.valueOf(tokenValue)); //VO
		return  RetResponse.makeOKRspWithMsg("业务处理成功啦",rtn);
		

		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回500
			return  RetResponse.makeOKRspWithMsg("系统发生异");
		}
	}
	
	
	// 修改用户基本信息业务函数
	@URLMapping(url = "/base")
	public RetResult<?> doBase(){
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
			return RetResponse.makeUnvalidateRsp("参数校验失败：信息填写不完整");
			
	
		}
			// 3.业务调用(业务校验，业务处理)
			if (accountService.UpdateBase(Long.valueOf(tokenValue), userInforParam)) {
				return RetResponse.makeOKRspWithMsg("修改基本信息成功");
			}else {
				
				return RetResponse.makeFailRsp("修改基本信息失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}
	
	
	}
	// 修改用户密码业务函数
	@URLMapping(url = "/security/password")
	public RetResult<?> doPassword(){
		try {
			// 1.获取参数
			PasswordParam passwordParam = AjaxJsonUtil.parsePayload2Object(request,PasswordParam.class);

			String oldPassword = passwordParam.getOldPassword();
			String newPassword = passwordParam.getNewPassword();
			String tokenValue = request.getHeader("token");
			// 2.参数校验
			if (oldPassword == null || oldPassword.isEmpty()) {
				// 失败返回201
				return RetResponse.makeUnvalidateRsp("参数校验失败：旧密码必填");
			}
			if (newPassword == null || newPassword.isEmpty()) {
				// 失败返回201
				return RetResponse.makeUnvalidateRsp("参数校验失败：新密码必填");
			}
			if (newPassword.equals(oldPassword)) {
				// 失败返回201
				return RetResponse.makeUnvalidateRsp("参数校验失败：旧密码和新密码重复");
			}
			// 3.业务调用(业务校验，业务处理)
			if (accountService.updatePassword(oldPassword,newPassword, Long.valueOf(tokenValue))) {
				return RetResponse.makeOKRspWithMsg("密码修改成功啦");	
			} else {
				return RetResponse.makeFailRsp("密码修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 5.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}
		
	}
	// 修改手机号第一步业务函数
	@URLMapping(url = "/security/mobile1")
	public RetResult<?> doMobile1(){
		 try {
				// 1.获取参数
				Mobile1param Mobile1param = AjaxJsonUtil.parsePayload2Object(request, Mobile1param.class);
				
				String userMobile = Mobile1param.getUserMobile();
				String smsCode = Mobile1param.getSmsCode();
				String tokenValue = request.getHeader("token");

				// 2.参数校验
				if (userMobile == null || userMobile.isEmpty()) {
					// 失败返回201
					return  RetResponse.makeUnvalidateRsp("参数校验失败：旧手机号必填");
				}
				if (smsCode == null || smsCode.isEmpty()) {
					// 失败返回201
					return  RetResponse.makeUnvalidateRsp("参数校验失败：验证码必填");
				}

				// 3.业务调用(业务校验，业务处理)
				VO4Login rtn = accountService.changeMobile1(userMobile,smsCode,Long.valueOf(tokenValue));
				if (rtn == null){
					// 失败返回203
					return  RetResponse.makeFailRsp("业务处理失败：手机号或验证码错误");
				} else {
					return  RetResponse.makeOKRspWithMsg("业务处理成功啦",rtn);	
				  }
				}catch (Exception e) {
					e.printStackTrace();
					// 4.异常返回204
					throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
				}
	}
	// 修改手机号第二步业务函数
	@URLMapping(url = "/security/mobile2")
	public RetResult<?> doMobile2(){
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

				return RetResponse.makeUnvalidateRsp("参数校验失败：手机号必填");
			}
			if (smsCode == null || smsCode.isEmpty()) {
				// 失败返回201
				return RetResponse.makeUnvalidateRsp("参数校验失败：验证码必填");
			}

			// 3.业务调用(业务校验，业务处理)
			if (accountService.changeMobile2(Long.valueOf(tokenValue),mobile1TokenValue, userMobile,smsCode)) {
				return RetResponse.makeOKRspWithMsg("业务处理成功啦");	
			} else {
				return RetResponse.makeFailRsp("业务处理失败，请确认第一步操作");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 4.异常返回204
			throw new BaseException("warn", "管理员，系统异常了", "用户，系统异常啦",e);
		}
	}
}
