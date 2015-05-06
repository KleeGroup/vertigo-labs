package snowblood.gen.services.tourdecontrole;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données TdcListe
 */
@DtDefinition(persistent = false)
public final class TdcListe implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String id;
	private String libelle;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Id'. 
	 * @return String id 
	 */
	@Field(domain = "DO_CODE", persistent = false, label = "Id")
	public String getId() {
		return id;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Id'.
	 * @param id String 
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Libellé'. 
	 * @return String libelle 
	 */
	@Field(domain = "DO_LIBELLE_COURT", persistent = false, label = "Libellé")
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

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
