package com.haigest.xingwei.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.haigest.xingwei.controller.param.UserInforParam;
import com.haigest.xingwei.db.T_sys_cacheDAO;
import com.haigest.xingwei.db.T_user_baseDAO;
import com.haigest.xingwei.db.dto.T_sys_cacheDTO;
import com.haigest.xingwei.db.dto.T_user_baseDTO;
import com.haigest.xingwei.service.bean.VO4Login;
import com.haigest.xingwei.service.bean.VO4ShowUserInfo;

public class AccountService {
		
	private T_user_baseDAO t_user_baseDAO = new T_user_baseDAO();
	private T_sys_cacheDAO t_sys_cacheDAO = new T_sys_cacheDAO();

	HashMap<Long, Long> hashmap = new HashMap<Long, Long>();
	
	// ��ʾ�û�������Ϣҵ����
		public VO4ShowUserInfo Show(long token) {
		        
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());
                
		VO4ShowUserInfo vo4ShowUserInfo = new VO4ShowUserInfo();
				
		vo4ShowUserInfo.setUserIntro(ls.getUserIntro());
		vo4ShowUserInfo.setUserLogo(ls.getUserLogo());
		vo4ShowUserInfo.setUserMobile(ls.getUserMobile());
		vo4ShowUserInfo.setUserName(ls.getUserName());
		vo4ShowUserInfo.setUserSex(ls.getUserSex());
		return vo4ShowUserInfo;
	}

	// �޸��û�������Ϣҵ����
	public boolean UpdateBase(long token, UserInforParam userInforParam) {
		try {
			//1.ҵ��У��
			//2.ҵ����
			T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
			T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());

			// �޸�DTO����
			ls.setUserName(userInforParam.getUserName());
			ls.setUserSex(userInforParam.getUserSex());
			ls.setUserIntro(userInforParam.getUserIntro());
			ls.setUserLogo(userInforParam.getUserLogo());

			Date date = new Date();
			Timestamp timeStamep = new Timestamp(date.getTime());
			ls.setDataUpdateAt(timeStamep); // ������±��ʱ��

			t_user_baseDAO.updateTUserBase(ls); // ���±�
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false ; 

		}

	}
	// ��������ҵ����
	public boolean updatePassword(String oldPass, String newPass, long token) {
		
		try {
			T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
			T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());
			//1.ҵ��У��
			if (!ls.getUserPassword().equals(oldPass))   //�жϾ������Ƿ���ȷ
				return false ;
			
			//2.ҵ����	
			ls.setUserPassword(newPass);
			t_user_baseDAO.updateTUserBase(ls);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
	}

	// �޸��ֻ��ŵ�һ��ҵ����(����һ��token)
	public VO4Login changeMobile1(String mobile, String sms ,long token) {
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());
		// 1.ҵ��У��
		if (!ls.getUserMobile().equals(mobile)||!sms.equals("8642"))  //�ж��ֻ��ź���֤���Ƿ���д��ȷ
			return null;
		//2.ҵ����
		VO4Login vo4Login = new VO4Login();
		vo4Login.token = System.currentTimeMillis();
		hashmap.put(token, vo4Login.token);
		System.out.println("mobile1Token:"+vo4Login.token);
		return vo4Login;
	}

	// �޸��ֻ��ŵڶ���ҵ����(�жϵ�һ�����ɵ�token)
	public boolean changeMobile2(long token, long mobile1TokenValue, String newMobile, String sms) {

		// 1.ҵ��У��
		if (!sms.equals("8642")) // �ж��ֻ��ź���֤���Ƿ���д��ȷ
			return false;
		// 2.ҵ����
		System.out.println(hashmap);
		try {
			System.out.println(hashmap.get(token));
			System.out.println(mobile1TokenValue);
			if (hashmap.get(token).equals(mobile1TokenValue)) {
				
				T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
				T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());
				ls.setUserMobile(newMobile);
				t_user_baseDAO.updateTUserBase(ls);
				hashmap.clear();
				return true;
			}
			return false;
		} catch (Exception e) {
			   return false;
		}
	}
}
