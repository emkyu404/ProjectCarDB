package model;

import java.util.HashMap;

public class Agence {
	private int idAgence;
	private String nom;
	private int telephone;
	private String coordGPS;
	private int idAdresse;
	
	public static HashMap<Integer,Agence>agences = new HashMap<Integer,Agence>();
	
	public Agence(int idAgence, String nom, int tel, String coordGPS, int idAdresse) {
		this.idAgence = idAgence;
		this.nom = nom;
		this.telephone = tel;
		this.coordGPS = coordGPS;
		this.idAdresse = idAdresse;
		
		agences.put(idAgence, this);
	}

	@Override
	public String toString() {
		return "Agence [idAgence=" + idAgence + ", nom=" + nom + ", telephone=" + telephone + ", coordGPS=" + coordGPS
				+ ", idAdresse=" + idAdresse + "]";
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getIdAdresse() {
		return idAdresse;
	}

	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}

	public int getIdAgence() {
		return idAgence;
	}

	public void setIdAgence(int idAgence) {
		this.idAgence = idAgence;
	}
	
}
