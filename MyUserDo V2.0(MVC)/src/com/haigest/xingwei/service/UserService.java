package com.haigest.xingwei.service;

import java.util.HashMap;
import java.util.List;

import com.haigest.common.algorithm.RandomUtil;
import com.haigest.xingwei.db.T_sys_cacheDAO;
import com.haigest.xingwei.db.T_user_baseDAO;
import com.haigest.xingwei.db.dto.T_sys_cacheDTO;
import com.haigest.xingwei.db.dto.T_user_baseDTO;
import com.haigest.xingwei.service.bean.VO4Login;



public class UserService {

	private T_user_baseDAO t_user_baseDAO = new T_user_baseDAO();

	private T_sys_cacheDAO t_sys_cacheDAO = new T_sys_cacheDAO();


	HashMap<Long, Long> hashmap = new HashMap<Long, Long>();

	// ��¼ҵ����
	public VO4Login login(String phone, String pass) {

		// 1ҵ��У��
		// ʧ�ܷ���202
		// �ޣ�Ʃ�� �����Ƿ���ڵ��ж�

		// 2ҵ����
		List<T_user_baseDTO> ls = t_user_baseDAO.findByProperty("USER_MOBILE", phone); // ���ݵ绰�����ҵ�ָ���û���DTO
		if (ls.size() == 0) {
			// �����Ƿ���ڵ��ж�
			return null;
		} else {
			T_user_baseDTO dto = ls.get(0); // ȡ��ָ��DTO
			if (dto.getUserPassword().equals(pass)) { // �жϵ�¼�����Ƿ�zhe
				VO4Login vo4Login = new VO4Login();
				vo4Login.token = RandomUtil.getToken();
		    
				T_sys_cacheDTO t_sys_cacheDTO = new T_sys_cacheDTO(); // newһ��DTO����
				t_sys_cacheDTO.setCacheKey(vo4Login.token + ""); // ��token��Ϊ��
				t_sys_cacheDTO.setCacheValue(dto.getDataCreateUserId() + "");// ���û���ID��Ϊֵ���Ӷ����ü�ֵ��
				//t_sys_cacheDTO.setID(new Long((long) 3)); // ���øü�¼��ID
				t_sys_cacheDAO.createTSysCache(t_sys_cacheDTO); // �ڻ��������һ����¼

				return vo4Login;
			} else
				return null;
		}
	}

	// �Զ���¼ҵ����
	public VO4Login doautologin(Long oldToken) {
         
		//1.ҵ��У��
		//2.ҵ�����
		List<T_sys_cacheDTO> ts = t_sys_cacheDAO.findByProperty("CACHE_KEY", oldToken);// ����ʱ����ҵ���Ӧ�ļ�¼
		if (ts.size() == 0) {
			// ��token�Ƿ���ڵ��ж�
			return null;
		} else {
			T_sys_cacheDTO dto = ts.get(0); // �ڻ������ȡ��������¼

			VO4Login vo4Login = new VO4Login();
			vo4Login.token = System.currentTimeMillis(); // �����µ�token
			String newToken = vo4Login.token + "";
			dto.setCacheKey(newToken);
			t_sys_cacheDAO.updateTSysCache(dto); // ���±�����ݣ��µ�tokenȡ���ɵ�token
			return vo4Login;
		}

	}

	// �˳���¼ҵ����
	public void logout(Long token) {

		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		t_sys_cacheDAO.removeTSysCache(ts);
	}

	
	
}
