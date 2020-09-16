package com.haigest.xingwei.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.haigest.xingwei.db.T_data_labelDAO;
import com.haigest.xingwei.db.T_sys_cacheDAO;
import com.haigest.xingwei.db.T_user_baseDAO;
import com.haigest.xingwei.db.dto.T_data_labelDTO;
import com.haigest.xingwei.db.dto.T_sys_cacheDTO;
import com.haigest.xingwei.service.bean.VO4Label;

public class SystemService {

	private T_user_baseDAO t_user_baseDAO = new T_user_baseDAO();

	private T_sys_cacheDAO t_sys_cacheDAO = new T_sys_cacheDAO();
	
	private T_data_labelDAO t_data_labelDAO = new T_data_labelDAO();


	HashMap<Long, Long> hashmap = new HashMap<Long, Long>();
	//��ӱ�ǩbo
public boolean addLabel(long token,String labelName,String labelExplain) {
		
		//1.ҵ��У��
		if(t_data_labelDAO.findByProperty("LABEL_NAME", labelName).size()>0) {//(��ǩ���Ƿ��Ѵ���)
			return false ; 
		}
         
		//2.ҵ����
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		long userId = Long.valueOf(ts.getCacheValue());  //ͨ��token��ȡ�û���ID
//		T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());		
//		long userId = ls.getDataCreateUserId();  
		
		
		T_data_labelDTO t_data_labelDTO= new T_data_labelDTO(); // newһ��DTO����
		 
		
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
	//���±�ǩҵ��bo
	public void updateLabel(long token,String labelExplain,long id){
		//1.ҵ��У��
		
		//2.ҵ����
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
	//ɾ��������ǩbo
	public boolean deleteLabel(long token,long id) {
       //1.ҵ��У��  	    	 
	   //2.ҵ����                 
		try {
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		long userId =Long.valueOf(ts.getCacheValue()) ;//ͨ��token��ȡ�û���ID
		
        T_data_labelDTO t_data_labelDTO = t_data_labelDAO.findByPrimaryKey(id);
	    long createrId = t_data_labelDTO.getDataCreateUserId(); //��ȡ��ǩ������ID
		
	    //�ж�2��ID�Ƿ���ͬ���ж��Ƿ���Ȩ��ɾ��
	    if(userId!=createrId) {
	    	return false ;  }
	    t_data_labelDAO.removeByPrimaryKey(id);
          return true ;  
		}catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
	}
	//�鿴��ǩ�б�bo
	public ArrayList<VO4Label> showLabelInfo() {
		//1.ҵ��У�� 
		//2.ҵ����  
		List<T_data_labelDTO>  dto = t_data_labelDAO.findAll();
		ArrayList<VO4Label> volist = new ArrayList<>();
		for(int i = 0;i<dto.size();i++) {
			VO4Label vo	= new VO4Label() ; 
			vo.setdataCreateAt(dto.get(i).getDataCreateAt());	
			vo.setDataCreateUserId(dto.get(i).getDataCreateUserId());
			vo.setDataUpdateAt(dto.get(i).getDataUpdateAt());
			System.out.println(dto.get(i).getDataUpdateAt()+"�������");
			vo.setDataUpdateUserId(dto.get(i).getDataUpdateUserId());
			vo.setId(dto.get(i).getId());
		    vo.setLabelExplain(dto.get(i).getLabelExplain());
		    vo.setLabelName(dto.get(i).getLabelName());
			vo.setDataStatus(dto.get(i).getDataStatus());
			volist.add(i,vo);
			}
		return volist;
		
	}
	//�鿴������ǩ��Ϣbo
	public VO4Label showOneLabel(long token,long id ) {
		//1.ҵ��У�� 
		//2.ҵ����  
		try {
			T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
			long userId =Long.valueOf(ts.getCacheValue()) ;//ͨ��token��ȡ�û���ID
	        T_data_labelDTO t_data_labelDTO = t_data_labelDAO.findByPrimaryKey(id);
		    long createrId = t_data_labelDTO.getDataCreateUserId();//��ȡ��ǩ������ID
		    //�ж�2��ID�Ƿ���ͬ���ж��Ƿ���Ȩ�޲鿴������ǩ��Ϣ
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
