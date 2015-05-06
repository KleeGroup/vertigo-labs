package snowblood.task;

/**
 * Enumération des codes de définition de job.
 *
 * @author bgenevaux
 */
public enum CodeJobDefinition {
	/**
	 * Import de signalement.
	 */
	IMPORT_SIGNALEMENT_APPLI("ISIS-SIGNALEMENT"),
	/**
	 * Import d'audit.
	 */
	IMPORT_AUDIT_APPLI("ISIS-AUDIT"),
	/**
	 * Import GENESIS.
	 */
	IMPORT_TOPO_GENESIS("GE1"),
	/**
	 * Import SRJ.
	 */
	IMPORT_SRJ("GE2"),
	/**
	 * Export équipement.
	 */
	EXPORT_EQUIPEMENT("IS2"),
	/**
	 * Export topographie.
	 */
	EXPORT_TOPO("IS1"),
	/**
	 * Export signalement.
	 */
	EXPORT_SIGNALEMENT("IS3"),
	/**
	 * Export des traces.
	 */
	EXPORT_TRACE("ISIS-TRACES"),
	/**
	 * Import topographie - Reprise de données.
	 */
	IMPORT_TOPO_RDD("RD1"),
	/**
	 * Import équipement - Reprise de données.
	 */
	IMPORT_EQUIPEMENT_RDD("RD2"),
	/**
	 * Récupération des éditions de signature de pénalité.
	 */
	RECUPERATION_EDITION_SIGNATURE_PENALITE("archirecupsign"),
	/**
	 * Import de signalements banalisés genesis.
	 */
	IMPORT_SIGNALEMENT_BANALISE_GENESIS("GE4"),
	/**
	 * Initialisation des RMA.
	 */
	INITIALISATION_RMA("ISIS-RMA"),
	/**
	 * Initialisation des décomptes.
	 */
	INITIALISATION_DECOMPTE("ISIS-DECOMPTES"),
	/**
	 * Calcul des tranches de pénalité.
	 */
	CALCUL_TRANCHE_PENALITE("ISIS-PENALITES"),
	/**
	 * Clôture des tranches de pénalité.
	 */
	CLOTURE_TRANCHE_PENALITE("ISIS-TRANCHES"),
	/**
	 * Purge corbeille GED.
	 */
	PURGE_CORBEILLE("GED-CORBEILLE"),
	/**
	 * Maintenance TDC.
	 */
	MAINTENANCE_TDC("TDC-MAINTENANCE");

	private final String code;

	private CodeJobDefinition(final String code) {
		this.code = code;
	}

	/**
	 * Accesseur sur le code.
	 *
	 * @return Le code.
	 */
	public String getCode() {
		return code;
	}
}
