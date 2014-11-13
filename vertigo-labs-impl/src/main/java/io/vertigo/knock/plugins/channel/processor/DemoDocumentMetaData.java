package io.vertigo.knock.plugins.channel.processor;

import io.vertigo.knock.metadata.MetaData;
import io.vertigo.knock.metadata.MetaDataType;
import io.vertigo.lang.Assertion;

/**
 * Liste des m�tadonn�es pour les Fichiers.
 *
 * @author pchretien
 * @version $Id: DemoDocumentMetaData.java,v 1.1 2011/07/19 15:00:32 npiedeloup Exp $
 */
public enum DemoDocumentMetaData implements MetaData {
	/** R�sum� du content. */
	SUMMARY(MetaDataType.STRING),

	/** Nom du client/prospet. */
	CUSTOMER(MetaDataType.STRING),

	/** Nom du projet. */
	PROJECT(MetaDataType.STRING),

	/** Titre extrait du document. */
	TITLE(MetaDataType.STRING), ;

	//-------------------------------------------------------------------------
	private final MetaDataType metaDataType;

	/**
	 * Initialise la m�tadonn�e en lui donnant un type
	 * @param metaDataType	Type de la m�tadonn�e
	 */
	private DemoDocumentMetaData(final MetaDataType metaDataType) {
		Assertion.checkNotNull(metaDataType);
		//---------------------------------------------------------
		this.metaDataType = metaDataType;
	}

	/** {@inheritDoc} */
	@Override
	public MetaDataType getType() {
		return metaDataType;
	}
}
