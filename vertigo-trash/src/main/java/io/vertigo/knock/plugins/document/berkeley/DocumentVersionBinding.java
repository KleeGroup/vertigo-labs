package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.knock.document.model.DocumentVersion;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

/**
 * Classe qui pour un DtObject permet de lire/�crire un tuple.
 * Le binding est ind�pendant de la DtDefinition.
 *
 * @author pchretien
 * @version $Id: DocumentVersionBinding.java,v 1.1 2011/08/17 12:15:58 npiedeloup Exp $
 */
final class DocumentVersionWriter extends TupleBinding {
	/** {@inheritDoc} */
	@Override
	public Object entryToObject(final TupleInput ti) {
		throw new UnsupportedOperationException("No read : Write only");
	}

	/** {@inheritDoc} */
	@Override
	public void objectToEntry(final Object object, final TupleOutput to) {
		try {
			doDocumentVersionToEntry((DocumentVersion) object, to);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void doDocumentVersionToEntry(final DocumentVersion documentVersion, final TupleOutput to) {
		to.writeString(documentVersion.getUrl());
		to.writeString(documentVersion.getDataSourceId());
		to.writeLong(documentVersion.getLastModified().getTime());
	}
}
