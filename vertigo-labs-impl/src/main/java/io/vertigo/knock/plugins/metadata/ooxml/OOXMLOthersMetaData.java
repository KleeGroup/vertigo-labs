package io.vertigo.knock.plugins.metadata.ooxml;

import io.vertigo.core.lang.Assertion;
import io.vertigo.knock.metadata.MetaDataType;

/**
 * Liste des m�tadonn�es pour les fichiers Office Open XML (docx, pptx, xlsx, ...).
 *
 * @author epaumier
 * @version $Id: OOXMLOthersMetaData.java,v 1.3 2013/10/22 12:12:20 pchretien Exp $
 */
public enum OOXMLOthersMetaData implements OOXMLMetaData {
	/** Contenu textuel du fichier. */
	CONTENT(MetaDataType.STRING);

	//-------------------------------------------------------------------------
	private final MetaDataType metaDataType;

	/**
	 * Constructeur.
	 * Initialise la m�tadonn�e en lui donnant un type
	 * @param metaDataType	Type de la m�tadonn�e
	 */
	private OOXMLOthersMetaData(final MetaDataType metaDataType) {
		Assertion.checkNotNull(metaDataType);
		//---------------------------------------------------------
		this.metaDataType = metaDataType;
	}

	/** {@inheritDoc} */
	public MetaDataType getType() {
		return metaDataType;
	}
}
