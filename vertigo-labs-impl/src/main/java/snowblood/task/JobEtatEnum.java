package snowblood.task;

/**
 * Enumération des différents états possibles pour un job.
 *
 * @author fdangelotillon
 */
public enum JobEtatEnum {
	/** Echec. */
	ECHEC("ECH"),
	/** En cours. */
	EN_COURS("ENC"),
	/** Succès. */
	SUCCES("SUC"),
	/** Succès partiel. */
	SUCCES_PARTIEL("SUP");

	private String code;

	private JobEtatEnum(final String code) {
		this.code = code;
	}

	/**
	 * Renvoie le code correspondant à l'état.
	 *
	 * @return le code.
	 */
	public String getCode() {
		return code;
	}

}
