package com.haigest.xingwei.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.haigest.common.db.DBUtil;
import com.haigest.common.exception.BaseException;
import com.haigest.core.db.C3P0Datasource;
import com.haigest.xingwei.db.dto.T_user_baseDTO;

/** 
 * t_user_base表-数据操作对象
 * @author 灯芯科技 李远念
 */  
public class T_user_baseDAO {

	/**
	 * CreateByDTO 插入一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int createTUserBase(T_user_baseDTO tUserBase) {
			String sql="insert into t_user_base (ID,USER_MOBILE,USER_PASSWORD,USER_NAME,USER_SEX,USER_LOGO,USER_INTRO,DATA_STATUS,DATA_CREATE_AT,DATA_CREATE_USER_ID,DATA_UPDATE_AT,DATA_UPDATE_USER_ID,EXT_FILED) values ("
			+       tUserBase.getId() + ","
			+ "'" + tUserBase.getUserMobile() + "'" + ","
			+ "'" + tUserBase.getUserPassword() + "'" + ","
			+ "'" + tUserBase.getUserName() + "'" + ","
			+       tUserBase.getUserSex() + ","
			+ "'" + tUserBase.getUserLogo() + "'" + ","
			+ "'" + tUserBase.getUserIntro() + "'" + ","
			+       tUserBase.getDataStatus() + ","
			+ "'" + tUserBase.getDataCreateAt() + "'" + ","
			+       tUserBase.getDataCreateUserId() + ","
			+ "'" + tUserBase.getDataUpdateAt() + "'" + ","
			+       tUserBase.getDataUpdateUserId() + ","
			+ "'" + tUserBase.getExtFiled() + "'"
			+")";
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * UpdateByDTO 更新一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int updateTUserBase(T_user_baseDTO tUserBase) {
			String sql = "update t_user_base set "
			+ "USER_MOBILE ='" + tUserBase.getUserMobile() + "',"
			+ "USER_PASSWORD ='" + tUserBase.getUserPassword() + "',"
			+ "USER_NAME ='" + tUserBase.getUserName() + "',"
			+ "USER_SEX =" +tUserBase.getUserSex() + ","
			+ "USER_LOGO ='" + tUserBase.getUserLogo() + "',"
			+ "USER_INTRO ='" + tUserBase.getUserIntro() + "',"
			+ "DATA_STATUS =" +tUserBase.getDataStatus() + ","
			+ "DATA_CREATE_AT ='" + tUserBase.getDataCreateAt() + "',"
			+ "DATA_CREATE_USER_ID =" +tUserBase.getDataCreateUserId() + ","
			+ "DATA_UPDATE_AT ='" + tUserBase.getDataUpdateAt() + "',"
			+ "DATA_UPDATE_USER_ID =" +tUserBase.getDataUpdateUserId() + ","
			+ "EXT_FILED ='" + tUserBase.getExtFiled() + "'"
			+ " where ID = " + tUserBase.getId();
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * UpdateByDTOs 更新多条数据
 	 * @author 灯芯科技 李远念
	 */
	public int updateTUserBases(List<T_user_baseDTO> tUserBases) {
		int updateCount=0;
		Iterator<T_user_baseDTO> it = tUserBases.iterator();
		while(it.hasNext()) {
			T_user_baseDTO tUserBase = it.next();
			updateCount += updateTUserBase(tUserBase);
		}
		return updateCount;
	}

	/**
	 * RemoveByPrimaryKey 按主键删除一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeByPrimaryKey(java.lang.Long id) {
			String sql = "delete from t_user_base where ID = " + id;
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * RemoveByDTO 按传入DTO删除一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeTUserBase(T_user_baseDTO tUserBase) {
		return removeByPrimaryKey(tUserBase.getId());
	}

	/**
	 * RemoveByDTOs 按传入DTO集合删除多条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeTUserBases(List<T_user_baseDTO> tUserBases)  {
		int updateCount=0;
		Iterator<T_user_baseDTO> it = tUserBases.iterator();
		while(it.hasNext()) {
			T_user_baseDTO tUserBase = it.next();
        	updateCount += removeTUserBase(tUserBase);
		}
		return updateCount;
	}

	/**
	 * findByPrimaryKey 按主键查找一条数据
 	 * @author 灯芯科技 李远念
	 */
	public T_user_baseDTO findByPrimaryKey(java.lang.Long id) {
		T_user_baseDTO tUserBase =  null;
		try{
			String sql = "select * from t_user_base where ID = " + id;
			CachedRowSet  rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			if(rs.next()) {
				tUserBase = new T_user_baseDTO();
				tUserBase.setId(new Long(rs.getLong("ID")));
				tUserBase.setUserMobile(rs.getString("USER_MOBILE"));
				tUserBase.setUserPassword(rs.getString("USER_PASSWORD"));
				tUserBase.setUserName(rs.getString("USER_NAME"));
				tUserBase.setUserSex(new Integer(rs.getInt("USER_SEX")));
				tUserBase.setUserLogo(rs.getString("USER_LOGO"));
				tUserBase.setUserIntro(rs.getString("USER_INTRO"));
				tUserBase.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tUserBase.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tUserBase.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tUserBase.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tUserBase.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tUserBase.setExtFiled(rs.getString("EXT_FILED"));
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return tUserBase;
	}

	/**
	 * findAll 获取全部数据
 	 * @author 灯芯科技 李远念
	 */
	public List<T_user_baseDTO> findAll() {
		List<T_user_baseDTO> ls=new ArrayList<T_user_baseDTO>();
	    try{
			String sql = "select * from t_user_base";
			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			while(rs.next()) {
				T_user_baseDTO tUserBase = new T_user_baseDTO();
				tUserBase.setId(new Long(rs.getLong("ID")));
				tUserBase.setUserMobile(rs.getString("USER_MOBILE"));
				tUserBase.setUserPassword(rs.getString("USER_PASSWORD"));
				tUserBase.setUserName(rs.getString("USER_NAME"));
				tUserBase.setUserSex(new Integer(rs.getInt("USER_SEX")));
				tUserBase.setUserLogo(rs.getString("USER_LOGO"));
				tUserBase.setUserIntro(rs.getString("USER_INTRO"));
				tUserBase.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tUserBase.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tUserBase.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tUserBase.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tUserBase.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tUserBase.setExtFiled(rs.getString("EXT_FILED"));
				ls.add(tUserBase);
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return ls;
	}

	/**
	 * findByProperty 按字段查数据
 	 * @author 灯芯科技 李远念
	 */
	public List<T_user_baseDTO> findByProperty(String propertyName, Object value) {
		List<T_user_baseDTO> ls=new ArrayList<T_user_baseDTO>();
	    try{
			String sql = "select * from t_user_base where " + propertyName + " = '" + value + "'";
			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			while(rs.next()) {
				T_user_baseDTO tUserBase = new T_user_baseDTO();
				tUserBase.setId(new Long(rs.getLong("ID")));
				tUserBase.setUserMobile(rs.getString("USER_MOBILE"));
				tUserBase.setUserPassword(rs.getString("USER_PASSWORD"));
				tUserBase.setUserName(rs.getString("USER_NAME"));
				tUserBase.setUserSex(new Integer(rs.getInt("USER_SEX")));
				tUserBase.setUserLogo(rs.getString("USER_LOGO"));
				tUserBase.setUserIntro(rs.getString("USER_INTRO"));
				tUserBase.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tUserBase.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tUserBase.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tUserBase.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tUserBase.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tUserBase.setExtFiled(rs.getString("EXT_FILED"));
				ls.add(tUserBase);
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return ls;
	}

}
