package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	final static String URL="jdbc:mysql://localhost:3306/it_center"; 
	final static String USER_NAME="root";
	final static String PASSWORD="hp15p251nz";
	
	public static Connection getConnection() {
		
		//String url ="jdbc:mariadb://itcenter.mariadb.database.azure.com:3306/it_center?useSSL=false";
		Connection con=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(URL,USER_NAME,PASSWORD);
			
			//Class.forName("org.mariadb.jdbc.Driver");
			//con =DriverManager.getConnection(url, "demo@itcenter", "Password12345");
			System.out.println("DB connected Successfully");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
}
