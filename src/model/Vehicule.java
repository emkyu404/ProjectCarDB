package model;

import utils.TypeBoite;
import utils.TypeCarburant;

public class Vehicule {
	private int idVehicule;
	private String matricule;
	private String marque;
	private String modele;
	private int kilometrage;
	private boolean climatise;
	private int consommationCarburant;
	private TypeBoite typeBoite;
	private TypeCarburant typeCarburant;
	private int idCategorie;
	private int idAgence;
	
	public Vehicule(int idVehicule, String matricule, String marque, String modele, int kilometrage, int climatise, int consommmationCarburant, String typeBoite, String typeCarburant, int idCategorie, int idAgence) {
		try {
			this.typeBoite = whichTypeBoite(typeBoite);
			this.typeCarburant = whichTypeCarburant(typeCarburant);
			this.idVehicule = idVehicule;
			this.matricule = matricule;
			this.marque = marque;
			this.modele = modele;
			this.kilometrage = kilometrage;
			
			if(climatise == 0) {
				this.climatise = false;
			}else {
				this.climatise = true;
			}
			
			this.consommationCarburant = consommationCarburant;
			this.idCategorie = idCategorie;
			this.idAgence = idAgence;
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private TypeBoite whichTypeBoite(String typeBoite) throws Exception {
		switch(typeBoite) {
			case "Automatique" : return TypeBoite.Automatique;
			case "Manuelle" : return TypeBoite.Manuelle;
			default : throw new Exception("Le type de boite indiqu� est incorrect");
		}
	}
	
	private TypeCarburant whichTypeCarburant(String typeBoite) throws Exception {
		switch(typeBoite) {
			case "Electrique" : return TypeCarburant.Electrique;
			case "Gazole" : return TypeCarburant.Gazole;
			case "Essence" : return TypeCarburant.Essence;
			case "Gpl" : return TypeCarburant.Gpl;
			case "Sp95" : return TypeCarburant.Sp95;
			default : throw new Exception("Le type de boite indiqu� est incorrect");
		}
	}
}