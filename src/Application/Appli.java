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
			
			System.out.println("1: Gestion des devis et locations");
			System.out.println("2: Gestion des clients");
			System.out.println("3: Gestion des ressources");	
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
		//Fermeture de l'acc�s � la database
		da.closeConnection();
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
			System.out.println("1: Ajouter un nouveau client");
			System.out.println("2: Modifier les informations d'un client");
			System.out.println("3: Supprimer les informations d'un client");
			System.out.println("4: Lister les v�hicules disponibles par cat�gorie");
			System.out.println("5: Rechercher un v�hicule par marque");
			System.out.println("6: Lister tous les v�hicules en cours de location");
			System.out.println("7: Louer un v�hicule");
			System.out.println("0: Retour");
			
			String input = sc.nextLine();
			
			switch(input) {
				
				case "4" : 
							da.getVehiculesAvailable();
							break;
					
				case "5" : 
							System.out.println("Indiquez une marque");
							String marque = sc.nextLine();
							da.getVehiculesByMarque(marque);
							break;
				case "6" : 
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
		System.out.println("");
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
			System.out.println("1: Ajouter un nouveau client");
			System.out.println("2: Modifier les informations d'un client");
			System.out.println("3: Supprimer les informations d'un client");
			System.out.println("4: Liste des clients par ordre alphab�tique");
			System.out.println("5: Rechercher un client par son nom");
			System.out.println("6: Rechercher tous les clients ayant une location en cours");
			System.out.println("7: Rechercher tous les clients ayant lou�s un v�hicule donn�");
			System.out.println("0: Retour");
			
			String input = sc.nextLine();
			
			switch(input) {
				case "1" : 
							System.out.println("Indiquez les informations de l'adresse du client avec le format suivant : rue,ville,codePostal");
							String adresse = sc.nextLine();
							String[] adresseInfos = adresse.split(",");
							//idAdresse = 100 est juste un nombre al�atoire car ce n'est pas cet ID qui sera ins�r� dans la base de donn�e
							Adresse ad = new Adresse(100, adresseInfos[0], adresseInfos[1], Integer.parseInt(adresseInfos[2]));
							da.addAdresse(ad);
							
							
							System.out.println("Indiquez les informations du nouveau client avec le format suivant : nom,prenom,email,numTelephone");
							String client = sc.nextLine();
							String[] clientInfos = client.split(",");
							int idAdresse = da.getIdAdresse(ad);
							
							Client newclient = new Client(100, clientInfos[0], clientInfos[1], clientInfos[2], Integer.parseInt(clientInfos[3]), null, idAdresse,-1);
							da.addNewClient(newclient);
							break;
					
				case "2" : 
					
				case "3" : 
							System.out.println("Indiquez le nom du client que vous voulez supprimer");
							String nom = sc.nextLine();
							
							List<Client> listeClient = da.getClient();
							List<Adresse> listeAdresse = da.getAdresses();
							
							for(Client c : listeClient) {
								if(nom.equals(c.getNom())) {
									da.deleteClient(c);
									
									for(Adresse adr : listeAdresse) {
										if(adr.getIdAdresse() == c.getIdAdresse()) {
											da.deleteAdresse(adr);
										}
									}
								}
							}
							
							
							break;
				case "4" : 
							da.getClient();
							break;
					
				case "5" : 
							System.out.println("Indiquez le nom d'un client");
							String clientName = sc.nextLine();
							da.getClientByName(clientName);
							break;
				case "6" : 
						    da.getClientRenting();
						    break;
						    
				case "7" : 
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
		System.out.println(Appli.SEPARATOR);
		System.out.println("Debut de la gestion des locations");
		System.out.println(Appli.SEPARATOR);
		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Que souhaitez-vous faire ?");
			System.out.println("");
			System.out.println("1: Ajouter une r�servation");
			System.out.println("2: Ajouter une location");
			System.out.println("3: Retour d'une location");
			System.out.println("4: Annulation d'une r�servation");
			System.out.println("5: Calcul du devis d'une location termin�");
			System.out.println("0: Retour");
			
			String input = sc.nextLine();
			
			switch(input) {
				case "1" : Appli.R�servationVehicule(sc, da);
							break;
					
				case "2" : Appli.LocationVehicule(sc, da);
							break;
							
				case "3" : Appli.RetourVehicule(sc, da);
						    break;
						    
				case "4" : Appli.AnnulationReservation(sc, da);
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
					List<Vehicule> listeVehiculesDispo = da.getVehiculesAvailableReservation(dateD, dateF, idCat);
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
								exit = true;
								
							}else {
									System.out.println("");
									System.out.println("L'ID indiqu� est incorrect, veuillez recommencer");
									System.out.println("");
							}
						}
						
						
					}else {
						System.out.println("Aucun v�hicule n'est disponible selon les crit�res demand�s");
						exit = true;
					}
				}
				
				
			}
			
			
			
		}
		
	}
	
	public static void LocationVehicule(Scanner sc, DataAccess da) {
		// Indiquer le couple idc, idv, dated, datef pour cr�er un tuple dans la table louer parmis
		System.out.println("");
		System.out.println("Location d'un v�hicule");
		System.out.println("");
		
		System.out.println("Indiquer les informations suivante : idClient, idVehicule, dateD, dateF (format des dates : YYYY-MM-DD)");
		System.out.println("ex : 1 2 2021-04-05 2021-06-03");
		System.out.println("");
		String input = sc.nextLine();
		
		String[] inputs = input.split(" ");
		int idC = Integer.parseInt(inputs[0]);
		int idV = Integer.parseInt(inputs[1]);
		Date dateD = Appli.DateParser(inputs[2]);
		Date dateF = Appli.DateParser(inputs[3]);
		
		if(da.reservationExist(idC, idV, dateD, dateF)) {
			boolean exit = false;
			while(!exit) {
				System.out.println("");
				System.out.println("Souhaitez-vous b�n�ficier de l'assurance ? (Y/N)");
				System.out.println("");
				
				input = sc.nextLine();
				
				if(input.equals("Y")) {
					exit = true;
					da.addLocation(idC, idV, dateD, dateF, true);
				}else if (input.equals("N")){
					exit = true;
					da.addLocation(idC, idV, dateD, dateF, false);
				}else {
					System.out.println("");
					System.out.println("R�ponse invalide, veuillez recommencer");
					System.out.println("");
				}
			}
		}else {
			System.out.println("La r�servation indiqu� n'existe pas.");
		}
	}
	
	public static void RetourVehicule(Scanner sc, DataAccess da) {
		System.out.println("");
		System.out.println("Retour d'un v�hicule");
		System.out.println("");
		
		System.out.println("Indiquer les informations suivante : idClient, idVehicule, dateD, dateF (format des dates : YYYY-MM-DD)");
		System.out.println("ex : 1 2 2021-04-05 2021-06-03");
		System.out.println("");
		String input = sc.nextLine();
		
		String[] inputs = input.split(" ");
		int idC = Integer.parseInt(inputs[0]);
		int idV = Integer.parseInt(inputs[1]);
		Date dateD = Appli.DateParser(inputs[2]);
		Date dateF = Appli.DateParser(inputs[3]);
		if(da.locationExist(idC, idV, dateD, dateF)) {
			boolean exit = false;
			boolean endommage = false;
			int montantCarburant = 0;
			while(!exit) {
				System.out.println("");
				System.out.println("Le v�hicule est-il endommag� ? (Y/N)");
				System.out.println("");
				
				input = sc.nextLine();
				
				if(input.equals("Y")) {
					exit = true;
					endommage = true;
				}else if (input.equals("N")){
					exit = true;
					endommage = false;
				}else {
					System.out.println("");
					System.out.println("R�ponse invalide, veuillez recommencer");
					System.out.println("");
				}
			}
			
			exit = false;
			
			while(!exit) {
				System.out.println("");
				System.out.println("Indiquez le montant de carburant restant (entre 0 et 100)");
				System.out.println("");
				
				input = sc.nextLine();
				
				montantCarburant = Integer.parseInt(input);
				
				if(montantCarburant <= 100 && montantCarburant >= 0) {
					exit = true;
					da.endLocation(idC,idV,dateD,dateF,endommage,montantCarburant);
				} else {
					System.out.println("");
					System.out.println("Valeur indiqu� invalide, veuillez recommencer.");
					System.out.println("");
				}
			}
		}else {
			System.out.println("La location indiqu� n'existe pas.");
		}
		
	}
	
	public static void AnnulationReservation(Scanner sc, DataAccess da) {
		System.out.println("");
		System.out.println("Annulation d'une r�servation");
		System.out.println("");
		
		System.out.println("Indiquer les informations suivante : idClient, idVehicule, dateD, dateF (format des dates : YYYY-MM-DD");
		System.out.println("ex : 1 2 2021-04-05 2021-06-03");
		System.out.println("");
		
		String input = sc.nextLine();
		String[] inputs = input.split(" ");
		int idC = Integer.parseInt(inputs[0]);
		int idV = Integer.parseInt(inputs[1]);
		Date dateD = Appli.DateParser(inputs[2]);
		Date dateF = Appli.DateParser(inputs[3]);
		
		if(da.reservationExist(idC, idV, dateD, dateF)) {
			boolean exit = false;
			while(!exit) {
				System.out.println("Souhaitez-vous annuler la r�servation du client " + idC + "pour le v�hicule" + idV + " du " + dateD + " au " + dateF + " ? (Y/N)");
				input = sc.nextLine();
				
				if(input.equals("Y")) {
					exit = true;
					da.cancelReservation(idC, idV, dateD, dateF);
					
				}else if(input.equals("N")) {
					exit = true;
					System.out.println("Annulation de la r�servation non effective.");
				}else {
					System.out.println("");
					System.out.println("Valeur indiqu� invalide, veuillez recommencer.");
					System.out.println("");
				}
			}
			
		}else {
			System.out.println("La r�servation indiqu� n'existe pas.");
		}
		
		
		
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
