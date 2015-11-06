package snowblood.services.file;

import io.vertigo.dynamo.file.model.VFile;

import java.io.File;

/**
 * Interface pour les acès aux fichiers de la GED.
 *
 * @author pforhan
 */
public interface FileServices {

	/**
	 * Ajoute un fichier dans la GED.
	 *
	 * @param fichier à insérer
	 * @return l'identifiant du document stocké.
	 */
	Long createFile(VFile fichier);

	/**
	 * Ajoute un fichier dans la GED.
	 * 
	 * @param fichier Fichier à ajouter.
	 * @param typeMime Typemime du fichier.
	 * @return Identifier du document stocké.
	 */
	Long createFile(final File fichier, final String typeMime);

	/**
	 * Mets à jour le contenu d'un document de la GED.
	 *
	 * @param filId identifiant du document
	 * @param fichier contenu à modifier
	 */
	void updateFile(Long filId, VFile fichier);

	/**
	 * Supprime un document de la GED.
	 *
	 * @param filId identifiant du document à supprimer.
	 */
	void deleteFile(Long filId);

	/**
	 * Récupère un fichier à partir d'un identifiant de document.
	 *
	 * @param filId Identifiant du document dans la GED
	 * @return Contenu du document.
	 */
	VFile getFileContent(Long filId);

}
