package io.vertigo.knock.plugins.channel.indexhandler;

import io.vertigo.commons.codec.CodecManager;
import io.vertigo.core.Home;
import io.vertigo.dynamo.domain.metamodel.DataType;
import io.vertigo.dynamo.domain.metamodel.DtField;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.search.SearchManager;
import io.vertigo.dynamo.search.metamodel.IndexDefinition;
import io.vertigo.dynamo.search.model.Index;
import io.vertigo.dynamo.transaction.KTransactionManager;
import io.vertigo.dynamo.transaction.KTransactionWritable;
import io.vertigo.knock.channel.ChannelManager;
import io.vertigo.knock.channel.metadefinition.ChannelDefinition;
import io.vertigo.knock.impl.channel.IndexHandlerPlugin;
import io.vertigo.lang.Assertion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Impl�mentation du IndexHandlerPlugin avec constitution de packet.
 * Cette constitution assure :
 * - un nombre d'�lement limit� : 50
 * - une taille m�moire limit�e : < freeMem + 20%
 * 
 * @author npiedeloup
 * @version $Id: IndexHandlerPluginImpl.java,v 1.12 2013/04/25 12:33:28 npiedeloup Exp $
 */
public final class IndexHandlerPluginImpl implements IndexHandlerPlugin {

	private final SearchManager searchManager;
	private final KTransactionManager transactionManager;

	private Map<IndexDefinition, Collection<Index<DtObject, DtObject>>> indexMap;
	private boolean initialized;

	/**
	 * Constructeur.
	 * @param searchManager Manager de recherche
	 * @param transactionManager Manager de gestion des transactions
	 * @param codecManager Manager de codec
	 */
	@Inject
	public IndexHandlerPluginImpl(final SearchManager searchManager, final KTransactionManager transactionManager, final CodecManager codecManager) {
		Assertion.checkNotNull(searchManager);
		Assertion.checkNotNull(transactionManager);
		//---------------------------------------------------------------------
		this.searchManager = searchManager;
		this.transactionManager = transactionManager;
	}

	//Initialise le plugin (le pattern Activable est activer avant l'init du ChannelManager)
	private synchronized void init() {
		if (!initialized) {//on synchronize l'initialisation, le boolean est fiable pour le doubleCheckLocking
			indexMap = new HashMap<>();
			for (final ChannelDefinition channelDefinition : getChannelManager().getChannelDefinitions()) {
				final Collection<Index<DtObject, DtObject>> indexes = new ArrayList<>();
				indexMap.put(channelDefinition.getIndexDefinition(), indexes);
			}
			initialized = true;
		}
	}

	/** {@inheritDoc} */
	public void onIndex(final Index<DtObject, DtObject> index) {
		if (!initialized) {
			init();
		}
		final Collection<Index<DtObject, DtObject>> indexCollection = indexMap.get(index.getDefinition());
		//final long freeMem = Runtime.getRuntime().freeMemory();
		long dataToIndexSize = 0;
		synchronized (indexCollection) {
			indexCollection.add(index);
			for (final Index<?, ?> testIndex : indexCollection) {
				dataToIndexSize += estimateDtObjectSize(testIndex.getIndexDtObject());
			}
		}
		//System.out.println("dataToIndexSize= " + dataToIndexSize * 100 / (1024 * 1024) / 100d + " Mo, " + indexCollection.size() + " objets, freeMem= " + freeMem * 100 / (1024 * 1024) / 100d + " Mo, " + (freeMem <= dataToIndexSize * 1.20d ? ">>FLUSH<<" : "-CONTINUE-"));
		if (indexCollection.size() >= 20 || dataToIndexSize >= 20 * 1024 * 1024) { //plus de 20 docs ou > 20Mo
			flush(index.getDefinition(), indexCollection);
		}
	}

	private long estimateDtObjectSize(final DtObject dto) {
		long estimatedSize = 1 * 1024;
		for (final DtField dtField : DtObjectUtil.findDtDefinition(dto).getFields()) {
			if (DataType.String == dtField.getDomain().getDataType()) {
				final String value = (String) dtField.getDataAccessor().getValue(dto);
				estimatedSize += value != null ? value.length() : 0;
			}
		}
		return estimatedSize;
	}

	/** {@inheritDoc} */
	public void flush() {
		if (!initialized) {
			init();
		}
		for (final Map.Entry<IndexDefinition, Collection<Index<DtObject, DtObject>>> entry : indexMap.entrySet()) {
			flush(entry.getKey(), entry.getValue());
		}
	}

	private void flush(final IndexDefinition indexDefinition, final Collection<Index<DtObject, DtObject>> indexCollection) {
		final KTransactionWritable transaction = transactionManager.createCurrentTransaction();
		try {
			synchronized (indexCollection) {
				if (!indexCollection.isEmpty()) {
					searchManager.getSearchServices().<DtObject, DtObject> putAll(indexDefinition, indexCollection);
					transaction.commit();
					indexCollection.clear();
				}
			}
		} finally {
			transaction.rollback();
		}
	}

	private static final ChannelManager getChannelManager() {
		return Home.getComponentSpace().resolve(ChannelManager.class);
	}
}
