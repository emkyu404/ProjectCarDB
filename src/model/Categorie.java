package model;

import java.util.HashMap;

public class Categorie {
	private int idCategorie;
	private String label;
	private float tarifJ;
	private float caution;
	
	public static HashMap<Integer,Categorie>categories = new HashMap<Integer, Categorie>();
	
	public Categorie(int idCategorie, String label, float tarifJ, float caution) {
		this.idCategorie=idCategorie;
		this.label = label;
		this.tarifJ = tarifJ;
		this.caution = caution;
		
		categories.put(idCategorie, this);
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public String getLabel() {
		return label;
	}

	public float getTarifJ() {
		return tarifJ;
	}

	public float getCaution() {
		return caution;
	}

	public static HashMap<Integer, Categorie> getCategories() {
		return categories;
	}
	
	public static Categorie getCategorie(int idCategorie) {
		return categories.get(idCategorie);
	}
}
