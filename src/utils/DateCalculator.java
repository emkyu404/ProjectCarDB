package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class DateCalculator {
	
	private final static int APIerrorFix= 1900;
	
	public static int numberOfDays(Date debut, Date fin) {
		LocalDate ldebut = LocalDate.of(debut.getYear() + APIerrorFix, debut.getMonth()+1, debut.getDate());
		LocalDate lfin = LocalDate.of(fin.getYear() + APIerrorFix, fin.getMonth()+1, fin.getDate());
		
		long noOfDaysBetween = ChronoUnit.DAYS.between(ldebut, lfin);
		return (int)noOfDaysBetween;
	}
}
