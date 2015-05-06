package snowblood.gen.domain.tourdecontrole;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données JobMode
 */
@javax.persistence.Entity
@javax.persistence.Table (name = "JOB_MODE")
@DtDefinition
public final class JobMode implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String jmoCd;
	private String libelle;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'JMO_CD'. 
	 * @return String jmoCd <b>Obligatoire</b>
	 */
	@javax.persistence.Id
	@javax.persistence.SequenceGenerator(name = "sequence", sequenceName = "SEQ_JOB_MODE")
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "sequence")
	@javax.persistence.Column(name = "JMO_CD")
	@Field(domain = "DO_CODE", type = "PRIMARY_KEY", notNull = true, label = "JMO_CD")
	public String getJmoCd() {
		return jmoCd;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * Définit la valeur de la propriété 'JMO_CD'.
	 * @param jmoCd String <b>Obligatoire</b>
	 */
	public void setJmoCd(final String jmoCd) {
		this.jmoCd = jmoCd;
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
