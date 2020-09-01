package com.haige.hr.xingwei.web.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haige.hr.core.ajax.AjaxJsonUtil;
import com.haige.hr.core.ret.RetResponse;
import com.haige.hr.core.ret.RetResult;
import com.haige.hr.xingwei.service.SystemService;
import com.haige.hr.xingwei.service.bean.VO4Label;
import com.haige.hr.xingwei.web.admin.param.AddLabelParam;
import com.haige.hr.xingwei.web.admin.param.UpdataParam;

/**
 *   ��ǩ����Servlet
 */
@WebServlet("/auth/system/label/*")
public class SystemServlet extends HttpServlet {

	private SystemService SystemService = new SystemService();
    //��ȡ�û���ͼ����
	public String getAction(HttpServletRequest request, String url) {
		String uri = request.getRequestURI(); // ��ȡ����·�����磺/open/user/login
		String action = uri.substring(uri.indexOf(url) + url.length()); // ��ȡ������ͼ��"login"
		return action;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ��ʼ��
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// ��ȡ��ͼ
		String action = getAction(request, "/auth/system/label/");

		if ("add".equals(action)) {
			this.doAdd(request, response);
		} else if ("update".equals(action)) {
			this.doUpdate(request, response);
		} else if ("delete".equals(action)) {
			this.doDelete(request, response);
		} else if ("getone".equals(action)) {
			this.doGetone(request, response);
		}

		else {
			// �Ҳ�������404
			RetResult<String> retResult = RetResponse.makeNotfoundRsp("��֪������Ҫ����");
			AjaxJsonUtil.doJsonResponse(retResult, response);
		}
	}
     //��ӱ�ǩ
	protected void doAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.��ȡ����
		String tokenValue = request.getHeader("token");

		AddLabelParam addLabelParam = AjaxJsonUtil.parsePayload2Object(request, AddLabelParam.class);

		String labelName = addLabelParam.getLabelName();
		String labelExplain = addLabelParam.getLabelExplain();
		// 2.����У��
		if (labelName == null || labelName.isEmpty()) {
			RetResult<String> retResult = RetResponse.makeUnvalidateRsp("����У��ʧ�ܣ���ǩ������");
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		}
		// 3.ҵ�����
		if (SystemService.addLabel(Long.valueOf(tokenValue), labelName, labelExplain)) {
			RetResult<String> retResult = RetResponse.makeOKRsp();
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		} else {
			RetResult<String> retResult = RetResponse.makeFailRsp("ʧ�ܣ���ǩ���Ѵ���");
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		}

	}
    
	//���±�ǩ
	protected void doUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.��ȡ����

		UpdataParam updataParam = AjaxJsonUtil.parsePayload2Object(request, UpdataParam.class);

		long id = updataParam.getId();
		String labelExplain = updataParam.getLabelExplain();
		String tokenValue = request.getHeader("token");

		// 2.����У��
		// 3.ҵ�����
		SystemService.updateLabel(Long.valueOf(tokenValue), labelExplain, id);
		RetResult<String> retResult = RetResponse.makeOKRsp();
		AjaxJsonUtil.doJsonResponse(retResult, response);
		return;
	}
    //ɾ����ǩ
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.��ȡ����
		String tokenValue = request.getHeader("token");
		String id = request.getParameter("id");

		// 2.����У��
		// 3.ҵ�����
		if (SystemService.deleteLabel(Long.valueOf(tokenValue), Long.valueOf(id))) {
			RetResult<String> retResult = RetResponse.makeOKRsp();
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		} else {
			RetResult<String> retResult = RetResponse.makeUnauthorizedRsp("ҵ����ʧ�ܣ���ȡȨ��ʧ��");
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		}

	}
    //�鿴��ǩ�б����ߵ�����ǩ��
	protected void doGetone(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.������ȡ
		String tokenValue = request.getHeader("token");
		String id = request.getParameter("id");
		// 2.����У��
		// 3.����
		if (id != null) {// ��ĿV.0�в�û��ʹ�����������Ľӿ�
			VO4Label rtn = SystemService.showOneLabel(Long.valueOf(tokenValue), Long.valueOf(id));
			if (rtn == null) {
				RetResult<String> retResult = RetResponse.makeFailRsp("ҵ����ʧ�ܣ���ȡʧ��");
				AjaxJsonUtil.doJsonResponse(retResult, response);
				return;
			} else {
				RetResult<VO4Label> retResult = RetResponse.makeOKRspWithMsg("��ѯ������Ϣ�ɹ�", rtn);
				AjaxJsonUtil.doJsonResponse(retResult, response);
				return;
			}
		} else {
			List<VO4Label> t = SystemService.showLabelInfo();
			if (t.size() == 0) {
				RetResult<String> retResult = RetResponse.makeUnauthorizedRsp("ҵ����ʧ�ܣ���ȡȨ��ʧ��");
				AjaxJsonUtil.doJsonResponse(retResult, response);
				return;
			} else {
				RetResult<List<VO4Label>> retResult = RetResponse.makeOKRspWithMsg("��ѯ����a��Ϣ�ɹ�", t);
				System.out.println(t.get(0).getDataUpdateAt()+"����֮��");
				AjaxJsonUtil.doJsonResponse(retResult, response);
				return;
			}
		}

	}

}
