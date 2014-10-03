package io.vertigo.knock.document;

import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.document.model.DocumentVersion;

/**
* @author npiedeloup
* @version $Id: DocumentStore.java,v 1.8 2013/04/25 11:59:53 npiedeloup Exp $
*/
public interface DocumentStore extends Iterable<Document> {

	void add(Document document);

	boolean contains(DocumentVersion documentVersion);

	long size();

	//	List<DocumentVersion> nextKeys(String lastKey, int nbDocs);

	//void remove(DocumentVersion documentVersion);

}
