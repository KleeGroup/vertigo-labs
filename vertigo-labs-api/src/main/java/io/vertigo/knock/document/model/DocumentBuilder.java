package io.vertigo.knock.document.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.knock.metadata.MetaDataContainer;

import java.util.UUID;

/**
* @author npiedeloup
* @version $Id: DocumentBuilder.java,v 1.2 2012/02/21 18:01:30 pchretien Exp $
*/
public final class DocumentBuilder implements Builder<Document> {
	//Version
	private Long size;

	//ExtractedMetaContent
	private String name;
	private String content;
	private String type;
	private MetaDataContainer extractedMetaDataContainer;
	//Processed
	private MetaDataContainer enhancedMetaDataContainer;
	//UserDefinedMetaData
	private MetaDataContainer userDefinedMetaDataContainer;

	private final DocumentVersion documentVersion;
	private final Document document;

	public DocumentBuilder(final DocumentVersion documentVersion) {
		Assertion.checkNotNull(documentVersion);
		//---------------------------------------------------------------------
		//Constructeur par d�faut.
		this.documentVersion = documentVersion;
		document = null;
		content = "";
		type = "";
	}

	public DocumentBuilder(final Document document) {
		Assertion.checkNotNull(document);
		Assertion.checkNotNull(document.getDocumentVersion());
		//---------------------------------------------------------------------
		documentVersion = document.getDocumentVersion();
		this.document = document;
	}

	//Version
	public void setSize(final long size) {
		this.size = size;
	}

	//ExtractedMetaContent
	public void setName(final String name) {
		this.name = name;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public void setExtractedMetaDataContainer(final MetaDataContainer extractedMetaDataContainer) {
		this.extractedMetaDataContainer = extractedMetaDataContainer;
	}

	public void setEnhancedMetaDataContainer(final MetaDataContainer enhancedMetaDataContainer) {
		this.enhancedMetaDataContainer = enhancedMetaDataContainer;
	}

	public void setUserDefinedMetaDataContainer(final MetaDataContainer userDefinedMetaDataContainer) {
		this.userDefinedMetaDataContainer = userDefinedMetaDataContainer;
	}

	public Document build() {
		if (document == null) {
			//Pour le premier document on commence la r�vision � 0. (Pas de r�vision)
			return new Document(documentVersion, size, nextRevision(), name, content, type,//
					get(MetaDataContainer.EMPTY_META_DATA_CONTAINER, extractedMetaDataContainer),//
					get(MetaDataContainer.EMPTY_META_DATA_CONTAINER, enhancedMetaDataContainer),//
					get(MetaDataContainer.EMPTY_META_DATA_CONTAINER, userDefinedMetaDataContainer));
		}

		final MetaDataContainer overriddenExtractedMetaDataContainer = get(document.getExtractedMetaDataContainer(), extractedMetaDataContainer);
		final MetaDataContainer overriddenEnhancedMetaDataContainer = get(document.getEnhancedMetaDataContainer(), enhancedMetaDataContainer);
		final MetaDataContainer overriddenUserDefinedMetaDataContainer = get(document.getUserDefinedMetaDataContainer(), userDefinedMetaDataContainer);

		return new Document(//
				documentVersion,//
				get(document.getSize(), size),//
				nextRevision(),//
				get(document.getName(), name),//
				get(document.getContent(), content),//
				get(document.getType(), type),//
				get(MetaDataContainer.EMPTY_META_DATA_CONTAINER, overriddenExtractedMetaDataContainer),//
				get(MetaDataContainer.EMPTY_META_DATA_CONTAINER, overriddenEnhancedMetaDataContainer),//
				get(MetaDataContainer.EMPTY_META_DATA_CONTAINER, overriddenUserDefinedMetaDataContainer));
	}

	private static UUID nextRevision() {
		return UUID.randomUUID();
	}

	private static <X> X get(final X firstValue, final X overriddenValue) {
		return overriddenValue != null ? overriddenValue : firstValue;
	}
}
