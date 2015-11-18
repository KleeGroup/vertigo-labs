package io.vertigo.knock.plugins.channel.converter;

import io.vertigo.commons.codec.CodecManager;
import io.vertigo.core.Home;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.search.metamodel.IndexDefinition;
import io.vertigo.dynamo.search.model.Index;
import io.vertigo.dynamo.transaction.KTransactionManager;
import io.vertigo.folio.document.model.Document;
import io.vertigo.folio.metadata.MetaDataContainer;
import io.vertigo.knock.plugins.channel.DocumentConverterPlugin;
import io.vertigo.knock.plugins.channel.processor.DemoDocumentMetaData;
import io.vertigo.lang.Assertion;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.inject.Named;

import domain.document.DocumentIndexed;
import domain.document.DocumentResult;

/**
 * FIXME : d�pendance � Demo => pas � sa place

 * @author npiedeloup
 * @version $Id: DemoDocumentConverterPlugin.java,v 1.14 2012/06/20 13:53:30 pchretien Exp $

 */
public final class DemoDocumentConverterPlugin implements DocumentConverterPlugin {
	private final CodecManager codecManager;
	private final String indexName;

	/**
	 * Constructeur.
	 * @param indexName Nom de l'index de sortie
	 * @param transactionManager Manager de gestion des transactions
	 * @param codecManager Manager de codec
	 */
	@Inject
	public DemoDocumentConverterPlugin(@Named("outputIndex") final String indexName, final KTransactionManager transactionManager, final CodecManager codecManager) {
		Assertion.checkArgNotEmpty(indexName);
		Assertion.checkNotNull(transactionManager);
		//-----
		this.indexName = indexName;
		this.codecManager = codecManager;
	}

	/** {@inheritDoc} */
	@Override
	public Index process(final Document document) {
		final DocumentIndexed documentIndexed = new DocumentIndexed();
		final DocumentResult documentResult = new DocumentResult();
		final IndexDefinition documentIndexDefinition = Home.getComponentSpace().resolve(indexName, IndexDefinition.class);

		//-----
		final String url = document.getDocumentVersion().getUrl().replaceAll("\\\\", "/"); //replace \ par /

		final MetaDataContainer mdc = document.getMetaDataContainer();

		final String fileName = document.getName();
		final String customer = (String) mdc.getValue(DemoDocumentMetaData.CUSTOMER);
		final String project = (String) mdc.getValue(DemoDocumentMetaData.PROJECT);

		final String title = (String) mdc.getValue(DemoDocumentMetaData.TITLE);
		final String summary = (String) mdc.getValue(DemoDocumentMetaData.SUMMARY);

		final URI uri = new URI(documentIndexDefinition, computeFingerPrint(url));

		documentIndexed.setDocUrl(url);
		documentIndexed.setDocName(fileName);
		if (customer != null) {
			documentIndexed.setCustomer(customer);
		}
		if (project != null) {
			documentIndexed.setProject(project);
		}
		if (title != null) {
			documentIndexed.setTitle(title);
		}
		documentIndexed.setContent(document.getContent());
		documentIndexed.setLastModified(document.getDocumentVersion().getLastModified());
		documentIndexed.setSize(document.getSize());
		documentIndexed.setType(document.getType());
		//--
		documentResult.setDocUrl(url);
		documentResult.setDocName(fileName);
		if (customer != null) {
			documentResult.setCustomer(customer);
		}
		if (project != null) {
			documentResult.setProject(project);
		}
		if (title != null) {
			documentResult.setTitle(title);
		}
		if (summary != null) {
			documentResult.setSummary(summary);
		}
		documentResult.setLastModified(document.getDocumentVersion().getLastModified());
		documentResult.setSize(document.getSize());
		documentResult.setType(document.getType());
		//-----
		return Index.createIndex(documentIndexDefinition, uri, documentIndexed, documentResult);

	}

	private String computeFingerPrint(final String url) {
		try {
			final byte[] md5 = codecManager.getMD5Encoder().encode(url.getBytes("UTF8"));
			return asHex(md5);
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

	private static String asHex(final byte[] buf) {
		final char[] chars = new char[2 * buf.length];
		for (int i = 0; i < buf.length; ++i) {
			chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
			chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
		}
		return new String(chars);
	}
}
