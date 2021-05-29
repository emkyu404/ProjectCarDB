package gestion;

import java.sql.Date;

import model.Categorie;
import model.Client;
import model.Louer;
import model.PFidelite;
import model.Vehicule;
import utils.DateCalculator;

public class Devis {
	private Client client;
	private Vehicule vehicule;
	private boolean assurance;
	private boolean endommage;
	private int duree;
	private int carburantRestant;
	private Date dateD;
	private Date dateF;
	private Date dateR;
	
	private final int montantAssurance = 1400;
	private final int prixDuPlein = 100;
	private final int tauxRetardJ = 500;
	private final int prixRemiseEnEtat = 5000;
	
	public Devis(Client c, Vehicule v, boolean a, boolean e, int carburantRestant, Date dateD, Date dateF, Date dateR) {
		this.client = c;
		this.vehicule = v;
		this.duree = DateCalculator.numberOfDays(dateD, dateR);
		this.assurance = a;
		this.endommage = e;
		this.carburantRestant = carburantRestant;
		this.dateD = dateD;
		this.dateF = dateF;
		this.dateR = dateR;
		System.out.println(dateD + " / " + dateF + " / " + dateR);
	}
	
	public float calculFacture() {
		float res = 0;
		//Calcul de la pénalité de retard
		int retard = DateCalculator.numberOfDays(dateF, dateR);
		if(retard > 0) {
			res += retard * tauxRetardJ;
		}
		
		// Calcul prix de la consommation de carburat
		System.out.println(this.vehicule.getConsommationCarburant());
		int cu = this.vehicule.getConsommationCarburant() - this.carburantRestant;
		if(cu >= 0 && cu < 25) {
			res += prixDuPlein;
		}else if(cu >= 25 && cu < 50) {
			res += prixDuPlein * (0.75);
		}else if(cu >= 50 && cu < 75) {
			res += prixDuPlein * (0.50);
		}else if(cu >= 50 && cu < 100) {
			res += prixDuPlein * (0.25);
		}
		
		// Calcul véhicule endommagé
		if(endommage && !assurance) {
			res += prixRemiseEnEtat;
		}
		
		// Calcul si assurance
		if(assurance) {
			res += montantAssurance;
		}
		
		// Calcul tarif de location
		res += Categorie.categories.get(vehicule.getIdCategorie()).getTarifJ() * DateCalculator.numberOfDays(dateD, dateF);
		
		//Déduction Pfidelite
		
		if(client.getIdPFidelite() > 0) {
			PFidelite pf = PFidelite.pfidelites.get(client.getIdPFidelite());
			if(DateCalculator.numberOfDays(client.getDateSouscription(), dateR) <= pf.getDuree()) {
				res = res - res*pf.getTauxR();
			}
		}
	
		
		return res;
	}

	@Override
	public String toString() {
		return "Devis [client=" + client + ", vehicule=" + vehicule + ", assurance=" + assurance + ", endommage="
				+ endommage + ", duree=" + duree + ", carburantRestant=" + carburantRestant + ", dateD=" + dateD
				+ ", dateF=" + dateF + ", dateR=" + dateR + ", montantAssurance=" + montantAssurance + ", prixDuPlein="
				+ prixDuPlein + ", tauxRetardJ=" + tauxRetardJ + ", prixRemiseEnEtat=" + prixRemiseEnEtat + "]";
	}
	
}
