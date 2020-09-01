package com.haige.hr.xingwei.db;

import java.util.*;
import javax.sql.rowset.*;
import com.haige.hr.core.db.*;
import com.haige.hr.common.db.*;
import com.haige.hr.common.exception.*;
import com.haige.hr.xingwei.db.dto.*;

/** 
 * t_data_record表-数据操作对象
 * @author 灯芯科技 李远念
 */  
public class T_data_recordDAO {

	/**
	 * CreateByDTO 插入一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int createTDataRecord(T_data_recordDTO tDataRecord) {
			String sql="insert into t_data_record (ID,RECORD_IP,RECORD_USER_ID,RECORD_LABELS,DATA_DETAILS,DATA_STATUS,DATA_CREATE_AT,DATA_CREATE_USER_ID,DATA_UPDATE_AT,DATA_UPDATE_USER_ID,EXT_FILED) values ("
			+       tDataRecord.getId() + ","
			+ "'" + tDataRecord.getRecordIp() + "'" + ","
			+ "'" + tDataRecord.getRecordUserId() + "'" + ","
			+ "'" + tDataRecord.getRecordLabels() + "'" + ","
			+ "'" + tDataRecord.getDataDetails() + "'" + ","
			+       tDataRecord.getDataStatus() + ","
			+ "'" + tDataRecord.getDataCreateAt() + "'" + ","
			+       tDataRecord.getDataCreateUserId() + ","
			+ "'" + tDataRecord.getDataUpdateAt() + "'" + ","
			+       tDataRecord.getDataUpdateUserId() + ","
			+ "'" + tDataRecord.getExtFiled() + "'"
			+")";
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * UpdateByDTO 更新一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int updateTDataRecord(T_data_recordDTO tDataRecord) {
			String sql = "update t_data_record set "
			+ "RECORD_IP ='" + tDataRecord.getRecordIp() + "',"
			+ "RECORD_USER_ID ='" + tDataRecord.getRecordUserId() + "',"
			+ "RECORD_LABELS ='" + tDataRecord.getRecordLabels() + "',"
			+ "DATA_DETAILS ='" + tDataRecord.getDataDetails() + "',"
			+ "DATA_STATUS =" +tDataRecord.getDataStatus() + ","
			+ "DATA_CREATE_AT ='" + tDataRecord.getDataCreateAt() + "',"
			+ "DATA_CREATE_USER_ID =" +tDataRecord.getDataCreateUserId() + ","
			+ "DATA_UPDATE_AT ='" + tDataRecord.getDataUpdateAt() + "',"
			+ "DATA_UPDATE_USER_ID =" +tDataRecord.getDataUpdateUserId() + ","
			+ "EXT_FILED ='" + tDataRecord.getExtFiled() + "'"
			+ " where ID = " + tDataRecord.getId()
			;
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * UpdateByDTOs 更新多条数据
 	 * @author 灯芯科技 李远念
	 */
	public int updateTDataRecords(List<T_data_recordDTO> tDataRecords) {
		int updateCount=0;
		Iterator<T_data_recordDTO> it = tDataRecords.iterator();
		while(it.hasNext()) {
			T_data_recordDTO tDataRecord = it.next();
			updateCount += updateTDataRecord(tDataRecord);
		}
		return updateCount;
	}

	/**
	 * RemoveByPrimaryKey 按主键删除一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeByPrimaryKey(java.lang.Long id) {
			String sql = "delete from t_data_record where ID = " + id;
		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);
	}

	/**
	 * RemoveByDTO 按传入DTO删除一条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeTDataRecord(T_data_recordDTO tDataRecord) {
		return removeByPrimaryKey(tDataRecord.getId());
	}

	/**
	 * RemoveByDTOs 按传入DTO集合删除多条数据
 	 * @author 灯芯科技 李远念
	 */
	public int removeTDataRecords(List<T_data_recordDTO> tDataRecords)  {
		int updateCount=0;
		Iterator<T_data_recordDTO> it = tDataRecords.iterator();
		while(it.hasNext()) {
			T_data_recordDTO tDataRecord = it.next();
        	updateCount += removeTDataRecord(tDataRecord);
		}
		return updateCount;
	}

