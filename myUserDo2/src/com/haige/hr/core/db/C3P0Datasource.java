package com.haige.hr.core.db;

import java.beans.PropertyVetoException;
import java.sql.*;
import com.haige.hr.common.exception.BaseError;
import com.haige.hr.common.exception.BaseException;
import com.haige.hr.core.constant.JdbcConst;
import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 
 * C3P0数据库连接池【数据源】
 * @author leeyn
 *     
 */

public final class C3P0Datasource {
	private static ComboPooledDataSource dataSource;

	private C3P0Datasource() {
	}

	static {

			dataSource = new ComboPooledDataSource();
			try {
				dataSource.setDriverClass(JdbcConst.JDBC_DRIVER);
			} catch (PropertyVetoException e) {
				throw new BaseError("数据库驱动类找不到："+JdbcConst.JDBC_DRIVER,"系统错误",e);
			}
			try {
				dataSource.setJdbcUrl(JdbcConst.JDBC_URL);
				dataSource.setUser(JdbcConst.JDBC_USERNAME);
				dataSource.setPassword(JdbcConst.JDBC_PASSWORD);
				dataSource.setInitialPoolSize(Integer.parseInt(JdbcConst.C3P0_INITIALPOOLSIZE));
				dataSource.setMinPoolSize(Integer.parseInt(JdbcConst.C3P0_MINPOOLSIZE));
				dataSource.setMaxPoolSize(Integer.parseInt(JdbcConst.C3P0_MAXPOOLSIZE));
				dataSource.setMaxIdleTime(Integer.parseInt(JdbcConst.C3P0_MAXIDLETIME));
				dataSource.setIdleConnectionTestPeriod(Integer.parseInt(JdbcConst.C3P0_IDLECONNECTIONTESTPERIOD));
				dataSource.setAcquireIncrement(Integer.parseInt(JdbcConst.C3P0_ACQUIREINCREMENT));
			}catch (Exception e) {
				throw new BaseError("配置文件信息有误：需要填写整形数据","系统错误",e);
			}
			
	}

	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new BaseException("error","数据库连接获取到","系统异常",e);
		}
	}

}
