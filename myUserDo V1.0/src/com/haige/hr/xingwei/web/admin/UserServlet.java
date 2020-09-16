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
 *  �û�ģ��Servlet 
 */
@WebServlet("/open/user/*")
public class UserServlet extends HttpServlet {

	private UserService userService = new UserService();
	
	
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
		String action = getAction(request,"/open/user/");
	
		if ("login".equals(action)) {
			this.doLogin(request, response);
		} else if ("logout".equals(action)) {
			this.doLogout(request, response);
		} else if ("autologin".equals(action)) {
			this.doautoLogin(request, response);
		}

		else {
			
			// �Ҳ�������404
			RetResult<String> retResult = RetResponse.makeNotfoundRsp("��֪������Ҫ����");
            AjaxJsonUtil.doJsonResponse(retResult, response);
		}
	}
	
	
	// ���ܣ��û���¼
	protected void doLogin(HttpServletRequest request, HttpServletResponse response)
		 {
		try {
			// 1��ȡ����

			LoginParam LoginParam =  AjaxJsonUtil.parsePayload2Object(request, LoginParam.class);
			
			// �û���¼ʱ����Ĳ���
			String phone = LoginParam.getUserMobile();
			String pass = LoginParam.getUserPassword();
			String yzm = LoginParam.getImgCode();
			String imgCodeToken = LoginParam.getImgCodeToken();
			int autoLogin = LoginParam.getAutoLogin();

			// ��ȡ����ͼ����֤��ʱ����session�Ĳ���
			HttpSession session = request.getSession();
			System.out.println(session.getId() + "�ڶ�������");
			String tu2 = (String) session.getAttribute("yz");
			String shijianchuo2 = (String) session.getAttribute("time");

			System.out.println("�û���¼ʱ�������֤�룺" + yzm);
			System.out.println("�û���¼�����ʱ�����" + imgCodeToken);
			System.out.println("��ǰ���ɵ���֤�룺" + tu2);
			System.out.println("��ǰ���ɵ�ʱ�����" + shijianchuo2);
			// 2����У��
			if (phone == null || phone.isEmpty()) {
				// ʧ�ܷ���402
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ����ű���");
				AjaxJsonUtil.doJsonResponse(retResult , response);return ;
			}
			if (pass == null || pass.isEmpty()) {
				// ʧ�ܷ���402
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ��������");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;			
			}
			if (yzm == null || yzm.isEmpty()) {
				// ʧ�ܷ���402
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ�ͼ����֤�����");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;			
			}
			if (!yzm.equals(tu2) || !imgCodeToken.equals(shijianchuo2)) {
				RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ�ͼ����֤�����");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;				 
			}
		 
			// 3ҵ�����
			VO4Login rtn = userService.login(phone, pass); // ���õ�¼ҵ����
			
			if (rtn == null) {
				// ʧ�ܷ���400
				RetResult<String> retResult = RetResponse.makeFailRsp("�˺�������󣬵�¼ʧ��");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;			
			} else {
				// �ɹ�����200

				RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("ҵ����ɹ���",rtn);
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;				
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 4.�쳣����500
		
			throw new BaseException("warn", "����Ա��ϵͳ�쳣��", "�û���ϵͳ�쳣��",e);
			
		}
	}

	// ���ܣ��Զ���¼
	protected void doautoLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1��ȡ����
			JSONObject jreq = AjaxJsonUtil.readAsText(request);

			String oldtoken = jreq.getString("token"); // ��ȡ�ɵ�token

			// 2����У��

			// 3ҵ�����(ҵ��У�飬ҵ����)
			VO4Login rtn = userService.doautologin(Long.valueOf(oldtoken));

			if (rtn == null) {
				// ʧ�ܷ���401
				RetResult<String> retResult = RetResponse.makeUnauthorizedRsp("ҵ����ʧ�ܣ���ȡȨ��ʧ��");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;				
			} else {
				// �ɹ�����200
				RetResult<VO4Login> retResult = RetResponse.makeOKRspWithMsg("ҵ����ɹ���",rtn);
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;				
			}
         
		} catch (Exception e) {
			e.printStackTrace();
			// 4.�쳣����204
			throw new BaseException("warn", "����Ա��ϵͳ�쳣��", "�û���ϵͳ�쳣��",e);
		}
	}

	// ���ܣ��˳���¼
	private void doLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// 1.��ȡ����
			String tokenValue = request.getHeader("token");
			// 2.����У��

			

			// 3.ҵ�����(ҵ��У�飬ҵ����)
			userService.logout(Long.valueOf(tokenValue));
			RetResult<String> retResult = RetResponse.makeOKRsp();
			 AjaxJsonUtil.doJsonResponse(retResult, response);return ;			
		} catch (Exception e) {
			e.printStackTrace();
			// 4.�쳣����204
			throw new BaseException("warn", "����Ա��ϵͳ�쳣��", "�û���ϵͳ�쳣��",e);
		}

	}
}


