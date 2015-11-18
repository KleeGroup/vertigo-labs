package io.vertigo.knock.plugins.channel.processor;

import io.vertigo.folio.document.model.Document;
import io.vertigo.folio.metadata.MetaDataContainer;
import io.vertigo.folio.metadata.MetaDataContainerBuilder;
import io.vertigo.knock.impl.channel.DocumentPostProcessorPlugin;

public final class ProjectAndCustomerDocumentPostProcessorPlugin implements DocumentPostProcessorPlugin {

	/** {@inheritDoc} */
	@Override
	public MetaDataContainer extract(final Document document) {
		//-----
		final String url = document.getDocumentVersion().getUrl();
		final String firstFolderName = extractFolderName(url, 1);
		final String secondFolderName = extractFolderName(url, 2);

		return new MetaDataContainerBuilder()//
				.withMetaData(DemoDocumentMetaData.CUSTOMER, firstFolderName)//
				.withMetaData(DemoDocumentMetaData.PROJECT, secondFolderName)//
				.build();
	}

	private static String extractFolderName(final String url, final int index) {
		final String[] urlSplitted = url.split("\\\\");
		if (urlSplitted.length > index) {
			return urlSplitted[index];
		}
		return null;
	}
}
