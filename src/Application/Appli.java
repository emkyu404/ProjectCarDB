package Application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import Database.DataAccess;
import model.Adresse;
import model.Categorie;
import model.Client;
import model.Vehicule;
import utils.DateCalculator;


public class Appli {
	
	public final static String SEPARATOR = "-------------------------------------------------";
	
	public static void main(String[] args) {
		String url = "jdbc:MYSQL://localhost/carDB";
		String usr = "root";
		String pas = "";
		
		try {
		DataAccess da = new DataAccess(url, usr, pas);
		
		Scanner sc = new Scanner(System.in);
		System.out.println(SEPARATOR);
		System.out.println("      Bienvenue sur CarRent");
		System.out.println(SEPARATOR);
		boolean exit = false;
		
		promptEnterKey();
		
		while(!exit) {
			
			System.out.println("Que souhaitez vous faire ? (indiquer un chiffre) :");
			System.out.println("");
			
			System.out.println("1 - Gestion des devis et locations");
			System.out.println("2 - Gestion des clients");
			System.out.println("3 - Gestion des ressources");	
			System.out.println("0: Sortie du programme");
			
			String input = sc.nextLine();
			System.out.println("");
			
			switch(input) {
					case "1": 
							Appli.GestionDevisLocation(sc,da);
							break;
						
					case "2": 
							Appli.GestionClients(sc,da);
							break;
					
					case "3":
							Appli.GestionRessource(sc,da);
							break;
						
					case "0":
							exit = true;
							break;
					
					default:
							ErreurMenu();
							break;
			}	
		}
		
		System.out.println("Fin du programme.");
		System.exit(1);
		}catch(SQLException e) {
			System.err.println("L'application Rentcar n'a pas pu se connecter � la base de donn�e. \n Erreur : \n");
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	/*********************************** APPLI RESSOURCE ***********************************/
	private static void GestionRessource(Scanner sc, DataAccess da) {
		
		System.out.println(Appli.SEPARATOR);
		System.out.println("Debut de la gestion des ressources");
		System.out.println(Appli.SEPARATOR);
		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Que souhaitez-vous faire ?");
			System.out.println("");
			System.out.println("1: Lister les v�hicules disponibles par cat�gorie");
			System.out.println("2: Rechercher un v�hicule par marque");
			System.out.println("3: Lister tous les v�hicules en cours de location");
			System.out.println("4: Louer un v�hicule");
			System.out.println("0: Annulation");
			
			String input = sc.nextLine();
			
			switch(input) {
				case "1" : 
							da.getVehiculesAvailable();
							break;
					
				case "2" : 
							System.out.println("Indiquez une marque");
							String marque = sc.nextLine();
							da.getVehiculesByMarque(marque);
							break;
				case "3" : 
						    da.getVehiculesBeingRent();
						    break;
				
				case "0" :  
							exit = true;
							break;
							
				default :  	
							Appli.ErreurMenu();
							break;
			
			}
		}
		System.out.println("Fin de la gestion des ressources");
		System.out.println("");
		
	}
	
	/*************************************************************************************/
	
	/*********************************** APPLI CLIENTS ***********************************/
	private static void GestionClients(Scanner sc, DataAccess da) {
	
		System.out.println(Appli.SEPARATOR);
		System.out.println("Debut de la gestion des ressources");
		System.out.println(Appli.SEPARATOR);
		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Que souhaitez-vous faire ?");
			System.out.println("");
			System.out.println("1: Liste des clients par ordre alphab�tique");
			System.out.println("2: Rechercher un client par son nom");
			System.out.println("3: Rechercher tous les clients ayant une location en cours");
			System.out.println("4: Rechercher tous les clients ayant lou�s un v�hicule donn�");
			System.out.println("0: Annulation");
			
			String input = sc.nextLine();
			
			switch(input) {
				case "1" : 
							da.getClient();
							break;
					
				case "2" : 
							System.out.println("Indiquez le nom d'un client");
							String nom = sc.nextLine();
							da.getClientByName(nom);
							break;
				case "3" : 
						    da.getClientRenting();
						    break;
						    
				case "4" : 
							System.out.println("Indiquez le mod�le du v�hicule");
							String modele = sc.nextLine();
							List<Vehicule> listeVehicules = da.getVehicules();
							Vehicule vehiculeLoue = null;
							
							for(Vehicule v : listeVehicules) {
								if(v.getModele().equals(modele)) {
									vehiculeLoue = v;
								}
							}
							
							da.getClientWHR(vehiculeLoue);
							break;
				    
				case "0" :  
							exit = true;
							break;
							
				default :  	
							Appli.ErreurMenu();
							break;
			
			}
		}
		System.out.println("Fin de la gestion clients");
		System.out.println("");
	}
	/**************************************************************************************/
	
