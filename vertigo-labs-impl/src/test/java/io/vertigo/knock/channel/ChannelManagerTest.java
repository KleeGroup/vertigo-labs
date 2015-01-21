package io.vertigo.knock.channel;

import io.vertigo.AbstractTestCaseJU4;
import io.vertigo.core.Home;
import io.vertigo.knock.channel.metadefinition.ChannelDefinition;
import io.vertigo.knock.crawler.CrawlerManager;
import io.vertigo.knock.document.DocumentStore;
import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.document.model.DocumentVersion;
import io.vertigo.knock.processors.DocumentPostProcessor;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Test de l'impl�mentation standard.
 *
 * @author pchretien
 * @version $Id: MetaDataManagerTest.java,v 1.5 2014/07/16 13:26:33 pchretien Exp $
 */
public final class ChannelManagerTest extends AbstractTestCaseJU4 {
	@Inject
	private ChannelManager channelManager;
	@Inject
	private CrawlerManager crawlerManager;
	@Inject
	private DocumentPostProcessor documentPostProcessor;

	@Test
	public void testDiskC() {
		final DocumentPostProcessor documentPostProcessor = new MockPostProcessorPlugin();
		final DocumentStore documentStore = new DocumentStore() {
			private final Map<DocumentVersion, Document> map = new HashMap<>();

			@Override
			public Iterator<Document> iterator() {
				return map.values().iterator();
			}

			@Override
			public long size() {
				return map.size();
			}

			@Override
			public boolean contains(final DocumentVersion documentVersion) {
				return map.containsKey(documentVersion);
			}

			@Override
			public void add(final Document document) {
//				System.out.println("add doc[" + map.size() + "]: " + document.getDocumentVersion().getUrl());
				map.put(document.getDocumentVersion(), document);
			}
		};
		final ChannelDefinition channelDefinition = new ChannelDefinition("CHN_MOCK",
				"test data channel",
				crawlerManager.getCrawler("testFS"),
				Collections.singletonList(documentPostProcessor),
				documentStore);
		Home.getDefinitionSpace().put(channelDefinition, ChannelDefinition.class);

		//-------

		channelManager.crawlChannel(channelDefinition);
		channelManager.processChannel(channelDefinition);

	}
}
