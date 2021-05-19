package model;

import java.sql.Date;
import java.util.ArrayList;

public class Louer {
	private int idVehicule;
	private int idClient;
	private Date dateDebut;
	private Date dateFin;
	
	public static ArrayList<Louer>locations = new ArrayList<Louer>();
	
	public Louer(int idVehicule, int idClient, Date dateDebut, Date dateFin) {
		this.idVehicule = idVehicule;
		this.idClient = idClient;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		
		if(!locations.contains(this)) {
			locations.add(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateDebut == null) ? 0 : dateDebut.hashCode());
		result = prime * result + ((dateFin == null) ? 0 : dateFin.hashCode());
		result = prime * result + idClient;
		result = prime * result + idVehicule;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Louer other = (Louer) obj;
		if (dateDebut == null) {
			if (other.dateDebut != null)
				return false;
		} else if (!dateDebut.equals(other.dateDebut))
			return false;
		if (dateFin == null) {
			if (other.dateFin != null)
				return false;
		} else if (!dateFin.equals(other.dateFin))
			return false;
		if (idClient != other.idClient)
			return false;
		if (idVehicule != other.idVehicule)
			return false;
		return true;
	}

	
	
}
