package com.lk.dragon.util;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接类
 * 
 * @author xmz
 * 
 */
public class DbConnectionUtil {
	public final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public final static String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

	public static void connectDb(String driver, String url, String userName,
			String password) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		DriverManager.getConnection(url, userName, password);

	}

	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(ORACLE_DRIVER);
			conn = (Connection) DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.1.168:1521:orcl", "sod", "sod");
			// new oracle.jdbc.driver.OracleDriver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 针对过长的战报邮件 处理SQL字符串太长
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void doLongMail(long to,String title,String info) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			//获取邮件Sequence
			String vsql = "select sod.mail_seq.nextval as mail_id from dual";
			pstmt = conn.prepareStatement(vsql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			//邮件ID
			int mail_id = rs.getInt(1);
			rs.close();
			pstmt.close();
			
			//构建邮件插入语句
			vsql = "insert into sod.mail_tab(mail_id,mail_title,mail_from,mail_to,mail_content,mail_type,mail_createtime) values(?,?,?,?,?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(vsql);

			pstmt.setInt(1, mail_id);
			pstmt.setString(2, title);
			pstmt.setInt(3, -20);
			pstmt.setLong(4, to);
			pstmt.setCharacterStream(5, new StringReader(info), info.length());
			pstmt.setInt(6, 2);
			pstmt.setString(7, DateTimeUtil.getNowTimeByFormat());
			
			//执行SQL
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			//关闭连接
			if (null != pstmt)
				pstmt.close();
			if (null != conn)
				conn.close();
		}
	}
	
	
}
