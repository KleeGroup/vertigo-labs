package snowblood.oldies.services;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données TdcConstructionFrequence
 */
@DtDefinition(persistent = false)
public final class TdcConstructionFrequence implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String typeFrequence;
	private Integer valeurPeriode;
	private String periodeUnite;
	private String chaineCron;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Période ou cron'. 
	 * @return String typeFrequence 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Période ou cron")
	public String getTypeFrequence() {
		return typeFrequence;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Période ou cron'.
	 * @param typeFrequence String 
	 */
	public void setTypeFrequence(final String typeFrequence) {
		this.typeFrequence = typeFrequence;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Valeur période'. 
	 * @return Integer valeurPeriode 
	 */
	@Field(domain = "DO_NOMBRE_ENTIER", persistent = false, label = "Valeur période")
	public Integer getValeurPeriode() {
		return valeurPeriode;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Valeur période'.
	 * @param valeurPeriode Integer 
	 */
	public void setValeurPeriode(final Integer valeurPeriode) {
		this.valeurPeriode = valeurPeriode;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Unité de la période'. 
	 * @return String periodeUnite 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Unité de la période")
	public String getPeriodeUnite() {
		return periodeUnite;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Unité de la période'.
	 * @param periodeUnite String 
	 */
	public void setPeriodeUnite(final String periodeUnite) {
		this.periodeUnite = periodeUnite;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Chaîne cron'. 
	 * @return String chaineCron 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Chaîne cron")
	public String getChaineCron() {
		return chaineCron;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Chaîne cron'.
	 * @param chaineCron String 
	 */
	public void setChaineCron(final String chaineCron) {
		this.chaineCron = chaineCron;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
