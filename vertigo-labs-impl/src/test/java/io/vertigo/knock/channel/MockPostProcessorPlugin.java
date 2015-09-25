package io.vertigo.knock.channel;

import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.impl.channel.DocumentPostProcessorPlugin;
import io.vertigo.knock.metadata.MetaData;
import io.vertigo.knock.metadata.MetaDataContainer;

import java.util.Iterator;

public final class MockPostProcessorPlugin implements DocumentPostProcessorPlugin {

	/** {@inheritDoc} */
	@Override
	public MetaDataContainer extract(final Document document) {
		System.out.println("I post-processed the document : " + document.getName());
		//		MetaDataContainer metaDataContainer = new MetaDataContainerBuilder()//
		//				.withMetaData(DemoDocumentMetaData.TITLE, "$" + id++)//
		//				.build();
		final MetaDataContainer metaDataContainer = document.getExtractedMetaDataContainer();
		final Iterator<MetaData> metaDataIterator = metaDataContainer.getMetaDataSet().iterator();
		while (metaDataIterator.hasNext()) {
			final MetaData metaData = metaDataIterator.next();
			System.out.println(metaData.toString());
		}
		System.out.println(document.getContent());

		return metaDataContainer;
	}
}
