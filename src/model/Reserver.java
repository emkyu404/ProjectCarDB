package model;

import java.util.ArrayList;

public class Reserver {
	private int idClient;
	private int idVehicule;
	
	public static ArrayList<Reserver> reservations = new ArrayList<Reserver>();
	
	public Reserver(int idC, int idV) {
		this.idClient = idC;
		this.idVehicule = idV;
		
		if(!reservations.contains(this)) {
			reservations.add(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Reserver other = (Reserver) obj;
		if (idClient != other.idClient)
			return false;
		if (idVehicule != other.idVehicule)
			return false;
		return true;
	}
}
