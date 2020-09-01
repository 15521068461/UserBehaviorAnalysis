package com.haige.hr.xingwei.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import com.haige.hr.common.exception.BaseException;
import com.haige.hr.xingwei.db.T_sys_cacheDAO;
import com.haige.hr.xingwei.db.dto.T_sys_cacheDTO;
import com.haige.hr.xingwei.db.T_user_baseDAO;
import com.haige.hr.xingwei.db.dto.T_user_baseDTO;
import com.haige.hr.xingwei.service.bean.VO4Login;
import com.haige.hr.xingwei.service.bean.VO4ShowUserInfo;
import com.haige.hr.xingwei.web.param.UserInforParam;

public class AccountService {
		
	private T_user_baseDAO t_user_baseDAO = new T_user_baseDAO();
	private T_sys_cacheDAO t_sys_cacheDAO = new T_sys_cacheDAO();

	HashMap<Long, Long> hashmap = new HashMap<Long, Long>();
	
	// 显示用户基本信息业务函数
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

	// 修改用户基本信息业务函数
	public boolean UpdateBase(long token, UserInforParam userInforParam) {
		try {
			//1.业务校验
			//2.业务处理
			T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
			T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());

			// 修改DTO数据
			ls.setUserName(userInforParam.getUserName());
			ls.setUserSex(userInforParam.getUserSex());
			ls.setUserIntro(userInforParam.getUserIntro());
			ls.setUserLogo(userInforParam.getUserLogo());

			Date date = new Date();
			Timestamp timeStamep = new Timestamp(date.getTime());
			ls.setDataUpdateAt(timeStamep); // 加入更新表的时间

			t_user_baseDAO.updateTUserBase(ls); // 更新表
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false ; 

		}

	}
	// 更新密码业务函数
	public boolean updatePassword(String oldPass, String newPass, long token) {
		
		try {
			T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
			T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());
			//1.业务校验
			if (!ls.getUserPassword().equals(oldPass))   //判断旧密码是否正确
				return false ;
			
			//2.业务处理	
			ls.setUserPassword(newPass);
			t_user_baseDAO.updateTUserBase(ls);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
	}

	// 修改手机号第一步业务函数(生成一个token)
	public VO4Login changeMobile1(String mobile, String sms ,long token) {
		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		T_user_baseDTO ls = t_user_baseDAO.findByPrimaryKey(Long.valueOf(ts.getCacheValue()).longValue());
		// 1.业务校验
		if (!ls.getUserMobile().equals(mobile)||!sms.equals("8642"))  //判断手机号和验证码是否填写正确
			return null;
		//2.业务处理
		VO4Login vo4Login = new VO4Login();
		vo4Login.token = System.currentTimeMillis();
		hashmap.put(token, vo4Login.token);
		System.out.println("mobile1Token:"+vo4Login.token);
		return vo4Login;
	}

	// 修改手机号第二步业务函数(判断第一步生成的token)
	public boolean changeMobile2(long token, long mobile1TokenValue, String newMobile, String sms) {

		// 1.业务校验
		if (!sms.equals("8642")) // 判断手机号和验证码是否填写正确
			return false;
		// 2.业务处理
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
