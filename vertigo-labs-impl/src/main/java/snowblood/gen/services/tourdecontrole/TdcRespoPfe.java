package snowblood.gen.services.tourdecontrole;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données TdcRespoPfe
 */
@DtDefinition(persistent = false)
public final class TdcRespoPfe implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String responsabilite;
	private String dossier;
	private Long resId;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Responsabilité'. 
	 * @return String responsabilite 
	 */
	@Field(domain = "DO_LIBELLE_LONG", persistent = false, label = "Responsabilité")
	public String getResponsabilite() {
		return responsabilite;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Responsabilité'.
	 * @param responsabilite String 
	 */
	public void setResponsabilite(final String responsabilite) {
		this.responsabilite = responsabilite;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Dossier PFE'. 
	 * @return String dossier 
	 */
	@Field(domain = "DO_LIBELLE_LONG", persistent = false, label = "Dossier PFE")
	public String getDossier() {
		return dossier;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Dossier PFE'.
	 * @param dossier String 
	 */
	public void setDossier(final String dossier) {
		this.dossier = dossier;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Id Responsabilité'. 
	 * @return Long resId 
	 */
	@Field(domain = "DO_IDENTIFIANT", persistent = false, label = "Id Responsabilité")
	public Long getResId() {
		return resId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Id Responsabilité'.
	 * @param resId Long 
	 */
	public void setResId(final Long resId) {
		this.resId = resId;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
