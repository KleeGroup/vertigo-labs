package zzzz;


import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est g�n�r�e automatiquement !
 * Objet de donn�es AbstractDocumentCriteria
 */
@DtDefinition(persistent = false)
public final class DocumentCriteria implements DtObject {
	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String keywords;

	/**
	 * Champ : DATA.
	 * R�cup�re la valeur de la propri�t� 'Mots-cl�s'. 
	 * @return String keywords 
	 */
	@Field(domain = "DO_DOC_CONTENT", persistent = false, label = "Mots-cl�s")
	public final String getKeywords() {
		return keywords;
	}

	/**
	 * Champ : DATA.
	 * D�finit la valeur de la propri�t� 'Mots-cl�s'.
	 * @param keywords String 
	 */
	public final void setKeywords(final String keywords) {
		this.keywords = keywords;
	}

	//Aucune Association d�clar�e

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
