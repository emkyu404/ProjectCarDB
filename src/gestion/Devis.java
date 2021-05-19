package gestion;

import model.Client;
import model.Vehicule;

public class Devis {
	private Client client;
	private Vehicule vehicule;
	private boolean assurance;
	private boolean endommage;
	private int duree;
	
	private final int montantAssurance = 1400;
	
	public Devis(Client c, Vehicule v, int d, boolean a, boolean e) {
		this.duree = d;
		this.assurance = a;
		this.endommage = e;
	}
	
	public Devis(Client c, Vehicule v, int d, boolean e) {
		this(c,v,d,false, e);
	}
	
	
	public float calculTarif() {
		float res = 0;
		
		return res;
	}
}
