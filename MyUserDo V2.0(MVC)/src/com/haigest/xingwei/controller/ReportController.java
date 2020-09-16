package com.haigest.xingwei.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.haigest.core.ajax.AjaxJsonUtil;
import com.haigest.core.mvc.Controller;
import com.haigest.core.mvc.URLMapping;
import com.haigest.core.ret.RetResponse;
import com.haigest.core.ret.RetResult;
import com.haigest.xingwei.db.dto.T_data_recordDTO;
import com.haigest.xingwei.service.ReportService;
@URLMapping(url = "/auth/report")
public class ReportController  extends Controller {


	private ReportService reportService = new ReportService();
	
	 //根据标签Id显示通用报表
	@URLMapping(url = "/common/chart")
	public RetResult<?> doChart() throws IOException{
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
			    	return RetResponse.makeFailRsp("业务处理失败：获取失败");
					 
			    	
			    }else {
			    	response.setContentType("application/json; charset=utf-8");
					PrintWriter out = response.getWriter();
					out.write(result);
					out.close();
					return null;
			    	

			    }
	} 
	
	//获取用户行为数据列表
	@URLMapping(url = "/common/list")
	public  RetResult<?> doList(){
		//1.获取参数
				String tokenValue = request.getHeader("token");   //暂时不用
				String searchKeywords = request.getParameter("searchKeywords");
				
				//2.参数校验
				//3.业务调用
			  List<T_data_recordDTO> list =reportService.showList(searchKeywords);
			  return RetResponse.makeOKRspWithMsg("业务处理成功啦",list);	
		     
	}
	
	   //获取日活报表
	@URLMapping(url = "/daily/chart")
	public  RetResult<?> doDaily() throws IOException{
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
			  return  RetResponse.makeFailRsp("业务处理失败：获取失败");
		    }else {
		    	
		    	response.setContentType("application/json; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.write(result);
				out.close();
				return null;
		    }
	
	}
}
