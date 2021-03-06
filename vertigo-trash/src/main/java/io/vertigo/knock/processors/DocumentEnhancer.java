package io.vertigo.knock.processors;

import io.vertigo.folio.document.model.Document;
import io.vertigo.folio.document.model.DocumentBuilder;
import io.vertigo.folio.metadata.MetaDataContainer;
import io.vertigo.folio.metadata.MetaDataContainerBuilder;
import io.vertigo.lang.Assertion;

import java.util.List;

/**
 * @author npiedeloup
 * @version $Id: DocumentEnhancer.java,v 1.7 2013/04/25 12:00:05 npiedeloup Exp $
 */
public final class DocumentEnhancer {
	private final List<DocumentPostProcessor> documentPostProcessors;

	public DocumentEnhancer(final List<DocumentPostProcessor> documentPostProcessors) {
		Assertion.checkNotNull(documentPostProcessors);
		//-----
		this.documentPostProcessors = documentPostProcessors;
	}

	public final Document enhance(final Document document) {
		Assertion.checkNotNull(document);
		//-----
		final MetaDataContainerBuilder metaDataContainerBuilder = new MetaDataContainerBuilder();
		for (final DocumentPostProcessor documentPostProcessor : documentPostProcessors) {
			metaDataContainerBuilder.withAllMetaDatas(documentPostProcessor.extract(document));
		}
		final MetaDataContainer metaDataContainer = metaDataContainerBuilder.build();

		return new DocumentBuilder(document)//
				.withEnhancedMetaDataContainer(metaDataContainer)//
				.build();
	}
}
