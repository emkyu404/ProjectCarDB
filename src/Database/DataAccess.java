package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Categorie;
import model.Client;
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
	
	public List<Categorie> getCategories(){
		System.out.println("----- Liste des catégories -----");
		ArrayList<Categorie> listeCategorie = new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * FROM Categorie";
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listeCategorie.add(new Categorie(res.getInt("idCategorie"), res.getString("label"), res.getFloat("tarifJ"), res.getFloat("caution")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeCategorie;
	}
	
	/* Liste des véhicules disponibles par catégorie */
	public List<Vehicule> getVehicules(){
		System.out.println("----- Liste des véhicules -----");
		ArrayList<Vehicule> listeVehicules = new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * from VEHICULE WHERE idVehicule";
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listeVehicules.add(new Vehicule(res.getInt("idVehicule"), res.getString("matricule"), res.getString("marque"), res.getString("modele"), res.getInt("kilometrage"), res.getInt("climatise"), res.getInt("consommationCarburant"), res.getString("typeBoite"), res.getString("typeCarburant"), res.getInt("idCategorie"), res.getInt("idAgence")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeVehicules;
	}
	
	/*
	 *  GESTION DES RESSOURCES
	 */
	
	
	/* Liste des véhicules disponibles par catégorie */
	public List<Vehicule> getVehiculesAvailable(){
		System.out.println("----- Liste des véhicules -----");
		ArrayList<Vehicule> listeVehicules = new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * from VEHICULE WHERE idVehicule NOT IN (SELECT idVehicule FROM LOUER WHERE CURDATE() BETWEEN dateDebut and dateFin) ORDER BY idCategorie";
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listeVehicules.add(new Vehicule(res.getInt("idVehicule"), res.getString("matricule"), res.getString("marque"), res.getString("modele"), res.getInt("kilometrage"), res.getInt("climatise"), res.getInt("consommationCarburant"), res.getString("typeBoite"), res.getString("typeCarburant"), res.getInt("idCategorie"), res.getInt("idAgence")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeVehicules;
	}
	
	/* Recherche par marque */
	public List<Vehicule> getVehiculesByMarque(String marque){
		System.out.println("----- Liste des véhicules par marque -----");
		ArrayList<Vehicule> listeVehicules = new ArrayList<>();
		
		try {
			String sql = "SELECT * from VEHICULE WHERE marque = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, marque);
			ResultSet res = ps.executeQuery();
			
			while(res.next()) {
				listeVehicules.add(new Vehicule(res.getInt("idVehicule"), res.getString("matricule"), res.getString("marque"), res.getString("modele"), res.getInt("kilometrage"), res.getInt("climatise"), res.getInt("consommationCarburant"), res.getString("typeBoite"), res.getString("typeCarburant"), res.getInt("idCategorie"), res.getInt("idAgence")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeVehicules;
	}
	
	
	/* Liste des véhicules disponibles par catégorie */
	public List<Vehicule> getVehiculesBeingRent(){
		System.out.println("----- Liste des véhicules -----");
		ArrayList<Vehicule> listeVehicules = new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * FROM Louer as L inner join Vehicule as V on L.idVehicule = V.idVehicule WHERE CURDATE() BETWEEN dateDebut and dateFin";
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listeVehicules.add(new Vehicule(res.getInt("idVehicule"), res.getString("matricule"), res.getString("marque"), res.getString("modele"), res.getInt("kilometrage"), res.getInt("climatise"), res.getInt("consommationCarburant"), res.getString("typeBoite"), res.getString("typeCarburant"), res.getInt("idCategorie"), res.getInt("idAgence")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeVehicules;
	}
	
	/*
	 * GESTION DES CLIENTS
	 */
	public List<Client> getClient(){
		System.out.println("----- Liste des clients -----");
		ArrayList<Client> listeClients = new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * from CLIENT ORDER BY nom asc";
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listeClients.add(new Client(res.getInt("idClient"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getInt("numTelephone"), res.getDate("dateSouscription"), res.getInt("idAdresse"), res.getInt("idPFidelite")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeClients;
	}
	
	public List<Client> getClientByName(String nom){
		System.out.println("----- Liste des clients portant le nom "+ nom +" -----");
		ArrayList<Client> listeClients = new ArrayList<>();
		
		try {
			String sql = "SELECT * FROM Client WHERE nom = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nom);
			ResultSet res = ps.executeQuery();
			
			while(res.next()) {
				listeClients.add(new Client(res.getInt("idClient"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getInt("numTelephone"), res.getDate("dateSouscription"), res.getInt("idAdresse"), res.getInt("idPFidelite")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeClients;
	}
	
	public List<Client> getClientRenting(){
		System.out.println("----- Liste des clients entrain de louer -----");
		ArrayList<Client> listeClients = new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * from CLIENT WHERE idClient IN (SELECT idClient FROM LOUER WHERE CURDATE() BETWEEN dateDebut and dateFin);";
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listeClients.add(new Client(res.getInt("idClient"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getInt("numTelephone"), res.getDate("dateSouscription"), res.getInt("idAdresse"), res.getInt("idPFidelite")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeClients;
	}
	
	/* WHR => Who has rent */
	public List<Client> getClientWHR(Vehicule v){
		System.out.println("----- Liste des clients qui ont loué le véhicule "+ v.getModele()+ " de matricule " + v.getMatricule()+ " -----");
		ArrayList<Client> listeClients = new ArrayList<>();
		
		try {
			String sql = "SELECT * FROM Client as C INNER JOIN Louer as L  on L.idClient = C.idClient WHERE idVehicule = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, v.getIdVehicule());
			ResultSet res = ps.executeQuery();
			
			while(res.next()) {
				listeClients.add(new Client(res.getInt("idClient"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getInt("numTelephone"), res.getDate("dateSouscription"), res.getInt("idAdresse"), res.getInt("idPFidelite")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeClients;
	}
	
}
