package model;

import java.util.HashMap;

public class PFidelite {
	private int idPFidelite;
	private String description;
	private int duree; // durée en année;
	private float prix;
	private float tauxR; //0 à 1;
	
	public static HashMap<Integer,PFidelite>pfidelites = new HashMap<Integer,PFidelite>();
	
	public PFidelite(int idPFidelite, String description, int duree, float prix, float tauxR) {
		this.idPFidelite = idPFidelite;
		this.description = description;
		this.duree = duree;
		this.prix = prix;
		this.tauxR = tauxR;
		
		pfidelites.put(idPFidelite, this);
	}

	public int getIdPFidelite() {
		return idPFidelite;
	}

	public String getDescription() {
		return description;
	}

	public int getDuree() {
		return duree;
	}

	public float getPrix() {
		return prix;
	}

	public float getTauxR() {
		return tauxR;
	}

	public static HashMap<Integer, PFidelite> getPfidelites() {
		return pfidelites;
	}

}
