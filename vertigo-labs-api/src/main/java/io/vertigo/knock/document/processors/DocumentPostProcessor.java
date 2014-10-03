package io.vertigo.knock.document.processors;

import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.metadata.MetaDataContainer;

/**
* @author npiedeloup
* @version $Id: DocumentPostProcessor.java,v 1.4 2011/08/02 08:19:20 pchretien Exp $
*/
public interface DocumentPostProcessor {
	MetaDataContainer extract(final Document document);
}
