package com.haige.hr.common.db;

import java.sql.*;
import javax.sql.rowset.*;

import com.haige.hr.common.exception.BaseException;
import com.sun.rowset.CachedRowSetImpl;
/**
 * 	数据库操作工具类
 * @author leeyn
 *
 */


public class DBUtil{
	//按传入的SQL进行数据库查询操作
	public static CachedRowSet querySQL(Connection conn,String sql){	
		Statement stmt = null;
		ResultSet rs = null;
		CachedRowSet crs=null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			crs = new CachedRowSetImpl();			
			crs.populate(rs);
		}catch(SQLException e){
			throw new BaseException("error","数据库查询有误："+sql,"系统异常",e);
        }finally{
			if(rs!=null){
				try{
					rs.close();	
				}catch(Exception e){
					throw new BaseException("warn","数据库RS未能正常释放",null,e);
				} }
			if(stmt!=null){
				try{
					stmt.close();	
				}catch(Exception e){
					throw new BaseException("warn","数据库STMT未能正常释放",null,e);
				}}
			if(conn!=null){
				try{
					conn.close();	
				}catch(Exception e){
					throw new BaseException("warn","数据库CONN未能正常释放",null,e);
				}
			}
		}		
		return crs;
	}
	
	
	
	//按传入的SQL进行数据库更新操作（单条、无事务）
	public static int updateSQL(Connection conn,String sql){
		int rtn=0;
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			rtn=stmt.executeUpdate(sql);	
		}catch(Exception e){
			throw new BaseException("error","数据库查询有误："+sql,"系统异常",e);
        }finally{

			if(stmt!=null){
				try{
					stmt.close();	
				}catch(Exception e){
					throw new BaseException("warn","数据库STMT未能正常释放",null,e);
				}
			}
			if(conn!=null){
				try{
					conn.close();	
				}catch(Exception e){
					throw new BaseException("warn","数据库CONN未能正常释放",null,e);
				}
			}
		}
		return rtn;
	}
	//按传入的SQL数组进行数据库更新操作（多条、有事务）
	public static int updateSQL(Connection conn,String sql[]){
		int rtn=0;
		Statement stmt = null;
		int i=0;
		try{
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for(i=0;i<sql.length;i++){				
				rtn+=stmt.executeUpdate(sql[i]);
			}			
			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			throw new BaseException("error","数据库操作有误："+sql[i],"系统异常",e);
        }finally{

			if(stmt!=null){
				try{
					stmt.close();	
				}catch(Exception e){
					throw new BaseException("warn","数据库STMT未能正常释放",null,e);
				}
			}
			if(conn!=null){
				try{
					conn.close();	
				}catch(Exception e){
					throw new BaseException("warn","数据库CONN未能正常释放",null,e);
				}
			}
		}
		
		return rtn;
	}
	
}