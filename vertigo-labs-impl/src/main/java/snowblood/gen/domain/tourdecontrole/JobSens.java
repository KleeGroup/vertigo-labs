package snowblood.gen.domain.tourdecontrole;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données JobSens
 */
@javax.persistence.Entity
@javax.persistence.Table (name = "JOB_SENS")
@DtDefinition
public final class JobSens implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String jseCd;
	private String libelle;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'JSE_CD'. 
	 * @return String jseCd <b>Obligatoire</b>
	 */
	@javax.persistence.Id
	@javax.persistence.SequenceGenerator(name = "sequence", sequenceName = "SEQ_JOB_SENS")
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "sequence")
	@javax.persistence.Column(name = "JSE_CD")
	@Field(domain = "DO_CODE", type = "PRIMARY_KEY", notNull = true, label = "JSE_CD")
	public String getJseCd() {
		return jseCd;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * Définit la valeur de la propriété 'JSE_CD'.
	 * @param jseCd String <b>Obligatoire</b>
	 */
	public void setJseCd(final String jseCd) {
		this.jseCd = jseCd;
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
