package com.haige.hr.xingwei.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haige.hr.core.ajax.AjaxJsonUtil;
import com.haige.hr.core.ret.RetResponse;
import com.haige.hr.core.ret.RetResult;
import com.haige.hr.xingwei.db.dto.T_data_recordDTO;
import com.haige.hr.xingwei.service.ReportService;


/**
 * ��ʾ����Servlet 
 */
@WebServlet("/auth/report/*")
public class ReportServlet extends HttpServlet {
	
	private ReportService reportService = new ReportService();
	//��ȡ��ͼ����
	public String getAction(HttpServletRequest request,String url) {
		String uri = request.getRequestURI(); //��ȡ����·�����磺/open/user/login
		String action = uri.substring(uri.indexOf(url)+url.length()); //��ȡ������ͼ��"login"
		return action ;	}
	
	
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		// ��ʼ��
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//��ȡ��ͼ
		String action = getAction(request,"/auth/report/");
		System.out.println(action);
		if ("common/chart".equals(action)) {
			this.doChart(request, response);
		}else if("common/list".equals(action)) {
			this.doList(request, response);
		}else if("daily/chart".equals(action)) {
			this.doDaily(request, response);
			
		}  
    }
    //���ݱ�ǩId��ʾͨ�ñ���
	protected void doChart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.��ȡ����
		String tokenValue = request.getHeader("token");
		String id = request.getParameter("id");
		//2.����У��
		//3.ҵ�����
		
		String jsonString = "{\r\n" + 
				"	\"code\": 200,\r\n" + 
				"	\"msg\": \"ҵ��ɹ�\",\r\n" + 
				"	\"data\": {\r\n" + 
				"		\"countList\": [%,%,%,%], \r\n" + 
				"		\"chart\": { \r\n" + 
				"\"title\": {\"text\": \"���ܵ���������ͼ\"},"+			  				
				"			\"xAxis\": {\r\n" + 
				"				\"type\":\"category\",\r\n" + 
				"				\"data\":[\"Sun\",\"Mon\", \"Tue\", \"Wed\", \"Thu\", \"Fri\", \"Sat\"]\r\n" + 
				"			},\r\n" + 
				"			\"yAxis\": {\r\n" + 
				"				\"type\":\"value\"\r\n" + 
				"			},\r\n" + 
				"			\"series\": [{\r\n" + 
				"				\"data\": [null,null,null,null,null,null,null],\r\n" + 
				"				\"type\": \"line\"\r\n" + 
				"			}]\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"}";
		 
		 String result = reportService.showChart(jsonString,Long.valueOf(tokenValue),Long.valueOf(id));
		
	    if(result==null) {
	    	RetResult<String> retResult = RetResponse.makeFailRsp("ҵ����ʧ�ܣ���ȡʧ��");
			 AjaxJsonUtil.doJsonResponse(retResult, response);return ;
	    	
	    }else {
	    
	    	response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(result);
			out.close();
	    }
	}
    
	//��ȡ�û���Ϊ�����б�
	protected void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//1.��ȡ����
		String tokenValue = request.getHeader("token");   //��ʱ����
		String searchKeywords = request.getParameter("searchKeywords");
		
		//2.����У��
		//3.ҵ�����
	  List<T_data_recordDTO> list =reportService.showList(searchKeywords);
	
	 	  

	 RetResult<List> retResult = RetResponse.makeOKRspWithMsg("ҵ����ɹ���",list);	
     AjaxJsonUtil.doJsonResponse(retResult, response);return ;
     
}
   //��ȡ�ջ��
	protected void doDaily(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		String tokenValue = request.getHeader("token");

		//2.����У��
		//3.ҵ�����
		
		String json = "{\r\n" + 
				"	\"code\": 200,\r\n" + 
				"	\"msg\": \"ҵ��ɹ�\",\r\n" + 
				"	\"data\": {\r\n" + 
				"		\"countList\": [%,%,%,%], \r\n" + 
			
				
				"		\"chart\": { \r\n" + 
			    "         \"title\": {\"text\": \"���ܵ���������ͼ\"},"+
				"			\"xAxis\": {\r\n" + 
				"				\"type\":\"category\",\r\n" + 
				"				\"data\":[\"Sun\",\"Mon\", \"Tue\", \"Wed\", \"Thu\", \"Fri\", \"Sat\"]\r\n" + 
				"			},\r\n" + 
				"			\"yAxis\": {\r\n" + 
				"				\"type\":\"value\"\r\n" + 
				"			},\r\n" + 
				"			\"series\": [{\r\n" + 
				"				\"data\": [null,null,null,null,null,null,null],\r\n" + 
				"				\"type\": \"line\"\r\n" + 
				"			}]\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"}";
		String result = reportService.showDailyChart(json,Long.valueOf(tokenValue));

		  if(result==null) {
			  RetResult<String> retResult = RetResponse.makeFailRsp("ҵ����ʧ�ܣ���ȡʧ��");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		    }else {
		    	response.setContentType("application/json; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write(result);
				out.close();
		    }
	}	
	
	
	
}
	
	
	