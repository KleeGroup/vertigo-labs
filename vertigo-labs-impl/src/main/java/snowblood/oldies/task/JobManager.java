package snowblood.oldies.task;

import io.vertigo.lang.Component;

import java.util.Date;
import java.util.Map;

import snowblood.gen.domain.tourdecontrole.Jobdefinition;
import snowblood.task.JobExecution;

/**
 * Manager des différents jobs.
 * - singleton d’enregistrement des définitions de jobs.
 * - point d'entrée de la gestion des jobs.
 *
 * @author bgenevaux
 */
public interface JobManager extends Component {

	/**
	 * Enregistre un job dans le manager.
	 *
	 * @param definition Le jobdefinition à enregistrer.
	 */
	void registerJob(Jobdefinition definition);

	/**
	 * Renvoie la liste des jobs enregistrés.
	 *
	 * @return Map<String, Jobdefinition> des jobs enregistrés.
	 */
	Map<String, Jobdefinition> retrieveJobs();

	/**
	 * Programme un job à une fréquence donnée en secondes.
	 *
	 * @param code le nom du job, tel qu'enregistré dans le manager.
	 * @param periodInSecond la fréquence en secondes.
	 */
	void scheduleEverySecondInterval(final String code, int periodInSecond);

	/**
	 * Programme un job pour exécution chaque jour à une date fixe.
	 *
	 * @param code le nom du job, tel qu'enregistré dans le manager.
	 * @param hour heure fixe d'exécution.
	 */
	void scheduleEveryDayAtHour(final String code, int hour);

	/**
	 * Programme un job pour une seule exécution à une date donnée.
	 *
	 * @param code le nom du job, tel qu'enregistré dans le manager.
	 * @param date date d'exécution.
	 */
	void scheduleAtDate(final String code, Date date);

	/**
	 * Programme un job avec une expression de type CRON.
	 *
	 * @param code le nom du job, tel qu'enregistré dans le manager.
	 * @param cronString expression CRON
	 */
	void scheduleCron(final String code, String cronString);

	/**
	 * Arrête la programmation d'un job.
	 *
	 * @param code le nom du job, tel qu'enregistré dans le manager.
	 */
	void stopSchedule(final String code);

	/**
	 * Exécution immédiate et asynchrone d'un job.
	 * Comme elle est asynchrone on ne peut pas avoir l'exécution
	 * Utiliser execute pour avoir le résultat immédiat.
	 *
	 * @param code le nom du job, tel qu'enregistré dans le manager.
	 * @param params les paramètres additionnels de l'exécution du job.
	 */
	void scheduleNow(final String code, final Map<String, String> params);

	/**
	 * Exécution immédiate et synchrone d'un job.
	 *
	 * @param code le nom du job, tel qu'enregistré dans le manager.
	 * @param params les paramètres additionnels de l'exécution du job.
	 * @return le résultat de l'exécution du job.
	 */
	JobExecution execute(final String code, final Map<String, String> params);
}
