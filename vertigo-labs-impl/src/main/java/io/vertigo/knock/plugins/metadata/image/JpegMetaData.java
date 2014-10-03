package io.vertigo.knock.plugins.metadata.image;

import io.vertigo.core.lang.Assertion;
import io.vertigo.knock.metadata.MetaData;
import io.vertigo.knock.metadata.MetaDataType;

/**
 * Liste des m�tadonn�es pour les MP3.
 * 
 * @author pchretien
 * @version $Id: JpegMetaData.java,v 1.3 2013/10/22 12:08:46 pchretien Exp $
 */
public enum JpegMetaData implements MetaData {
	// /** Album. */
	// ALBUM(MetaDataType.STRING),
	// /** Title */
	// TITLE(MetaDataType.STRING),
	// /** Artist. */
	// ARTIST(MetaDataType.STRING);
	;

	//-------------------------------------------------------------------------
	private final MetaDataType metaDataType;

	/** Constructeur.
	 * Initialise la m�tadonn�e en lui donnant un type
	 * @param metaDataType	metaDataType de la m�tadonn�e
	 */
	private JpegMetaData(final MetaDataType metaDataType) {
		Assertion.checkNotNull(metaDataType);
		//---------------------------------------------------------
		this.metaDataType = metaDataType;
	}

	/** {@inheritDoc} */
	public MetaDataType getType() {
		return metaDataType;
	}
}
