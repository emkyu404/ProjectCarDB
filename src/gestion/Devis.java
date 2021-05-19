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
	
	private final int montantAssurance = 1400;
	private final int prixDuPlein = 100;
	private final int tauxRetardJ = 500;
	private final int prixRemiseEnEtat = 5000;
	
	public Devis(Louer l, boolean a, boolean e, int carburantRestant) {
		this.client = l.getClient();
		this.vehicule = l.getVehicule();
		this.duree = l.getDureeLocation();
		this.assurance = a;
		this.endommage = e;
		this.carburantRestant = carburantRestant;
		this.dateD = l.getDateDebut();
		this.dateF = l.getDateFin();
	}
	
	public Devis(Louer l, boolean e, int carburantRestant) {
		this(l,false,e, carburantRestant);
	}
	
	
	public float calculFacture() {
		float res = 0;
		//Calcul de la pénalité de retard
		Date todayDate = new Date(System.currentTimeMillis());
		int retard = DateCalculator.numberOfDays(dateD, todayDate) - DateCalculator.numberOfDays(dateD, dateF);
		if(retard > 0) {
			res += retard * tauxRetardJ;
		}
		
		// Calcul prix de la consommation de carburat
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
		float tarifL = Categorie.categories.get(vehicule.getIdCategorie()).getTarifJ() * DateCalculator.numberOfDays(dateD, dateF);
		
		//Déduction Pfidelite
		
		if(!(client.getIdPFidelite() < 0)) {
			PFidelite pf = PFidelite.pfidelites.get(client.getIdPFidelite());
			if((client.getDateSouscription().getYear() + 1900) + pf.getDuree() <= todayDate.getYear() + 1900) {
				tarifL = tarifL - tarifL*pf.getTauxR();
			}
		}
		
		res += tarifL;
	
		
		return res;
	}
}
