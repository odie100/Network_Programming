package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {

	public Connection cnx;

	public static String db_user = "odie";
	public static String db_name = "data_network";
	public static String db_type = "mysql";
	public static String db_host = "localhost";
	public static String db_password = "";
	
	
	public static Connection ConnectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection cn = DriverManager.getConnection("jdbc:"+db_type+"://"+db_host+":3306/"+db_name, db_user, "");
			System.out.println("Connection success");
			return cn;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
			return null;
		}
	}
	
	public Connection getCnx() {
		return cnx;
	}
	public void setCnx(Connection cnx) {
		this.cnx = cnx;
	}
	public static String getDb_user() {
		return db_user;
	}
	public static void setDb_user(String db_user) {
		ConnectionDataBase.db_user = db_user;
	}
	public static String getDb_name() {
		return db_name;
	}
	public static void setDb_name(String db_name) {
		ConnectionDataBase.db_name = db_name;
	}
	public static String getDb_type() {
		return db_type;
	}
	public static void setDb_type(String db_type) {
		ConnectionDataBase.db_type = db_type;
	}
	public static String getDb_host() {
		return db_host;
	}
	public static void setDb_host(String db_host) {
		ConnectionDataBase.db_host = db_host;
	}
	public static String getDb_password() {
		return db_password;
	}
	public static void setDb_password(String db_password) {
		ConnectionDataBase.db_password = db_password;
	}
	
	
	
	
}
