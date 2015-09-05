package com.xiaoy.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

/**
 * 获取数据库连接
 * 
 * @author XiaoY
 * @date: 2015年9月5日 下午7:07:33
 */
public class MySQLConnection {

	/**
	 * 通用的数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		String DriverClass = null;
		String jdbcURL = null;
		String user = null;
		String password = null;

		// 读取类路径下的jdbc.perproties文件
		InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.perproties");
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
	}
}
