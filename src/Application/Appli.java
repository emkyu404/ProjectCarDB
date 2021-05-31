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
import model.Agence;
import model.Categorie;
import model.Client;
import model.Vehicule;
import utils.DateCalculator;
import utils.TypeBoite;
import utils.TypeCarburant;


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
		//Fermeture de l'accès à la database
		da.closeConnection();
		System.exit(1);
		}catch(SQLException e) {
			System.err.println("L'application Rentcar n'a pas pu se connecter à la base de donnée. \n Erreur : \n");
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
			System.out.println("1: Ajouter un nouveau véhicule");
			System.out.println("2: Modifier les informations d'un véhicule");
			System.out.println("3: Supprimer un véhicule");
			System.out.println("4: Lister les véhicules disponibles par catégorie");
			System.out.println("5: Rechercher un véhicule par marque");
			System.out.println("6: Lister tous les véhicules en cours de location");
			System.out.println("7: Louer un véhicule");
			System.out.println("0: Retour");
			
			String input = sc.nextLine();
			
			switch(input) {
			
				case "1" : Appli.ajouterVehicule(sc, da);
							break;
							
				case "2" : Appli.modificationVehicule(sc, da);
							break;
				
				case "3" : Appli.supprimerVehicule(sc, da);
							break;
				
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
	
	private static void ajouterVehicule(Scanner sc, DataAccess da) {
		System.out.println("");
		System.out.println("Ajout d'un nouveau véhicule");
		System.out.println("");
		

		System.out.println("Veuillez choisir la catégorie de véhicule");
		System.out.println("");
		System.out.println("1: LUXE");
		System.out.println("2: CONFORT");
		System.out.println("3: ECONOMIQUE");
		System.out.println("0: Annulation");
		
		int idCat = 0;
		
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
			    
			case "0" :  System.out.println("Annulation de l'ajout d'un nouveau véhicule");
						return;
						
			default :  	
						Appli.ErreurMenu();
						break;
		}
		
		String matricule, marque, modele;
		int kilometrage;
		int climatise = 0;
		int idAgence = 0;
		TypeBoite tb = null;
		TypeCarburant tc = null;
		boolean exit = false;
		while(!exit) {
			System.out.println("Indiquer les informations du véhicule : matricule, marque, modele, kilometrage");
			System.out.println("ex : UB-235-FB,Renault,Zoe,5000");
			System.out.println("");
			
			input = sc.nextLine();
			
			String[] inputs = input.split(",");
			if(inputs.length == 4) {
				exit = true;
				
				matricule = inputs[0];
				marque = inputs[1];
				modele = inputs[2];
				kilometrage = Integer.parseInt(inputs[3]);
				boolean exit2 = false;
				while(!exit2) {
					System.out.println("");
					System.out.println("Le véhicule est-il climatisé ? (Y/N)");
					System.out.println("");
					
					input = sc.nextLine();
					
					if(input.equals("Y")) {
						exit2 = true;
						climatise = 1;
					}else if (input.equals("N")){
						exit2 = true;
						climatise = 0;
					}else {
						System.out.println("");
						System.out.println("Réponse invalide, veuillez recommencer");
						System.out.println("");
					}
				}
				
				// Choisir le type de boite
				exit2 = false;
				while(!exit2) {
					System.out.println("");
					System.out.println("Choisir le type de de boite :");
					System.out.println("1: Automatique");
					System.out.println("2: Manuelle");
					
					input = sc.nextLine();
					
					switch(input) {
						case "1" : tb = TypeBoite.Automatique; exit2 = true; break;
						case "2" : tb = TypeBoite.Manuelle; exit2 = true; break;
						default : System.out.println("Choix invalide, veuillez recommencer.");
					}
				}
				
				exit2 = false;
				
				while(!exit2) {
					System.out.println("");
					System.out.println("Choisir le type de carburant :");
					System.out.println("1: Electrique");
					System.out.println("2: Gazole");
					System.out.println("3: Essence");
					System.out.println("4: Gpl");
					System.out.println("5: Sp95");
					
					input = sc.nextLine();
					
					switch(input) {
						case "1" : tc = TypeCarburant.Electrique; exit2 = true; break;
						case "2" : tc = TypeCarburant.Gazole; exit2 = true; break;
						case "3" : tc = TypeCarburant.Essence; exit2 = true; break;
						case "4" : tc = TypeCarburant.Gpl; exit2 = true; break;
						case "5" : tc = TypeCarburant.Sp95; exit2 = true; break;
						default : System.out.println("Choix invalide, veuillez recommencer.");
					}
				}
				
				exit2 = false;
				
				while(!exit2) {
					System.out.println("");
					System.out.println("Choisir l'agence du véhicule :");
					
					for(Agence a : da.getAgences()) {
						System.out.println(a.getIdAgence() + ": " + a.getNom());
					}
					
					input = sc.nextLine();
					
					try {
						idAgence = Integer.parseInt(input);
						exit2 = true;
						
					}catch(Exception e) {
						System.out.println("Erreur dans le choix de l'agence, veuillez recommencer");
					}
					
				}
				
				Vehicule nouveauV = new Vehicule(-1, matricule, marque, modele, kilometrage, climatise, 100, tb.toString(), tc.toString(),idCat, idAgence);
				da.addNewVehicule(nouveauV);
				
			}else {
				System.out.println("Erreur dans l'entrée des données, veuillez recommencer");
			}
		}
	}
	
	private static void supprimerVehicule(Scanner sc, DataAccess da) {
		System.out.println("");
		System.out.println("Ajout d'un nouveau véhicule");
		System.out.println("");
		
		System.out.println("Indiquer le matricule du véhicule à supprimer :");
		
		String input = sc.nextLine();
		
		da.deleteVehicule(input);
		
	}
	
	private static void modificationVehicule(Scanner sc, DataAccess da) {
		System.out.println("");
		System.out.println("Modification d'un véhicule");
		System.out.println("");
		
		System.out.println("Indiquer le matricule du véhicule à modifier :");
		
		boolean exit4 = false;
		while(!exit4) {
			String input = sc.nextLine();
			try {
				Vehicule vehiculeToModify = da.getVehiculeByMatricule(input).get(0);
				exit4 = true;
				boolean exit3 = false;
				while(!exit3) {
					System.out.println("");
					System.out.println("Que souhaitez-vous modifier ?");
					System.out.println("1: Informations du véhicules");
					System.out.println("2: Agence associé au véhicule");
					System.out.println("3: Modification(s) terminée(s)");
					System.out.println("0: Annulation des modifications");
					input = sc.nextLine();
					switch(input) {
						case "1" :  boolean exit = false;
									while(!exit) {
										System.out.println("Indiquer les informations du véhicule : matricule, marque, modele, kilometrage");
										System.out.println("ex : UB-235-FB,Renault,Zoe,5000");
										System.out.println("");
										input = sc.nextLine();
									
										String[] inputs = input.split(",");
										if(inputs.length == 4) {
											exit = true;
											vehiculeToModify.setMatricule(inputs[0]);
											vehiculeToModify.setMarque(inputs[1]);
											vehiculeToModify.setModele(inputs[2]);
											vehiculeToModify.setKilometrage(Integer.parseInt(inputs[3]));
											boolean exit2 = false;
											while(!exit2) {
												System.out.println("");
												System.out.println("Le véhicule est-il climatisé ? (Y/N)");
												System.out.println("");
												
												input = sc.nextLine();
												
												if(input.equals("Y")) {
													exit2 = true;
													vehiculeToModify.setClimatise(true);
												}else if (input.equals("N")){
													exit2 = true;
													vehiculeToModify.setClimatise(false);
												}else {
													System.out.println("");
													System.out.println("Réponse invalide, veuillez recommencer");
													System.out.println("");
												}
											}
											
											// Choisir le type de boite
											exit2 = false;
											while(!exit2) {
												System.out.println("");
												System.out.println("Choisir le type de de boite :");
												System.out.println("1: Automatique");
												System.out.println("2: Manuelle");
												
												input = sc.nextLine();
												
												switch(input) {
													case "1" : vehiculeToModify.setTypeBoite(TypeBoite.Automatique); exit2 = true; break;
													case "2" : vehiculeToModify.setTypeBoite(TypeBoite.Manuelle); exit2=true; break;
													default : System.out.println("Choix invalide, veuillez recommencer."); break;
													
												}
											}
											
											exit2 = false;
											
											while(!exit2) {
												System.out.println("");
												System.out.println("Choisir le type de carburant :");
												System.out.println("1: Electrique");
												System.out.println("2: Gazole");
												System.out.println("3: Essence");
												System.out.println("4: Gpl");
												System.out.println("5: Sp95");
												
												input = sc.nextLine();
												
												switch(input) {
													case "1" : vehiculeToModify.setTypeCarburant(TypeCarburant.Electrique); exit2 = true; break;
													case "2" : vehiculeToModify.setTypeCarburant(TypeCarburant.Gazole); exit2 = true; break;
													case "3" : vehiculeToModify.setTypeCarburant(TypeCarburant.Essence); exit2 = true; break;
													case "4" : vehiculeToModify.setTypeCarburant(TypeCarburant.Gpl); exit2 = true; break;
													case "5" : vehiculeToModify.setTypeCarburant(TypeCarburant.Sp95); exit2 = true; break;
													default : System.out.println("Choix invalide, veuillez recommencer."); break;
												}
											}
											
										}else {
											System.out.println("Nombre d'arguments invalide, veuillez recommencer.");
										} 
									} 
									break;
						
						case "2" : boolean exit2 = false;
									while(!exit2) {
										System.out.println("");
										System.out.println("Choisir l'agence du véhicule :");
										
										for(Agence a : da.getAgences()) {
											System.out.println(a.getIdAgence() + ": " + a.getNom());
										}
										
										input = sc.nextLine();
										
										try {
											vehiculeToModify.setIdAgence(Integer.parseInt(input));
											exit2 = true;
											
										}catch(Exception e) {
											System.out.println("Erreur dans le choix de l'agence, veuillez recommencer");
										}
										
									} break;
									
						case "3" : exit3 = true;
								   da.updateVehicule(vehiculeToModify);
								   break;
						case "0" : System.out.println("Annulation de la modification");
								   return;
						
								   
						default : System.out.println("");
						          System.out.println("Choix invalide, veuillez recommencer.");
						          System.out.println("");
					}
				}
				
			}catch(Exception e) {
				System.out.println("Une erreur est survenu, veuillez recommencer.");
			}
		}
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
			System.out.println("4: Liste des clients par ordre alphabétique");
			System.out.println("5: Rechercher un client par son nom");
			System.out.println("6: Rechercher tous les clients ayant une location en cours");
			System.out.println("7: Rechercher tous les clients ayant loués un véhicule donné");
			System.out.println("0: Retour");
			
			String input = sc.nextLine();
			
			switch(input) {
				case "1" : 
							System.out.println("Indiquez les informations de l'adresse du client avec le format suivant : rue,ville,codePostal");
							String adresse = sc.nextLine();
							String[] adresseInfos = adresse.split(",");
							//idAdresse = 100 est juste un nombre aléatoire car ce n'est pas cet ID qui sera inséré dans la base de donnée
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
		System.out.println(Appli.SEPARATOR);
		System.out.println("Debut de la gestion des locations");
		System.out.println(Appli.SEPARATOR);
		
		boolean exit = false;
		
		while(!exit) {
			System.out.println("Que souhaitez-vous faire ?");
			System.out.println("");
			System.out.println("1: Ajouter une réservation");
			System.out.println("2: Ajouter une location");
			System.out.println("3: Retour d'une location");
			System.out.println("4: Annulation d'une réservation");
			System.out.println("0: Retour");
			
			String input = sc.nextLine();
			
			switch(input) {
				case "1" : Appli.RéservationVehicule(sc, da);
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
	
	/* Réservation d'un véhicule */
	private static void RéservationVehicule(Scanner sc, DataAccess da) {
		System.out.println(Appli.SEPARATOR);
		System.out.println("Reservation d'un véhicule");
		System.out.println(Appli.SEPARATOR);
		boolean exit = false;
		
		
		while(!exit) {
			System.out.println("Veuillez choisir une catégorie de véhicule");
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
			/* Indication des dates de réservations */
			System.out.println("");
			System.out.println("Veuillez indiquer les dates de réservations (début et fin) souhaitées au format suivant : YYYY-MM-DD/YYYY-MM-DD");
			System.out.println("");
			input = sc.nextLine();
			
			String[] dates = input.split("/");
			Date dateD = Appli.DateParser(dates[0]);
			Date dateF = Appli.DateParser(dates[1]);
			
				if(idCat != 0) {
					List<Vehicule> listeVehiculesDispo = da.getVehiculesAvailableReservation(dateD, dateF, idCat);
					if(listeVehiculesDispo.size() > 0) {
						
						System.out.println("");
						System.out.println("Voici les véhicules disponibles");
						System.out.println("");
						for(Vehicule v : listeVehiculesDispo) {
							System.out.println(v.getIdVehicule() + ": [Marque : " + v.getMarque() + " - Modele : " + v.getModele() + "]");
						}
						
						boolean exit2 = false;
						System.out.println("");
						System.out.println("Veuillez choisir un véhicule à réserver (indiquez l'ID associé)");
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
									System.out.println("L'ID indiqué est incorrect, veuillez recommencer");
									System.out.println("");
							}
						}
						
						
					}else {
						System.out.println("Aucun véhicule n'est disponible selon les critères demandés");
						exit = true;
					}
				}
				
				
			}
			
			
			
		}
		
	}
	
	public static void LocationVehicule(Scanner sc, DataAccess da) {
		// Indiquer le couple idc, idv, dated, datef pour créer un tuple dans la table louer parmis
		System.out.println("");
		System.out.println("Location d'un véhicule");
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
				System.out.println("Souhaitez-vous bénéficier de l'assurance ? (Y/N)");
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
					System.out.println("Réponse invalide, veuillez recommencer");
					System.out.println("");
				}
			}
		}else {
			System.out.println("La réservation indiqué n'existe pas.");
		}
	}
	
	public static void RetourVehicule(Scanner sc, DataAccess da) {
		System.out.println("");
		System.out.println("Retour d'un véhicule");
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
				System.out.println("Le véhicule est-il endommagé ? (Y/N)");
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
					System.out.println("Réponse invalide, veuillez recommencer");
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
					System.out.println("Valeur indiqué invalide, veuillez recommencer.");
					System.out.println("");
				}
			}
		}else {
			System.out.println("La location indiqué n'existe pas.");
		}
		
	}
	
	public static void AnnulationReservation(Scanner sc, DataAccess da) {
		System.out.println("");
		System.out.println("Annulation d'une réservation");
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
				System.out.println("Souhaitez-vous annuler la réservation du client " + idC + "pour le véhicule" + idV + " du " + dateD + " au " + dateF + " ? (Y/N)");
				input = sc.nextLine();
				
				if(input.equals("Y")) {
					exit = true;
					da.cancelReservation(idC, idV, dateD, dateF);
					
				}else if(input.equals("N")) {
					exit = true;
					System.out.println("Annulation de la réservation non effective.");
				}else {
					System.out.println("");
					System.out.println("Valeur indiqué invalide, veuillez recommencer.");
					System.out.println("");
				}
			}
			
		}else {
			System.out.println("La réservation indiqué n'existe pas.");
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
		  System.out.println("Erreur : La commande indiqué ne correspond pas à une action possible.");
		  System.out.println("Veuillez réessayer et choisir parmis les options possibles.");
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
