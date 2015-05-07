package snowblood.gen.domain;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données JobDeltaComplet
 */
@javax.persistence.Entity
@javax.persistence.Table (name = "JOB_DELTA_COMPLET")
@DtDefinition
public final class JobDeltaComplet implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String jdcCd;
	private String libelle;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'JDC_CD'. 
	 * @return String jdcCd <b>Obligatoire</b>
	 */
	@javax.persistence.Id
	@javax.persistence.SequenceGenerator(name = "sequence", sequenceName = "SEQ_JOB_DELTA_COMPLET")
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "sequence")
	@javax.persistence.Column(name = "JDC_CD")
	@Field(domain = "DO_CODE", type = "PRIMARY_KEY", notNull = true, label = "JDC_CD")
	public String getJdcCd() {
		return jdcCd;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * Définit la valeur de la propriété 'JDC_CD'.
	 * @param jdcCd String <b>Obligatoire</b>
	 */
	public void setJdcCd(final String jdcCd) {
		this.jdcCd = jdcCd;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Libellé'. 
	 * @return String libelle 
	 */
	@javax.persistence.Column(name = "LIBELLE")
	@Field(domain = "DO_LIBELLE_COURT", label = "Libellé")
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


	// Association : Jobdefinition non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
