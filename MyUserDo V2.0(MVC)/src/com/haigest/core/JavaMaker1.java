package com.haigest.core;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *    生产未优化过的DAO/DTO源码【仅供学习，生产请用JavaMaker2】
 * @author 灯芯科技 李远念
 *
 */
public class JavaMaker1 {

	private String driver;
	private String url;
	private String username;
	private String password;

	private String dtopath1;
	private String daopath1;
	private String dtopath;
	private String daopath;
	private String folder;

	public JavaMaker1() {
		driver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://localhost:3306/demo_yunyin";
		username = "root";
		password = "root";
		daopath1 = "com.haigest.yunyin.db";
		dtopath1 = daopath1 + ".dto";
		dtopath = dtopath1.replace('.', '/');
		daopath = daopath1.replace('.', '/');
		// 此处设置输出文件存放位置
		folder = System.getProperty("user.dir").replace('\\', '/') + "/src";
	}

	public static void main(String agrs[]) {
		JavaMaker1 jm = new JavaMaker1();
		jm.makeDto();
		jm.makeDao();
	}

	/**
	 * 生成DTO源代码
	 * 
	 * @return
	 */
	public void makeDto() {

		try {
			// 连接数据库
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, username, password);
			// 获取到数据表
			DatabaseMetaData databasemetadata = connection.getMetaData();
			String as[] = { "TABLE" };
			ResultSet resultset = databasemetadata.getTables("", "", "", as);
			Vector<String> vector = new Vector<String>();
			String tablename;
			// 遍历数据表
			for (; resultset.next(); System.out.println("生成DTO-数据表:" + tablename)) {
				tablename = resultset.getString("TABLE_NAME");
				vector.add(tablename);
			}

			File file;
			PrintWriter printwriter = null;
			Statement statement = connection.createStatement();
			for (Iterator<String> iterator = vector.iterator(); iterator.hasNext(); printwriter.flush()) {
				// 开头小写的表名 例如：czjd_admin
				String tablename1 = (String) iterator.next();				
				// 大驼峰的表名 例如：CzjdAdmin
				String TableName = underlineToCamel(tablename1, false);
				String s2 = "select * from";
				s2 = s2 + " " + tablename1;
				file = new File(folder + "/" + dtopath);
				file.mkdirs();
				// 生成DTO.java源文件
				file = new File(folder + "/" + dtopath + "/" + TableName + "DTO.java");
				printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

				resultset = statement.executeQuery(s2);
				// 获取字段信息
				ResultSetMetaData resultsetmetadata = resultset.getMetaData();
				// 数据字段总数
				int i = resultsetmetadata.getColumnCount();

				printwriter.println("package " + dtopath1 + ";");
				printwriter.println();
				
				printwriter.println("/** ");
				printwriter.println(" * " + tablename1 + "表-数据传输对象");
				printwriter.println(" * @author 灯芯科技 李远念");
				printwriter.println(" */  ");

				printwriter.print("public class " + TableName + "DTO implements java.io.Serializable");
				printwriter.println(" {");
				printwriter.println();
				printwriter.println("	private static final long serialVersionUID = 1L;");
				printwriter.println();
				printwriter.println("	// field属性");
				printwriter.println();
				// 数据库字段类型
				// resultsetmetadata.getColumnClassName(j) java.lang.String
				// 数据库字段名
				// resultsetmetadata.getColumnName(j) id
				for (int j = 1; j <= i; j++) {
					printwriter.println("	private " + resultsetmetadata.getColumnClassName(j) + " "
							+ underlineToCamel(resultsetmetadata.getColumnName(j), true) + ";");// 转小驼峰
				}
				printwriter.println();
				// 生成所有字段set方法
				printwriter.println("	// setter方法");
				for (int k = 1; k <= i; k++) {
					printwriter.println();
					printwriter.print("	public void set" + underlineToCamel(resultsetmetadata.getColumnName(k), false)// 转大驼峰
							+ "(" + resultsetmetadata.getColumnClassName(k) + " _"
							+ underlineToCamel(resultsetmetadata.getColumnName(k), true) + ")");// 转小驼峰
					printwriter.println(" {");
					printwriter.println("		this." + underlineToCamel(resultsetmetadata.getColumnName(k), true)
							+ " = _" + underlineToCamel(resultsetmetadata.getColumnName(k), true) + ";");
					printwriter.println("	}");
				}

				printwriter.println();
				// 生成所有字段get方法
				printwriter.println("	// getter方法");
				for (int l = 1; l <= i; l++) {
					printwriter.println();
					printwriter.print("	public " + resultsetmetadata.getColumnClassName(l) + " get"
							+ underlineToCamel(resultsetmetadata.getColumnName(l), false)// 转大驼峰
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
			for (; resultset.next(); System.out.println("生成DAO-数据表:" + tablename)) {
				tablename = resultset.getString("TABLE_NAME");
				vector.add(tablename);
			}
			File file;
			PrintWriter printwriter = null;
			Statement statement = connection.createStatement();
			for (Iterator<String> iterator = vector.iterator(); iterator.hasNext(); printwriter.flush()) {
				String tablename1 = (String) iterator.next();
				String TableName = underlineToCamel(tablename1, false);																						// 例如：Czjd_admin
				String s2 = "select * from";
				s2 = s2 + " " + tablename1;
				file = new File(folder + "/" + daopath);
				file.mkdirs();
				file = new File(folder + "/" + daopath + "/" + TableName + "DAO.java");
				printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
				printwriter.println("package " + daopath1 + ";");
				printwriter.println();
				printwriter.println("import java.sql.*;");
				printwriter.println("import java.util.*;");
				printwriter.println("import " + dtopath1 + ".*;");
				printwriter.println();
				printwriter.println("/** ");
				printwriter.println(" * " + tablename1 + "表-数据操作对象");
				printwriter.println(" * @author 灯芯科技 李远念");
				printwriter.println(" */  ");
				printwriter.println("public class " + TableName + "DAO {");
				printwriter.println();
				resultset = statement.executeQuery(s2);
				ResultSetMetaData resultsetmetadata = resultset.getMetaData();
				int i = resultsetmetadata.getColumnCount();
				printwriter.println("	String driver = \"" + driver + "\";");
				printwriter.println("	String url = \"" + url + "\";");
				printwriter.println("	String username = \"" + username + "\";");
				printwriter.println("	String password = \"" + password + "\";");
				printwriter.println();
				printwriter.println("	// CreateByDTO 插入一条数据");
				printwriter.print("	public int create" + TableName + "(" + TableName + "DTO " + underlineToCamel(tablename1, true) + ")");
				printwriter.println(" {");
				printwriter.println("		Connection con=null;");
				printwriter.println("		Statement  stmt=null;");
				//printwriter.println("		ResultSet  rs=null;");
				printwriter.println("		try{");
				printwriter.println("			Class.forName(driver);");
				printwriter.println("			con = DriverManager.getConnection(url,username,password);");
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
				printwriter.println("			stmt = con.createStatement();");
				printwriter.println("			return stmt.executeUpdate(sql);");
				printwriter.println("		} catch(Exception sqlex1) {");
				printwriter.println("			sqlex1.printStackTrace();");
				printwriter.println("			return 0;");
				printwriter.println("		} finally {");
				printwriter.println("			if (stmt != null) { ");
				printwriter.println("				try {");
				printwriter.println("					stmt.close();");
				printwriter.println("				} catch(SQLException sqlex2) {");
				printwriter.println("					sqlex2.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("			if (con != null) { ");
				printwriter.println("				try {");
				printwriter.println("					con.close();");
				printwriter.println("				} catch(SQLException sqlex3) {");
				printwriter.println("					sqlex3.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("		}");
				printwriter.println("	}");
				printwriter.println();
				printwriter.println("	// UpdateByDTO 更新一条数据");
				printwriter.print("	public int update" + TableName + "(" + TableName + "DTO " + underlineToCamel(tablename1, true) + ")");
				printwriter.println(" {");
				printwriter.println("		Connection con=null;");
				printwriter.println("		Statement  stmt=null;");
				//printwriter.println("		ResultSet  rs=null;");
				printwriter.println("		try{");
				printwriter.println("			Class.forName(driver);");
				printwriter.println("			con = DriverManager.getConnection(url,username,password);");
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
				printwriter.println("			stmt = con.createStatement();");
				printwriter.println("			return stmt.executeUpdate(sql);");
				printwriter.println("		} catch(Exception sqlex1) {");
				printwriter.println("			sqlex1.printStackTrace();");
				printwriter.println("			return 0;");
				printwriter.println("		} finally {");
				printwriter.println("			if (stmt != null) { ");
				printwriter.println("				try {");
				printwriter.println("					stmt.close();");
				printwriter.println("				} catch(SQLException sqlex2) {");
				printwriter.println("					sqlex2.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("			if (con != null) { ");
				printwriter.println("				try {");
				printwriter.println("					con.close();");
				printwriter.println("				} catch(SQLException sqlex3) {");
				printwriter.println("					sqlex3.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("		}");
				printwriter.println("	}");
				printwriter.println();
				printwriter.println("	// UpdateByDTOs 更新多条数据");
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
				printwriter.println("	// RemoveByPrimaryKey 按主键删除一条数据");
				printwriter.print("	public int removeByPrimaryKey(" + resultsetmetadata.getColumnClassName(1)+" "
						+ underlineToCamel(resultsetmetadata.getColumnName(1), true) + ")");
				printwriter.println(" {");
				printwriter.println("		Connection con=null;");
				printwriter.println("		Statement  stmt=null;");
				//printwriter.println("		ResultSet  rs=null;");
				printwriter.println("		try{");
				printwriter.println("			Class.forName(driver);");
				printwriter.println("			con = DriverManager.getConnection(url,username,password);");
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

				printwriter.println("			stmt = con.createStatement();");
				printwriter.println("			return stmt.executeUpdate(sql);");
				printwriter.println("		} catch(Exception sqlex1) {");
				printwriter.println("			sqlex1.printStackTrace();");
				printwriter.println("			return 0;");
				printwriter.println("		} finally {");
				printwriter.println("			if (stmt != null) { ");
				printwriter.println("				try {");
				printwriter.println("					stmt.close();");
				printwriter.println("				} catch(SQLException sqlex2) {");
				printwriter.println("					sqlex2.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("			if (con != null) { ");
				printwriter.println("				try {");
				printwriter.println("					con.close();");
				printwriter.println("				} catch(SQLException sqlex3) {");
				printwriter.println("					sqlex3.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("		}");
				printwriter.println("	}");
				printwriter.println();
				printwriter.println("	// RemoveByDTO 按传入DTO删除一条数据");
				printwriter.print("	public int remove" + TableName + "(" + TableName + "DTO " + underlineToCamel(tablename1, true) + ")");
				printwriter.println(" {");
				printwriter.println("		return removeByPrimaryKey(" + underlineToCamel(tablename1, true) + ".get"
						+ underlineToCamel(resultsetmetadata.getColumnName(1), false) + "());");
				printwriter.println("	}");
				printwriter.println();
				printwriter.println("	// RemoveByDTOs 按传入DTO集合删除多条数据");
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
				
				
				printwriter.println("	// findByPrimaryKey 按主键查找一条数据");
				printwriter.print("	public " + TableName + "DTO" + " findByPrimaryKey" + "("
						+ resultsetmetadata.getColumnClassName(1) + " " + underlineToCamel(resultsetmetadata.getColumnName(1), true) + ")");
				printwriter.println(" {");
				printwriter.println("		Connection con=null;\n		Statement  stmt=null;\n		ResultSet  rs=null;");
				printwriter.println("		" + TableName + "DTO " + underlineToCamel(tablename1, true) + " = null;");
				printwriter.println("		try{");
				printwriter.println("			Class.forName(driver);");
				printwriter.println("			con = DriverManager.getConnection(url,username,password);");
				printwriter.println("			stmt = con.createStatement();");
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
				printwriter.println("			rs = stmt.executeQuery(sql);");
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
				printwriter.println("			sqlex1.printStackTrace();");
				printwriter.println("		} finally {");
				printwriter.println("			if (rs != null) { ");
				printwriter.println("				try {");
				printwriter.println("					rs.close();");
				printwriter.println("				} catch(SQLException sqlex2) {");
				printwriter.println("					sqlex2.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("			if (stmt != null) { ");
				printwriter.println("				try {");
				printwriter.println("					stmt.close();");
				printwriter.println("				} catch(SQLException sqlex3) {");
				printwriter.println("					sqlex3.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("			if (con != null) { ");
				printwriter.println("				try {");
				printwriter.println("					con.close();");
				printwriter.println("				} catch(SQLException sqlex4) {");
				printwriter.println("					sqlex4.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("		}");
				printwriter.println("		return " + underlineToCamel(tablename1, true) + ";");
				printwriter.println("	}");
				printwriter.println();
				
				
				printwriter.println("	// findAll 获取全部数据");
				printwriter.println("	public List<" + TableName + "DTO> findAll() {");
				printwriter.println("		List<" + TableName + "DTO> ls=new ArrayList<" + TableName + "DTO>();");
				printwriter.println("		Connection con=null;");
				printwriter.println("		Statement  stmt=null;");
				printwriter.println("		ResultSet  rs=null;");
				printwriter.println("	    try{");
				printwriter.println("			Class.forName(driver);");
				printwriter.println("			con = DriverManager.getConnection(url,username,password);");
				printwriter.println("			stmt = con.createStatement();");
				printwriter.println("			String sql = \"select * from " + tablename1 + "\";");
				printwriter.println("			rs = stmt.executeQuery(sql);");
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
				printwriter.println("			sqlex1.printStackTrace();");
				printwriter.println("		} finally {");
				printwriter.println("			if (rs != null) { ");
				printwriter.println("				try {");
				printwriter.println("					rs.close();");
				printwriter.println("				} catch(SQLException sqlex2) {");
				printwriter.println("					sqlex2.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("			if (stmt != null) { ");
				printwriter.println("				try {");
				printwriter.println("					stmt.close();");
				printwriter.println("				} catch(SQLException sqlex3) {");
				printwriter.println("					sqlex3.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("			if (con != null) { ");
				printwriter.println("				try {");
				printwriter.println("					con.close();");
				printwriter.println("				} catch(SQLException sqlex4) {");
				printwriter.println("					sqlex4.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("		}");
				printwriter.println("		return ls;");
				printwriter.println("	}");
				printwriter.println();

				printwriter.println("	// findByProperty 按字段查数据");
				printwriter.println("	public List<" + TableName + "DTO> findByProperty(String propertyName, Object value) {");
				printwriter.println("		List<" + TableName + "DTO> ls=new ArrayList<" + TableName + "DTO>();");
				printwriter.println("		Connection con=null;");
				printwriter.println("		Statement  stmt=null;");
				printwriter.println("		ResultSet  rs=null;");
				printwriter.println("	    try{");
				printwriter.println("			Class.forName(driver);");
				printwriter.println("			con = DriverManager.getConnection(url,username,password);");
				printwriter.println("			stmt = con.createStatement();");
				printwriter.println("			String sql = \"select * from " + tablename1
											+ " where \" + propertyName + \" = '\" + value + \"'\";");
				printwriter.println("			rs = stmt.executeQuery(sql);");
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
				printwriter.println("			sqlex1.printStackTrace();");
				printwriter.println("		} finally {");
				printwriter.println("			if (rs != null) { ");
				printwriter.println("				try {");
				printwriter.println("					rs.close();");
				printwriter.println("				} catch(SQLException sqlex2) {");
				printwriter.println("					sqlex2.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("			if (stmt != null) { ");
				printwriter.println("				try {");
				printwriter.println("					stmt.close();");
				printwriter.println("				} catch(SQLException sqlex3) {");
				printwriter.println("					sqlex3.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
				printwriter.println("			if (con != null) { ");
				printwriter.println("				try {");
				printwriter.println("					con.close();");
				printwriter.println("				} catch(SQLException sqlex4) {");
				printwriter.println("					sqlex4.printStackTrace();");
				printwriter.println("				}");
				printwriter.println("			}");
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
	 * 下划线转驼峰法
	 * 
	 * @param line       源字符串
	 * @param smallCamel 大小驼峰,是否为小驼峰
	 * @return 转换后的字符串
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
	 * 驼峰法转下划线
	 * 
	 * @param line 源字符串
	 * @return 转换后的字符串
	 */
	@SuppressWarnings("unused")
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
