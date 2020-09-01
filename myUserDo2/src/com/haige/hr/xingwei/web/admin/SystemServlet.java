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
 *   标签管理Servlet
 */
@WebServlet("/auth/system/label/*")
public class SystemServlet extends HttpServlet {

	private SystemService SystemService = new SystemService();
    //获取用户意图函数
	public String getAction(HttpServletRequest request, String url) {
		String uri = request.getRequestURI(); // 获取请求路径，如：/open/user/login
		String action = uri.substring(uri.indexOf(url) + url.length()); // 获取具体意图，"login"
		return action;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 初始化
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 获取意图
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
			// 找不到返回404
			RetResult<String> retResult = RetResponse.makeNotfoundRsp("不知道你想要干嘛");
			AjaxJsonUtil.doJsonResponse(retResult, response);
		}
	}
     //添加标签
	protected void doAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.获取参数
		String tokenValue = request.getHeader("token");

		AddLabelParam addLabelParam = AjaxJsonUtil.parsePayload2Object(request, AddLabelParam.class);

		String labelName = addLabelParam.getLabelName();
		String labelExplain = addLabelParam.getLabelExplain();
		// 2.参数校验
		if (labelName == null || labelName.isEmpty()) {
			RetResult<String> retResult = RetResponse.makeUnvalidateRsp("参数校验失败：标签名必填");
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		}
		// 3.业务调用
		if (SystemService.addLabel(Long.valueOf(tokenValue), labelName, labelExplain)) {
			RetResult<String> retResult = RetResponse.makeOKRsp();
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		} else {
			RetResult<String> retResult = RetResponse.makeFailRsp("失败，标签名已存在");
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		}

	}
    
	//更新标签
	protected void doUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.获取参数

		UpdataParam updataParam = AjaxJsonUtil.parsePayload2Object(request, UpdataParam.class);

		long id = updataParam.getId();
		String labelExplain = updataParam.getLabelExplain();
		String tokenValue = request.getHeader("token");

		// 2.参数校验
		// 3.业务调用
		SystemService.updateLabel(Long.valueOf(tokenValue), labelExplain, id);
		RetResult<String> retResult = RetResponse.makeOKRsp();
		AjaxJsonUtil.doJsonResponse(retResult, response);
		return;
	}
    //删除标签
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.获取参数
		String tokenValue = request.getHeader("token");
		String id = request.getParameter("id");

		// 2.参数校验
		// 3.业务调用
		if (SystemService.deleteLabel(Long.valueOf(tokenValue), Long.valueOf(id))) {
			RetResult<String> retResult = RetResponse.makeOKRsp();
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		} else {
			RetResult<String> retResult = RetResponse.makeUnauthorizedRsp("业务处理失败：获取权限失败");
			AjaxJsonUtil.doJsonResponse(retResult, response);
			return;
		}

	}
    //查看标签列表（或者单个标签）
	protected void doGetone(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.参数获取
		String tokenValue = request.getHeader("token");
		String id = request.getParameter("id");
		// 2.参数校验
		// 3.调用
		if (id != null) {// 项目V.0中并没有使用这个方法里的接口
			VO4Label rtn = SystemService.showOneLabel(Long.valueOf(tokenValue), Long.valueOf(id));
			if (rtn == null) {
				RetResult<String> retResult = RetResponse.makeFailRsp("业务处理失败：获取失败");
				AjaxJsonUtil.doJsonResponse(retResult, response);
				return;
			} else {
				RetResult<VO4Label> retResult = RetResponse.makeOKRspWithMsg("查询基本信息成功", rtn);
				AjaxJsonUtil.doJsonResponse(retResult, response);
				return;
			}
		} else {
			List<VO4Label> t = SystemService.showLabelInfo();
			if (t.size() == 0) {
				RetResult<String> retResult = RetResponse.makeUnauthorizedRsp("业务处理失败：获取权限失败");
				AjaxJsonUtil.doJsonResponse(retResult, response);
				return;
			} else {
				RetResult<List<VO4Label>> retResult = RetResponse.makeOKRspWithMsg("查询基本a信息成功", t);
				System.out.println(t.get(0).getDataUpdateAt()+"还是之后");
				AjaxJsonUtil.doJsonResponse(retResult, response);
				return;
			}
		}

	}

}
