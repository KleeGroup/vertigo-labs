package snowblood.gen.fileinfo;

import io.vertigo.dynamo.file.metamodel.FileInfoDefinition;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.impl.file.model.AbstractFileInfo;

/**
 * Attention cette classe est générée automatiquement !
 * Objet représentant un fichier persistant IsisFileInfo
 */
public final class SnowbloodFileInfo extends AbstractFileInfo {
	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur par défaut.
	 * @param kFile Données du fichier
	 */
	public SnowbloodFileInfo(final VFile kFile) {
		super(FileInfoDefinition.findFileInfoDefinition(SnowbloodFileInfo.class), kFile);
	}
}

