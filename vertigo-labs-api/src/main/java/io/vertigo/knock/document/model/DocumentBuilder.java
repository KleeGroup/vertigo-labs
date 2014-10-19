package io.vertigo.knock.document.model;

import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;

import java.util.UUID;

/**
 * @author npiedeloup
 * @version $Id: DocumentBuilder.java,v 1.2 2012/02/21 18:01:30 pchretien Exp $
 */
public final class DocumentBuilder implements Builder<Document> {
	//Version
	private Long mySize;

	//ExtractedMetaContent
	private String myName;
	private String myContent;
	private String myType;
	private MetaDataContainer myExtractedMetaDataContainer;
	//Processed
	private MetaDataContainer myEnhancedMetaDataContainer;
	//UserDefinedMetaData
	private MetaDataContainer myUserDefinedMetaDataContainer;

	private final DocumentVersion documentVersion;
	private final Document document;

	public DocumentBuilder(final DocumentVersion documentVersion) {
		Assertion.checkNotNull(documentVersion);
		//---------------------------------------------------------------------
		//Constructeur par d�faut.
		this.documentVersion = documentVersion;
		document = null;
		myContent = "";
		myType = "";
	}

	public DocumentBuilder(final Document document) {
		Assertion.checkNotNull(document);
		Assertion.checkNotNull(document.getDocumentVersion());
		//---------------------------------------------------------------------
		documentVersion = document.getDocumentVersion();
		this.document = document;
	}

	//Version
	public DocumentBuilder withSize(final long size) {
		this.mySize = size;
		return this;
	}

	//ExtractedMetaContent
	public DocumentBuilder withName(final String name) {
		this.myName = name;
		return this;
	}

	public DocumentBuilder withContent(final String content) {
		this.myContent = content;
		return this;
	}

	public DocumentBuilder withType(final String type) {
		this.myType = type;
		return this;
	}

	public DocumentBuilder withExtractedMetaDataContainer(final MetaDataContainer extractedMetaDataContainer) {
		this.myExtractedMetaDataContainer = extractedMetaDataContainer;
		return this;
	}

	public DocumentBuilder withEnhancedMetaDataContainer(final MetaDataContainer enhancedMetaDataContainer) {
		this.myEnhancedMetaDataContainer = enhancedMetaDataContainer;
		return this;
	}

	public DocumentBuilder withUserDefinedMetaDataContainer(final MetaDataContainer userDefinedMetaDataContainer) {
		this.myUserDefinedMetaDataContainer = userDefinedMetaDataContainer;
		return this;
	}

	public Document build() {
		if (document == null) {
			//Pour le premier document on commence la r�vision � 0. (Pas de r�vision)
			return new Document(documentVersion, mySize, nextRevision(), myName, myContent, myType,//
					get(MetaDataContainer.EMPTY_META_DATA_CONTAINER, myExtractedMetaDataContainer),//
					get(MetaDataContainer.EMPTY_META_DATA_CONTAINER, myEnhancedMetaDataContainer),//
					get(MetaDataContainer.EMPTY_META_DATA_CONTAINER, myUserDefinedMetaDataContainer));
		}

		final MetaDataContainer overriddenExtractedMetaDataContainer = get(document.getExtractedMetaDataContainer(), myExtractedMetaDataContainer);
		final MetaDataContainer overriddenEnhancedMetaDataContainer = get(document.getEnhancedMetaDataContainer(), myEnhancedMetaDataContainer);
		final MetaDataContainer overriddenUserDefinedMetaDataContainer = get(document.getUserDefinedMetaDataContainer(), myUserDefinedMetaDataContainer);

		return new Document(//
				documentVersion,//
				get(document.getSize(), mySize),//
				nextRevision(),//
				get(document.getName(), myName),//
				get(document.getContent(), myContent),//
				get(document.getType(), myType),//
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
