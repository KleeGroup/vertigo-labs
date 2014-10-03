package io.vertigo.knock.impl.channel;

import io.vertigo.core.component.Plugin;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.search.model.Index;

/** 
 * @author npiedeloup
 * @version $Id: IndexHandlerPlugin.java,v 1.1 2011/08/02 13:10:44 pchretien Exp $
 */
public interface IndexHandlerPlugin extends Plugin {

	void onIndex(final Index<DtObject, DtObject> index);

	/**
	 * Lance l'indexation des donn�es.
	 */
	void flush();
}
