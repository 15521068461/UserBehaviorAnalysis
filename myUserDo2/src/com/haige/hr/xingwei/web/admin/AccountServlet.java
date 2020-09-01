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
 * �û�����Servlet 
 */
@WebServlet("/auth/personal/account/*")
public class AccountServlet extends HttpServlet {

	private AccountService accountService = new AccountService();

	
	public String getAction(HttpServletRequest request,String url) {
		String uri = request.getRequestURI(); //��ȡ����·�����磺/open/user/login
		String action = uri.substring(uri.indexOf(url)+url.length()); //��ȡ������ͼ��"login"
		return action ;
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// ��ʼ��
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// ��ȡ��ͼ
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
			// �Ҳ�������404
			RetResult<String> retResult = RetResponse.makeNotfoundRsp("��֪������Ҫ����");
            AjaxJsonUtil.doJsonResponse(retResult, response);
		}
	}
     
	
	// ��ʾ�û�������Ϣҵ����
	protected void doShow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.��ȡ����
			String tokenValue = request.getHeader("token");

			// 2.����У��

			// 3.ҵ�����(ҵ��У�飬ҵ����)
		VO4ShowUserInfo rtn = accountService.Show(Long.valueOf(tokenValue)); //VO
		RetResult<VO4ShowUserInfo> retResult = RetResponse.makeOKRspWithMsg("ҵ����ɹ���",rtn);
		 AjaxJsonUtil.doJsonResponse(retResult, response);return ;	

		} catch (Exception e) {
			e.printStackTrace();
			// 4.�쳣����500
			RetResult<String> retResult = RetResponse.makeOKRspWithMsg("ϵͳ������");
			 AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		}
	}

	// �޸��û�������Ϣҵ����
	protected void doBase(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.��ȡ����
		   UserInforParam userInforParam = AjaxJsonUtil.parsePayload2Object(request, UserInforParam.class);
			
			String userName = userInforParam.getUserName();
			int userSex = userInforParam.getUserSex();
			String userIntro = userInforParam.getUserIntro();
			String userLogo = userInforParam.getUserLogo();

			String tokenValue = request.getHeader("token");
			// 2.����У��
		if (userName.isEmpty() || userName == null || userSex== 0|| userIntro.isEmpty()
				|| userIntro == null || userLogo.isEmpty() || userLogo == null) {
			RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ���Ϣ��д������");
			AjaxJsonUtil.doJsonResponse(retResult , response);return ;
	
		}
			// 3.ҵ�����(ҵ��У�飬ҵ����)
			if (accountService.UpdateBase(Long.valueOf(tokenValue), userInforParam)) {
 	RetResult<VO4ShowUserInfo> retResult = RetResponse.makeOKRspWithMsg("�޸Ļ�����Ϣ�ɹ�");
 	 AjaxJsonUtil.doJsonResponse(retResult, response);return ;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 4.�쳣����204
			throw new BaseException("warn", "����Ա��ϵͳ�쳣��", "�û���ϵͳ�쳣��",e);
		}
	}

	// �޸��û�����ҵ����
	protected void doPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.��ȡ����
			PasswordParam passwordParam = AjaxJsonUtil.parsePayload2Object(request,PasswordParam.class);

			String oldPassword = passwordParam.getOldPassword();
			String newPassword = passwordParam.getNewPassword();
			String tokenValue = request.getHeader("token");
			// 2.����У��
			if (oldPassword == null || oldPassword.isEmpty()) {
				// ʧ�ܷ���201
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ����������");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			if (newPassword == null || newPassword.isEmpty()) {
				// ʧ�ܷ���201
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ����������");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			if (newPassword.equals(oldPassword)) {
				// ʧ�ܷ���201
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ���������������ظ�");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			// 3.ҵ�����(ҵ��У�飬ҵ����)
			if (accountService.updatePassword(oldPassword,newPassword, Long.valueOf(tokenValue))) {
				RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("�����޸ĳɹ���");	
                AjaxJsonUtil.doJsonResponse(retResult, response);return ;
			} else {
				RetResult<String> retResult = RetResponse.makeFailRsp("�����޸�ʧ��");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;	
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 5.�쳣����204
			throw new BaseException("warn", "����Ա��ϵͳ�쳣��", "�û���ϵͳ�쳣��",e);
		}

	}

	// �޸��ֻ��ŵ�һ��ҵ����
	protected void doMobile1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        try {
		// 1.��ȡ����
		Mobile1param Mobile1param = AjaxJsonUtil.parsePayload2Object(request, Mobile1param.class);
		
		String userMobile = Mobile1param.getUserMobile();
		String smsCode = Mobile1param.getSmsCode();
		String tokenValue = request.getHeader("token");

		// 2.����У��
		if (userMobile == null || userMobile.isEmpty()) {
			// ʧ�ܷ���201
			RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ����ֻ��ű���");
			AjaxJsonUtil.doJsonResponse(retResult , response);return ;
		}
		if (smsCode == null || smsCode.isEmpty()) {
			// ʧ�ܷ���201
			RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ���֤�����");
			AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		}

		// 3.ҵ�����(ҵ��У�飬ҵ����)
		VO4Login rtn = accountService.changeMobile1(userMobile,smsCode,Long.valueOf(tokenValue));
		if (rtn == null){
			// ʧ�ܷ���203
			RetResult<String> retResult = RetResponse.makeFailRsp("ҵ����ʧ�ܣ��ֻ��Ż���֤�����");
			 AjaxJsonUtil.doJsonResponse(retResult, response);return ;	
		} else {
			RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("ҵ����ɹ���",rtn);	
            AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		  }
		}catch (Exception e) {
			e.printStackTrace();
			// 4.�쳣����204
			throw new BaseException("warn", "����Ա��ϵͳ�쳣��", "�û���ϵͳ�쳣��",e);
		}
	}

	// �޸��ֻ��ŵڶ���ҵ����
	protected void doMobile2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.��ȡ����
		Mobile2param mobile2param = AjaxJsonUtil.parsePayload2Object(request, Mobile2param.class);
			String userMobile = mobile2param.getUserMobile();
			String smsCode = mobile2param.getSmsCode();
			long mobile1TokenValue = mobile2param.getMobile1Token();
			
			
			
			String tokenValue = request.getHeader("token");

			// 2.����У��
			if (userMobile == null || userMobile.isEmpty()) {
				// ʧ�ܷ���201

           RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ��ֻ��ű���");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			if (smsCode == null || smsCode.isEmpty()) {
				// ʧ�ܷ���201
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ���֤�����");
				AjaxJsonUtil.doJsonResponse(retResult, response);return ;
			}

			// 3.ҵ�����(ҵ��У�飬ҵ����)
			if (accountService.changeMobile2(Long.valueOf(tokenValue),mobile1TokenValue, userMobile,smsCode)) {
				RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("ҵ����ɹ���");	
	            AjaxJsonUtil.doJsonResponse(retResult, response);return ;
			} else {
				RetResult<String> retResult = RetResponse.makeFailRsp("ҵ����ʧ�ܣ���ȷ�ϵ�һ������");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;	
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 4.�쳣����204
			throw new BaseException("warn", "����Ա��ϵͳ�쳣��", "�û���ϵͳ�쳣��",e);
		}
	}
}



