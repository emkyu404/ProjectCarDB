package model;

import java.util.HashMap;

public class Adresse {
	private int idAdresse;
	private String rue;
	private String ville;
	private int codePostal;
	
	public static HashMap<Integer, Adresse> adresses = new HashMap<Integer,Adresse>();
	
	public Adresse(int idAdresse, String rue, String ville, int codePostal) {
		this.idAdresse = idAdresse;
		this.rue = rue;
		this.ville = ville;
		this.codePostal = codePostal;
		adresses.put(idAdresse, this);
	}
}
