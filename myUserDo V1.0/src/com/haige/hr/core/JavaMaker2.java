package com.haige.hr.core;



import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.haige.hr.common.config.PropertiesUtil;


public class JavaMaker2 {

	private String driver;
	private String url;
	private String username;
	private String password;

	private String dtopath1;
	private String daopath1;
	private String dtopath;
	private String daopath;
	private String dspath;//���ݿ����ӳع������·��
	private String dbutilpath;//���ݿ�����������·��
	private String expath;//�Զ����쳣��
	private String cfgpath1;//���ó�����
	private String cfgpath;//���ó�����·��
	private String cfgutilpath;//���ù��߰�·��
	private String folder;

	public JavaMaker2() {
		driver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://localhost:3306/atm2";
		username = "root";
		password = "root";
		daopath1 = "com.haige.hr.xingwei.db";
		dtopath1 = daopath1 + ".dto";
		dspath = "com.haige.hr.core.db";
		dbutilpath = "com.haige.hr.common.db";
		expath = "com.haige.hr.common.exception";
		cfgpath1 = "com.haige.hr.core.constant";
		cfgutilpath = "com.haige.hr.common.config";
		dtopath = dtopath1.replace('.', '/');
		daopath = daopath1.replace('.', '/');
		cfgpath = cfgpath1.replace('.', '/');
		// �˴���������ļ����λ��
		folder = System.getProperty("user.dir").replace('\\', '/') + "/src";
	}

