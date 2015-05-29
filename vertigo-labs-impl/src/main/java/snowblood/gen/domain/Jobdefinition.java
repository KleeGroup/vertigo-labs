package snowblood.gen.domain;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est gérée manuellement.
 * Objet de données Jobdefinition
 */
@javax.persistence.Entity
@javax.persistence.Table (name = "JOBDEFINITION")
@DtDefinition
public final class Jobdefinition implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long jodId;
	private String code;
	private String libelle;
	private String description;
	private Integer retentionJournaux;
	private Integer fenetreSupervision;
	private Integer attenteInterruption;
	private String source;
	private String cible;
	private String encodage;
	private Boolean multiExecutions;
	private Boolean manuelAutorise;
	private Boolean activation;
	private Boolean interruptible;
	private Boolean testable;
	private String frequence;
	private String affinite;
	private String parametresEtendus;
	private String implementation;
	private Boolean completPossible;
	private String repertoireDistantDEchange;
	// Enumerations
	private String directionCd;
	private String rejectRuleCd;
	private String dataModeCd;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'JOD_ID'. 
	 * @return Long jodId <b>Obligatoire</b>
	 */
	@javax.persistence.Id
	@javax.persistence.SequenceGenerator(name = "sequence", sequenceName = "SEQ_JOBDEFINITION")
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "sequence")
	@javax.persistence.Column(name = "JOD_ID")
	@Field(domain = "DO_IDENTIFIANT", type = "PRIMARY_KEY", notNull = true, label = "JOD_ID")
	public Long getJodId() {
		return jodId;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * Définit la valeur de la propriété 'JOD_ID'.
	 * @param jodId Long <b>Obligatoire</b>
	 */
	public void setJodId(final Long jodId) {
		this.jodId = jodId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Code'. 
	 * @return String code 
	 */
	@javax.persistence.Column(name = "CODE")
	@Field(domain = "DO_CODE", label = "Code")
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
	 * Récupère la valeur de la propriété 'Libellé'. 
	 * @return String libelle 
	 */
	@javax.persistence.Column(name = "LIBELLE")
	@Field(domain = "DO_LIBELLE_LONG", label = "Libellé")
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
	@javax.persistence.Column(name = "DESCRIPTION")
	@Field(domain = "DO_COMMENTAIRE", label = "Description")
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
	 * Récupère la valeur de la propriété 'Rétention journaux'. 
	 * @return Integer retentionJournaux 
	 */
	@javax.persistence.Column(name = "RETENTION_JOURNAUX")
	@Field(domain = "DO_NOMBRE_ENTIER", label = "Rétention journaux")
	public Integer getRetentionJournaux() {
		return retentionJournaux;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Rétention journaux'.
	 * @param retentionJournaux Integer 
	 */
	public void setRetentionJournaux(final Integer retentionJournaux) {
		this.retentionJournaux = retentionJournaux;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Fenêtre supervision'. 
	 * @return Integer fenetreSupervision 
	 */
	@javax.persistence.Column(name = "FENETRE_SUPERVISION")
	@Field(domain = "DO_NOMBRE_ENTIER", label = "Fenêtre supervision")
	public Integer getFenetreSupervision() {
		return fenetreSupervision;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Fenêtre supervision'.
	 * @param fenetreSupervision Integer 
	 */
	public void setFenetreSupervision(final Integer fenetreSupervision) {
		this.fenetreSupervision = fenetreSupervision;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Attente interruption'. 
	 * @return Integer attenteInterruption 
	 */
	@javax.persistence.Column(name = "ATTENTE_INTERRUPTION")
	@Field(domain = "DO_NOMBRE_ENTIER", label = "Attente interruption")
	public Integer getAttenteInterruption() {
		return attenteInterruption;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Attente interruption'.
	 * @param attenteInterruption Integer 
	 */
	public void setAttenteInterruption(final Integer attenteInterruption) {
		this.attenteInterruption = attenteInterruption;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Source'. 
	 * @return String source 
	 */
	@javax.persistence.Column(name = "SOURCE")
	@Field(domain = "DO_CODE", label = "Source")
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
	 * Récupère la valeur de la propriété 'Cible'. 
	 * @return String cible 
	 */
	@javax.persistence.Column(name = "CIBLE")
	@Field(domain = "DO_CODE", label = "Cible")
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
	@javax.persistence.Column(name = "ENCODAGE")
	@Field(domain = "DO_LIBELLE_COURT", label = "Encodage")
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
	 * Récupère la valeur de la propriété 'Multi-exécutions'. 
	 * @return Boolean multiExecutions <b>Obligatoire</b>
	 */
	@javax.persistence.Column(name = "MULTI_EXECUTIONS")
	@Field(domain = "DO_BOOLEEN", notNull = true, label = "Multi-exécutions")
	public Boolean getMultiExecutions() {
		return multiExecutions;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Multi-exécutions'.
	 * @param multiExecutions Boolean <b>Obligatoire</b>
	 */
	public void setMultiExecutions(final Boolean multiExecutions) {
		this.multiExecutions = multiExecutions;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Manuel autorisé'. 
	 * @return Boolean manuelAutorise <b>Obligatoire</b>
	 */
	@javax.persistence.Column(name = "MANUEL_AUTORISE")
	@Field(domain = "DO_BOOLEEN", notNull = true, label = "Manuel autorisé")
	public Boolean getManuelAutorise() {
		return manuelAutorise;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Manuel autorisé'.
	 * @param manuelAutorise Boolean <b>Obligatoire</b>
	 */
	public void setManuelAutorise(final Boolean manuelAutorise) {
		this.manuelAutorise = manuelAutorise;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Activation'. 
	 * @return Boolean activation <b>Obligatoire</b>
	 */
	@javax.persistence.Column(name = "ACTIVATION")
	@Field(domain = "DO_BOOLEEN", notNull = true, label = "Activation")
	public Boolean getActivation() {
		return activation;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Activation'.
	 * @param activation Boolean <b>Obligatoire</b>
	 */
	public void setActivation(final Boolean activation) {
		this.activation = activation;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Interruptible'. 
	 * @return Boolean interruptible <b>Obligatoire</b>
	 */
	@javax.persistence.Column(name = "INTERRUPTIBLE")
	@Field(domain = "DO_BOOLEEN", notNull = true, label = "Interruptible")
	public Boolean getInterruptible() {
		return interruptible;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Interruptible'.
	 * @param interruptible Boolean <b>Obligatoire</b>
	 */
	public void setInterruptible(final Boolean interruptible) {
		this.interruptible = interruptible;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Testable'. 
	 * @return Boolean testable <b>Obligatoire</b>
	 */
	@javax.persistence.Column(name = "TESTABLE")
	@Field(domain = "DO_BOOLEEN", notNull = true, label = "Testable")
	public Boolean getTestable() {
		return testable;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Testable'.
	 * @param testable Boolean <b>Obligatoire</b>
	 */
	public void setTestable(final Boolean testable) {
		this.testable = testable;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Fréquence'. 
	 * @return String frequence 
	 */
	@javax.persistence.Column(name = "FREQUENCE")
	@Field(domain = "DO_LIBELLE_COURT", label = "Fréquence")
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
	 * Récupère la valeur de la propriété 'Affinité'. 
	 * @return String affinite 
	 */
	@javax.persistence.Column(name = "AFFINITE")
	@Field(domain = "DO_LIBELLE_LONG", label = "Affinité")
	public String getAffinite() {
		return affinite;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Affinité'.
	 * @param affinite String 
	 */
	public void setAffinite(final String affinite) {
		this.affinite = affinite;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Paramètres étendus'. 
	 * @return String parametresEtendus 
	 */
	@javax.persistence.Column(name = "PARAMETRES_ETENDUS")
	@Field(domain = "DO_COMMENTAIRE", label = "Paramètres étendus")
	public String getParametresEtendus() {
		return parametresEtendus;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Paramètres étendus'.
	 * @param parametresEtendus String 
	 */
	public void setParametresEtendus(final String parametresEtendus) {
		this.parametresEtendus = parametresEtendus;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Implémentation'. 
	 * @return String implementation 
	 */
	@javax.persistence.Column(name = "IMPLEMENTATION")
	@Field(domain = "DO_LIBELLE_LONG", label = "Implémentation")
	public String getImplementation() {
		return implementation;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Implémentation'.
	 * @param implementation String 
	 */
	public void setImplementation(final String implementation) {
		this.implementation = implementation;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Complet possible'. 
	 * @return Boolean completPossible <b>Obligatoire</b>
	 */
	@javax.persistence.Column(name = "COMPLET_POSSIBLE")
	@Field(domain = "DO_BOOLEEN", notNull = true, label = "Complet possible")
	public Boolean getCompletPossible() {
		return completPossible;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Complet possible'.
	 * @param completPossible Boolean <b>Obligatoire</b>
	 */
	public void setCompletPossible(final Boolean completPossible) {
		this.completPossible = completPossible;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Répertoire distant d'échange'. 
	 * @return String repertoireDistantDEchange 
	 */
	@javax.persistence.Column(name = "REPERTOIRE_DISTANT_D_ECHANGE")
	@Field(domain = "DO_FICHIER", label = "Répertoire distant d'échange")
	public String getRepertoireDistantDEchange() {
		return repertoireDistantDEchange;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Répertoire distant d'échange'.
	 * @param repertoireDistantDEchange String 
	 */
	public void setRepertoireDistantDEchange(final String repertoireDistantDEchange) {
		this.repertoireDistantDEchange = repertoireDistantDEchange;
	}


    // ************************************************************************
    // Direction : neutral, import or export.
    // Checked against ActivityDirection enumeration. 

    /**
	 * Champ : directionCd
	 * Returns direction code. 
	 * @return String jdcCd 
	 */
	@javax.persistence.Column(name = "DIRECTION_CD")
	@Field(domain = "DO_CODE", label = "Data direction")
	public String getDirectionCd() {
		return directionCd;
	}
    
    /**
	 * Champ : directionCd
	 * Returns direction (from enumeration). 
	 * @return ActivityDirection
	 */
	public ActivityDirection getDirection() {
		return ActivityDirection.valueOf(directionCd);
	}
	
	/**
	 * Champ : directionCd
	 * Sets data mode (complete or delta).
	 * @param mode ActivityDirection
	 */
	public void setDirection(final ActivityDirection direction) {
		directionCd = direction.getCode();
	}

	/**
	 * Champ : directionCd
	 * Sets direction.
	 * @param direction String
	 */
	public void setDirectionCd(final String directionCode) {
		directionCd = directionCode;
	}

    // ************************************************************************
    // Data mode : complete or delta.
    // Checked against ActivityDataMode enumeration. 

    /**
	 * Champ : DataModeCd
	 * Returns data mode code. 
	 * @return String jdcCd 
	 */
	@javax.persistence.Column(name = "DATA_MODE_CD")
	@Field(domain = "DO_CODE", label = "Are data complete or delta")
	public String getDataModeCd() {
		return dataModeCd;
	}
    
    /**
	 * Champ : DataModeCd
	 * Returns data mode (from enumeration). 
	 * @return ActivityDataMode
	 */
	public ActivityDataMode getDataMode() {
		return ActivityDataMode.valueOf(dataModeCd);
	}
	
	/**
	 * Champ : DataModeCd
	 * Sets data mode (complete or delta).
	 * @param mode ActivityDataMode
	 */
	public void setDataMode(final ActivityDataMode mode) {
		dataModeCd = mode.getCode();
	}

	/**
	 * Champ : DataModeCd
	 * Sets data mode (complete or delta).
	 * @param modeCd String
	 */
	public void setDataModeCd(final String modeCd) {
		dataModeCd = modeCd;
	}

    // ************************************************************************
    // Reject rule : not applicable, global, line by line
    // Checked against ActivityRejectRule enumeration. 

    /**
	 * Champ : RejectRule
	 * Returns reject rule code 
	 * @return String rejectRuleCd
	 */
	@javax.persistence.Column(name = "REJECT_RULE_CD")
	@Field(domain = "DO_CODE", label = "Are data rejected globally or line by line")
	public String getRejectRuleCd() {
		return rejectRuleCd;
	}
    
    /**
	 * Champ : RejectRule
	 * Returns reject rule (from enumeration) 
	 * @return ActivityRejectRule
	 */
	public ActivityRejectRule getRejectRule() {
		return ActivityRejectRule.valueOf(rejectRuleCd);
	}
	
	/**
	 * Champ : RejectRule
	 * Sets reject rule. 
	 * @param mode ActivityRejectRule
	 */
	public void setRejectRule(final ActivityRejectRule rule) {
		rejectRuleCd = rule.getCode();
	}

	/**
	 * Champ : RejectRule
	 * Sets reject rule. 
	 * @param ruleCd String
	 */
	public void setRejectRuleCd(final String ruleCd) {
		rejectRuleCd = ruleCd;
	}

	// ************************************************************************
    
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
