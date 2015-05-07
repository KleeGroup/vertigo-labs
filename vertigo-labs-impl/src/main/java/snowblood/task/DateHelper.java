package snowblood.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * DateHelper helper qui s'occupe des dates.
 *
 * @author msayoudi
 * @version $Id$
 */

public final class DateHelper {

	public static final long MILLISECOND_PER_HOUR = 60 * 60 * 1000;
	public static final long MILLISECOND_PER_DAY = 24 * MILLISECOND_PER_HOUR;
	public static final long MILLISECOND_WORKING_DAYS = MILLISECOND_PER_DAY * 5;

	private static final String FORMAT_PATTERN = "dd/MM/yyyy HH:mm:ss";

	private DateHelper() {
		// TAF
	}

	/**
	 * Convertit une date d'un fuseau vers le fuseau GMT.
	 *
	 * @param date la date à convertir
	 * @param fromTimeZoneLibelle le fuseau de départ
	 * @return la date convertie
	 */
	public static Date convertDateToGMT(final Date date, final String fromTimeZoneLibelle) {
		return convertDateToAutreFuseau(date, fromTimeZoneLibelle, "GMT");
	}

	//	/**
	//	 * Convertit une date du fuseau GMT vers un autre fuseau.
	//	 *
	//	 * @param date la date à convertir
	//	 * @param toTimeZoneLibelle le fuseau d'arrivée
	//	 * @return la date convertie
	//	 */
	//	public static Date convertDateFromGMT(final Date date, final String toTimeZoneLibelle) {
	//		return convertDateToAutreFuseau(date, "GMT", toTimeZoneLibelle);
	//	}

	/**
	 * Convertit une date d'un fuseau horaire vers un autre.
	 *
	 * @param dateInit la date à convertir
	 * @param fromTimeZoneLibelle le fuseau de départ
	 * @param toTimeZoneLibelle le fuseau d'arrivée
	 * @return la date convertie
	 */
	public static Date convertDateToAutreFuseau(final Date dateInit, final String fromTimeZoneLibelle, final String toTimeZoneLibelle) {

		final SimpleDateFormat sdfFrom = new SimpleDateFormat(FORMAT_PATTERN, Locale.FRANCE);
		final SimpleDateFormat sdfTo = new SimpleDateFormat(FORMAT_PATTERN, Locale.FRANCE);
		final SimpleDateFormat sdfParis = new SimpleDateFormat(FORMAT_PATTERN, Locale.FRANCE);
		final TimeZone fromTimeZone = TimeZone.getTimeZone(fromTimeZoneLibelle);
		final TimeZone toTimeZone = TimeZone.getTimeZone(toTimeZoneLibelle);
		sdfFrom.setTimeZone(fromTimeZone);
		sdfTo.setTimeZone(toTimeZone);
		final String dateInitString = sdfParis.format(dateInit);
		try {
			final Date dateFrom = sdfFrom.parse(dateInitString);

			final String dateToString = sdfTo.format(dateFrom);

			// final Calendar calendar = Calendar.getInstance();
			// calendar.setTime(date);
			// calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
			// if (fromTimeZone.inDaylightTime(calendar.getTime())) {
			// calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
			// }
			//
			// calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
			// if (toTimeZone.inDaylightTime(calendar.getTime())) {
			// calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
			// }
			// final Date dateToReturn = calendar.getTime();

			// final String dateStringToReturn = sdfFrom.format(dateToReturn);

			return sdfParis.parse(dateToString);
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}

	}

}
