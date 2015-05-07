package snowblood.oldies.services;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données TdcAffichage
 */
@DtDefinition(persistent = false)
public final class TdcAffichage implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long jodId;
	private String service;
	private String libelle;
	private String description;
	private Boolean actif;
	private String frequence;
	private String etat;
	private java.util.Date dernierRun;
	private java.util.Date prochainRun;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Id Jobdefinition'. 
	 * @return Long jodId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", notNull = true, persistent = false, label = "Id Jobdefinition")
	public Long getJodId() {
		return jodId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Id Jobdefinition'.
	 * @param jodId Long <b>Obligatoire</b>
	 */
	public void setJodId(final Long jodId) {
		this.jodId = jodId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Service'. 
	 * @return String service <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE_COURT", notNull = true, persistent = false, label = "Service")
	public String getService() {
		return service;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Service'.
	 * @param service String <b>Obligatoire</b>
	 */
	public void setService(final String service) {
		this.service = service;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Libellé'. 
	 * @return String libelle 
	 */
	@Field(domain = "DO_LIBELLE_LONG", persistent = false, label = "Libellé")
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Libellé'.
	 * @param libelle String 
	 */
	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Description'. 
	 * @return String description 
	 */
	@Field(domain = "DO_COMMENTAIRE", persistent = false, label = "Description")
	public String getDescription() {
		return description;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Description'.
	 * @param description String 
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Actif'. 
	 * @return Boolean actif <b>Obligatoire</b>
	 */
	@Field(domain = "DO_BOOLEEN", notNull = true, persistent = false, label = "Actif")
	public Boolean getActif() {
		return actif;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Actif'.
	 * @param actif Boolean <b>Obligatoire</b>
	 */
	public void setActif(final Boolean actif) {
		this.actif = actif;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Fréquence'. 
	 * @return String frequence 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Fréquence")
	public String getFrequence() {
		return frequence;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Fréquence'.
	 * @param frequence String 
	 */
	public void setFrequence(final String frequence) {
		this.frequence = frequence;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Etat'. 
	 * @return String etat <b>Obligatoire</b>
	 */
	@Field(domain = "DO_CODE", notNull = true, persistent = false, label = "Etat")
	public String getEtat() {
		return etat;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Etat'.
	 * @param etat String <b>Obligatoire</b>
	 */
	public void setEtat(final String etat) {
		this.etat = etat;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Dernier run'. 
	 * @return java.util.Date dernierRun 
	 */
	@Field(domain = "DO_DATE_HEURE", persistent = false, label = "Dernier run")
	public java.util.Date getDernierRun() {
		return dernierRun;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Dernier run'.
	 * @param dernierRun java.util.Date 
	 */
	public void setDernierRun(final java.util.Date dernierRun) {
		this.dernierRun = dernierRun;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Prochain run'. 
	 * @return java.util.Date prochainRun 
	 */
	@Field(domain = "DO_DATE_HEURE", persistent = false, label = "Prochain run")
	public java.util.Date getProchainRun() {
		return prochainRun;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Prochain run'.
	 * @param prochainRun java.util.Date 
	 */
	public void setProchainRun(final java.util.Date prochainRun) {
		this.prochainRun = prochainRun;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
