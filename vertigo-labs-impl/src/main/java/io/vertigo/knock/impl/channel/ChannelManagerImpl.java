package io.vertigo.knock.impl.channel;

import io.vertigo.core.Home;
import io.vertigo.dynamo.transaction.KTransactionManager;
import io.vertigo.knock.channel.ChannelManager;
import io.vertigo.knock.channel.metadefinition.ChannelDefinition;
import io.vertigo.knock.crawler.CrawlerManager;
import io.vertigo.knock.document.DocumentStore;
import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.document.model.DocumentVersion;
import io.vertigo.knock.impl.channel.datapipe.StatsDocument;
import io.vertigo.knock.processors.DocumentEnhancer;
import io.vertigo.knock.processors.DocumentPostProcessor;
import io.vertigo.lang.Assertion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public final class ChannelManagerImpl implements ChannelManager {
	//	private final Logger logger = Logger.getLogger(getClass());
	private final CrawlerManager crawlerManager;
	//	private final IndexHandlerPlugin indexHandlerPlugin;

	private final Map<ChannelDefinition, String> mapStateChannel = new HashMap<>();
	private final List<DocumentPostProcessor> documentPostProcessors;

	/**
	 * Constructeur.
	 * @param crawlerManager Manager de parcours de source
	 * @param transactionManager Manager de transaction
	 * param indexHandlerPlugin Handler d'indexation
	 */
	@Inject
	public ChannelManagerImpl(final CrawlerManager crawlerManager, final List<DocumentPostProcessorPlugin> documentPostProcessorPlugins, final KTransactionManager transactionManager/*, final IndexHandlerPlugin indexHandlerPlugin*/) {
		Assertion.checkNotNull(crawlerManager);
		Assertion.checkNotNull(transactionManager);
		//	Assertion.checkNotNull(indexHandlerPlugin);
		Assertion.checkNotNull(documentPostProcessorPlugins);
		//-----
		this.documentPostProcessors = new ArrayList<>();
		for (final DocumentPostProcessorPlugin documentPostProcessorPlugin : documentPostProcessorPlugins) {
			documentPostProcessors.add(documentPostProcessorPlugin);
		}

		this.crawlerManager = crawlerManager;
		//>>TODO a d�placer====
		Home.getDefinitionSpace().register(ChannelDefinition.class);
		//<<TODO a d�placer====
		//this.indexHandlerPlugin = indexHandlerPlugin;
	}

	//	/** {@inheritDoc} */
	//	@Override
	//	public List<ChannelDefinition> getChannelDefinitions() {
	//		return new ArrayList<>(Home.getDefinitionSpace().getAll(ChannelDefinition.class));
	//	}

	/** {@inheritDoc} */
	@Override
	public void crawlChannel(final ChannelDefinition channelDefinition) {
		mapStateChannel.put(channelDefinition, String.format("STARTING CRAWL"));
		final StatsDocument statsDocument = new StatsDocument(channelDefinition.getLabel());
		final DocumentStore documentStore = channelDefinition.getDocumentStore();
		final String lastDocumentUrl = "";//loadLastStoredDir(channelDefinition.getLabel());
		long nbDocumentStored = 0;
		long nbDocumentCrawled = 0;
		boolean finishOk = false;
		try {
			for (final DocumentVersion documentVersion : channelDefinition.getCrawler().crawl(lastDocumentUrl)) {
				nbDocumentCrawled++;
				if (!documentStore.contains(documentVersion)) {
					//TODO en ascynchrone mettre tout ca en work (extract+store)
					//on lit le doc (extraction)
					final long time = System.currentTimeMillis();
					final Document document = crawlerManager.readDocument(documentVersion);
					statsDocument.addStats(document, System.currentTimeMillis() - time);
					//et on le stock
					documentStore.add(document);
					nbDocumentStored++;
				} //else on est � jour
				mapStateChannel.put(channelDefinition, String.format("CRAWLING %1$s docs, %2$s updated (lastFile:%3$s)", nbDocumentCrawled, nbDocumentStored, documentVersion.getUrl()));
				if (nbDocumentCrawled % 50 == 0) {
					System.gc(); //TODO : on a un etrange OOM avec que des byte[] en unreachable object. Le gc permet de vider  la m�moire.
				}
			}
			finishOk = true;
		} finally {
			mapStateChannel.put(channelDefinition, String.format((finishOk ? "[OK]" : "[KO]") + " FINISHED %1$s docs CRAWLED, %2$s updated", nbDocumentCrawled, nbDocumentStored));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void processChannel(final ChannelDefinition channelDefinition) {
		mapStateChannel.put(channelDefinition, String.format("STARTING PROCESS"));
		final DocumentEnhancer documentProcessor = getEnhancer(channelDefinition);
		final DocumentStore documentStore = channelDefinition.getDocumentStore();
		long nbDocumentProcessed = 0;
		boolean finishOk = false;
		try {
			for (final Document document : readStoredDocuments(channelDefinition)) {
				final Document processedDocument = documentProcessor.enhance(document);
				//et on le stock
				documentStore.add(processedDocument);
				nbDocumentProcessed++;
				mapStateChannel.put(channelDefinition, String.format("%1$s %2$s docs", "PROCESSING", nbDocumentProcessed));

			}
			finishOk = true;
		} finally {
			mapStateChannel.put(channelDefinition, String.format((finishOk ? "[OK]" : "[KO]") + " FINISHED %1$s docs PROCESSED", nbDocumentProcessed));
		}
	}

	//	/** {@inheritDoc} */
	//	@Override
	//	public void indexChannel(final ChannelDefinition channelDefinition) {
	//		mapStateChannel.put(channelDefinition, String.format("STARTING INDEX"));
	//		final IndexHandlerPlugin indexHandler = getIndexHandlerPlugin();
	//		final DocumentConverter documentconverter = channelDefinition.getDocumentConverter();
	//		long nbDocumentIndexed = 0;
	//		boolean finishOk = false;
	//		try {
	//			for (final Document document : readStoredDocuments(channelDefinition)) {
	//				final Index<DtObject, DtObject> index = documentconverter.process(document);
	//				try {
	//					indexHandler.onIndex(index);
	//					nbDocumentIndexed++;
	//				} catch (final Exception e) {
	//					logger.error("Impossible d'indexer :" + document.getName() + " " + index.getURI() + " index:" + index.getIndexDtObject(), e);
	//				}
	//				mapStateChannel.put(channelDefinition, String.format("INDEXING %1$s docs", nbDocumentIndexed));
	//			}
	//			finishOk = true;
	//		} finally {
	//			mapStateChannel.put(channelDefinition, String.format((finishOk ? "[OK]" : "[KO]") + " FINISHED %1$s docs INDEXED", nbDocumentIndexed));
	//			indexHandler.flush();
	//		}
	//	}

	private static DocumentEnhancer getEnhancer(final ChannelDefinition channelDefinition) {
		return new DocumentEnhancer(channelDefinition.getDocumentPostProcessors());
	}

	//	/** {@inheritDoc} */
	//	@Override
	//	public List<DocumentPostProcessor> getDocumentPostProcessors() {
	//		return documentPostProcessors;
	//	}

	private static Iterable<Document> readStoredDocuments(final ChannelDefinition channelDefinition) {
		return channelDefinition.getDocumentStore();
	}
	//
	//	/** {@inheritDoc} */
	//	@Override
	//	public void crawlAndIndexChannel(final ChannelDefinition channelDefinition) {
	//		mapStateChannel.put(channelDefinition, String.format("STARTING CRAWL & INDEX"));
	//		final StatsDocument statsDocument = new StatsDocument(channelDefinition.getLabel());
	//		final DocumentStore documentStore = channelDefinition.getDocumentStore();
	//		final DocumentEnhancer documentProcessor = getEnhancer(channelDefinition);
	//		final IndexHandlerPlugin indexHandler = getIndexHandlerPlugin();
	//
	//		String lastUpdatedDocumentUrl = "";//loadLastStoredDir(channelDefinition.getLabel());
	//		final long startTime = System.currentTimeMillis();
	//		long nbDocumentStored = 0;
	//		long nbDocumentCrawled = 0;
	//		final String lastDocumentUrl = ""; //loadLastStoredDir(channelDefinition.getLabel());
	//		boolean finishOk = false;
	//		try {
	//			for (final DocumentVersion documentVersion : channelDefinition.getCrawler().crawl(lastDocumentUrl)) {
	//				nbDocumentCrawled++;
	//				if (!documentStore.contains(documentVersion)) {
	//					//on lit le doc (extraction)
	//					final long time = System.currentTimeMillis();
	//					final Document document = crawlerManager.readDocument(documentVersion);
	//					statsDocument.addStats(document, System.currentTimeMillis() - time);
	//
	//					final Document processedDocument = documentProcessor.enhance(document);
	//					//et on le stock
	//					documentStore.add(processedDocument);
	//					//puis on index
	//					final Index<DtObject, DtObject> index = channelDefinition.getDocumentConverter().enhance(processedDocument);
	//					indexHandler.onIndex(index);
	//					nbDocumentStored++;
	//					lastUpdatedDocumentUrl = documentVersion.getUrl();
	//				} //else on est � jour
	//				if (nbDocumentCrawled % 50 == 0) {
	//					indexHandler.flush(); //on flush r�guli�rement
	//					System.gc(); //TODO : on a un etrange OOM avec que des byte[] en unreachable object. Le gc permet de vider  la m�moire.
	//				}
	//				final long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
	//				final double crawlingSpeed = elapsedTime > 0 ? nbDocumentCrawled * 100 / elapsedTime / 100d : Double.NaN;
	//				mapStateChannel.put(channelDefinition, String.format("CRAWLING & INDEXING %1$s docs in %5$ss (%6$s doc/s), %2$s updated <br/>(lastFile:%3$s)<br/>(lastUpdatedFile:%4$s)", nbDocumentCrawled, nbDocumentStored, documentVersion.getUrl(), lastUpdatedDocumentUrl, elapsedTime, crawlingSpeed));
	//			}
	//			finishOk = true;
	//		} finally {
	//			final long finishTime = (System.currentTimeMillis() - startTime) / 1000;
	//			final double crawlingSpeed = finishTime > 0 ? nbDocumentCrawled * 100 / finishTime / 100d : Double.NaN;
	//			mapStateChannel.put(channelDefinition, String.format((finishOk ? "[OK]" : "[KO]") + " FINISHED %1$s docs CRAWLED & INDEXED in %3$ss (%4$s doc/s), %2$s updated", nbDocumentCrawled, nbDocumentStored, finishTime, crawlingSpeed));
	//			indexHandler.flush();
	//		}
	//	}
	//
	//	/**
	//	 * @return Plugin de gestion des documents � indexer.
	//	 */
	//	private IndexHandlerPlugin getIndexHandlerPlugin() {
	//		return indexHandlerPlugin;
	//	}
	//
	//	/** {@inheritDoc} */
	//	@Override
	//	public DocumentConverter getDocumentConverter(final String documentConverterId) {
	//		return Home.getComponentSpace().resolve(documentConverterId, DocumentConverterPlugin.class);
	//		//		final Collection<Plugin> plugins = Home.getContainer().getPlugins(ChannelManager.class);
	//		//		for (final Plugin plugin : plugins) {
	//		//			if (plugin instanceof DocumentConverterPlugin && documentConverterId.equals(((DocumentConverterPlugin) plugin).getId())) {
	//		//				return (DocumentConverterPlugin) plugin;
	//		//			}
	//		//		}
	//		//		throw new KRuntimeException("Pas de DocumentConverter d'id : " + documentConverterId);
	//	}
	//
	//	//private static String SAVE_FILE_NAME = "lastIndexedFile.ser";
	//
	//	//	/** {@inheritDoc} */
	//	//	public Work<Document, ?> createDocumentExtractMdcWork(final Document document) {
	//	//		return new DocumentExtractMdcWork(document);
	//	//	}
	//
	//	/** {@inheritDoc} */
	//	@Override
	//	public String getChannelState(final ChannelDefinition channelDefinition) {
	//		final String state = mapStateChannel.get(channelDefinition);
	//		return state != null ? state : "NOT STARTED";
	//	}
	//
	//	/** {@inheritDoc} */
	//	@Override
	//	public boolean isRunning() {
	//		for (final ChannelDefinition definition : getChannelDefinitions()) {
	//			if (getChannelState(definition).contains("ING")) {
	//				return true;
	//			}
	//		}
	//		return false;
	//	}
}
