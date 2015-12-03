package com.bestsoft.database;

import java.sql.*;

public class DBDemo {
	public static void main(String[] args) {
		try {
			String url = "jdbc:mysql://10.139.105.233:3306/wskh_htzq";
			String user ="wskh_htzq";
			String pwd = "wskh";

			// 加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// 建立到MySQL的连接
			Connection conn = DriverManager.getConnection(url, user, pwd);
			
			
			DatabaseMetaData metaData = conn.getMetaData();
			System.out.println("DatabaseProductName : " +  metaData.getDatabaseProductName());

			// 执行SQL语句
			Statement stmt = conn.createStatement();// 创建语句对象，用以执行sql语言
			ResultSet rs = stmt.executeQuery("select sysdate()");

			// 处理结果集
			while (rs.next()) {
				String name = rs.getString(1);
				System.out.println(name);
			}
			rs.close();// 关闭数据库
			conn.close();
		} catch (Exception ex) {
			System.out.println("Error : " + ex.toString());
		}
	}
}