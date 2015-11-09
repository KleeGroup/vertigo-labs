package io.vertigo.knock.impl.crawler;

import io.vertigo.knock.crawling.CrawlerManager;
import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.document.model.DocumentVersion;

import java.util.List;

import javax.inject.Inject;

/**
 * Impl�mentation standard du Manager de crawling de document.
 * Ce manager g�re les diff�rentes sources de donn�es, et utilise un plugin adapat� leurs sp�cificit�es techniques.
 * @author npiedeloup
 * @version $Id: CrawlerManagerImpl.java,v 1.11 2014/02/17 17:55:57 npiedeloup Exp $
 */
public final class CrawlerManagerImpl implements CrawlerManager {
	@Inject
	private List<CrawlerPlugin> crawlerPlugins;

	/** {@inheritDoc} */
	@Override
	public Document readDocument(final DocumentVersion documentVersion) {
		return getCrawler(documentVersion.getDataSourceId()).readDocument(documentVersion);
	}

	/** {@inheritDoc} */
	@Override
	public CrawlerPlugin getCrawler(final String dataSourceId) {
		for (final CrawlerPlugin crawler : crawlerPlugins) {
			if (crawler.accept(dataSourceId)) {
				return crawler;
			}
		}
		throw new RuntimeException("Pas de crawler pour la dataSource " + dataSourceId);
	}
}
