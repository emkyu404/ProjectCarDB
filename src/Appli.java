
import java.sql.Connection;
import java.sql.Date;
import Database.DataAccess;
import model.Categorie;
import model.Vehicule;
import utils.DateCalculator;


public class Appli {
	public static void main(String[] args) {
		String url = "jdbc:MYSQL://localhost/carDB";
		String usr = "root";
		String pas = "";
		
		
		DataAccess da = new DataAccess(url, usr, pas);
		da.getCategories();
		System.out.println(da.getClientRenting());
		da.getVehicules();
		
		da.getPFidelite();
		System.out.println(Vehicule.vehicules);
		Vehicule v = Vehicule.vehicules.get(1);
		System.out.println(da.getRecette(new Date(2021-1900,3-1,1), new Date(2021-1900, 4-1, 1)));
	}
}
