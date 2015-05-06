package snowblood.gen.services.tourdecontrole;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données TdcParamAffichage
 */
@DtDefinition(persistent = false)
public final class TdcParamAffichage implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long jodId;
	private String deltaComplet;
	private String libelle;
	private String cible;
	private String encodage;
	private Boolean activationAuto;
	private Integer retention;
	private Integer attInterrupt;
	private String source;
	private String sens;
	private String code;
	private String rejet;
	private String frequence;
	private Integer fenSupervision;
	private String description;

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
	 * Récupère la valeur de la propriété 'Mode'. 
	 * @return String deltaComplet <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE_COURT", notNull = true, persistent = false, label = "Mode")
	public String getDeltaComplet() {
		return deltaComplet;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Mode'.
	 * @param deltaComplet String <b>Obligatoire</b>
	 */
	public void setDeltaComplet(final String deltaComplet) {
		this.deltaComplet = deltaComplet;
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
	 * Récupère la valeur de la propriété 'Cible'. 
	 * @return String cible 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Cible")
	public String getCible() {
		return cible;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Cible'.
	 * @param cible String 
	 */
	public void setCible(final String cible) {
		this.cible = cible;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Encodage'. 
	 * @return String encodage 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Encodage")
	public String getEncodage() {
		return encodage;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Encodage'.
	 * @param encodage String 
	 */
	public void setEncodage(final String encodage) {
		this.encodage = encodage;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Actif'. 
	 * @return Boolean activationAuto <b>Obligatoire</b>
	 */
	@Field(domain = "DO_BOOLEEN", notNull = true, persistent = false, label = "Actif")
	public Boolean getActivationAuto() {
		return activationAuto;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Actif'.
	 * @param activationAuto Boolean <b>Obligatoire</b>
	 */
	public void setActivationAuto(final Boolean activationAuto) {
		this.activationAuto = activationAuto;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Rétention des journaux'. 
	 * @return Integer retention 
	 */
	@Field(domain = "DO_NOMBRE_ENTIER", persistent = false, label = "Rétention des journaux")
	public Integer getRetention() {
		return retention;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Rétention des journaux'.
	 * @param retention Integer 
	 */
	public void setRetention(final Integer retention) {
		this.retention = retention;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Attente avant interruption'. 
	 * @return Integer attInterrupt <b>Obligatoire</b>
	 */
	@Field(domain = "DO_NOMBRE_ENTIER", notNull = true, persistent = false, label = "Attente avant interruption")
	public Integer getAttInterrupt() {
		return attInterrupt;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Attente avant interruption'.
	 * @param attInterrupt Integer <b>Obligatoire</b>
	 */
	public void setAttInterrupt(final Integer attInterrupt) {
		this.attInterrupt = attInterrupt;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Source'. 
	 * @return String source 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Source")
	public String getSource() {
		return source;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Source'.
	 * @param source String 
	 */
	public void setSource(final String source) {
		this.source = source;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Sens'. 
	 * @return String sens 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Sens")
	public String getSens() {
		return sens;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Sens'.
	 * @param sens String 
	 */
	public void setSens(final String sens) {
		this.sens = sens;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Code'. 
	 * @return String code 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Code")
	public String getCode() {
		return code;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Code'.
	 * @param code String 
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Rejet'. 
	 * @return String rejet 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Rejet")
	public String getRejet() {
		return rejet;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Rejet'.
	 * @param rejet String 
	 */
	public void setRejet(final String rejet) {
		this.rejet = rejet;
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
	 * Récupère la valeur de la propriété 'Fenêtre de supervision'. 
	 * @return Integer fenSupervision 
	 */
	@Field(domain = "DO_NOMBRE_ENTIER", persistent = false, label = "Fenêtre de supervision")
	public Integer getFenSupervision() {
		return fenSupervision;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Fenêtre de supervision'.
	 * @param fenSupervision Integer 
	 */
	public void setFenSupervision(final Integer fenSupervision) {
		this.fenSupervision = fenSupervision;
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

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
