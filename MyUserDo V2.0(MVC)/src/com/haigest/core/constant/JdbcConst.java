package com.haigest.core.constant;

import java.util.Map;
import com.haigest.common.config.PropertiesUtil;

/**
 * JDBC配置信息
 * @author leeyn
 *
 */
public final class JdbcConst {
	
	private static Map<String, String> map=PropertiesUtil.getProperties("/jdbc.properties");

	public static final String JDBC_PASSWORD;
	public static final String C3P0_MINPOOLSIZE;
	public static final String C3P0_MAXPOOLSIZE;
	public static final String C3P0_MAXIDLETIME;
	public static final String JDBC_USERNAME;
	public static final String C3P0_INITIALPOOLSIZE;
	public static final String C3P0_ACQUIREINCREMENT;
	public static final String C3P0_IDLECONNECTIONTESTPERIOD;
	public static final String JDBC_URL;
	public static final String JDBC_DRIVER;

	static {
		JDBC_PASSWORD = map.get("jdbc.password");
		C3P0_MINPOOLSIZE = map.get("c3p0.minPoolSize");
		C3P0_MAXPOOLSIZE = map.get("c3p0.maxPoolSize");
		C3P0_MAXIDLETIME = map.get("c3p0.maxIdleTime");
		JDBC_USERNAME = map.get("jdbc.username");
		C3P0_INITIALPOOLSIZE = map.get("c3p0.initialPoolSize");
		C3P0_ACQUIREINCREMENT = map.get("c3p0.acquireIncrement");
		C3P0_IDLECONNECTIONTESTPERIOD = map.get("c3p0.idleConnectionTestPeriod");
		JDBC_URL = map.get("jdbc.url");
		JDBC_DRIVER = map.get("jdbc.driver");
	}
	
}