	/**
	 * findByPrimaryKey 按主键查找一条数据
 	 * @author 灯芯科技 李远念
	 */
	public T_data_recordDTO findByPrimaryKey(java.lang.Long id) {
		T_data_recordDTO tDataRecord =  null;
		try{
			String sql = "select * from t_data_record where ID = " + id;
			CachedRowSet  rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			if(rs.next()) {
				tDataRecord = new T_data_recordDTO();
				tDataRecord.setId(new Long(rs.getLong("ID")));
				tDataRecord.setRecordIp(rs.getString("RECORD_IP"));
				tDataRecord.setRecordUserId(rs.getString("RECORD_USER_ID"));
				tDataRecord.setRecordLabels(rs.getString("RECORD_LABELS"));
				tDataRecord.setDataDetails(rs.getString("DATA_DETAILS"));
				tDataRecord.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tDataRecord.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tDataRecord.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tDataRecord.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tDataRecord.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tDataRecord.setExtFiled(rs.getString("EXT_FILED"));
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return tDataRecord;
	}

	/**
	 * findAll 获取全部数据
 	 * @author 灯芯科技 李远念
	 */
	public List<T_data_recordDTO> findAll(String name) {
		List<T_data_recordDTO> ls=new ArrayList<T_data_recordDTO>();
	    try{
			String sql = "select * from t_data_record where RECORD_LABELS ='"+ name+ "'";
			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			while(rs.next()) {
				T_data_recordDTO tDataRecord = new T_data_recordDTO();
				tDataRecord.setId(new Long(rs.getLong("ID")));
				tDataRecord.setRecordIp(rs.getString("RECORD_IP"));
				tDataRecord.setRecordUserId(rs.getString("RECORD_USER_ID"));
				tDataRecord.setRecordLabels(rs.getString("RECORD_LABELS"));
				tDataRecord.setDataDetails(rs.getString("DATA_DETAILS"));
				tDataRecord.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tDataRecord.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tDataRecord.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tDataRecord.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tDataRecord.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tDataRecord.setExtFiled(rs.getString("EXT_FILED"));
				ls.add(tDataRecord);
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
	public List<T_data_recordDTO> findByProperty(String propertyName, Object value) {
		List<T_data_recordDTO> ls=new ArrayList<T_data_recordDTO>();
	    try{
			String sql = "select * from t_data_record where " + propertyName + " = '" + value + "'";
			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			while(rs.next()) {
				T_data_recordDTO tDataRecord = new T_data_recordDTO();
				tDataRecord.setId(new Long(rs.getLong("ID")));
				tDataRecord.setRecordIp(rs.getString("RECORD_IP"));
				tDataRecord.setRecordUserId(rs.getString("RECORD_USER_ID"));
				tDataRecord.setRecordLabels(rs.getString("RECORD_LABELS"));
				tDataRecord.setDataDetails(rs.getString("DATA_DETAILS"));
				tDataRecord.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
				tDataRecord.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				tDataRecord.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
				tDataRecord.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
				tDataRecord.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
				tDataRecord.setExtFiled(rs.getString("EXT_FILED"));
				ls.add(tDataRecord);
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return ls;
	}
//searchKeywords
	public List<T_data_recordDTO>  searchKeywords(String Keyword) {
		List<T_data_recordDTO> ls=new ArrayList<T_data_recordDTO>();
	    try{
			String sql = "select * from t_data_record WHERE RECORD_LABELS LIKE '%"+Keyword+"%'";
			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
			while(rs.next()) {
				T_data_recordDTO tDataRecord = new T_data_recordDTO();
				tDataRecord.setId(new Long(rs.getLong("ID")));
				tDataRecord.setRecordIp(rs.getString("RECORD_IP"));
				tDataRecord.setRecordUserId(rs.getString("RECORD_USER_ID"));
				tDataRecord.setDataDetails(rs.getString("DATA_DETAILS"));
				tDataRecord.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
				ls.add(tDataRecord);
			}
		} catch(Exception sqlex1) {
			throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
		}
		return ls;
	}
//	findByWeek
	  public int findByWeek(int a,String name) {
		  List<T_data_recordDTO> ls=new ArrayList<T_data_recordDTO>();
		    try{
				String sql =  "SELECT * FROM t_data_record WHERE TO_DAYS(NOW()) - TO_DAYS(DATA_CREATE_AT) = "+(a - 1) +
						 " and   RECORD_LABELS ='"+ name+ "'";
				CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
				while(rs.next()) {
					T_data_recordDTO tDataRecord = new T_data_recordDTO();
					tDataRecord.setId(new Long(rs.getLong("ID")));
					tDataRecord.setRecordIp(rs.getString("RECORD_IP"));
					tDataRecord.setRecordUserId(rs.getString("RECORD_USER_ID"));
					tDataRecord.setRecordLabels(rs.getString("RECORD_LABELS"));
					tDataRecord.setDataDetails(rs.getString("DATA_DETAILS"));
					tDataRecord.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
					tDataRecord.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
					tDataRecord.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
					tDataRecord.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
					tDataRecord.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
					tDataRecord.setExtFiled(rs.getString("EXT_FILED"));
					ls.add(tDataRecord);
				}
			} catch(Exception sqlex1) {
				throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
			}
			return ls.size();
	  }
//  findThisMonth
	  public int findThisMonth(String name) {
		  List<T_data_recordDTO> ls=new ArrayList<T_data_recordDTO>();
		    try{
				String sql = "SELECT * FROM t_data_record WHERE DATE_FORMAT(DATA_CREATE_AT, '%Y%m') = DATE_FORMAT(CURDATE(), '%Y%m')"+
						" and RECORD_LABELS ='"+ name+ "'";
				CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
				while(rs.next()) {
					T_data_recordDTO tDataRecord = new T_data_recordDTO();
					tDataRecord.setId(new Long(rs.getLong("ID")));
					tDataRecord.setRecordIp(rs.getString("RECORD_IP"));
					tDataRecord.setRecordUserId(rs.getString("RECORD_USER_ID"));
					tDataRecord.setRecordLabels(rs.getString("RECORD_LABELS"));
					tDataRecord.setDataDetails(rs.getString("DATA_DETAILS"));
					tDataRecord.setDataStatus(new Integer(rs.getInt("DATA_STATUS")));
					tDataRecord.setDataCreateAt(rs.getTimestamp("DATA_CREATE_AT"));
					tDataRecord.setDataCreateUserId(new Long(rs.getLong("DATA_CREATE_USER_ID")));
					tDataRecord.setDataUpdateAt(rs.getTimestamp("DATA_UPDATE_AT"));
					tDataRecord.setDataUpdateUserId(new Long(rs.getLong("DATA_UPDATE_USER_ID")));
					tDataRecord.setExtFiled(rs.getString("EXT_FILED"));
					ls.add(tDataRecord);
				}
			} catch(Exception sqlex1) {
				throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
			}
			return ls.size();	  
	  }
//	findDailyUser
	public int findDailyUser(int a) {
		int rowCount = 0 ; 
		try{
    String sql="SELECT count(distinct RECORD_USER_ID) FROM t_data_record WHERE TO_DAYS(NOW()) - TO_DAYS(DATA_CREATE_AT) ="+(a - 1);
    CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
    while(rs.next()) {
    	 rowCount = rs.getInt("count(distinct RECORD_USER_ID)");
    	  System.out.println("返回的结果为:"+rowCount);
    	}
    
    
		  } catch(Exception sqlex1) {
				throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
			}
		  return rowCount ;
	  } 
//findDailyUserThisWeek{
	public int findDailyUserThisWeek(int week) {
		int rowCount = 0;
		try {
		String sql = "SELECT count(distinct RECORD_USER_ID) FROM t_data_record where DATE_SUB(CURDATE(), INTERVAL "
				+ (week - 1) + " DAY) <= DATA_CREATE_AT ";
		CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
		while (rs.next()) {
			rowCount = rs.getInt("count(distinct RECORD_USER_ID)");
			System.out.println("返回的结果为:" + rowCount);
		}
		 } catch(Exception sqlex1) {
				throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
			}
		return rowCount;
	}
    //findDailyUserThisMonth
	public int findDailyUserThisMonth() {
		int rowCount = 0;
		try {
		String sql = "SELECT count(distinct RECORD_USER_ID) FROM t_data_record WHERE DATE_FORMAT(DATA_CREATE_AT, '%Y%m') = DATE_FORMAT(CURDATE(), '%Y%m')";
		CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
		while (rs.next()) {
			rowCount = rs.getInt("count(distinct RECORD_USER_ID)");
			System.out.println("返回的结果为:" + rowCount);
		}
		 } catch(Exception sqlex1) {
				throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
			}
		return rowCount;
	}
	
	//findDailyUserAll
	public int findDailyUserAll() {
		
		int rowCount = 0;
		try {
		String sql = "select count(distinct RECORD_USER_ID) from t_data_record ";
		CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);
		while (rs.next()) {
			rowCount = rs.getInt("count(distinct RECORD_USER_ID)");
			System.out.println("返回的结果为:" + rowCount);
		}
		 } catch(Exception sqlex1) {
				throw new BaseException("error","数据结果集处理异常","系统异常",sqlex1);
			}
		return rowCount;
		
	}
	
	
	
}
