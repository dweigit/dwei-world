package com.bestsoft.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 连接数ORACLE据库
 * 
 * @author Administrator
 */
public class OracleDemo {

	String driver = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@10.139.104.127:1521/orcl";
	String name = "wskh_qh";
	String pwd = "wskh";

	public Connection getCon() {
		Connection con = null;
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			System.out.println("驱动加载失败!");
			e1.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(url, name, pwd);
		} catch (SQLException e) {
			System.out.println("驱动或数据库连接失败！");
			e.printStackTrace();
		}
		return con;
	}

	public static void main(String[] args) throws SQLException {
		OracleDemo db = new OracleDemo();
		Connection con = db.getCon();
		DatabaseMetaData metaData = con.getMetaData();
		System.out.println("DatabaseProductName : " +  metaData.getDatabaseProductName());
		if (con != null) {
			System.out.println("数据库连接成功！");
		}
	}
}
