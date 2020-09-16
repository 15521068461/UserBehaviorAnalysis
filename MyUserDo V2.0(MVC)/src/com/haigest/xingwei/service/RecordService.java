package com.haigest.xingwei.service;

import java.sql.Timestamp;
import java.util.Date;

import com.haigest.xingwei.db.T_data_recordDAO;
import com.haigest.xingwei.db.dto.T_data_recordDTO;


public class RecordService {

	T_data_recordDAO t_data_recordDAO = new T_data_recordDAO();
	
	public boolean insertRecord(String recordUserId,String recordIp,String recordLabels,String dataDetails ) {
		//1.ҵ��У��
		//2.ҵ����
		try {
		T_data_recordDTO t_data_recordDTO = new T_data_recordDTO();
		t_data_recordDTO.setRecordUserId(recordUserId);
		t_data_recordDTO.setRecordIp(recordIp);
		t_data_recordDTO.setRecordLabels(recordLabels);
		t_data_recordDTO.setDataDetails(dataDetails);
	    
		Date date = new Date();
		Timestamp timeStamep = new Timestamp(date.getTime());
		
		t_data_recordDTO.setDataCreateAt(timeStamep);
		
		t_data_recordDAO.createTDataRecord(t_data_recordDTO);
		   return true ; 
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	
	
	
	
	
	
}
