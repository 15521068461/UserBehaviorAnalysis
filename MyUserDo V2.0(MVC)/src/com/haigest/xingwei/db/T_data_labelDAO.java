package com.haigest.xingwei.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.haigest.common.db.DBUtil;
import com.haigest.common.exception.BaseException;
import com.haigest.core.db.C3P0Datasource;
import com.haigest.xingwei.db.dto.T_data_labelDTO;

/** 
 * t_data_label表-数据操作对象
 * @author 灯芯科技 李远念
 */  
public class T_data_labelDAO {

	/**
	 * CreateByDTO 插入一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int createTDataLabel(T_data_labelDTO tDataLabel) {
			String sql="insert into t_data_label (ID,LABEL_NAME,LABEL_EXPLAIN,DATA_STATUS,DATA_CREATE_AT,DATA_CREATE_USER_ID,DATA_UPDATE_AT,DATA_UPDATE_USER_ID,EXT_FILED) values ("
			+       tDataLabel.getId() + ","
			+ "'" + tDataLabel.getLabelName() + "'" + ","
			+ "'" + tDataLabel.getLabelExplain() + "'" + ","
			+       tDataLabel.getDataStatus() + ","
			+ "'" + tDataLabel.getDataCreateAt() + "'" + ","
			+       tDataLabel.getDataCreateUserId() + ","
			+ "'" + tDataLabel.getDataUpdateAt() + "'" + ","
			+       tDataLabel.getDataUpdateUserId() + ","
			+ "'" + tDataLabel.getExtFiled() + "'"
			+")";
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * UpdateByDTO 更新一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int updateTDataLabel(T_data_labelDTO tDataLabel) {
			String sql = "update t_data_label set "
			+ "LABEL_NAME ='" + tDataLabel.getLabelName() + "',"
			+ "LABEL_EXPLAIN ='" + tDataLabel.getLabelExplain() + "',"
			+ "DATA_STATUS =" +tDataLabel.getDataStatus() + ","
			+ "DATA_CREATE_AT ='" + tDataLabel.getDataCreateAt() + "',"
			+ "DATA_CREATE_USER_ID =" +tDataLabel.getDataCreateUserId() + ","
			+ "DATA_UPDATE_AT ='" + tDataLabel.getDataUpdateAt() + "',"
			+ "DATA_UPDATE_USER_ID =" +tDataLabel.getDataUpdateUserId() + ","
			+ "EXT_FILED ='" + tDataLabel.getExtFiled() + "'"
			+ " where ID = " + tDataLabel.getId()
			;
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * UpdateByDTOs 更新多条数据
 	 * @author 灯芯科技 李远念
	 */
	public int updateTDataLabels(List<T_data_labelDTO> tDataLabels) {
		int updateCount=0;
		Iterator<T_data_labelDTO> it = tDataLabels.iterator();
		while(it.hasNext()) {
			T_data_labelDTO tDataLabel = it.next();
			updateCount += updateTDataLabel(tDataLabel);
		}
		return updateCount;
	}

	/**
	 * RemoveByPrimaryKey 按主键删除一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeByPrimaryKey(java.lang.Long id) {
			String sql = "delete from t_data_label where ID = " + id;
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * RemoveByDTO 按传入DTO删除一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeTDataLabel(T_data_labelDTO tDataLabel) {
		return removeByPrimaryKey(tDataLabel.getId());
	}

	/**
	 * RemoveByDTOs 按传入DTO集合删除多条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeTDataLabels(List<T_data_labelDTO> tDataLabels)  {
		int updateCount=0;
		Iterator<T_data_labelDTO> it = tDataLabels.iterator();
		while(it.hasNext()) {
			T_data_labelDTO tDataLabel = it.next();
        	updateCount += removeTDataLabel(tDataLabel);
		}
		return updateCount;
	}

	/**
	 * findByPrimaryKey 按主键查找一条数据
 	 * @author 灯芯科技 李远念
	 */
	public T_data_labelDTO findByPrimaryKey(java.lang.Long id) {
		T_data_labelDTO tDataLabel =  null;
		try{
			String sql = "select * from t_data_label where ID = " + id;
			CachedRowSet  rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			if(rs.next()) {
				tDataLabel = new T_data_labelDTO();
				tDataLabel.setId(new Long(rs.getLong("ID")));
				tDataLabel.setLabelName(rs.getString("LABEL_NAME"));
				tDataLabel.setLabelExplain(rs.getString("LABEL_EXPLAIN"));
				tDataLabel.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tDataLabel.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tDataLabel.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tDataLabel.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tDataLabel.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tDataLabel.setExtFiled(rs.getString("EXT_FILED"));
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return tDataLabel;
	}

	/**
	 * findAll 获取全部数据
 	 * @author 灯芯科技 李远念
	 */
	public List<T_data_labelDTO> findAll() {
		List<T_data_labelDTO> ls=new ArrayList<T_data_labelDTO>();
	    try{
			String sql = "select * from t_data_label";
			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			while(rs.next()) {
				T_data_labelDTO tDataLabel = new T_data_labelDTO();
				tDataLabel.setId(new Long(rs.getLong("ID")));
				tDataLabel.setLabelName(rs.getString("LABEL_NAME"));
				tDataLabel.setLabelExplain(rs.getString("LABEL_EXPLAIN"));
				tDataLabel.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tDataLabel.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tDataLabel.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tDataLabel.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tDataLabel.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tDataLabel.setExtFiled(rs.getString("EXT_FILED"));
				ls.add(tDataLabel);
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
	public List<T_data_labelDTO> findByProperty(String propertyName, Object value) {
		List<T_data_labelDTO> ls=new ArrayList<T_data_labelDTO>();
	    try{
			String sql = "select * from t_data_label where " + propertyName + " = '" + value + "'";
			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			while(rs.next()) {
				T_data_labelDTO tDataLabel = new T_data_labelDTO();
				tDataLabel.setId(new Long(rs.getLong("ID")));
				tDataLabel.setLabelName(rs.getString("LABEL_NAME"));
				tDataLabel.setLabelExplain(rs.getString("LABEL_EXPLAIN"));
				tDataLabel.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tDataLabel.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tDataLabel.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tDataLabel.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tDataLabel.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tDataLabel.setExtFiled(rs.getString("EXT_FILED"));
				ls.add(tDataLabel);
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return ls;
	}

}
