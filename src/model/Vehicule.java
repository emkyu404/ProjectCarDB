package model;

import java.util.HashMap;

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
	
	public static HashMap<Integer, Vehicule>vehicules = new HashMap<>();
	
	public Vehicule(int idVehicule, String matricule, String marque, String modele, int kilometrage, int climatise, int consommationCarburant, String typeBoite, String typeCarburant, int idCategorie, int idAgence) {
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
			
			if(idVehicule > 0) {
				vehicules.put(idVehicule, this);
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private TypeBoite whichTypeBoite(String typeBoite) throws Exception {
		switch(typeBoite) {
			case "Automatique" : return TypeBoite.Automatique;
			case "Manuelle" : return TypeBoite.Manuelle;
			default : throw new Exception("Le type de boite indiqué est incorrect");
		}
	}
	
	private TypeCarburant whichTypeCarburant(String typeCarburant) throws Exception {
		switch(typeCarburant) {
			case "Electrique" : return TypeCarburant.Electrique;
			case "Gazole" : return TypeCarburant.Gazole;
			case "Essence" : return TypeCarburant.Essence;
			case "Gpl" : return TypeCarburant.Gpl;
			case "Sp95" : return TypeCarburant.Sp95;
			default : throw new Exception("Le type de carburant indiqué est incorrect");
		}
	}

	public int getIdVehicule() {
		return idVehicule;
	}

	public String getMatricule() {
		return matricule;
	}

	public String getMarque() {
		return marque;
	}

	public String getModele() {
		return modele;
	}

	public int getKilometrage() {
		return kilometrage;
	}

	public boolean isClimatise() {
		return climatise;
	}

	public int getConsommationCarburant() {
		return consommationCarburant;
	}

	public TypeBoite getTypeBoite() {
		return typeBoite;
	}

	public TypeCarburant getTypeCarburant() {
		return typeCarburant;
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public Categorie getCategorie() {
		return Categorie.getCategorie(this.idCategorie);
	}
	public int getIdAgence() {
		return idAgence;
	}

	public static HashMap<Integer, Vehicule> getVehicules() {
		return vehicules;
	}

	@Override
	public String toString() {
		return "Matricule : " + matricule + "\n Marque : " + marque + "\n Modele : " + modele + "\n Climatise : "
				+ climatise + "\n Type Boite : " + typeBoite + "\n Type Carburant : " + typeCarburant + "\n Catégorie : " + this.getCategorie().getLabel() + "\n \n";
	}

	public void setIdVehicule(int idVehicule) {
		this.idVehicule = idVehicule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public void setKilometrage(int kilometrage) {
		this.kilometrage = kilometrage;
	}

	public void setClimatise(boolean climatise) {
		this.climatise = climatise;
	}

	public void setConsommationCarburant(int consommationCarburant) {
		this.consommationCarburant = consommationCarburant;
	}

	public void setTypeBoite(TypeBoite typeBoite) {
		this.typeBoite = typeBoite;
	}

	public void setTypeCarburant(TypeCarburant typeCarburant) {
		this.typeCarburant = typeCarburant;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public void setIdAgence(int idAgence) {
		this.idAgence = idAgence;
	}

	public static void setVehicules(HashMap<Integer, Vehicule> vehicules) {
		Vehicule.vehicules = vehicules;
	}
	
}
