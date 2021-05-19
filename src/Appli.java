<<<<<<< HEAD
=======
import java.sql.Connection;
import java.sql.Date;

>>>>>>> ae78289644a4309d7747d766e9f6281c7c8dd26f
import Database.DataAccess;
import utils.DateCalculator;


public class Appli {
	public static void main(String[] args) {
		String url = "jdbc:MYSQL://localhost/carDB";
		String usr = "root";
		String pas = "";
		
		
		DataAccess da = new DataAccess(url, usr, pas);
		
<<<<<<< HEAD
		da.getVehicules();
=======
	
		
>>>>>>> ae78289644a4309d7747d766e9f6281c7c8dd26f
	}
}
