package snowblood.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	 * Convertit une date d'un fuseau pour la stocker dans la colonne GMT correspondante (le nombre de millisecondes est
	 * correct, pas forcément la date affichée).
	 *
	 * @param date la date à convertir
	 * @param fromTimeZoneLibelle le fuseau de départ
	 * @return la date convertie
	 */
	public static Date convertDateToGMTToSave(final Date date, final String fromTimeZoneLibelle) {
		final SimpleDateFormat sdfFrom = new SimpleDateFormat(FORMAT_PATTERN, Locale.FRANCE);
		final SimpleDateFormat sdfParis = new SimpleDateFormat(FORMAT_PATTERN, Locale.FRANCE);
		final TimeZone fromTimeZone = TimeZone.getTimeZone(fromTimeZoneLibelle);
		sdfFrom.setTimeZone(fromTimeZone);

		final String dateInitString = sdfParis.format(date);
		Date dateToReturn;
		try {
			dateToReturn = sdfFrom.parse(dateInitString);
			return dateToReturn;
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Convertit la vraie date (le nombre de milisecondes) d'un fuseau pour l'afficher sous le même forme mais avec le
	 * fuseau de localisation du serveur.
	 *
	 * @param strTimeZone Fuseau horaire locale
	 * @param dateOuvertureTechniqueGmt Date UTC (le nombre de milisecondes)
	 * @return Date d'affichage dans le fuseau horaire de localisation du serveur
	 */
	public static Date convertDateGmtToDateLocaleAffichage(final String strTimeZone, final Date dateOuvertureTechniqueGmt) {
		final SimpleDateFormat sdfLocal = new SimpleDateFormat(FORMAT_PATTERN, Locale.FRANCE);
		sdfLocal.setTimeZone(TimeZone.getTimeZone(strTimeZone));
		final String strDateLocale = sdfLocal.format(dateOuvertureTechniqueGmt);

		final SimpleDateFormat sdfServer = new SimpleDateFormat(FORMAT_PATTERN, Locale.FRANCE);
		final Date dateLocaleAffichage;
		try {
			dateLocaleAffichage = sdfServer.parse(strDateLocale);
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
		return dateLocaleAffichage;
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

	/**
	 * Convertit une date du fuseau GMT vers un autre fuseau.
	 *
	 * @param date la date à convertir
	 * @param toTimeZoneLibelle le fuseau d'arrivée
	 * @return la date convertie
	 */
	public static Date convertDateFromGMT(final Date date, final String toTimeZoneLibelle) {
		return convertDateToAutreFuseau(date, "GMT", toTimeZoneLibelle);
	}

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
		Date dateFrom;
		try {
			dateFrom = sdfFrom.parse(dateInitString);

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

	/**
	 * Retourne le nombre de millisecondes entre deux dates sans compter les week-ends.
	 *
	 * @param dateBegin la date de début
	 * @param dateEnd la date de fin
	 * @return le nombre de millisecondes entre deux dates sans compter les week-ends
	 */
	// public static long intervalleEnMillisSansWeekend(final Date dateBegin, final Date dateEnd) {
	// final Date dateBeginAtSaturday0 = findSaturdayAtSameWeekAtZero(dateBegin);
	// final Date calEndAtSaturday0 = findSaturdayAtSameWeekAtZero(dateEnd);
	//
	// final long intervalleEnMillis = intervalleEnMillis(dateBeginAtSaturday0, calEndAtSaturday0);
	// final long nbSemaines = Double.valueOf(Math.floor(Long.valueOf(intervalleEnMillis).doubleValue() /
	// 604800000)).longValue();
	//
	// final long workingDaysInMillis = nbSemaines * MILLISECOND_WORKING_DAYS;
	//
	// final boolean isBeginBeforeSaturday = dateBeginAtSaturday0.getTime() - dateBegin.getTime() > 0;
	// final boolean isEndBeforeSaturday = calEndAtSaturday0.getTime() - dateEnd.getTime() > 0;
	//
	// long correctionInMillis = 0;
	//
	// if (isBeginBeforeSaturday) {
	// correctionInMillis -= (dateBeginAtSaturday0.getTime() - dateBegin.getTime());
	// }
	//
	// if (isEndBeforeSaturday) {
	// correctionInMillis += (calEndAtSaturday0.getTime() - dateEnd.getTime());
	// }
	// return workingDaysInMillis - correctionInMillis;
	// }

	/**
	 * Retourne le nombre de jours entre deux dates sans compter les week-ends.
	 *
	 * @param dateBegin la date de début
	 * @param dateEnd la date de fin
	 * @return le nombre de jours entre deux dates sans compter les week-ends
	 */
	// public static long intervalleEnJoursSansWeekend(final Date dateBegin, final Date dateEnd) {
	// final long intervalleEnMillis = intervalleEnMillisSansWeekend(dateBegin, dateEnd);
	// final long nbJours = Double.valueOf(Math.floor(Long.valueOf(intervalleEnMillis).doubleValue() /
	// 86400000)).intValue();
	// return nbJours;
	// }

	/**
	 * Renvoie vrai si le jour est un samedi à la date donnée.
	 *
	 * @param date la date donnée
	 * @param tz la timeZone
	 * @return vrai si le jour est un samedi à la date donnée
	 */
	public static boolean isSaturday(final Date date, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(tz);
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
	}

	/**
	 * Renvoie vrai si le jour est un dimanche à la date donnée.
	 *
	 * @param date la date donnée
	 * @param tz la timeZone
	 * @return vrai si le jour est un dimanche à la date donnée
	 */
	public static boolean isSunday(final Date date, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(tz);
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	/**
	 * Renvoie le samedi de la même semaine que la date passée en paramètre.
	 *
	 * @param date la date donnée
	 * @param tz la timeZone
	 * @return le samedi de la même semaine que la date passée en paramètre
	 */
	public static Date findSaturdayAtSameWeekAtZero(final Date date, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(tz);
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY:
				cal.add(Calendar.DAY_OF_MONTH, 5);
				break;
			case Calendar.TUESDAY:
				cal.add(Calendar.DAY_OF_MONTH, 4);
				break;
			case Calendar.WEDNESDAY:
				cal.add(Calendar.DAY_OF_MONTH, 3);
				break;
			case Calendar.THURSDAY:
				cal.add(Calendar.DAY_OF_MONTH, 2);
				break;
			case Calendar.FRIDAY:
				cal.add(Calendar.DAY_OF_MONTH, 1);
				break;
			case Calendar.SUNDAY:
				cal.add(Calendar.DAY_OF_MONTH, -1);
				break;
			default:
				break;
		}
		return cal.getTime();
	}

	/**
	 * Renvoie le lundi de la prochaine semaine de la date passée en paramètre.
	 *
	 * @param date la date donnée
	 * @param tz la timeZone
	 * @return le lundi de la prochaine semaine de la date passée en paramètre
	 */
	public static Date findMondayAtNextWeek(final Date date, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(tz);
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY:
				cal.add(Calendar.DAY_OF_MONTH, 7);
				break;
			case Calendar.TUESDAY:
				cal.add(Calendar.DAY_OF_MONTH, 6);
				break;
			case Calendar.WEDNESDAY:
				cal.add(Calendar.DAY_OF_MONTH, 5);
				break;
			case Calendar.THURSDAY:
				cal.add(Calendar.DAY_OF_MONTH, 4);
				break;
			case Calendar.FRIDAY:
				cal.add(Calendar.DAY_OF_MONTH, 3);
				break;
			case Calendar.SATURDAY:
				cal.add(Calendar.DAY_OF_MONTH, 2);
				break;
			case Calendar.SUNDAY:
				cal.add(Calendar.DAY_OF_MONTH, 1);
				break;
			default:
				break;
		}
		return cal.getTime();
	}

	/**
	 * Renvoie la date de 30 jours avant.
	 *
	 * @param date la date donnée
	 * @param tz la timeZone
	 * @return la date de 30 jours avant
	 */
	public static Date findDateBefore30Day(final Date date, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(tz);
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -30);

		return cal.getTime();
	}

	/**
	 * Renvoie le jour suivant à minuit, par rapport à la date donnée.
	 *
	 * @param date la date donnée
	 * @param tz la timeZone
	 * @return le jour suivant à minuit, par rapport à la date donnée
	 */
	public static Date findDateNextDay0(final Date date, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(tz);
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * Renvoie le jour suivant à minuit, par rapport à la date donnée.
	 *
	 * @param date la date donnée
	 * @param tz la timeZone
	 * @return le jour suivant à minuit, par rapport à la date donnée
	 */
	public static Date findDateOneMonthBefore(final Date date, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(tz);
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	/**
	 * Retourne le nombre de millisecondes entre deux dates.
	 *
	 * @param dateBegin la date de début
	 * @param dateEnd la date de fin
	 * @return le nombre de millisecondes entre deux dates
	 */
	public static long intervalleEnMillis(final Date dateBegin, final Date dateEnd) {
		return dateEnd.getTime() - dateBegin.getTime();
	}

	/**
	 * Retourne vrai si l'heure de la date donnée est entre deux et trois dans une timeZone donnée.
	 *
	 * @param date Date
	 * @param tz Timezone
	 * @return True si l'heure est comprise en 2 (inclue) et 3 (exclue).
	 */
	public static boolean isHeureEntre2Et3(final Date date, final TimeZone tz) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(tz);
		calendar.setTime(date);
		return (2 <= calendar.get(Calendar.HOUR_OF_DAY)) && (calendar.get(Calendar.HOUR_OF_DAY) < 3);
	}

	/**
	 * Retourne l'année d'une date en chiffre.
	 *
	 * @param date Date
	 * @return l'année en chiffre
	 */
	public static Integer getAnnee(final Date date) {

		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Génère une "date et heure" à partir de la date et du nombre de minute fournis.
	 *
	 * @param dateOuverture Date.
	 * @param minutesOuverture Nombre de minutes.
	 * @return Date/Heure complète.
	 */
	public static Date composerHeureDate(final Date dateOuverture, final Integer minutesOuverture) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOuverture);

		if (minutesOuverture != null) {
			final int heures = minutesOuverture / 60;
			final int minutes = minutesOuverture % 60;
			calendar.set(Calendar.HOUR, heures);
			calendar.set(Calendar.MINUTE, minutes);
		}

		return calendar.getTime();
	}

}
