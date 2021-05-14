package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccess {
	private String url;
	private String usr;
	private String pas;
	
	private Connection conn;
	
	public DataAccess(String url, String usr, String pas) {
		this.url = url;
		this.usr = usr;
		this.pas = pas;
		DBConnection();
	}
	
	private void DBConnection() {
		try {
			this.conn = DriverManager.getConnection(url, usr, pas);
			System.out.println("Connexion à la base de donnée réussi");
		}catch(SQLException e) {
			System.err.println("ERREUR : " + e.getMessage());
		}
	}
}
