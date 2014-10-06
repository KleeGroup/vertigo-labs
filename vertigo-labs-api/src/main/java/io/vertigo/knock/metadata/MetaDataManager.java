package io.vertigo.knock.metadata;

import io.vertigo.core.component.Manager;
import io.vertigo.dynamo.file.model.KFile;

/**
 * Gestionnaire centralisant l'extraction des m�tadonn�es.
 *
 * @author pchretien, npiedeloup
 * @version $Id: MetaDataManager.java,v 1.3 2014/01/28 18:49:34 pchretien Exp $
 */
public interface MetaDataManager extends Manager {
	/**
	 * Extraction des M�taDonn�es d'un fichier. 
	 * @param file Fichier dont on veut extraire les m�tadonn�es
	 */
	MetaDataContainer extractMetaData(final KFile file);
}