package model;

public class Gerant extends Employe{

	private String login;
	private String mdp;
	
	public Gerant(int idE, String n, String p, String mail, String tel, String log, String mdp) {
		super(idE, n, p, mail, tel);
		this.login = log;
		this.mdp = mdp;
	}

}
