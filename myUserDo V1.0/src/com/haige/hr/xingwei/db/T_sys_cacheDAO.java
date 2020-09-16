package com.haige.hr.xingwei.db;

import java.util.*;
import javax.sql.rowset.*;
import com.haige.hr.core.db.*;
import com.haige.hr.common.db.*;
import com.haige.hr.common.exception.*;
import com.haige.hr.xingwei.db.dto.*;

/** 
 * t_sys_cache表-数据操作对象
 * @author 灯芯科技 李远念
 */  
public class T_sys_cacheDAO {

	/**
	 * CreateByDTO 插入一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int createTSysCache(T_sys_cacheDTO tSysCache) {
			String sql="insert into t_sys_cache (ID,CACHE_KEY,CACHE_VALUE,DATA_STATUS,DATA_CREATE_AT,DATA_CREATE_USER_ID,DATA_UPDATE_AT,DATA_UPDATE_USER_ID,EXT_FILED) values ("
			+       tSysCache.getId() + ","
			+ "'" + tSysCache.getCacheKey() + "'" + ","
			+ "'" + tSysCache.getCacheValue() + "'" + ","
			+       tSysCache.getDataStatus() + ","
			+       tSysCache.getDataCreateAt()  + ","       //此地方有改动
			+       tSysCache.getDataCreateUserId() + ","
			+        tSysCache.getDataUpdateAt() +  ","     //此地方有改动
			+       tSysCache.getDataUpdateUserId() + ","
			+ "'" + tSysCache.getExtFiled() + "'"
			+")";
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * UpdateByDTO 更新一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int updateTSysCache(T_sys_cacheDTO tSysCache) {
			String sql = "update t_sys_cache set "
			+ "CACHE_KEY ='" + tSysCache.getCacheKey() + "',"
			+ "CACHE_VALUE ='" + tSysCache.getCacheValue() + "',"
			+ "DATA_STATUS =" +tSysCache.getDataStatus() + ","
			+ "DATA_CREATE_AT =" + tSysCache.getDataCreateAt() + ","     //此地方有改动
			+ "DATA_CREATE_USER_ID =" +tSysCache.getDataCreateUserId() + ","
			+ "DATA_UPDATE_AT =" + tSysCache.getDataUpdateAt() + ","   //此地方有改动
			+ "DATA_UPDATE_USER_ID =" +tSysCache.getDataUpdateUserId() + ","
			+ "EXT_FILED ='" + tSysCache.getExtFiled() + "'"
			+ " where ID = " + tSysCache.getId()
			;
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * UpdateByDTOs 更新多条数据
 	 * @author 灯芯科技 李远念
	 */
	public int updateTSysCaches(List<T_sys_cacheDTO> tSysCaches) {
		int updateCount=0;
		Iterator<T_sys_cacheDTO> it = tSysCaches.iterator();
		while(it.hasNext()) {
			T_sys_cacheDTO tSysCache = it.next();
			updateCount += updateTSysCache(tSysCache);
		}
		return updateCount;
	}

	/**
	 * RemoveByPrimaryKey 按主键删除一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeByPrimaryKey(java.lang.Long id) {
			String sql = "delete from t_sys_cache where ID = " + id;
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * RemoveByDTO 按传入DTO删除一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeTSysCache(T_sys_cacheDTO tSysCache) {
		return removeByPrimaryKey(tSysCache.getId());
	}

	/**
	 * RemoveByDTOs 按传入DTO集合删除多条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeTSysCaches(List<T_sys_cacheDTO> tSysCaches)  {
		int updateCount=0;
		Iterator<T_sys_cacheDTO> it = tSysCaches.iterator();
		while(it.hasNext()) {
			T_sys_cacheDTO tSysCache = it.next();
        	updateCount += removeTSysCache(tSysCache);
		}
		return updateCount;
	}

	/**
	 * findByPrimaryKey 按主键查找一条数据
 	 * @author 灯芯科技 李远念
	 */
	public T_sys_cacheDTO findByPrimaryKey(java.lang.Long id) {
		T_sys_cacheDTO tSysCache =  null;
		try{
			String sql = "select * from t_sys_cache where ID = " + id;
			CachedRowSet  rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			if(rs.next()) {
				tSysCache = new T_sys_cacheDTO();
				tSysCache.setId(new Long(rs.getLong("ID")));
				tSysCache.setCacheKey(rs.getString("CACHE_KEY"));
				tSysCache.setCacheValue(rs.getString("CACHE_VALUE"));
				tSysCache.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tSysCache.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tSysCache.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tSysCache.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tSysCache.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tSysCache.setExtFiled(rs.getString("EXT_FILED"));
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return tSysCache;
	}

	/**
	 * findAll 获取全部数据
 	 * @author 灯芯科技 李远念
	 */
	public List<T_sys_cacheDTO> findAll() {
		List<T_sys_cacheDTO> ls=new ArrayList<T_sys_cacheDTO>();
	    try{
			String sql = "select * from t_sys_cache";
			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			while(rs.next()) {
				T_sys_cacheDTO tSysCache = new T_sys_cacheDTO();
				tSysCache.setId(new Long(rs.getLong("ID")));
				tSysCache.setCacheKey(rs.getString("CACHE_KEY"));
				tSysCache.setCacheValue(rs.getString("CACHE_VALUE"));
				tSysCache.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tSysCache.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tSysCache.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tSysCache.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tSysCache.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tSysCache.setExtFiled(rs.getString("EXT_FILED"));
				ls.add(tSysCache);
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
	public List<T_sys_cacheDTO> findByProperty(String propertyName, Object value) {
		List<T_sys_cacheDTO> ls=new ArrayList<T_sys_cacheDTO>();
	    try{
			String sql = "select * from t_sys_cache where " + propertyName + " = '" + value + "'";
			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			while(rs.next()) {
				T_sys_cacheDTO tSysCache = new T_sys_cacheDTO();
				tSysCache.setId(new Long(rs.getLong("ID")));
				tSysCache.setCacheKey(rs.getString("CACHE_KEY"));
				tSysCache.setCacheValue(rs.getString("CACHE_VALUE"));
				tSysCache.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tSysCache.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tSysCache.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tSysCache.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tSysCache.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tSysCache.setExtFiled(rs.getString("EXT_FILED"));
				ls.add(tSysCache);
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return ls;
	}

}
