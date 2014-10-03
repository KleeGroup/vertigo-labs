package io.vertigo.knock.plugins.metadata.tika;

import org.apache.tika.parser.AutoDetectParser;

/**
 * Extrait les m�tadonn�es classiques via Tika.
 * Doit �tre surcharg� pour extraire les m�tadonn�es de chaque format
 * @author epaumier
 *
 */
public class AutoTikaMetaDataExtractorPlugin extends AbstractTikaMetaDataExtractorPlugin<AutoTikaMetaData> {

	/**
	 * Constructeur.
	 * Cr�e l'extracteur wrappant le parseur auto-d�tectant de Tika.
	 */
	public AutoTikaMetaDataExtractorPlugin() {
		super(new AutoDetectParser(), AutoTikaMetaData.CONTENT);

		bindMetaData(AutoTikaMetaData.TITLE, "title");
		bindMetaData(AutoTikaMetaData.DESCRIPTION, "description");
		bindMetaData(AutoTikaMetaData.SUBJECT, "subject");
		bindMetaData(AutoTikaMetaData.KEYWORD, "Keyword");
		bindMetaData(AutoTikaMetaData.LANGUAGE, "language");

		bindMetaData(AutoTikaMetaData.INITIAL_CREATOR, "initial-creator");
		bindMetaData(AutoTikaMetaData.CREATOR, "creator");

		bindMetaData(AutoTikaMetaData.CREATION_DATE, "Creation-Date");
		bindMetaData(AutoTikaMetaData.DATE, "date");

		bindMetaData(AutoTikaMetaData.GENERATOR, "generator");
		bindMetaData(AutoTikaMetaData.PAGE_COUNT, "nbPage");
		bindMetaData(AutoTikaMetaData.WORD_COUNT, "nbWord");
		bindMetaData(AutoTikaMetaData.CHARACTER_COUNT, "nbCharacter");
	}

}
