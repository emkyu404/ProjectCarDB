package Application;

import java.sql.Connection;
import java.sql.Date;
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
			System.out.println("1: Lister les véhicules disponibles par catégorie");
			System.out.println("2: Rechercher un véhicule par marque");
			System.out.println("3: Lister tous les véhicules en cours de location");
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
			System.out.println("1: Liste des clients par ordre alphabétique");
			System.out.println("2: Rechercher un client par son nom");
			System.out.println("3: Rechercher tous les clients ayant une location en cours");
			System.out.println("4: Rechercher tous les clients ayant loués un véhicule donné");
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
							System.out.println("Indiquez le modèle du véhicule");
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
	
	/**************************************************************************************/
	
	public static void promptEnterKey() {
		Scanner sc = new Scanner(System.in);
		System.out.println("   ... Entrer une touche pour continuer ...");
		sc.nextLine();
		System.out.println(SEPARATOR);
	}
	
	public static void ErreurMenu() {
		  System.out.println("Erreur : La commande indiqué ne correspond pas à une action possible.");
		  System.out.println("Veuillez réessayer et choisir parmis les options possibles.");
		  System.out.println("");
	}
}
