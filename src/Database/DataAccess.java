package Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gestion.Devis;
import model.Adresse;
import model.Agence;
import model.Categorie;
import model.Client;
import model.PFidelite;
import model.Vehicule;

public class DataAccess {
	private String url;
	private String usr;
	private String pas;
	
	private Connection conn;
	
	public DataAccess(String url, String usr, String pas) throws SQLException{
		this.url = url;
		this.usr = usr;
		this.pas = pas;
		try {
		DBConnection();
		}catch(SQLException e){
			throw e;
		}
	}
	
	private void DBConnection() throws SQLException {
		try {
			this.conn = DriverManager.getConnection(url, usr, pas);
			System.out.println("Connexion à la base de donnée réussi");
		}catch(SQLException e) {
			throw e;
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
	
	public List<PFidelite> getPFidelite(){
		System.out.println("----- Liste des PFidelite -----");
		ArrayList<PFidelite> listePFidelites= new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * FROM PFidelite";
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listePFidelites.add(new PFidelite(res.getInt("idPfidelite"), res.getString("description"), res.getInt("duree"), res.getFloat("prix"), res.getFloat("tauxR")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listePFidelites;
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
	
	/* Ajout d'un véhicule */
	public void addNewVehicule(Vehicule v) {	
		try {
			String sql = "INSERT INTO VEHICULE (matricule, marque, modele, kilometrage, climatise, consommationCarburant, typeBoite, typeCarburant, idCategorie, idAgence) VALUES (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, v.getMatricule());
			ps.setString(2, v.getMarque());
			ps.setString(3, v.getModele());
			ps.setInt(4, v.getKilometrage());
			ps.setBoolean(5, v.isClimatise());
			ps.setInt(6, v.getConsommationCarburant());
			ps.setString(7, v.getTypeBoite().toString());
			ps.setString(8, v.getTypeCarburant().toString());
			ps.setInt(9, v.getIdCategorie());
			ps.setInt(10, v.getIdAgence());
			
			int row = ps.executeUpdate();
			
			System.out.println("L'ajout du véhicule " + v.getMatricule() + " a bien été effectué");
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	/* Suppression d'un véhicule */
	public void deleteVehicule(Vehicule v) {	
		try {
			String sql = "DELETE FROM VEHICULE WHERE idVehicule = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, v.getIdVehicule());
			
			int row = ps.executeUpdate();
			
			System.out.println("La suppression du véhicule " + v.getMatricule() + " a bien été effectué");
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	/* Modification d'un véhicule */
	public void updateVehicule(Vehicule v) {	
		try {
			String sql = "UPDATE VEHICULE SET matricule = ?, marque = ?, modele = ?, kilometrage = ?, climatise = ?, consommationCarburant = ?, typeBoite = ?, typeCarburant = ?, idCategorie = ?, idAgence = ? WHERE idVehicule = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, v.getMatricule());
			ps.setString(2, v.getMarque());
			ps.setString(3, v.getModele());
			ps.setInt(4, v.getKilometrage());
			ps.setBoolean(5, v.isClimatise());
			ps.setInt(6, v.getConsommationCarburant());
			ps.setString(7, v.getTypeBoite().toString());
			ps.setString(8, v.getTypeCarburant().toString());
			ps.setInt(9, v.getIdCategorie());
			ps.setInt(10, v.getIdAgence());
			ps.setInt(11, v.getIdVehicule());
			
			int row = ps.executeUpdate();
			
			System.out.println("La modification du véhicule " + v.getMatricule() + " a bien été effectué");
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	
	
	
	/* Liste des véhicules disponibles par catégorie */
	public List<Vehicule> getVehiculesAvailable(){
		System.out.println("----- Liste des véhicules disponible par catégorie -----");
		ArrayList<Vehicule> listeVehicules = new ArrayList<>();
		
		try {
			String sql = "SELECT * from VEHICULE WHERE idVehicule NOT IN (SELECT idVehicule FROM LOUER WHERE CURDATE() BETWEEN dateDebut and dateFin) AND CURDATE() NOT IN (SELECT idVehicule FROM RESERVER WHERE CURDATE() BETWEEN dateDebut and dateFin) ORDER BY idCategorie";
			PreparedStatement s = conn.prepareStatement(sql);
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listeVehicules.add(new Vehicule(res.getInt("idVehicule"), res.getString("matricule"), res.getString("marque"), res.getString("modele"), res.getInt("kilometrage"), res.getInt("climatise"), res.getInt("consommationCarburant"), res.getString("typeBoite"), res.getString("typeCarburant"), res.getInt("idCategorie"), res.getInt("idAgence")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeVehicules;
	}
	
	/* Liste des véhicules disponibles à la réservation en fonction de la catégorie et des dates indiqués */
	public List<Vehicule> getVehiculesAvailableReservation(Date dateD, Date dateF, int idCat){
		System.out.println("----- Liste des véhicules disponible par catégorie du "+ dateD + " au " + dateF + "-----");
		ArrayList<Vehicule> listeVehicules = new ArrayList<>();
		
		try {
			String sql = "SELECT * from VEHICULE WHERE idVehicule NOT IN (SELECT idVehicule FROM LOUER WHERE dateDebut BETWEEN ? and ?) AND idVehicule NOT IN (SELECT idVehicule FROM RESERVER WHERE dateDebut BETWEEN ? and ?) AND idVehicule NOT IN (SELECT idVehicule FROM LOUER WHERE dateFin BETWEEN ? and ?) AND idVehicule NOT IN (SELECT idVehicule FROM RESERVER WHERE dateFin BETWEEN ? and ?) AND idCategorie = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, dateD);
			ps.setDate(2, dateF);
			ps.setDate(3, dateD);
			ps.setDate(4, dateF);
			ps.setDate(5, dateD);
			ps.setDate(6, dateF);
			ps.setDate(7, dateD);
			ps.setDate(8, dateF);
			ps.setInt(9, idCat);
			ResultSet res = ps.executeQuery();
			
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
	
	public void addReservation(int idC, int idV, Date dateD, Date dateF) {
		System.out.println("----- Ajout d'une réservation -----");
		
		try {
			String sql = "INSERT INTO RESERVER (idClient, idVehicule, dateDebut, dateFin) VALUES (?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idC);
			ps.setInt(2, idV);
			ps.setDate(3, dateD);
			ps.setDate(4, dateF);
			int row = ps.executeUpdate();
			System.out.println("Ajout de la réservation du véhicule " + idV + " pour le client "+ idC + " du " + dateD + " au " + dateF);
		} catch(SQLException e){
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	public void cancelReservation(int idC, int idV, Date dateD, Date dateF) {
		System.out.println("----- Annulation d'une réservation -----");
		
		try {
			String sql = "DELETE FROM RESERVER WHERE idClient = ? AND idVehicule = ? AND dateDebut = ? AND dateFIN = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idC);
			ps.setInt(2, idV);
			ps.setDate(3, dateD);
			ps.setDate(4, dateF);
			
			int row2 = ps.executeUpdate();
			
			System.out.println("Suppression de la réservation effectué.");
		} catch(SQLException e){
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	public void addLocation(int idC, int idV, Date dateD, Date dateF, boolean assurance) {
		System.out.println("----- Ajout d'une location -----");
		
		try {
			String sql = "INSERT INTO LOUER (idClient, idVehicule, dateDebut, dateFin, assurance) VALUES (?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idC);
			ps.setInt(2, idV);
			ps.setDate(3, dateD);
			ps.setDate(4, dateF);
			ps.setBoolean(5, assurance);
			int row = ps.executeUpdate();
			
			System.out.println("Ajout de la location du véhicule " + idV + " pour le client "+ idC + " du " + dateD + " au " + dateF);
			
			String sql2 = "DELETE FROM RESERVER WHERE idClient = ? AND idVehicule = ? AND dateDebut = ? AND dateFIN = ?";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, idC);
			ps2.setInt(2, idV);
			ps2.setDate(3, dateD);
			ps2.setDate(4, dateF);
			
			int row2 = ps.executeUpdate();
			
			System.out.println("Suppression de la réservation correspondante");
		} catch(SQLException e){
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	public void endLocation(int idC, int idV, Date dateD, Date dateF, boolean endo, int mtnCarb) {
		System.out.println("----- Retour de la location -----");
		try {
			String sql = "UPDATE LOUER SET endommage = ?, carburantRestant = ?, dateRetour = CURDATE() WHERE idClient = ? AND idVehicule = ? AND dateDebut = ? AND dateFin = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setBoolean(1, endo);
			ps.setInt(2, mtnCarb);
			ps.setInt(3, idC);
			ps.setInt(4, idV);
			ps.setDate(5, dateD);
			ps.setDate(6, dateF);
			
			int row = ps.executeUpdate();
			System.out.println("Retour de la location validé.");
			
		}catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	/*
	 * GESTION DES CLIENTS
	 */
	/* Ajout d'un nouveau client */
	public void addNewClient(Client c) {	
		try {
			String sql = "INSERT INTO CLIENT (nom, prenom, email, numTelephone, dateSouscription,idAdresse,idPFidelite) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getNom());
			ps.setString(2, c.getPrenom());
			ps.setString(3, c.getEmail());
			ps.setInt(4, c.getNumTelephone());
			ps.setDate(5, c.getDateSouscription());
			ps.setInt(6, c.getIdAdresse());
			if(c.getIdPFidelite() == -1) {
				ps.setNull(7, java.sql.Types.INTEGER);
			}else {
				ps.setInt(7, c.getIdPFidelite());
			}
			
			int row = ps.executeUpdate();
			
			System.out.println("L'ajout des informations du client " + c.getPrenom() + " " + c.getNom() + " a bien été effectué");
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	/* Ajout d'une nouvelle adresse */
	public void addAdresse(Adresse ad) {
		try {
			String sql = "INSERT INTO ADRESSE (rue, ville, codePostal) VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ad.getRue());
			ps.setString(2, ad.getVille());
			ps.setInt(3, ad.getCodePostal());
			
			int row = ps.executeUpdate();
			
			System.out.println("L'adresse " + ad.getRue() + " vient d'être ajoutée");
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	/* recuperation de l'id d'une adresse dans la base de donnée */
	public int getIdAdresse(Adresse ad){	
		int idAd = 0;
		try {
			String sql = "SELECT idAdresse from Adresse WHERE rue = ? AND ville = ? AND codePostal = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ad.getRue());
			ps.setString(2, ad.getVille());
			ps.setInt(3, ad.getCodePostal());
			
			ResultSet res = ps.executeQuery();
			if(res.next()) {
				idAd = res.getInt("IdAdresse");
			}
			
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return idAd;
	}
	
	/* Suppression d'un client */
	public void deleteClient(Client c) {	
		try {
			String sql = "DELETE FROM CLIENT WHERE idClient = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, c.getIdClient());
			
			int row = ps.executeUpdate();
			
			System.out.println("La suppression des informations du client " + c.getPrenom() + " " + c.getNom()  + " a bien été effectué");
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	/* Suppression d'une adresse */
	public void deleteAdresse(Adresse ad) {
		try {
			String sql = "DELETE FROM ADRESSE WHERE idAdresse = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ad.getIdAdresse());
			
			int row = ps.executeUpdate();
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	/* recuperation de toutes les adresses */
	public List<Adresse> getAdresses(){
		ArrayList<Adresse> listeAdresses = new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * from ADRESSE";
			ResultSet res = s.executeQuery(sql);
			
			while(res.next()) {
				listeAdresses.add(new Adresse(res.getInt("idAdresse"),res.getString("rue"),res.getString("ville"),res.getInt("codePostal")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeAdresses;
	}
	
	/* Modification d'un véhicule */
	public void updateClient(Client c) {	
		try {
			String sql = "UPDATE CLIENT SET nom = ?, prenom = ?, email = ?, numTelephone = ?, dateSouscription = ?, idAdresse = ?, idPFidelite = ? WHERE idClient = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getNom());
			ps.setString(2, c.getPrenom());
			ps.setString(3, c.getEmail());
			ps.setInt(4, c.getNumTelephone());
			ps.setDate(5, c.getDateSouscription());
			ps.setInt(6, c.getIdAdresse());
			if(c.getIdPFidelite() == -1) {
				ps.setNull(7, java.sql.Types.INTEGER);
			}else {
				ps.setInt(7, c.getIdPFidelite());
			}
			ps.setInt(8, c.getIdClient());
			
			int row = ps.executeUpdate();
			
			System.out.println("La modification des informations du client " + c.getPrenom() + " " + c.getNom() + " a bien été effectué");
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
	public void updateAdresse(Adresse ad) {	
		try {
			String sql = "UPDATE ADRESSE SET RUE = ?, ville = ?, codePostal = ? WHERE idAdresse = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ad.getRue());
			ps.setString(2, ad.getVille());
			ps.setInt(3, ad.getCodePostal());
			ps.setInt(4, ad.getIdAdresse());
			
			int row = ps.executeUpdate();
			
			System.out.println("La modification de l'adresse " + ad.getRue() + " a bien été effectué");
			
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
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
		System.out.println("----- Liste des clients en train de louer -----");
		ArrayList<Client> listeClients = new ArrayList<>();
		
		try {
			String sql = "SELECT * from CLIENT WHERE idClient IN (SELECT idClient FROM LOUER WHERE dateRetour is null)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			
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
			String sql = "SELECT DISTINCT C.idClient, C.nom, C.prenom, C.email, C.numTelephone, C.dateSouscription,C.idAdresse,C.idPFidelite FROM Client as C INNER JOIN Louer as L  on L.idClient = C.idClient WHERE idVehicule = ?";
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
	
	/* WHNR => Who has never rent */
	public List<Client> getClientWHNR(Vehicule v){
		System.out.println("----- Liste des clients qui n'ont jamais loués -----");
		ArrayList<Client> listeClients = new ArrayList<>();
		
		try {
			Statement s = conn.createStatement();
			String sql = "SELECT * from CLIENT WHERE idClient NOT IN (SELECT idClient FROM LOUER);";
			ResultSet res = s.executeQuery(sql);
			while(res.next()) {
				listeClients.add(new Client(res.getInt("idClient"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getInt("numTelephone"), res.getDate("dateSouscription"), res.getInt("idAdresse"), res.getInt("idPFidelite")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeClients;
	}
	
	public float getRecette(Date debut, Date fin) {
		System.out.println("----- Montant global des recettes de l'entreprise de gestion en euros pour les locations entre " + debut + " et " + fin + " -----");
		float recette = 0;
		
		ArrayList<Vehicule> listeVehicules = new ArrayList();
		
		try {
			String sql = "SELECT * FROM (LOUER as L inner join Vehicule as V on V.idVehicule = L.idVehicule) inner join Client as C on L.idClient = C.idClient WHERE L.dateRetour BETWEEN ? AND ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, debut);
			ps.setDate(2, fin);
			ResultSet res = ps.executeQuery();
			
			while(res.next()) {
				Vehicule v = new Vehicule(res.getInt("idVehicule"), res.getString("matricule"), res.getString("marque"), res.getString("modele"), res.getInt("kilometrage"), res.getInt("climatise"), res.getInt("consommationCarburant"), res.getString("typeBoite"), res.getString("typeCarburant"), res.getInt("idCategorie"), res.getInt("idAgence"));
				Client c = new Client(res.getInt("idClient"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getInt("numTelephone"), res.getDate("dateSouscription"), res.getInt("idAdresse"), res.getInt("idPFidelite"));
				Devis d = new Devis(c, v, res.getBoolean("assurance"), res.getBoolean("endommage"), res.getInt("carburantRestant"), res.getDate("dateDebut"), res.getDate("dateFin"), res.getDate("dateRetour"));
				recette += d.calculFacture();
				System.out.println(v);
				System.out.println(c);
			}
			
			for(Vehicule vh : listeVehicules) {
				
			}
		}catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		return recette;
	}
	
	/* Récupération des agences ayant 80% de leurs véhicules disponibles */
	public List<Agence> getAgenceDispo80(){
		System.out.println("----- Liste des agences ayant au moins 80% de leurs véhicules disponibles -----");
		ArrayList<Agence> listeAgence = new ArrayList<>();
		
		try {
			String sql = "SELECT a.idAgence, a.nom, a.telephone, a.coordGPS, a.idAdresse, COUNT(DISTINCT v.idVehicule) AS NBVehicule FROM (Agence as A inner join Vehicule as V on a.idAgence = v.idAgence) left join Louer as l on l.idVehicule = v.idVehicule WHERE v.idVehicule NOT IN (SELECT idVehicule FROM LOUER) OR v.idVehicule NOT IN (SELECT idVehicule FROM LOUER WHERE dateRetour IS NULL) GROUP BY v.idAgence HAVING NBVehicule >= 0.8 * (SELECT COUNT(*) FROM vehicule as v2 WHERE v2.idAgence = V.idAgence)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			
			while(res.next()) {
				listeAgence.add(new Agence(res.getInt("idAgence"), res.getString("nom"), res.getInt("telephone"), res.getString("coordGPS"), res.getInt("idAdresse")));
			}
		} catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		return listeAgence;
	}
	
	public boolean reservationExist(int idc, int idv, Date dateD, Date dateF) {
		System.out.println("------ Vérification qu'une réservation existe -----");
		boolean result = false;
		try {
			String sql = "SELECT * FROM RESERVER WHERE idClient = ? AND idVehicule = ? AND dateDebut = ? AND dateFin = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idc);
			ps.setInt(2, idv);
			ps.setDate(3, dateD);
			ps.setDate(4, dateF);
			ResultSet res = ps.executeQuery();
			
			int cpt = 0;
			while(res.next()) {
				cpt++;
			}
			
			if(cpt == 1) {
				result = true;
			}else {
				result = false;
			}
		}catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		return result;
	}
	
	public boolean locationExist(int idc, int idv, Date dateD, Date dateF) {
		System.out.println("------ Vérification qu'une réservation existe -----");
		boolean result = false;
		try {
			String sql = "SELECT * FROM LOUER WHERE idClient = ? AND idVehicule = ? AND dateDebut = ? AND dateFin = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idc);
			ps.setInt(2, idv);
			ps.setDate(3, dateD);
			ps.setDate(4, dateF);
			ResultSet res = ps.executeQuery();
			
			int cpt = 0;
			while(res.next()) {
				cpt++;
			}
			
			if(cpt == 1) {
				result = true;
			}else {
				result = false;
			}
		}catch(SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		return result;
	}
	
	public void closeConnection() {
		try {
			this.conn.close();
			System.out.println("Déconnexion à la base de donnée.");
		} catch (SQLException e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}
	
}
