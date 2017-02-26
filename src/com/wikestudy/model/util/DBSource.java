package com.wikestudy.model.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.impl.Log4JLogger;

public class DBSource {
	//　静态数据源
	private static DataSource dataSource;

	static Log4JLogger log = new Log4JLogger("log4j.properties");
	public DBSource(Properties properties) {
		// 根据配置文件获取数据源
		if (dataSource == null) {
			try {
				dataSource = BasicDataSourceFactory.createDataSource(properties);
			} catch (Exception e) {
				log.info("异常");
				log.error(e,e.fillInStackTrace());
				e.printStackTrace();
			}
			System.out.println(dataSource.getClass());
			System.out.println("成功初始化数据源");
		}
	}

	public static Connection getConnection(){
		if (dataSource != null) {
			try {
				return dataSource.getConnection();
			} catch (SQLException e) {
				log.info("异常");
				log.error(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}

		return null;

	}

	public static void destroy() throws Exception {
		if (dataSource != null) {
			BasicDataSource basicDataSource = (BasicDataSource) dataSource;
			basicDataSource.close();
		}
	}
	
	
}
