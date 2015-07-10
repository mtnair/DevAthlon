package me.Pangasius.minigames.stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.Pangasius.minigames.Main;

import org.bukkit.Bukkit;

public class MySQL {
	
	private Connection conn;
	
	private String host, port, database, username, password;
	
	public MySQL(String host, String port, String database, String username, String password){
		
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
		
	}
	
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		if (checkConn()) {
			return conn;
		}
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database , username, password);
		return conn;
	}
	
	public boolean checkConn() throws SQLException {
		
		return conn != null && !conn.isClosed();
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public boolean closeConn() throws SQLException {
		if (conn == null) {
			return false;
		}
		conn.close();
		return true;
	}
	
	public ResultSet querySQL(String query) throws SQLException, ClassNotFoundException {
		if (!checkConn()) {
			openConnection();
		}

		Statement statement = conn.createStatement();

		ResultSet result = statement.executeQuery(query);

		return result;
	}
	
	public int updateSQL(String query) throws SQLException, ClassNotFoundException {
		if (!checkConn()) {
			openConnection();
		}

		Statement statement = conn.createStatement();

		int result = statement.executeUpdate(query);

		return result;
	}

}
