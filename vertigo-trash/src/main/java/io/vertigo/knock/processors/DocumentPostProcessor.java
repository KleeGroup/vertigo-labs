package io.vertigo.knock.processors;

import io.vertigo.folio.document.model.Document;
import io.vertigo.folio.metadata.MetaDataContainer;

/**
* @author npiedeloup
* @version $Id: DocumentPostProcessor.java,v 1.4 2011/08/02 08:19:20 pchretien Exp $
*/
public interface DocumentPostProcessor {
	MetaDataContainer extract(final Document document);
}
