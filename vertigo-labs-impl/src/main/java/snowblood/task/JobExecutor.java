package snowblood.task;

import java.util.Map;

/**
 * Interface générique pour les différents jobs exécutables.
 *
 * @author bgenevaux
 */
public interface JobExecutor {

	/**
	 * Exécute un job.
	 *
	 * @param params paramètres étendus du job.
	 */
	void run(final Map<String, String> params);

	/**
	 * Retourne le rapport d'exécution du job.
	 *
	 * @return le rapport d'exécution du job.
	 */
	Map<String, String> getRapport();
}
