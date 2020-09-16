依赖包说明

	数据库
	
		JDBC驱动 mysql-connector-java-5.1.5-bin.jar
			驱动类必须是com.mysql.jdbc.Driver
			URL根据不同版本MySQL有不同
			
		连接池 c3p0-0.9.1.2.jar
			https://blog.csdn.net/zhanghanlun/article/details/80918422
			
	日志
		日志规范以及简单实现 commons-logging-1.2.jar		
			https://blog.csdn.net/backbug/article/details/78655664
			https://www.cnblogs.com/hoobey/p/6079057.html
			能够选择使用Log4j（或其他如slf4j等）还是JDK Logging，但是他不依赖Log4j，JDK Logging的API。
			如果项目的classpath中包含了log4j的类库，就会使用log4j，否则就使用JDK Logging。
			使用commons-logging能够灵活的选择使用那些日志方式，而且不需要修改源代码。（类似于JDBC的API接口）
			日志对象需要使用【否则切换log4j为其它日志实现组件要改代码】
				private final static Log logger = LogFactory.getLog(CommonsTest.class);
				
		log4j日志实现组件 log4j-1.2.17.jar
			commons-loggin 发现 有log4j的jar，会自动调用里面的实现
	
	JSON
		阿里JSON工具库 fastjson-1.2.2.jar
			https://www.cnblogs.com/jajian/p/10051901.html
	