package com.haige.hr.xingwei.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.haige.hr.common.exception.BaseException;
import com.haige.hr.xingwei.db.T_data_labelDAO;
import com.haige.hr.xingwei.db.dto.T_data_labelDTO;
import com.haige.hr.xingwei.db.dto.T_data_recordDTO;
import com.haige.hr.xingwei.db.T_data_recordDAO;
import com.haige.hr.xingwei.db.T_sys_cacheDAO;
import com.haige.hr.xingwei.db.dto.T_sys_cacheDTO;


public class ReportService {


	private T_sys_cacheDAO t_sys_cacheDAO = new T_sys_cacheDAO();
	private T_data_labelDAO t_data_labelDAO = new T_data_labelDAO();
	private T_data_recordDAO t_data_recordDAO = new T_data_recordDAO();
    
	HashMap<Long, Long> hashmap = new HashMap<Long, Long>();
	
	//显示通用报表bo
	public String showChart(String jsonString,long token, long id){
		//1.业务校验      (判断token算不算业务校验?)
		//2.业务处理      
		String name = null; //标签的名字(用作筛选)
		try {
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		long userId =Long.valueOf(ts.getCacheValue()) ;//通过token获取用户的ID
		T_data_labelDTO t_data_labelDTO = t_data_labelDAO.findByPrimaryKey(id);
		name =t_data_labelDTO.getLabelName();
	    long createrId = t_data_labelDTO.getDataCreateUserId();//获取标签创建者ID
	  //判断2个ID是否相同，判断是否有权限获取报表
	    if(userId!=createrId) {
	    	return null ;  }
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String newJsonString = jsonString;
		System.out.println(name);
		
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(calendar.DAY_OF_WEEK); //获取今天是星期几
		int thisWeekAllData = 0 ;
		
		//替换字符串，因此改变报表数据
		for(int i = week; i>0;i-- ) {
			newJsonString =newJsonString.replaceFirst("null", t_data_recordDAO.findByWeek(i,name)+"");
			thisWeekAllData=thisWeekAllData+t_data_recordDAO.findByWeek(i,name) ;
			System.out.println("a"+i);
		}
		newJsonString =newJsonString.replaceFirst("%", t_data_recordDAO.findByWeek(1,name)+"");
		newJsonString =newJsonString.replaceFirst("%", thisWeekAllData+"");
		newJsonString =newJsonString.replaceFirst("%", t_data_recordDAO.findThisMonth(name)+"");
		newJsonString =newJsonString.replaceFirst("%", t_data_recordDAO.findAll(name).size()+"");
		
		return newJsonString;
	}       
	//获取用户行为数据列表bo
    public List<T_data_recordDTO> showList(String searchKeywords) {
		
		//1.业务校验
    	//2.业务处理
      return t_data_recordDAO.searchKeywords(searchKeywords); 
}
    //获取日活报表bo
    public String showDailyChart(String jsonString,long token){
     //1.业务校验      (判断token算不算业务校验?)
		//2.业务处理      
		try {
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		long userId =Long.valueOf(ts.getCacheValue()) ;

		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String newJsonString = jsonString;
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(calendar.DAY_OF_WEEK); //获取今天是星期几
		
		//替换字符串，因此改变报表数据
		for(int i = week; i>0;i-- ) {
			newJsonString =newJsonString.replaceFirst("null", t_data_recordDAO.findDailyUser(i)+"");
			//thisWeekAllData=thisWeekAllData+t_data_recordDAO.findByWeek(i,) ;
			System.out.println("a"+i);
		}
		
		newJsonString =newJsonString.replaceFirst("%", t_data_recordDAO.findDailyUser(1)+"");
		newJsonString =newJsonString.replaceFirst("%", t_data_recordDAO.findDailyUserThisWeek(week)+"");
		newJsonString =newJsonString.replaceFirst("%", t_data_recordDAO.findDailyUserThisMonth()+"");
		newJsonString =newJsonString.replaceFirst("%", t_data_recordDAO.findDailyUserAll()+"");
		
		return newJsonString;

    }
	
}
