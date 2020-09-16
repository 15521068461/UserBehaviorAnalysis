package com.haigest.xingwei.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.haigest.xingwei.db.T_data_labelDAO;
import com.haigest.xingwei.db.T_data_recordDAO;
import com.haigest.xingwei.db.T_sys_cacheDAO;
import com.haigest.xingwei.db.dto.T_data_labelDTO;
import com.haigest.xingwei.db.dto.T_data_recordDTO;
import com.haigest.xingwei.db.dto.T_sys_cacheDTO;


public class ReportService {


	private T_sys_cacheDAO t_sys_cacheDAO = new T_sys_cacheDAO();
	private T_data_labelDAO t_data_labelDAO = new T_data_labelDAO();
	private T_data_recordDAO t_data_recordDAO = new T_data_recordDAO();
    
	HashMap<Long, Long> hashmap = new HashMap<Long, Long>();
	
	//��ʾͨ�ñ���bo
	public String showChart(String jsonString,long token, long id){
		//1.ҵ��У��      (�ж�token�㲻��ҵ��У��?)
		//2.ҵ����      
		String name = null; //��ǩ������(����ɸѡ)
		try {
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		long userId =Long.valueOf(ts.getCacheValue()) ;//ͨ��token��ȡ�û���ID
		T_data_labelDTO t_data_labelDTO = t_data_labelDAO.findByPrimaryKey(id);
		name =t_data_labelDTO.getLabelName();
	    long createrId = t_data_labelDTO.getDataCreateUserId();//��ȡ��ǩ������ID
	  //�ж�2��ID�Ƿ���ͬ���ж��Ƿ���Ȩ�޻�ȡ����
	    if(userId!=createrId) {
	    	return null ;  }
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String newJsonString = jsonString;
		System.out.println(name);
		
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(calendar.DAY_OF_WEEK); //��ȡ���������ڼ�
		int thisWeekAllData = 0 ;
		
		//�滻�ַ�������˸ı䱨������
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
	//��ȡ�û���Ϊ�����б�bo
    public List<T_data_recordDTO> showList(String searchKeywords) {
		
		//1.ҵ��У��
    	//2.ҵ����
      return t_data_recordDAO.searchKeywords(searchKeywords); 
}
    //��ȡ�ջ��bo
    public String showDailyChart(String jsonString,long token){
     //1.ҵ��У��      (�ж�token�㲻��ҵ��У��?)
		//2.ҵ����      
		try {
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		long userId =Long.valueOf(ts.getCacheValue()) ;

		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String newJsonString = jsonString;
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(calendar.DAY_OF_WEEK); //��ȡ���������ڼ�
		
		//�滻�ַ�������˸ı䱨������
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
