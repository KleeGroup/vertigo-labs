package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.core.util.ClassUtil;
import io.vertigo.core.util.StringUtil;
import io.vertigo.knock.impl.metadata.FileInfoMetaData;
import io.vertigo.knock.metadata.MetaData;
import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.knock.metadata.MetaDataContainerBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sleepycat.bind.tuple.TupleInput;

/**
 * Classe qui pour un DtObject permet de lire/�crire un tuple.
 * Le binding est ind�pendant de la DtDefinition.
 *
 * @author pchretien
 * @version $Id: AbstractDocumentBindingReader.java,v 1.5 2013/04/25 11:59:53 npiedeloup Exp $
 */
abstract class AbstractDocumentBindingReader implements DocumentBindingReader {

	protected final MetaDataContainer filterMetaDataContainer(final MetaDataContainer mdc) {
		final List<String> excludedMetaDataName = new ArrayList<>();
		excludedMetaDataName.add("CONTENT");
		final List<MetaData> excludedMetaData = new ArrayList<>(4);
		excludedMetaData.add(FileInfoMetaData.FILE_NAME);
		excludedMetaData.add(FileInfoMetaData.SIZE);
		excludedMetaData.add(FileInfoMetaData.FILE_EXTENSION);
		excludedMetaData.add(FileInfoMetaData.LAST_MODIFIED);

		final MetaDataContainerBuilder mdcBuilder = new MetaDataContainerBuilder();
		for (final MetaData metaData : mdc.getMetaDataSet()) {
			if (!excludedMetaData.contains(metaData) && !excludedMetaDataName.contains(metaData.toString())) {
				mdcBuilder.withMetaData(metaData, mdc.getValue(metaData));
			}
		}
		return mdcBuilder.build();
	}

	protected final MetaDataContainer doEntryToMdc(final TupleInput ti) {
		final MetaDataContainerBuilder metaDataContainerBuilder = new MetaDataContainerBuilder();
		final int size = ti.readInt();
		for (int i = 0; i < size; i++) {
			final String metaDataClassName = ti.readString();
			final String metaDataName = ti.readString();
			final Class<? extends Enum> metaDataClass = (Class<? extends Enum>) ClassUtil.classForName(metaDataClassName);
			final MetaData metaData = (MetaData) Enum.valueOf(metaDataClass, metaDataName);
			final boolean valueNotNull = ti.readBoolean();
			final Object value;
			if (valueNotNull) {
				switch (metaData.getType()) {
					case DATE:
						value = new Date(ti.readLong());
						break;
					case INTEGER:
						value = ti.readInt();
						break;
					case LONG:
						value = ti.readLong();
						break;
					case STRING:
						value = ti.readString();
						break;
					default:
						throw new RuntimeException(StringUtil.format("Type de meta-donn�es non g�r� : {0} pour {1}.{2}", null, metaData.getType(), metaDataClassName, metaDataName));
				}
			} else {
				value = null;
			}
			metaDataContainerBuilder.withMetaData(metaData, value);
		}
		return metaDataContainerBuilder.build();
	}
}
