package snowblood.gen.domain;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données JobEtat
 */
@javax.persistence.Entity
@javax.persistence.Table (name = "JOB_ETAT")
@DtDefinition
public final class JobEtat implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String jetCd;
	private String libelle;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'JET_CD'. 
	 * @return String jetCd <b>Obligatoire</b>
	 */
	@javax.persistence.Id
	@javax.persistence.SequenceGenerator(name = "sequence", sequenceName = "SEQ_JOB_ETAT")
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "sequence")
	@javax.persistence.Column(name = "JET_CD")
	@Field(domain = "DO_CODE", type = "PRIMARY_KEY", notNull = true, label = "JET_CD")
	public String getJetCd() {
		return jetCd;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * Définit la valeur de la propriété 'JET_CD'.
	 * @param jetCd String <b>Obligatoire</b>
	 */
	public void setJetCd(final String jetCd) {
		this.jetCd = jetCd;
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


	// Association : Jobexecution non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}