	public static void main(String agrs[]) {
		JavaMaker2 jm = new JavaMaker2();
		jm.makeDto();
		jm.makeDao();
		jm.makeConfig("common.properties");
	}
	/**
	 * ����ConfigԴ����
	 * configFile �����src��·�� xxx.properties(����/)
	 * �����ļ��еļ��������� Сд.�ָ� ����
	 * @return
	 */
	public void makeConfig(String configFile) {
           System.out.println("���ҵ�");
		try {
			Properties props = new Properties();
			InputStream inStream = new PropertiesUtil().getClass().getResourceAsStream(
					"/"+configFile);
			Map<String, String> map=null;
			props.load(inStream);
			map=new HashMap<String, String>();
			Enumeration<?> en=props.propertyNames();
			while (en.hasMoreElements()) {
				String key=(String) en.nextElement();
				String property=props.getProperty(key);
				map.put(key, property);
			}
			inStream.close();
			
			File file;
			PrintWriter printwriter = null;
			// ���շ� �ļ���
			String FileName = pointToCamel(configFile, false);			
			file = new File(folder + "/" + cfgpath);
			file.mkdirs();
			// ����Properties.javaԴ�ļ�
			file = new File(folder + "/" + cfgpath + "/" + FileName + ".java");
			printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			printwriter.println("package " + cfgpath1 + ";");
			printwriter.println();	
			printwriter.println("import java.util.Map;");
			printwriter.println("import " + cfgutilpath + ".*;");
			printwriter.println();	
			printwriter.println("/** ");
			printwriter.println(" * " + configFile + "�����ļ�-��������");
			printwriter.println(" * @author ��о�Ƽ� ��Զ��");
			printwriter.println(" */  ");
			printwriter.println("public final class " + FileName + " {");
			printwriter.println();			
			printwriter.println("	private static Map<String, String> map=PropertiesUtil.getProperties(\"/" + configFile + "\");");
			printwriter.println();	
			for(Map.Entry<String, String> entry : map.entrySet()){
				printwriter.println("	public static final String " + camelToUnderline(pointToCamel(entry.getKey(), true)).toUpperCase() + ";");
			}	
			printwriter.println();	
			printwriter.println("	static {");
			for(Map.Entry<String, String> entry : map.entrySet()){
				printwriter.println("		" + camelToUnderline(pointToCamel(entry.getKey(), true)).toUpperCase() + " = map.get(\""+entry.getKey()+"\");");
			}
			printwriter.println("	}");
			printwriter.println();
			printwriter.println("}");
			if(printwriter!=null)printwriter.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	/**
	 * ����DTOԴ����
	 * 
	 * @return
	 */
	public void makeDto() {

		try {
			// �������ݿ�
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, username, password);
			// ��ȡ�����ݱ�
			DatabaseMetaData databasemetadata = connection.getMetaData();
			String as[] = { "TABLE" };
			ResultSet resultset = databasemetadata.getTables("", "", "", as);
			Vector<String> vector = new Vector<String>();
			String tablename;
			// �������ݱ�
			for (; resultset.next(); System.out.println("����DTO-���ݱ�:" + tablename)) {
				tablename = resultset.getString("TABLE_NAME");
				vector.add(tablename);
			}

			File file;
			PrintWriter printwriter = null;
			Statement statement = connection.createStatement();
			for (Iterator<String> iterator = vector.iterator(); iterator.hasNext(); printwriter.flush()) {
				// ��ͷСд�ı��� ���磺czjd_admin
				String tablename1 = (String) iterator.next();				
				// ���շ�ı��� ���磺CzjdAdmin
				String TableName = underlineToCamel(tablename1, false);
				String s2 = "select * from";
				s2 = s2 + " " + tablename1;
				file = new File(folder + "/" + dtopath);
				file.mkdirs();
				// ����DTO.javaԴ�ļ�
				file = new File(folder + "/" + dtopath + "/" + TableName + "DTO.java");
				printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

				resultset = statement.executeQuery(s2);
				// ��ȡ�ֶ���Ϣ
				ResultSetMetaData resultsetmetadata = resultset.getMetaData();
				// �����ֶ�����
				int i = resultsetmetadata.getColumnCount();

				printwriter.println("package " + dtopath1 + ";");
				printwriter.println();
				printwriter.println("/** ");
				printwriter.println(" * " + tablename1 + "��-���ݴ������");
				printwriter.println(" * @author ��о�Ƽ� ��Զ��");
				printwriter.println(" */  ");
				printwriter.println();
				printwriter.print("public class " + TableName + "DTO implements java.io.Serializable");
				printwriter.println(" {");
				printwriter.println();
				printwriter.println("	private static final long serialVersionUID = 1L;");
				printwriter.println();
				printwriter.println("	// field����");
				printwriter.println();
				// ���ݿ��ֶ�����
				// resultsetmetadata.getColumnClassName(j) java.lang.String
				// ���ݿ��ֶ���
				// resultsetmetadata.getColumnName(j) id
				for (int j = 1; j <= i; j++) {
					printwriter.println("	private " + resultsetmetadata.getColumnClassName(j) + " "
							+ underlineToCamel(resultsetmetadata.getColumnName(j), true) + ";");// תС�շ�
				}
				printwriter.println();
				// ���������ֶ�set����
				printwriter.println("	// setter����");
				for (int k = 1; k <= i; k++) {
					printwriter.println();
					printwriter.print("	public void set" + underlineToCamel(resultsetmetadata.getColumnName(k), false)// ת���շ�
							+ "(" + resultsetmetadata.getColumnClassName(k) + " _"
							+ underlineToCamel(resultsetmetadata.getColumnName(k), true) + ")");// תС�շ�
					printwriter.println(" {");
					printwriter.println("		this." + underlineToCamel(resultsetmetadata.getColumnName(k), true)
							+ " = _" + underlineToCamel(resultsetmetadata.getColumnName(k), true) + ";");
					printwriter.println("	}");
				}

				printwriter.println();
				// ���������ֶ�get����
				printwriter.println("	// getter����");
				for (int l = 1; l <= i; l++) {
					printwriter.println();
					printwriter.print("	public " + resultsetmetadata.getColumnClassName(l) + " get"
							+ underlineToCamel(resultsetmetadata.getColumnName(l), false)// ת���շ�
							+ "()");
					printwriter.println(" {");
					printwriter.println(
							"		return this." + underlineToCamel(resultsetmetadata.getColumnName(l), true) + ";");
					printwriter.println("	}");
				}

				printwriter.println();
				printwriter.println("}");

				resultset.close();
			}
			statement.close();
			connection.close();
			if(printwriter!=null)printwriter.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void makeDao() {

		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, username, password);
			DatabaseMetaData databasemetadata = connection.getMetaData();
			String as[] = { "TABLE" };
			ResultSet resultset = databasemetadata.getTables("", "", "", as);
			Vector<String> vector = new Vector<String>();
			String tablename;
			for (; resultset.next(); System.out.println("����DAO-���ݱ�:" + tablename)) {
				tablename = resultset.getString("TABLE_NAME");
				vector.add(tablename);
			}
			File file;
			PrintWriter printwriter = null;
			Statement statement = connection.createStatement();
			for (Iterator<String> iterator = vector.iterator(); iterator.hasNext(); printwriter.flush()) {
				String tablename1 = (String) iterator.next();
				String TableName = underlineToCamel(tablename1, false);																						// ���磺Czjd_admin
				String s2 = "select * from";
				s2 = s2 + " " + tablename1;
				file = new File(folder + "/" + daopath);
				file.mkdirs();
				file = new File(folder + "/" + daopath + "/" + TableName + "DAO.java");
				printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
				printwriter.println("package " + daopath1 + ";");
				printwriter.println();
				printwriter.println("import java.util.*;");
				printwriter.println("import javax.sql.rowset.*;");
				printwriter.println("import " + dspath + ".*;");
				printwriter.println("import " + dbutilpath + ".*;");
				printwriter.println("import " + expath + ".*;");
				printwriter.println("import " + dtopath1 + ".*;");
				printwriter.println();
				printwriter.println("/** ");
				printwriter.println(" * " + tablename1 + "��-���ݲ�������");
				printwriter.println(" * @author ��о�Ƽ� ��Զ��");
				printwriter.println(" */  ");
				printwriter.println("public class " + TableName + "DAO {");
				printwriter.println();
				resultset = statement.executeQuery(s2);
				ResultSetMetaData resultsetmetadata = resultset.getMetaData();
				int i = resultsetmetadata.getColumnCount();


				printwriter.println("	/**");
				printwriter.println("	 * CreateByDTO ����һ������");
				printwriter.println(" 	 * @author ��о�Ƽ� ��Զ��");
				printwriter.println("	 */");
				printwriter.print("	public int create" + TableName + "(" + TableName + "DTO " + underlineToCamel(tablename1, true) + ")");
				printwriter.println(" {");
				
				printwriter.print("			String sql=\"insert into " + tablename1 + " (");
				for (int j = 1; j <= i; j++) {
					printwriter.print(resultsetmetadata.getColumnName(j));
					if (j == i)
						printwriter.print(")");
					else
						printwriter.print(",");
				}
				printwriter.println(" values (\"");
				for (int k = 1; k <= i; k++) {
					printwriter.print("			+ ");
					if (resultsetmetadata.getColumnClassName(k).equals("java.lang.String")
							|| resultsetmetadata.getColumnClassName(k).equals("java.sql.Timestamp")
							|| resultsetmetadata.getColumnClassName(k).equals("java.sql.Date")
							|| resultsetmetadata.getColumnClassName(k).equals("java.sql.Time")) {
						printwriter.print("\"'\" + ");
						printwriter.print(underlineToCamel(tablename1, true) + "." + "get"
								+ underlineToCamel(resultsetmetadata.getColumnName(k), false) + "()");
						printwriter.print(" + \"'\"");
						if (k == i) {
							printwriter.println();
							printwriter.println("			+\")\";");
						} else {
							printwriter.print(" + \",\"");
							printwriter.println();
						}
					} else {
						printwriter.print("      " + underlineToCamel(tablename1, true) + "." + "get"
								+ underlineToCamel(resultsetmetadata.getColumnName(k), false) + "()");
						if (k == i) {
							printwriter.println();
							printwriter.println("        + \")\";");
						} else {
							printwriter.print(" + \",\"");
							printwriter.println();
						}
					}
				}
				printwriter.println("		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);");
				printwriter.println("	}");
				printwriter.println();


				printwriter.println("	/**");
				printwriter.println("	 * UpdateByDTO ����һ������");
				printwriter.println(" 	 * @author ��о�Ƽ� ��Զ��");
				printwriter.println("	 */");
				printwriter.print("	public int update" + TableName + "(" + TableName + "DTO " + underlineToCamel(tablename1, true) + ")");
				printwriter.println(" {");				
				printwriter.println("			String sql = \"update " + tablename1 + " set \"");
				for (int l = 2; l <= i; l++) {
					if (resultsetmetadata.getColumnClassName(l).equals("java.lang.String")
							|| resultsetmetadata.getColumnClassName(l).equals("java.sql.Timestamp")
							|| resultsetmetadata.getColumnClassName(l).equals("java.sql.Date")
							|| resultsetmetadata.getColumnClassName(l).equals("java.sql.Time")) {
						if (l != i) {
							printwriter
									.print("			+ \"" + resultsetmetadata.getColumnName(l) + " ='\" + " + underlineToCamel(tablename1, true)
											+ ".get" + underlineToCamel(resultsetmetadata.getColumnName(l), false) + "()");
							printwriter.print(" + \"',\"");
							printwriter.println();
							continue;
						}
						printwriter.print("			+ \"" + resultsetmetadata.getColumnName(l) + " ='\" + " + underlineToCamel(tablename1, true)
								+ ".get" + underlineToCamel(resultsetmetadata.getColumnName(l), false) + "()");
						printwriter.print(" + \"'\"");
						printwriter.println();
						if (resultsetmetadata.getColumnClassName(1).equals("java.lang.String")
								|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Timestamp")
								|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Date")
								|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Time")) {
							printwriter.println("			+ \" where " + resultsetmetadata.getColumnName(1) + " = '\" + "
									+ underlineToCamel(tablename1, true) + ".get"
									+ underlineToCamel(resultsetmetadata.getColumnName(l), false) + "() + \"'\"");
							printwriter.print("			;");
							printwriter.println();
						} else {
							printwriter.println(
									"			+ \" where " + resultsetmetadata.getColumnName(1) + " = \" + " + underlineToCamel(tablename1, true)
											+ ".get" + underlineToCamel(resultsetmetadata.getColumnName(1), false) + "()");
							printwriter.print("			;");
							printwriter.println();
						}
						continue;
					}
					if (l != i) {
						printwriter.print("			+ \"" + resultsetmetadata.getColumnName(l) + " =\" +" + underlineToCamel(tablename1, true)
								+ ".get" + underlineToCamel(resultsetmetadata.getColumnName(l), false) + "()");
						printwriter.print(" + \",\"");
						printwriter.println();
						continue;
					}
					printwriter.print("			+ \"" + resultsetmetadata.getColumnName(l) + " =\" + " + underlineToCamel(tablename1, true)
							+ ".get" + underlineToCamel(resultsetmetadata.getColumnName(l), false) + "()");
					printwriter.println();
					if (resultsetmetadata.getColumnClassName(1).equals("java.lang.String")
							|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Timestamp")
							|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Date")
							|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Time")) {
						printwriter.println("			+ \" where " + resultsetmetadata.getColumnName(1) + " = '\" + "
								+ underlineToCamel(tablename1, true) + ".get" + underlineToCamel(resultsetmetadata.getColumnName(1), false) + "() + \"'\"");
						printwriter.print("			;");
						printwriter.println();
					} else {
						printwriter.println("			+ \" where " + resultsetmetadata.getColumnName(1) + " = \" + "
								+ underlineToCamel(tablename1, true) + ".get" + underlineToCamel(resultsetmetadata.getColumnName(1), false) + "()");
						printwriter.print("			;");
						printwriter.println();
					}
				}				
				printwriter.println("		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);");
				printwriter.println("	}");
				printwriter.println();


				printwriter.println("	/**");
				printwriter.println("	 * UpdateByDTOs ���¶�������");
				printwriter.println(" 	 * @author ��о�Ƽ� ��Զ��");
				printwriter.println("	 */");
				printwriter.print("	public int update" + TableName + "s(" + "List<" + TableName + "DTO> " + underlineToCamel(tablename1, true) + "s)");
				printwriter.println(" {");
				printwriter.println("		int updateCount=0;");
				printwriter.println("		Iterator<" + TableName + "DTO> it = " + underlineToCamel(tablename1, true) + "s.iterator();");
				printwriter.print("		while(it.hasNext())");
				printwriter.println(" {");
				printwriter.println("			" + TableName + "DTO" + " " + underlineToCamel(tablename1, true) + " = it.next();");
				printwriter.println("			updateCount += update" + TableName + "(" + underlineToCamel(tablename1, true) + ");");
				printwriter.println("		}");
				printwriter.println("		return updateCount;");
				printwriter.println("	}");
				printwriter.println();


				printwriter.println("	/**");
				printwriter.println("	 * RemoveByPrimaryKey ������ɾ��һ������");
				printwriter.println(" 	 * @author ��о�Ƽ� ��Զ��");
				printwriter.println("	 */");
				printwriter.print("	public int removeByPrimaryKey(" + resultsetmetadata.getColumnClassName(1)+" "
						+ underlineToCamel(resultsetmetadata.getColumnName(1), true) + ")");
				printwriter.println(" {");				
				if (resultsetmetadata.getColumnClassName(1).equals("java.lang.String")
						|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Timestamp")
						|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Date")
						|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Time"))
					printwriter.println("			String sql = \"delete from " + tablename1 + " where "
							+ resultsetmetadata.getColumnName(1) + " = '\" + " + underlineToCamel(resultsetmetadata.getColumnName(1), true)
							+ " + \"'\";");
				else
					printwriter.println("			String sql = \"delete from " + tablename1 + " where "
							+ resultsetmetadata.getColumnName(1) + " = \" + " + underlineToCamel(resultsetmetadata.getColumnName(1), true)
							+ ";");

				printwriter.println("		return DBUtil.updateSQL(C3P0Datasource.getConnection(),sql);");
				printwriter.println("	}");
				printwriter.println();


				printwriter.println("	/**");
				printwriter.println("	 * RemoveByDTO ������DTOɾ��һ������");
				printwriter.println(" 	 * @author ��о�Ƽ� ��Զ��");
				printwriter.println("	 */");
				printwriter.print("	public int remove" + TableName + "(" + TableName + "DTO " + underlineToCamel(tablename1, true) + ")");
				printwriter.println(" {");
				printwriter.println("		return removeByPrimaryKey(" + underlineToCamel(tablename1, true) + ".get"
						+ underlineToCamel(resultsetmetadata.getColumnName(1), false) + "());");
				printwriter.println("	}");
				printwriter.println();
				
				
				printwriter.println("	/**");
				printwriter.println("	 * RemoveByDTOs ������DTO����ɾ����������");
				printwriter.println(" 	 * @author ��о�Ƽ� ��Զ��");
				printwriter.println("	 */");
				printwriter.print("	public int remove" + TableName + "s(" + "List<" + TableName + "DTO> " + underlineToCamel(tablename1, true) + "s)");
				printwriter.println("  {");
				printwriter.println("		int updateCount=0;");
				printwriter.println("		Iterator<" + TableName + "DTO> it = " + underlineToCamel(tablename1, true) + "s.iterator();");
				printwriter.print("		while(it.hasNext())");
				printwriter.println(" {");
				printwriter.println("			" + TableName + "DTO" + " " + underlineToCamel(tablename1, true) + " = it.next();");
				printwriter.println("        	updateCount += remove" + TableName + "(" + underlineToCamel(tablename1, true) + ");");
				printwriter.println("		}");
				printwriter.println("		return updateCount;");
				printwriter.println("	}");
				printwriter.println();
				
				
				printwriter.println("	/**");
				printwriter.println("	 * findByPrimaryKey ����������һ������");
				printwriter.println(" 	 * @author ��о�Ƽ� ��Զ��");
				printwriter.println("	 */");
				printwriter.print("	public " + TableName + "DTO" + " findByPrimaryKey" + "("
						+ resultsetmetadata.getColumnClassName(1) + " " + underlineToCamel(resultsetmetadata.getColumnName(1), true) + ")");
				printwriter.println(" {");				
				printwriter.println("		" + TableName + "DTO " + underlineToCamel(tablename1, true) + " =  null;");
				printwriter.println("		try{");				
				if (resultsetmetadata.getColumnClassName(1).equals("java.lang.String")
						|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Timestamp")
						|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Date")
						|| resultsetmetadata.getColumnClassName(1).equals("java.sql.Time"))
					printwriter.println("			String sql = \"select * from " + tablename1 + " where "
							+ resultsetmetadata.getColumnName(1) + " = '\" + " + underlineToCamel(resultsetmetadata.getColumnName(1), true)
							+ " + \"'\"" + ";");
				else
					printwriter.println("			String sql = \"select * from " + tablename1 + " where "
							+ resultsetmetadata.getColumnName(1) + " = \" + " + underlineToCamel(resultsetmetadata.getColumnName(1), true)
							+ ";");
				printwriter.println("			CachedRowSet  rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);");
				printwriter.print("			if(rs.next())");
				printwriter.println(" {");
				printwriter.println("				" + underlineToCamel(tablename1, true) + " = new " + TableName + "DTO();");
				for (int i1 = 1; i1 <= i; i1++) {
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.String"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getString(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.sql.Timestamp"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getTimestamp(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.sql.Date"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getDate(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.sql.Time"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getTime(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Integer"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Integer(rs.getInt(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Long"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Long(rs.getLong(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Float"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Float(rs.getFloat(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Double"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Double(rs.getDouble(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.math.BigDecimal"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getBigDecimal(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.BigDecimal"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Integer(rs.getBigDecimal(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
				}
				printwriter.println("			}");
				printwriter.println("		} catch(Exception sqlex1) {");
				printwriter.println("			throw new BaseException(\"error\",\"���ݽ���������쳣\",\"ϵͳ�쳣\",sqlex1);");
				printwriter.println("		}");
				printwriter.println("		return " + underlineToCamel(tablename1, true) + ";");
				printwriter.println("	}");
				printwriter.println();
				
				
				printwriter.println("	/**");
				printwriter.println("	 * findAll ��ȡȫ������");
				printwriter.println(" 	 * @author ��о�Ƽ� ��Զ��");
				printwriter.println("	 */");
				printwriter.println("	public List<" + TableName + "DTO> findAll() {");
				printwriter.println("		List<" + TableName + "DTO> ls=new ArrayList<" + TableName + "DTO>();");
				printwriter.println("	    try{");				
				printwriter.println("			String sql = \"select * from " + tablename1 + "\";");
				printwriter.println("			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);");
				printwriter.print("			while(rs.next())");
				printwriter.println(" {");
				printwriter.println("				"  + TableName + "DTO " + underlineToCamel(tablename1, true) + " = new " + TableName + "DTO();");
				for (int i1 = 1; i1 <= i; i1++) {
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.String"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getString(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.sql.Timestamp"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getTimestamp(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.sql.Date"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getDate(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.sql.Time"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getTime(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Integer"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Integer(rs.getInt(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Long"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Long(rs.getLong(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Float"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Float(rs.getFloat(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Double"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Double(rs.getDouble(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.math.BigDecimal"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getBigDecimal(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.BigDecimal"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Integer(rs.getBigDecimal(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
				}

				printwriter.println("				ls.add(" + underlineToCamel(tablename1, true) + ");");
				printwriter.println("			}");
				printwriter.println("		} catch(Exception sqlex1) {");
				printwriter.println("			throw new BaseException(\"error\",\"���ݽ���������쳣\",\"ϵͳ�쳣\",sqlex1);");
				printwriter.println("		}");
				printwriter.println("		return ls;");
				printwriter.println("	}");
				printwriter.println();

				printwriter.println("	/**");
				printwriter.println("	 * findByProperty ���ֶβ�����");
				printwriter.println(" 	 * @author ��о�Ƽ� ��Զ��");
				printwriter.println("	 */");
				printwriter.println("	public List<" + TableName + "DTO> findByProperty(String propertyName, Object value) {");
				printwriter.println("		List<" + TableName + "DTO> ls=new ArrayList<" + TableName + "DTO>();");
				printwriter.println("	    try{");
				printwriter.println("			String sql = \"select * from " + tablename1
											+ " where \" + propertyName + \" = '\" + value + \"'\";");
				printwriter.println("			CachedRowSet rs = DBUtil.querySQL(C3P0Datasource.getConnection(),sql);");
				printwriter.print("			while(rs.next())");
				printwriter.println(" {");
				printwriter.println("				" + TableName + "DTO " + underlineToCamel(tablename1, true) + " = new " + TableName + "DTO();");
				for (int i1 = 1; i1 <= i; i1++) {
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.String"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getString(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.sql.Timestamp"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getTimestamp(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.sql.Date"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getDate(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.sql.Time"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getTime(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Integer"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Integer(rs.getInt(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Long"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Long(rs.getLong(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Float"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Float(rs.getFloat(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.Double"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Double(rs.getDouble(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.math.BigDecimal"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(rs.getBigDecimal(\""
								+ resultsetmetadata.getColumnName(i1) + "\"));");
					if (resultsetmetadata.getColumnClassName(i1).equals("java.lang.BigDecimal"))
						printwriter.println("				" + underlineToCamel(tablename1, true) + ".set"
								+ underlineToCamel(resultsetmetadata.getColumnName(i1), false) + "(new Integer(rs.getBigDecimal(\""
								+ resultsetmetadata.getColumnName(i1) + "\")));");
				}
				printwriter.println("				ls.add(" + underlineToCamel(tablename1, true) + ");");
				printwriter.println("			}");
				printwriter.println("		} catch(Exception sqlex1) {");
				printwriter.println("			throw new BaseException(\"error\",\"���ݽ���������쳣\",\"ϵͳ�쳣\",sqlex1);");
				printwriter.println("		}");
				printwriter.println("		return ls;");
				printwriter.println("	}");
				printwriter.println();
				printwriter.println("}");
			}

			resultset.close();
			statement.close();
			connection.close();
			printwriter.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * �»���ת�շ巨
	 * 
	 * @param line       Դ�ַ���
	 * @param smallCamel ��С�շ�,�Ƿ�ΪС�շ�
	 * @return ת������ַ���
	 */
	private static String underlineToCamel(String line, boolean smallCamel) {
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0))
					: Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('_');
			if (index > 0) {
				sb.append(word.substring(1, index).toLowerCase());
			} else {
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}
	
	/**
	 * ��ת�շ巨
	 * 
	 * @param line       Դ�ַ���
	 * @param smallCamel ��С�շ�,�Ƿ�ΪС�շ�
	 * @return ת������ַ���
	 */
	private static String pointToCamel(String line, boolean smallCamel) {
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(.)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0))
					: Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('.');
			if (index > 0) {
				sb.append(word.substring(1, index).toLowerCase());
			} else {
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * �շ巨ת�»���
	 * 
	 * @param line Դ�ַ���
	 * @return ת������ַ���
	 */
	private static String camelToUnderline(String line) {
		if (line == null || "".equals(line)) {
			return "";
		}
		line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(word.toUpperCase());
			sb.append(matcher.end() == line.length() ? "" : "_");
		}
		return sb.toString();
	}
}
