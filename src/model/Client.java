package model;

import java.sql.Date;
import java.util.HashMap;

public class Client {
	private int idClient;
	private String nom;
	private String prenom;
	private String email;
	private String numTelephone;
	private Date dateSouscription;
	private int idAdresse;
	private int idPFidelite;
	
	public static HashMap<Integer, Client>clients = new HashMap<>();
	
	public Client(int idClient, String nom, String prenom, String email, String numTelephone, Date dateSouscription, int idAdresse, int idPfidelite){
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.numTelephone = numTelephone;
		this.dateSouscription = dateSouscription;
		this.idAdresse = idAdresse;
		this.idPFidelite = idPfidelite;
		clients.put(idClient, this);
	}
	
	public Client(int idClient, String nom, String prenom, String email, String numTelephone , int idAdresse) {
		this(idClient, nom, prenom, email, numTelephone,null, idAdresse, -1);
	}

	public int getIdClient() {
		return idClient;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getEmail() {
		return email;
	}

	public String getNumTelephone() {
		return numTelephone;
	}

	public Date getDateSouscription() {
		return dateSouscription;
	}

	public int getIdAdresse() {
		return idAdresse;
	}

	public int getIdPFidelite() {
		return idPFidelite;
	}

	public static HashMap<Integer, Client> getClients() {
		return clients;
	}
}
