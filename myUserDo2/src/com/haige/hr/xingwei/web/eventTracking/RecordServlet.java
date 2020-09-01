package com.haige.hr.xingwei.web.eventTracking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.haige.hr.core.ajax.AjaxJsonUtil;
import com.haige.hr.core.ret.RetResponse;
import com.haige.hr.core.ret.RetResult;
import com.haige.hr.xingwei.service.RecordService;
import com.haige.hr.xingwei.web.eventTracking.param.RecordParam;


/**
 * Servlet implementation class RecordServlet
 */
@WebServlet("/record")
public class RecordServlet extends HttpServlet {

	  RecordService recordService = new RecordService();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// 初始化
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				doGet(request,response);
}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doPost(request,response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.获取参数
		String recordUserId = request.getHeader("recordUserId");
		String recordIp = request.getHeader("recordIp");
		
        RecordParam recordParam = AjaxJsonUtil.parsePayload2Object(request, RecordParam.class);
		
		//2.参数校验
		
		//(暂时忽略不写)
		
		//3.业务调用
		if(recordService.insertRecord(recordUserId, recordIp, recordParam.getRecordLabels(), recordParam.getDataDetails())){
	
			RetResult<String> retResult = RetResponse.makeOKRspWithMsg("业务处理成功");
			AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		}else {
			RetResult<String> retResult = RetResponse.makeFailRsp("插入数据失败");
			AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		}
		
		
	}

	
	
	
	
	
}
