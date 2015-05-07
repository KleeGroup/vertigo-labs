package snowblood.gen.services;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données TdcDetailCritere
 */
@DtDefinition(persistent = false)
public final class TdcDetailCritere implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private java.util.Date dateDebut;
	private java.util.Date dateFin;
	private String etat;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Exécution entre le ...'. 
	 * @return java.util.Date dateDebut 
	 */
	@Field(domain = "DO_DATE", persistent = false, label = "Exécution entre le ...")
	public java.util.Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Exécution entre le ...'.
	 * @param dateDebut java.util.Date 
	 */
	public void setDateDebut(final java.util.Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'et le ...'. 
	 * @return java.util.Date dateFin 
	 */
	@Field(domain = "DO_DATE", persistent = false, label = "et le ...")
	public java.util.Date getDateFin() {
		return dateFin;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'et le ...'.
	 * @param dateFin java.util.Date 
	 */
	public void setDateFin(final java.util.Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Etat exécution'. 
	 * @return String etat 
	 */
	@Field(domain = "DO_CODE", persistent = false, label = "Etat exécution")
	public String getEtat() {
		return etat;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Etat exécution'.
	 * @param etat String 
	 */
	public void setEtat(final String etat) {
		this.etat = etat;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
