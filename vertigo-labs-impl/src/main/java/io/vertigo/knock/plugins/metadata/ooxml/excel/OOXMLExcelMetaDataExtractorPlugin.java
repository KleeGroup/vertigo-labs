package io.vertigo.knock.plugins.metadata.ooxml.excel;

import io.vertigo.core.lang.Assertion;
import io.vertigo.dynamo.file.model.KFile;
import io.vertigo.dynamo.file.util.FileUtil;
import io.vertigo.knock.plugins.metadata.ooxml.CommonOOXMLMetaDataExtractorPlugin;

/**
 * Permet d'�tendre l'extracteur OOXML pour des traitements particuliers sur les feuillets Excel.
 * @author epaumier
 * @version $Id: OOXMLExcelMetaDataExtractorPlugin.java,v 1.4 2014/01/28 18:49:34 pchretien Exp $
 */
public final class OOXMLExcelMetaDataExtractorPlugin extends CommonOOXMLMetaDataExtractorPlugin {
	/** {@inheritDoc} */
	@Override
	public boolean accept(final KFile file) {
		Assertion.checkNotNull(file);
		//---------------------------------------------------------------------
		final String fileExtension = FileUtil.getFileExtension(file.getFileName());
		return "xlsx".equalsIgnoreCase(fileExtension);
	}
}
