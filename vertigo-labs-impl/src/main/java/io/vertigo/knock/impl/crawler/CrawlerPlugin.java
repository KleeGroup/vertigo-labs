package io.vertigo.knock.impl.crawler;

import io.vertigo.core.component.Plugin;
import io.vertigo.knock.crawler.Crawler;
import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.document.model.DocumentVersion;

/**
 * Plugin de gestion d'une datasource particuli�re.
 * @author npiedeloup
 * @version $Id: CrawlerPlugin.java,v 1.6 2014/02/17 17:55:57 npiedeloup Exp $
 */
public interface CrawlerPlugin extends Crawler, Plugin {
	/**
	 * Lit le document avec ses m�ta-donn�es.
	 * @param documentVersion Identifiant de la version du document
	 * @return Document lu
	 */
	Document readDocument(final DocumentVersion documentVersion);

	/**
	 * @param dataSourceId Id de la datasource
	 * @return si ce plugin g�re cette datasource
	 */
	boolean accept(final String dataSourceId);
}
