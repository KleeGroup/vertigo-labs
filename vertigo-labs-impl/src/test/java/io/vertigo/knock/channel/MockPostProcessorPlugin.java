package io.vertigo.knock.channel;

import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.impl.channel.DocumentPostProcessorPlugin;
import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.knock.metadata.MetaDataContainerBuilder;
import io.vertigo.knock.plugins.channel.processor.DemoDocumentMetaData;

public final class MockPostProcessorPlugin implements DocumentPostProcessorPlugin {
	private static int id = 0;

	/** {@inheritDoc} */
	@Override
	public MetaDataContainer extract(final Document document) {
		return new MetaDataContainerBuilder()//
				.withMetaData(DemoDocumentMetaData.TITLE, "$" + id++)//
				.build();
	}
}
