package snowblood.gen.domain;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données JobRejet
 */
@javax.persistence.Entity
@javax.persistence.Table (name = "JOB_REJET")
@DtDefinition
public final class JobRejet implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String jreCd;
	private String libelle;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'JRE_CD'. 
	 * @return String jreCd <b>Obligatoire</b>
	 */
	@javax.persistence.Id
	@javax.persistence.SequenceGenerator(name = "sequence", sequenceName = "SEQ_JOB_REJET")
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "sequence")
	@javax.persistence.Column(name = "JRE_CD")
	@Field(domain = "DO_CODE", type = "PRIMARY_KEY", notNull = true, label = "JRE_CD")
	public String getJreCd() {
		return jreCd;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * Définit la valeur de la propriété 'JRE_CD'.
	 * @param jreCd String <b>Obligatoire</b>
	 */
	public void setJreCd(final String jreCd) {
		this.jreCd = jreCd;
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


	// Association : Jobdefinition non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
