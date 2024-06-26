package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/*public class ConnectDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DESKTOP-40UK884:1433;databaseName=QLTV;encrypt=true;trustServerCertificate=true";
            String userName = "sa";
            String password = "123456";
            Connection conn = DriverManager.getConnection(url, userName, password);
            
            System.out.println("Connection successful");
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}*/
public class ConnectDB{
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://DESKTOP-40UK884:1433;databaseName=QLTV;encrypt=true;trustServerCertificate=true";
            String userName = "sa";
            String password = "123456";
            conn = DriverManager.getConnection(url, userName, password);
            if( conn != null)
            	System.out.println("Thành công");
		} catch ( Exception e) {
			System.out.println(e.toString());
		}
		return conn;
	}
}