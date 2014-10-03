package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.ClassUtil;
import io.vertigo.core.util.StringUtil;
import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.document.model.DocumentBuilder;
import io.vertigo.knock.document.model.DocumentVersion;
import io.vertigo.knock.document.model.DocumentVersionBuilder;
import io.vertigo.knock.metadata.MetaData;
import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.knock.metadata.MetaDataContainerBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

/**
 * Classe qui pour un DtObject permet de lire/�crire un tuple.
 * Le binding est ind�pendant de la DtDefinition.
 *
 * @author pchretien
 * @version $Id: DocumentBinding.java,v 1.9 2013/04/25 11:59:53 npiedeloup Exp $
 */
final class DocumentBinding extends TupleBinding implements DocumentBindingReader {
	private static final String CURRENT_VERSION = "V4";
	private final Map<String, DocumentBindingReader> documentReaderVersionMap = new HashMap<>();
	private final boolean readVersionOnly;

	/**
	 * Constructeur.
	 * @param readVersionOnly Si le reader est optimiser pour ne lire que la version
	 */
	DocumentBinding(final boolean readVersionOnly) {
		documentReaderVersionMap.put(CURRENT_VERSION, this);
		this.readVersionOnly = readVersionOnly;
	}

	/** {@inheritDoc} */
	@Override
	public Object entryToObject(final TupleInput ti) {
		try {
			return checkVersionAndDoEntryToDocument(ti);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void objectToEntry(final Object object, final TupleOutput to) {
		Assertion.checkArgument(!readVersionOnly, "Mode readVersionOnly, ne permet pas l'�criture.");
		try {
			doDocumentToEntry((Document) object, to);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Document checkVersionAndDoEntryToDocument(final TupleInput ti) throws Exception {
		final String version = ti.readString();
		final DocumentBindingReader documentBindingReader = documentReaderVersionMap.get(version);
		return documentBindingReader.doEntryToDocument(ti);
	}

	/** {@inheritDoc} */
	public Document doEntryToDocument(final TupleInput ti) throws Exception {
		final DocumentVersion documentVersion = doEntryToDocumentVersion(ti);
		final DocumentBuilder documentBuilder = new DocumentBuilder(documentVersion);
		final Long size = ti.readLong();
		final String name = ti.readString();

		documentBuilder//
				.withName(name)//
				.withSize(size);

		if (!readVersionOnly) {
			final String content = ti.readString();
			final String type = ti.readString();

			final MetaDataContainer extractedMetaDataContainer = doEntryToMdc(ti);
			final MetaDataContainer enhancedMetaDataContainer = doEntryToMdc(ti);
			final MetaDataContainer userDefinedMetaDataContainer = doEntryToMdc(ti);

			documentBuilder//
					.withContent(content)//
					.withType(type)//
					.withExtractedMetaDataContainer(extractedMetaDataContainer)//
					.withEnhancedMetaDataContainer(enhancedMetaDataContainer)//
					.withUserDefinedMetaDataContainer(userDefinedMetaDataContainer);
		}
		return documentBuilder.build();
	}

	private void doDocumentToEntry(final Document document, final TupleOutput to) {
		to.writeString(CURRENT_VERSION);
		doDocumentVersionToEntry(document.getDocumentVersion(), to);

		to.writeLong(document.getSize());
		to.writeString(document.getName());
		to.writeString(document.getContent());
		to.writeString(document.getType());
		//Assertion.invariant(!document.getExtractedMetaDataContainer().getMetaDataSet().isEmpty(), "WRITING: Les donn�es extraitent sont vide");
		doMdcToEntry(document.getExtractedMetaDataContainer(), to);
		doMdcToEntry(document.getEnhancedMetaDataContainer(), to);
		doMdcToEntry(document.getUserDefinedMetaDataContainer(), to);
	}

	private void doDocumentVersionToEntry(final DocumentVersion documentVersion, final TupleOutput to) {
		to.writeString(documentVersion.getUrl());
		to.writeString(documentVersion.getDataSourceId());
		to.writeLong(documentVersion.getLastModified().getTime());
	}

	private void doMdcToEntry(final MetaDataContainer metaDataContainer, final TupleOutput to) {
		to.writeInt(metaDataContainer.getMetaDataSet().size());
		for (final MetaData metaData : metaDataContainer.getMetaDataSet()) {
			Assertion.checkArgument(metaData instanceof Enum, "Les MetaData doivent �tre des enums pour �tre serializable !! {0} est une {1}", metaData, metaData.getClass().getName());
			//-----------------------------------------------------------------
			to.writeString(metaData.getClass().getName());
			to.writeString(metaData.toString());//TODO :WARN ne marche que parceque ce sont des enums !!
			final Object value = metaDataContainer.getValue(metaData);
			to.writeBoolean(value != null);
			if (value != null) {
				switch (metaData.getType()) {
					case DATE:
						to.writeLong(((Date) value).getTime());
						break;
					case INTEGER:
						to.writeInt((Integer) value);
						break;
					case LONG:
						to.writeLong((Long) value);
						break;
					case STRING:
						to.writeString((String) value);
						break;
					default:
						throw new IllegalStateException();
				}
			}
		}
	}

	private DocumentVersion doEntryToDocumentVersion(final TupleInput ti) {
		final String url = ti.readString();
		final String dataSourceId = ti.readString();
		final Date lastModified = new Date(ti.readLong());

		return new DocumentVersionBuilder()//
				.withSourceUrl(url)//
				.withLastModified(lastModified)//
				.withDataSourceId(dataSourceId)//
				.build();
	}

	private MetaDataContainer doEntryToMdc(final TupleInput ti) {
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
						throw new IllegalStateException(StringUtil.format("Type de meta-donn�es non g�r� : {0} pour {1}.{2}", metaData.getType(), metaDataClassName, metaDataName));
				}
			} else {
				value = null;
			}
			metaDataContainerBuilder.withMetaData(metaData, value);
		}
		return metaDataContainerBuilder.build();
	}
}
