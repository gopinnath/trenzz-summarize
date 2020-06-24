package ind.gopinnath.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

	public static LocalDateTime getPreviuosSunday() {
		LocalDateTime previousSunday = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).minus(1,ChronoUnit.WEEKS);
		return previousSunday;
	}

}
