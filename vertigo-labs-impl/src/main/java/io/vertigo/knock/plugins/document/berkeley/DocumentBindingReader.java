package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.knock.document.model.Document;

import com.sleepycat.bind.tuple.TupleInput;

/**
 * Classe qui pour un DtObject permet de lire/�crire un tuple.
 * Le binding est ind�pendant de la DtDefinition.
 *
 * @author pchretien
 * @version $Id: DocumentBindingReader.java,v 1.2 2011/08/02 08:19:40 pchretien Exp $
 */
interface DocumentBindingReader {

	Document doEntryToDocument(final TupleInput ti) throws Exception;

}