	/******************************** APPLI DEVIS/LOCATION ********************************/
	private static void GestionDevisLocation(Scanner sc, DataAccess da) {
		
		/*Tout ce qui est reservation et location d'un vehicule*/
	}
	
	/* R�servation d'un v�hicule */
	private static void R�servationVehicule(Scanner sc, DataAccess da) {
		System.out.println(Appli.SEPARATOR);
		System.out.println("Reservation d'un v�hicule");
		System.out.println(Appli.SEPARATOR);
		boolean exit = false;
		
		
		while(!exit) {
			System.out.println("Veuillez choisir une cat�gorie de v�hicule");
			System.out.println("");
			System.out.println("1: LUXE");
			System.out.println("2: CONFORT");
			System.out.println("3: ECONOMIQUE");
			System.out.println("0: Annulation");
			
			int idVehicule, idCat = 0;
			
			String input = sc.nextLine();
			
			switch(input) {
				case "1" : 
							idCat = 1;
							break;
					
				case "2" : 
							idCat = 2;
							break;
				case "3" : 
							idCat = 3;
						    break;
				    
				case "0" :  
							exit = true;
							break;
							
				default :  	
							Appli.ErreurMenu();
							break;
			}
			
			if(!exit) {
			/* Indication des dates de r�servations */
			System.out.println("");
			System.out.println("Veuillez indiquer les dates de r�servations (d�but et fin) souhait�es au format suivant : YYYY-MM-DD/YYYY-MM-DD");
			System.out.println("");
			input = sc.nextLine();
			
			String[] dates = input.split("/");
			Date dateD = Appli.DateParser(dates[0]);
			Date dateF = Appli.DateParser(dates[1]);
			
				if(idCat != 0) {
					List<Vehicule> listeVehiculesDispo = da.getVehiculesAvailableReservation(dateF, dateF, idCat);
					if(listeVehiculesDispo.size() > 0) {
						
						System.out.println("");
						System.out.println("Voici les v�hicules disponibles");
						System.out.println("");
						for(Vehicule v : listeVehiculesDispo) {
							System.out.println(v.getIdVehicule() + ": [Marque : " + v.getMarque() + " - Modele : " + v.getModele() + "]");
						}
						
						boolean exit2 = false;
						System.out.println("");
						System.out.println("Veuillez choisir un v�hicule � r�server (indiquez l'ID associ�)");
						System.out.println("");
						while(!exit2) {
							input = sc.nextLine();
							
							int idV = Integer.parseInt(input);
							
							if(idV > 0) {
								exit2 = true;
								System.out.println("");
								System.out.println("Indiquez votre ID Client");
								System.out.println("");
								
								input = sc.nextLine();
								int idC = Integer.parseInt(input);
								da.addReservation(idC, idV, dateD, dateF);
								
							}else {
									System.out.println("");
									System.out.println("L'ID indiqu� est incorrect, veuillez recommencer");
									System.out.println("");
							}
						}
						
						
					}else {
						System.out.println("Aucun v�hicule n'est disponible selon les crit�res demand�s");
					}
				}
				
				
			}
			
			
			
		}
		
	}
	
	public static void LocationVehicule() {
		// Indiquer le couple idc, idv, dated, datef pour cr�er un tuple dans la table louer parmis
		System.out.println("");
		System.out.println("Locati");
		System.out.println("");
		
	}
	
	public static void RetourVehicule() {
		// Indiquer le couple idc, idv, dated, datef etc ... pour terminer la location (modification de la dateRetour)
		// Affichage du montant de la location en +
	}
	
	public static void AnnulationReservation() {
		
	}
	
	
	
	/**************************************************************************************/
	
	public static void promptEnterKey() {
		Scanner sc = new Scanner(System.in);
		System.out.println("   ... Entrer une touche pour continuer ...");
		sc.nextLine();
		System.out.println(SEPARATOR);
	}
	
	public static void ErreurMenu() {
		  System.out.println("Erreur : La commande indiqu� ne correspond pas � une action possible.");
		  System.out.println("Veuillez r�essayer et choisir parmis les options possibles.");
		  System.out.println("");
	}
	
	public static Date DateParser(String s) {
		String[] date = s.split("-");
		int year = Integer.parseInt(date[0]) - 1900;
		int month = Integer.parseInt(date[1]) - 1;
		int day = Integer.parseInt(date[2]);;
		return new Date(year,month,day);
	}
}