package snowblood.oldies.services;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données TdcDetailAffichage
 */
@DtDefinition(persistent = false)
public final class TdcDetailAffichage implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private java.util.Date dateDebut;
	private String dateDebutFormatte;
	private java.util.Date dateFin;
	private String dateFinFormatte;
	private String duree;
	private String etat;
	private String lancement;
	private String serveur;
	private Long joeId;
	private String logs;
	private String data;
	private String rapport;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Début'. 
	 * @return java.util.Date dateDebut 
	 */
	@Field(domain = "DO_DATE", persistent = false, label = "Début")
	public java.util.Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Début'.
	 * @param dateDebut java.util.Date 
	 */
	public void setDateDebut(final java.util.Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Début'. 
	 * @return String dateDebutFormatte 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Début")
	public String getDateDebutFormatte() {
		return dateDebutFormatte;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Début'.
	 * @param dateDebutFormatte String 
	 */
	public void setDateDebutFormatte(final String dateDebutFormatte) {
		this.dateDebutFormatte = dateDebutFormatte;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Fin'. 
	 * @return java.util.Date dateFin 
	 */
	@Field(domain = "DO_DATE", persistent = false, label = "Fin")
	public java.util.Date getDateFin() {
		return dateFin;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Fin'.
	 * @param dateFin java.util.Date 
	 */
	public void setDateFin(final java.util.Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Début'. 
	 * @return String dateFinFormatte 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Début")
	public String getDateFinFormatte() {
		return dateFinFormatte;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Début'.
	 * @param dateFinFormatte String 
	 */
	public void setDateFinFormatte(final String dateFinFormatte) {
		this.dateFinFormatte = dateFinFormatte;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Durée'. 
	 * @return String duree 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Durée")
	public String getDuree() {
		return duree;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Durée'.
	 * @param duree String 
	 */
	public void setDuree(final String duree) {
		this.duree = duree;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Etat'. 
	 * @return String etat 
	 */
	@Field(domain = "DO_CODE", persistent = false, label = "Etat")
	public String getEtat() {
		return etat;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Etat'.
	 * @param etat String 
	 */
	public void setEtat(final String etat) {
		this.etat = etat;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Lancement'. 
	 * @return String lancement 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Lancement")
	public String getLancement() {
		return lancement;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Lancement'.
	 * @param lancement String 
	 */
	public void setLancement(final String lancement) {
		this.lancement = lancement;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'SERVEUR'. 
	 * @return String serveur 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "SERVEUR")
	public String getServeur() {
		return serveur;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'SERVEUR'.
	 * @param serveur String 
	 */
	public void setServeur(final String serveur) {
		this.serveur = serveur;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'ID Jobexecution'. 
	 * @return Long joeId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", notNull = true, persistent = false, label = "ID Jobexecution")
	public Long getJoeId() {
		return joeId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'ID Jobexecution'.
	 * @param joeId Long <b>Obligatoire</b>
	 */
	public void setJoeId(final Long joeId) {
		this.joeId = joeId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Logs'. 
	 * @return String logs 
	 */
	@Field(domain = "DO_LIBELLE_LONG", persistent = false, label = "Logs")
	public String getLogs() {
		return logs;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Logs'.
	 * @param logs String 
	 */
	public void setLogs(final String logs) {
		this.logs = logs;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Data'. 
	 * @return String data 
	 */
	@Field(domain = "DO_LIBELLE_LONG", persistent = false, label = "Data")
	public String getData() {
		return data;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Data'.
	 * @param data String 
	 */
	public void setData(final String data) {
		this.data = data;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Rapport'. 
	 * @return String rapport 
	 */
	@Field(domain = "DO_COMMENTAIRE", persistent = false, label = "Rapport")
	public String getRapport() {
		return rapport;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Rapport'.
	 * @param rapport String 
	 */
	public void setRapport(final String rapport) {
		this.rapport = rapport;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
