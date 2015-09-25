package io.vertigo.knock.channel;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.search.model.Index;
import io.vertigo.knock.document.model.Document;

/** 
 * @author npiedeloup
 * @version $Id: DocumentConverter.java,v 1.1 2011/08/02 14:13:38 pchretien Exp $
 */
public interface DocumentConverter {

	Index<DtObject, DtObject> process(Document document);
}
