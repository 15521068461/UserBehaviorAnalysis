package com.haigest.xingwei.service;

import com.haigest.xingwei.db.T_sys_cacheDAO;
import com.haigest.xingwei.db.T_user_baseDAO;
import com.haigest.xingwei.db.dto.T_sys_cacheDTO;
import com.haigest.xingwei.db.dto.T_user_baseDTO;

public class UploadService {

	private T_user_baseDAO t_user_baseDAO = new T_user_baseDAO();
	private T_sys_cacheDAO t_sys_cacheDAO = new T_sys_cacheDAO();
	
	
	public void upload(long token,String url){
	System.out.println("ллʦ");
	T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
	T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());
	ls.setUserLogo(url);
	
	t_user_baseDAO.updateTUserBase(ls);
}

	
	
	
	
}