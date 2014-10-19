package io.vertigo.knock.plugins.metadata.microsoft;

import io.vertigo.dynamo.file.model.KFile;
import io.vertigo.knock.impl.metadata.MetaDataExtractorPlugin;
import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.knock.metadata.MetaDataContainerBuilder;
import io.vertigo.lang.Assertion;

import java.io.InputStream;

import org.apache.poi.poifs.eventfilesystem.POIFSReader;

/**
 * Fabrique Abstraite des documents Lucene
 * � partir des Fichiers de la suite Open office de Microsoft.
 *
 * @author pchretien
 * @version $Id: AbstractMSMetaDataExtractorPlugin.java,v 1.5 2014/02/27 10:21:46 pchretien Exp $
 */
public abstract class AbstractMSMetaDataExtractorPlugin implements MetaDataExtractorPlugin {

	/**
	 * Extrait le contenu texte du FileInfo.
	 * @param file Document
	 */
	protected abstract String extractContent(final KFile file) throws Exception;

	/** {@inheritDoc} */
	public MetaDataContainer extractMetaData(final KFile file) throws Exception {
		Assertion.checkNotNull(file);
		//----------------------------------------------------------------------
		final MetaDataContainerBuilder metaDataContainerBuilder = new MetaDataContainerBuilder();
		//Etape 1 : Extraction des m�tadonn�es MS
		final POIFSReader reader = new POIFSReader();
		reader.registerListener(new POIFSReaderListenerImpl(metaDataContainerBuilder), "\005SummaryInformation");

		try (final InputStream inputStream = file.createInputStream()) {
			reader.read(inputStream);
		}
		//Etape 2 : Extraction des contenus selon le format (xls, ppt, doc)
		return metaDataContainerBuilder//
				.withMetaData(MSMetaData.CONTENT, extractContent(file))//
				.build();
	}
}
