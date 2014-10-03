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
	private String sourceUrl;
	private String dataSourceId;
	private Date lastModified;

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

	public void setSourceUrl(final String sourceUrl) {
		Assertion.checkNotNull(sourceUrl);
		Assertion.checkArgument(this.sourceUrl == null, "sourceUrl mus be empty");
		//---------------------------------------------------------------------
		this.sourceUrl = sourceUrl;
	}

	public void setDataSourceId(final String dataSourceId) {
		Assertion.checkNotNull(dataSourceId);
		Assertion.checkArgument(this.dataSourceId == null, "dataSourceId mus be empty");
		//---------------------------------------------------------------------
		this.dataSourceId = dataSourceId;
	}

	public void setLastModified(final Date lastModified) {
		Assertion.checkNotNull(lastModified);
		Assertion.checkArgument(this.lastModified == null, "lastModified mus be empty");
		//---------------------------------------------------------------------
		this.lastModified = lastModified;
	}

	public DocumentVersion build() {
		if (documentVersion == null) {
			return new DocumentVersion(dataSourceId, sourceUrl, lastModified);
		}

		return new DocumentVersion(//
				get(documentVersion.getDataSourceId(), dataSourceId), //
				get(documentVersion.getUrl(), sourceUrl),//
				get(documentVersion.getLastModified(), lastModified));
	}

	private static <X> X get(final X firstValue, final X overriddenValue) {
		return overriddenValue != null ? overriddenValue : firstValue;
	}
}
