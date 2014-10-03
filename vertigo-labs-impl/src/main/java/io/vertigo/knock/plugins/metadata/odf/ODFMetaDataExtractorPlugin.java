package io.vertigo.knock.plugins.metadata.odf;

import io.vertigo.core.lang.Assertion;
import io.vertigo.dynamo.file.model.KFile;
import io.vertigo.dynamo.file.util.FileUtil;
import io.vertigo.knock.plugins.metadata.tika.AbstractTikaMetaDataExtractorPlugin;

import org.apache.tika.parser.odf.OpenDocumentParser;

/**
 * Extraction des m�tadonn�es ODF via Tika.
 * 
 * @author epaumier
 * @version $Id: ODFMetaDataExtractorPlugin.java,v 1.4 2014/01/28 18:49:34 pchretien Exp $
 */
public final class ODFMetaDataExtractorPlugin extends AbstractTikaMetaDataExtractorPlugin<ODFMetaData> {

	/**
	 * Constructeur.
	 * Cr�e l'extracteur wrappant le parseur de document OpenOffice de Tika.
	 */
	public ODFMetaDataExtractorPlugin() {
		super(new OpenDocumentParser(), ODFMetaData.CONTENT);

		bindMetaData(ODFMetaData.TITLE, "title");
		bindMetaData(ODFMetaData.DESCRIPTION, "description");
		bindMetaData(ODFMetaData.SUBJECT, "subject");
		bindMetaData(ODFMetaData.KEYWORD, "Keyword");
		bindMetaData(ODFMetaData.LANGUAGE, "language");

		bindMetaData(ODFMetaData.INITIAL_CREATOR, "initial-creator");
		bindMetaData(ODFMetaData.CREATOR, "creator");

		bindMetaData(ODFMetaData.CREATION_DATE, "Creation-Date");
		bindMetaData(ODFMetaData.DATE, "date");
		bindMetaData(ODFMetaData.EDITING_DURATION, "Edit-Time");
		bindMetaData(ODFMetaData.EDITING_CYCLES, "editing-cycles");

		bindMetaData(ODFMetaData.GENERATOR, "generator");

		bindMetaData(ODFMetaData.TABLE_COUNT, "nbTab");
		bindMetaData(ODFMetaData.OBJECT_COUNT, "nbObject");
		bindMetaData(ODFMetaData.IMAGE_COUNT, "nbImg");
		bindMetaData(ODFMetaData.PAGE_COUNT, "nbPage");
		bindMetaData(ODFMetaData.PARAGRAPH_COUNT, "nbPara");
		bindMetaData(ODFMetaData.WORD_COUNT, "nbWord");
		bindMetaData(ODFMetaData.CHARACTER_COUNT, "nbCharacter");
	}

	/** {@inheritDoc} */
	@Override
	public boolean accept(final KFile file) {
		Assertion.checkNotNull(file);
		//---------------------------------------------------------------------
		final String fileExtension = FileUtil.getFileExtension(file.getFileName());
		return "odt".equalsIgnoreCase(fileExtension);
	}
}
