import java.sql.Connection;
import java.sql.Date;

import Database.DataAccess;
import utils.DateCalculator;


public class Appli {
	public static void main(String[] args) {
		String url = "jdbc:MYSQL://localhost/carDB";
		String usr = "root";
		String pas = "";
		
		
		DataAccess da = new DataAccess(url, usr, pas);
		
	
		
	}
}
