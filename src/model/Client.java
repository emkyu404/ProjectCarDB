package model;

import java.sql.Date;

public class Client {
	private int idClient;
	private String nom;
	private String prenom;
	private String email;
	private String numTelephone;
	private Date dateSouscription;
	private int idAdresse;
	private int idPFidelite;
	
	public Client(int idClient, String nom, String prenom, String email, String numTelephone, Date dateSouscription, int idAdresse, int idPfidelite){
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.numTelephone = numTelephone;
		this.dateSouscription = dateSouscription;
		this.idAdresse = idAdresse;
		this.idPFidelite = idPfidelite;
	}
	
	public Client(int idClient, String nom, String prenom, String email, String numTelephone, Date dateSouscription, int idAdresse) {
		this(idClient, nom, prenom, email, numTelephone, dateSouscription, idAdresse, -1);
	}
}
