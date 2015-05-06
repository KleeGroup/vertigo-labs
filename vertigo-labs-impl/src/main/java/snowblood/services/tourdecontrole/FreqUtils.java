package snowblood.services.tourdecontrole;

//import org.quartz.CronExpression;

/**
 * Fournit des fonctions utilitaires pour gérer les fréquences d'exécution des jobs.
 *
 * @author fdangelotillon
 */
public class FreqUtils {

	public static final String IS_CRON = "IS_CRON";
	public static final String IS_PERIOD = "IS_PERIOD";

	private FreqUtils() {
		super();
	}

	/**
	 * Indique si une expression est de type cron ou period.
	 *
	 * @param freq l'expression à analyser.
	 * @return IS_CRON si l'expression est de type cron, IS_PERIOD si elle est de type period.
	 */
	public static String cronOuPeriode(final String freq) {
		//
		// if (StringUtils.isBlank(freq)) {
		// return null;
		// }
		// if (CronExpression.isValidExpression(freq)) {
		// return IS_CRON;
		// }
		// try {
		// Integer.parseInt(freq);
		// return IS_PERIOD;
		// } catch (final NumberFormatException e) {
		// // Frequence n'est pas un int
		// }
		return null;
	}

}
