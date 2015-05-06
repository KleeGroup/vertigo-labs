package snowblood.services.job;

import java.io.File;

import snowblood.gen.domain.tourdecontrole.Jobexecution;

/**
 * Interface de gestion des Job.
 *
 * @author hsellami-ext
 */
public interface JobServices {

	/**
	 * Trouve le prochain id d'exécution d'un job.
	 *
	 * @return le prochain id d'exécution d'un job.
	 */
	Long getNextJobexecutionId();

	/**
	 * Enregistre un jobexecution.
	 *
	 * @param execution le jobexecution à enregistrer.
	 */
	void saveJobexecution(Jobexecution execution);

	/**
	 * Rafraichit un job execution.
	 *
	 * @param execution le jobexecution à rafraichir.
	 * @return le jobexecution rafraichi.
	 */
	Jobexecution refreshJobexecution(Jobexecution execution);

	/**
	 * Sauvegarde l'archive de logs du jobexecution renseigné.
	 *
	 * @param jobex le jobexecution.
	 * @param path Path.
	 * @return le jobexecution mis à jour.
	 */
	Jobexecution sauvegarderLogs(Jobexecution jobex, String path);

	/**
	 * Sauvegarde l'archive de data du jobexecution renseigné.
	 *
	 * @param jobex le jobexecution.
	 * @param path Path.
	 * @return le jobexecution mis à jour.
	 */

	Jobexecution sauvegarderData(Jobexecution jobex, String path);

	/**
	 * Enregistre les jobs à enregistrer et ordonnance les jobs à ordonnancer.
	 */
	void registerJobsAndSchedule();

	/**
	 * Retourne un identifiant d'une définition de job à partir de son code.
	 *
	 * @param code le code.
	 * @return l'identifiant.
	 */
	Long getJobdefinitionIdByCode(String code);

	/**
	 * Initialise une session pour les jobs.
	 * utilisateur : system.
	 * profil : admin.
	 */
	void initJobSession();

	/**
	 * Stoppe la session pour les jobs.
	 * utilisateur : system.
	 * profil : admin.
	 */
	void stopJobSession();
}
