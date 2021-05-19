package model;

public class Employe {
	
	private int idEmp;
	private String nom;
	private String prenom;
	private String email;
	private String numTelephone;
	
	public Employe(int idE, String n, String p, String mail, String tel) {
		this.idEmp = idE;
		this.nom = n;
		this.prenom = p;
		this.email = mail;
		this.numTelephone = tel;
	}
}
