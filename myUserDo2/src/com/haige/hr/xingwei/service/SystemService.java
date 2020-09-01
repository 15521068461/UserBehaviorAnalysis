package com.haige.hr.xingwei.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.haige.hr.common.exception.BaseException;
import com.haige.hr.xingwei.db.T_data_labelDAO;
import com.haige.hr.xingwei.db.dto.T_data_labelDTO;
import com.haige.hr.xingwei.db.T_data_recordDAO;
import com.haige.hr.xingwei.db.T_sys_cacheDAO;
import com.haige.hr.xingwei.db.dto.T_sys_cacheDTO;
import com.haige.hr.xingwei.db.T_user_baseDAO;
import com.haige.hr.xingwei.db.dto.T_user_baseDTO;
import com.haige.hr.xingwei.service.bean.VO4Label;

public class SystemService {

	private T_user_baseDAO t_user_baseDAO = new T_user_baseDAO();

	private T_sys_cacheDAO t_sys_cacheDAO = new T_sys_cacheDAO();
	
	private T_data_labelDAO t_data_labelDAO = new T_data_labelDAO();


	HashMap<Long, Long> hashmap = new HashMap<Long, Long>();
	//添加标签bo
public boolean addLabel(long token,String labelName,String labelExplain) {
		
		//1.业务校验
		if(t_data_labelDAO.findByProperty("LABEL_NAME", labelName).size()>0) {//(标签名是否已存在)
			return false ; 
		}
         
		//2.业务处理
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		long userId = Long.valueOf(ts.getCacheValue());  //通过token获取用户的ID
//		T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());		
//		long userId = ls.getDataCreateUserId();  
		
		
		T_data_labelDTO t_data_labelDTO= new T_data_labelDTO(); // new一个DTO对象
		 
		
		t_data_labelDTO.setLabelName(labelName);
		t_data_labelDTO.setLabelExplain(labelExplain);
		t_data_labelDTO.setDataCreateUserId(userId);
		t_data_labelDTO.setDataUpdateUserId(userId);
		
		Date date = new Date();
		Timestamp timeStamep = new Timestamp(date.getTime());
		
		t_data_labelDTO.setDataCreateAt(timeStamep);
		t_data_labelDTO.setDataUpdateAt(timeStamep);
		t_data_labelDAO.createTDataLabel(t_data_labelDTO);
		
		return true ; 
	}
	//更新标签业务bo
	public void updateLabel(long token,String labelExplain,long id){
		//1.业务校验
		
		//2.业务处理
			T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
			long userId =Long.valueOf(ts.getCacheValue()) ;
			T_data_labelDTO t_data_labelDTO = t_data_labelDAO.findByPrimaryKey(id);		
		   //long updaterId = t_data_labelDTO.getDataUpdateUserId(); 
		    
			Date date = new Date();
			Timestamp timeStamep = new Timestamp(date.getTime());
			
			 
			t_data_labelDTO.setDataUpdateUserId(userId);
			t_data_labelDTO.setDataUpdateAt(timeStamep);
			t_data_labelDTO.setLabelExplain(labelExplain);
			t_data_labelDAO.updateTDataLabel(t_data_labelDTO);
	}
	//删除单个标签bo
	public boolean deleteLabel(long token,long id) {
       //1.业务校验  	    	 
	   //2.业务处理                 
		try {
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		long userId =Long.valueOf(ts.getCacheValue()) ;//通过token获取用户的ID
		
        T_data_labelDTO t_data_labelDTO = t_data_labelDAO.findByPrimaryKey(id);
	    long createrId = t_data_labelDTO.getDataCreateUserId(); //获取标签创建者ID
		
	    //判断2个ID是否相同，判断是否有权限删除
	    if(userId!=createrId) {
	    	return false ;  }
	    t_data_labelDAO.removeByPrimaryKey(id);
          return true ;  
		}catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
	}
	//查看标签列表bo
	public ArrayList<VO4Label> showLabelInfo() {
		//1.业务校验 
		//2.业务处理  
		List<T_data_labelDTO>  dto = t_data_labelDAO.findAll();
		ArrayList<VO4Label> volist = new ArrayList<>();
		for(int i = 0;i<dto.size();i++) {
			VO4Label vo	= new VO4Label() ; 
			vo.setdataCreateAt(dto.get(i).getDataCreateAt());	
			vo.setDataCreateUserId(dto.get(i).getDataCreateUserId());
			vo.setDataUpdateAt(dto.get(i).getDataUpdateAt());
			System.out.println(dto.get(i).getDataUpdateAt()+"就是这个");
			vo.setDataUpdateUserId(dto.get(i).getDataUpdateUserId());
			vo.setId(dto.get(i).getId());
		    vo.setLabelExplain(dto.get(i).getLabelExplain());
		    vo.setLabelName(dto.get(i).getLabelName());
			vo.setDataStatus(dto.get(i).getDataStatus());
			volist.add(i,vo);
			}
		return volist;
		
	}
	//查看单个标签信息bo
	public VO4Label showOneLabel(long token,long id ) {
		//1.业务校验 
		//2.业务处理  
		try {
			T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
			long userId =Long.valueOf(ts.getCacheValue()) ;//通过token获取用户的ID
	        T_data_labelDTO t_data_labelDTO = t_data_labelDAO.findByPrimaryKey(id);
		    long createrId = t_data_labelDTO.getDataCreateUserId();//获取标签创建者ID
		    //判断2个ID是否相同，判断是否有权限查看单个标签信息
		    if(userId!=createrId) {
		    	return null ;  }
		    T_data_labelDTO  dto=  t_data_labelDAO.findByPrimaryKey(id);
		    VO4Label vo4Label = new VO4Label();
		    vo4Label.setDataCreateUserId(dto.getDataCreateUserId());
		    vo4Label.setDataStatus(dto.getDataStatus());
		    vo4Label.setDataUpdateAt(dto.getDataUpdateAt());
		    vo4Label.setDataUpdateUserId(dto.getDataUpdateUserId());
		    vo4Label.setId(dto.getId());
		    vo4Label.setLabelExplain(dto.getLabelExplain());
		    vo4Label.setLabelName(dto.getLabelName());
		    vo4Label.setdataCreateAt(dto.getDataCreateAt());
		  
	          return vo4Label ;
			}catch (Exception e) {
				e.printStackTrace();
				return null ; 
			}
		
	}
	
	
}
