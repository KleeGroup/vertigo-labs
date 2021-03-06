package io.vertigo.knock.plugins.channel.processor;

import io.vertigo.folio.document.model.Document;
import io.vertigo.folio.metadata.MetaData;
import io.vertigo.folio.metadata.MetaDataContainer;
import io.vertigo.folio.metadata.MetaDataContainerBuilder;
import io.vertigo.knock.impl.channel.DocumentPostProcessorPlugin;
import io.vertigo.knock.plugins.metadata.microsoft.MSMetaData;
import io.vertigo.knock.plugins.metadata.odf.ODFMetaData;
import io.vertigo.knock.plugins.metadata.ooxml.OOXMLCoreMetaData;
import io.vertigo.knock.plugins.metadata.pdf.PDFMetaData;

import java.util.Set;

public final class TitleDocumentPostProcessorPlugin implements DocumentPostProcessorPlugin {

	/** {@inheritDoc} */
	@Override
	public MetaDataContainer extract(final Document document) {
		//-----
		final String title = extractTitle(document.getMetaDataContainer());

		return new MetaDataContainerBuilder()//
				.withMetaData(DemoDocumentMetaData.TITLE, title)//
				.build();
	}

	private static String extractTitle(final MetaDataContainer metaDataContainer) {
		final Set<MetaData> metaDataSet = metaDataContainer.getMetaDataSet();

		//		if (metaDataSet.contains(Mp3MetaData.TITLE)) {
		//			return (String) metaDataContainer.getValue(Mp3MetaData.TITLE);
		//		} else
		if (metaDataSet.contains(MSMetaData.TITLE)) {
			return (String) metaDataContainer.getValue(MSMetaData.TITLE);
		} else if (metaDataSet.contains(PDFMetaData.TITLE)) {
			return (String) metaDataContainer.getValue(PDFMetaData.TITLE);
		} else if (metaDataSet.contains(OOXMLCoreMetaData.TITLE)) {
			return (String) metaDataContainer.getValue(OOXMLCoreMetaData.TITLE);
		} else if (metaDataSet.contains(ODFMetaData.TITLE)) {
			return (String) metaDataContainer.getValue(ODFMetaData.TITLE);
		}
		return null;
	}

}
