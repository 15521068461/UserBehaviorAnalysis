package com.haige.hr.xingwei.service;

import java.util.HashMap;
import java.util.List;

import com.haige.hr.common.algorithm.RondomUtil;
import com.haige.hr.xingwei.db.T_sys_cacheDAO;
import com.haige.hr.xingwei.db.dto.T_sys_cacheDTO;
import com.haige.hr.xingwei.db.T_user_baseDAO;
import com.haige.hr.xingwei.db.dto.T_user_baseDTO;
import com.haige.hr.xingwei.service.bean.VO4Login;



public class UserService {

	private T_user_baseDAO t_user_baseDAO = new T_user_baseDAO();

	private T_sys_cacheDAO t_sys_cacheDAO = new T_sys_cacheDAO();


	HashMap<Long, Long> hashmap = new HashMap<Long, Long>();

	// 登录业务函数
	public VO4Login login(String phone, String pass) {

		// 1业务校验
		// 失败返回202
		// 无，譬如 卡号是否存在的判断

		// 2业务处理
		List<T_user_baseDTO> ls = t_user_baseDAO.findByProperty("USER_MOBILE", phone); // 根据电话号码找到指定用户的DTO
		if (ls.size() == 0) {
			// 卡号是否存在的判断
			return null;
		} else {
			T_user_baseDTO dto = ls.get(0); // 取到指定DTO
			if (dto.getUserPassword().equals(pass)) { // 判断登录密码是否zhe
				VO4Login vo4Login = new VO4Login();
				vo4Login.token = RondomUtil.getToken();
		
				T_sys_cacheDTO t_sys_cacheDTO = new T_sys_cacheDTO(); // new一个DTO对象
				t_sys_cacheDTO.setCacheKey(vo4Login.token + ""); // 把token作为键
				t_sys_cacheDTO.setCacheValue(dto.getDataCreateUserId() + "");// 把用户的ID作为值，从而设置键值对
				//t_sys_cacheDTO.setID(new Long((long) 3)); // 设置该记录的ID
				t_sys_cacheDAO.createTSysCache(t_sys_cacheDTO); // 在缓冲表生成一条记录

				return vo4Login;
			} else
				return null;
		}
	}

	// 自动登录业务函数
	public VO4Login doautologin(Long oldToken) {
         
		//1.业务校验
		//2.业务调用
		List<T_sys_cacheDTO> ts = t_sys_cacheDAO.findByProperty("CACHE_KEY", oldToken);// 根据时间戳找到对应的记录
		if (ts.size() == 0) {
			// 旧token是否存在的判断
			return null;
		} else {
			T_sys_cacheDTO dto = ts.get(0); // 在缓存表中取出该条记录

			VO4Login vo4Login = new VO4Login();
			vo4Login.token = System.currentTimeMillis(); // 生成新的token
			String newToken = vo4Login.token + "";
			dto.setCacheKey(newToken);
			t_sys_cacheDAO.updateTSysCache(dto); // 更新表的数据，新的token取代旧的token
			return vo4Login;
		}

	}

	// 退出登录业务函数
	public void logout(Long token) {

		T_sys_cacheDTO ts = (T_sys_cacheDTO) t_sys_cacheDAO.findByProperty("CACHE_KEY", token).get(0);
		t_sys_cacheDAO.removeTSysCache(ts);
	}

	
	
}
