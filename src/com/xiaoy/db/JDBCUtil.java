package com.xiaoy.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {

	/**
	 * 获取连接
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		return new JDBCUtil().instantiation();
	}

	/**
	 * 初始化连接
	 * 
	 * @return Connection
	 */
	private Connection instantiation() {
		try {
			String DriverClass = null;
			String jdbcURL = null;
			String user = null;
			String password = null;
			// 读取类路径下的jdbc.perproties文件
			InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
			Properties properties = new Properties();
			properties.load(in);

			DriverClass = properties.getProperty("DriverClass");
			jdbcURL = properties.getProperty("jdbcURL");
			user = properties.getProperty("user");
			password = properties.getProperty("password");

			Driver driver = (Driver) Class.forName(DriverClass).newInstance();
			Properties info = new Properties();
			info.put("user", user);
			info.put("password", password);

			Connection connection = driver.connect(jdbcURL, info);
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 关闭连接
	 */
	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
