package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Vehicule;

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
	
	public List<Vehicule> getVehicules(){
		System.out.println("----- Liste des véhicules -----");
		ArrayList<Vehicule> listeVehicules = new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * from VEHICULE";
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listeVehicules.add(new Vehicule(res.getInt("idVehicule"), res.getString("matricule"), res.getString("marque"), res.getString("modele"), res.getInt("kilometrage"), res.getInt("climatise"), res.getInt("consommationCarburant"), res.getString("typeBoite"), res.getString("typeCarburant"), res.getInt("idCategorie"), res.getInt("idAgence")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeVehicules;
	}
}
