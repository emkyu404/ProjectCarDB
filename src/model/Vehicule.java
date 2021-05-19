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
	
	public Vehicule(int idVehicule, String matricule, String marque, String modele, int kilometrage, boolean climatise, int consommmationCarburant, String typeBoite, String typeCarburant, int idCategorie, int idAgence) {
		
	}
	
	private TypeBoite whichTypeBoite(String typeBoite) {
		switch(typeBoite) {
		
		}
	}
}
