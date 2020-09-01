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
 * 显示报表Servlet 
 */
@WebServlet("/auth/report/*")
public class ReportServlet extends HttpServlet {
	
	private ReportService reportService = new ReportService();
	//获取意图函数
	public String getAction(HttpServletRequest request,String url) {
		String uri = request.getRequestURI(); //获取请求路径，如：/open/user/login
		String action = uri.substring(uri.indexOf(url)+url.length()); //获取具体意图，"login"
		return action ;	}
	
	
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		// 初始化
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//获取意图
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
    //根据标签Id显示通用报表
	protected void doChart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.获取参数
		String tokenValue = request.getHeader("token");
		String id = request.getParameter("id");
		//2.参数校验
		//3.业务调用
		
		String jsonString = "{\r\n" + 
				"	\"code\": 200,\r\n" + 
				"	\"msg\": \"业务成功\",\r\n" + 
				"	\"data\": {\r\n" + 
				"		\"countList\": [%,%,%,%], \r\n" + 
				"		\"chart\": { \r\n" + 
				"\"title\": {\"text\": \"这周的数据折线图\"},"+			  				
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
	    	RetResult<String> retResult = RetResponse.makeFailRsp("业务处理失败：获取失败");
			 AjaxJsonUtil.doJsonResponse(retResult, response);return ;
	    	
	    }else {
	    
	    	response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(result);
			out.close();
	    }
	}
    
	//获取用户行为数据列表
	protected void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//1.获取参数
		String tokenValue = request.getHeader("token");   //暂时不用
		String searchKeywords = request.getParameter("searchKeywords");
		
		//2.参数校验
		//3.业务调用
	  List<T_data_recordDTO> list =reportService.showList(searchKeywords);
	
	 	  

	 RetResult<List> retResult = RetResponse.makeOKRspWithMsg("业务处理成功啦",list);	
     AjaxJsonUtil.doJsonResponse(retResult, response);return ;
     
}
   //获取日活报表
	protected void doDaily(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		String tokenValue = request.getHeader("token");

		//2.参数校验
		//3.业务调用
		
		String json = "{\r\n" + 
				"	\"code\": 200,\r\n" + 
				"	\"msg\": \"业务成功\",\r\n" + 
				"	\"data\": {\r\n" + 
				"		\"countList\": [%,%,%,%], \r\n" + 
			
				
				"		\"chart\": { \r\n" + 
			    "         \"title\": {\"text\": \"这周的数据折线图\"},"+
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
			  RetResult<String> retResult = RetResponse.makeFailRsp("业务处理失败：获取失败");
				 AjaxJsonUtil.doJsonResponse(retResult, response);return ;
		    }else {
		    	response.setContentType("application/json; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write(result);
				out.close();
		    }
	}	
	
	
	
}
	
	
	