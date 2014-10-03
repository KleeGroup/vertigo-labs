package io.vertigo.knock.document.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

import java.util.Date;

/**
* @author npiedeloup
* @version $Id: DocumentVersionBuilder.java,v 1.3 2012/03/26 17:06:52 pchretien Exp $
*/
public final class DocumentVersionBuilder implements Builder<DocumentVersion> {
	//URL
	private String mySourceUrl;
	private String myDataSourceId;
	private Date myLastModified;

	private final DocumentVersion documentVersion;

	/**
	 * Constructeur.
	 */
	public DocumentVersionBuilder() {
		//Constructeur par d�faut.
		documentVersion = null;
	}

	public DocumentVersionBuilder(final DocumentVersion documentVersion) {
		Assertion.checkNotNull(documentVersion);
		//---------------------------------------------------------------------
		this.documentVersion = documentVersion;
	}

	public DocumentVersionBuilder withSourceUrl(final String sourceUrl) {
		Assertion.checkNotNull(sourceUrl);
		Assertion.checkArgument(this.mySourceUrl == null, "sourceUrl mus be empty");
		//---------------------------------------------------------------------
		this.mySourceUrl = sourceUrl;
		return this;
	}

	public DocumentVersionBuilder withDataSourceId(final String dataSourceId) {
		Assertion.checkNotNull(dataSourceId);
		Assertion.checkArgument(this.myDataSourceId == null, "dataSourceId mus be empty");
		//---------------------------------------------------------------------
		this.myDataSourceId = dataSourceId;
		return this;
	}

	public DocumentVersionBuilder withLastModified(final Date lastModified) {
		Assertion.checkNotNull(lastModified);
		Assertion.checkArgument(this.myLastModified == null, "lastModified mus be empty");
		//---------------------------------------------------------------------
		this.myLastModified = lastModified;
		return this;
	}

	public DocumentVersion build() {
		if (documentVersion == null) {
			return new DocumentVersion(myDataSourceId, mySourceUrl, myLastModified);
		}

		return new DocumentVersion(//
				get(documentVersion.getDataSourceId(), myDataSourceId), //
				get(documentVersion.getUrl(), mySourceUrl),//
				get(documentVersion.getLastModified(), myLastModified));
	}

	private static <X> X get(final X firstValue, final X overriddenValue) {
		return overriddenValue != null ? overriddenValue : firstValue;
	}
}
