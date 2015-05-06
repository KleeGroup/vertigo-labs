package snowblood.services.file;

import io.vertigo.dynamo.domain.model.FileInfoURI;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.metamodel.FileInfoDefinition;
import io.vertigo.dynamo.file.model.FileInfo;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.persistence.PersistenceManager;
import io.vertigo.dynamo.transaction.Transactional;

import java.io.File;

import javax.inject.Inject;

import snowblood.gen.fileinfo.SnowbloodFileInfo;

/**
 * Implémentation des services d'accès aux fichiers.
 *
 * @author pforhan
 */
@Transactional
public class FileServicesImpl implements FileServices {

	@Inject
	private PersistenceManager persistenceManager;

	@Inject
	private FileManager fileManager;

	@Override
	public Long createFile(final VFile fichier) {
		final SnowbloodFileInfo fileInfo = new SnowbloodFileInfo(fichier);
		persistenceManager.getFileInfoBroker().create(fileInfo);

		return (Long) fileInfo.getURI().getKey();
	}

	@Override
	public Long createFile(final File fichier, final String typeMime) {
		final VFile kLog = fileManager.createFile(fichier.getName(), typeMime, fichier);
		return createFile(kLog);
	}

	@Override
	public void updateFile(final Long filId, final VFile fichier) {
		final FileInfoURI uri = createUri(filId);
		final FileInfo fileInfo = persistenceManager.getFileInfoBroker().getFileInfo(uri);

		final FileInfo newFileInfo = new SnowbloodFileInfo(fichier);
		newFileInfo.setURIStored(fileInfo.getURI());
		persistenceManager.getFileInfoBroker().update(newFileInfo);
	}

	@Override
	public void deleteFile(final Long filId) {
		final FileInfoURI uri = createUri(filId);
		persistenceManager.getFileInfoBroker().deleteFileInfo(uri);
	}

	@Override
	public VFile getFileContent(final Long filId) {
		final FileInfoURI uri = createUri(filId);
		final FileInfo fileInfo = persistenceManager.getFileInfoBroker().getFileInfo(uri);
		return fileInfo.getVFile();
	}

	private static FileInfoURI createUri(final Long filId) {
		return new FileInfoURI(FileInfoDefinition.findFileInfoDefinition(SnowbloodFileInfo.class), filId);
	}

}
