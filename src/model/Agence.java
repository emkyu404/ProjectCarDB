package model;

import java.util.HashMap;

public class Agence {
	private int idAgence;
	private String nom;
	private String telephone;
	private String coordGPS;
	private int idAdresse;
	
	public static HashMap<Integer,Agence>agences = new HashMap<Integer,Agence>();
	
	public Agence(int idAgence, String nom, String telephone, String coordGPS, int idAdresse) {
		this.idAgence = idAgence;
		this.nom = nom;
		this.telephone = telephone;
		this.coordGPS = coordGPS;
		this.idAdresse = idAdresse;
		
		agences.put(idAgence, this);
	}
	
}
