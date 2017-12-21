package com.zhang.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	
	private static String className;
	private static String url;
	private static String user;
	private static String password;
	static {
		try {
			InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("dbcfg.properties");
			Properties pro = new Properties();
			pro.load(in);
			
			className = pro.getProperty("className");
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			
			Class.forName(className);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	
	public static void release(Connection conn, Statement stat, ResultSet rs) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (Exception e){
				
			}
		}
		rs = null;
		if (stat!=null) {
			try {
				stat.close();
			} catch (Exception e){
				
			}
		}
		stat = null;
		if (conn!=null) {
			try {
				conn.close();
			} catch (Exception e){
				
			}
		}
		conn = null;
	}

}
