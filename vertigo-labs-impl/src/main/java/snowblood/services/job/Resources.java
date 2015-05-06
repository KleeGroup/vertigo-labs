package snowblood.services.job;

import io.vertigo.lang.MessageKey;

/**
 * Messages communs à plusieurs Jobs.
 *
 * @author bgenevaux
 */
public enum Resources implements MessageKey {

	FORMAT_FICHIER_INCORRECT,

	STRUCTURE_INCORRECTE,

	ERREURS_DETECTEES,

	ERREURS_DOUBLONS_DETECTES,

	DOUBLONS_DETECTES,

	IMPORT_ERREUR_PAS_DE_FICHIER,

	RDD_IMPORT_LANCE,

	OPERATION_IMPOSSIBLE,
}
