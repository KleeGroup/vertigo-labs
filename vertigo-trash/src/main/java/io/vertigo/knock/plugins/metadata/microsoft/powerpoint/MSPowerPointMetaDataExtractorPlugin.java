package io.vertigo.knock.plugins.metadata.microsoft.powerpoint;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.file.util.FileUtil;
import io.vertigo.knock.plugins.metadata.microsoft.AbstractMSMetaDataExtractorPlugin;
import io.vertigo.lang.Assertion;

import java.io.InputStream;

/**
 * @author pchretien
 * @version $Id: MSPowerPointMetaDataExtractorPlugin.java,v 1.5 2014/02/27 10:21:46 pchretien Exp $
 */
public final class MSPowerPointMetaDataExtractorPlugin extends AbstractMSMetaDataExtractorPlugin {
	/** {@inheritDoc} */
	@Override
	protected String extractContent(final VFile file) throws Exception {
		Assertion.checkNotNull(file);
		//----------------------------------------------------------------------
		//Extraction des donn�es d'un fichier word

		final QuickButCruddyTextExtractor extractor;
		try (final InputStream inputStream = file.createInputStream()) {
			extractor = new QuickButCruddyTextExtractor(inputStream);
		}

		return extractor.getTextAsString();
	}

	/** {@inheritDoc} */
	@Override
	public boolean accept(final VFile file) {
		Assertion.checkNotNull(file);
		//-----
		final String fileExtension = FileUtil.getFileExtension(file.getFileName());
		return "ppt".equalsIgnoreCase(fileExtension);
	}
}
