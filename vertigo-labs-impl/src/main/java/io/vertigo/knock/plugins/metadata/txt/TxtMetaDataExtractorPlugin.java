package io.vertigo.knock.plugins.metadata.txt;

import io.vertigo.dynamo.file.model.KFile;
import io.vertigo.dynamo.file.util.FileUtil;
import io.vertigo.knock.impl.metadata.MetaDataExtractorPlugin;
import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.knock.metadata.MetaDataContainerBuilder;
import io.vertigo.lang.Assertion;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Gestion des documents de type txt.
 *
 * @author pchretien
 * @version $Id: TxtMetaDataExtractorPlugin.java,v 1.5 2014/02/27 10:22:04 pchretien Exp $
 */
public final class TxtMetaDataExtractorPlugin implements MetaDataExtractorPlugin {
	private static final int MAX_CONTENT_LENGTH = 25 * 1024 * 1024; //inutil de faire trop grand : 25Mo max

	private final String[] extensions;

	/**
	 * Constructeur.
	 */
	@Inject
	public TxtMetaDataExtractorPlugin(final @Named("extensions") String extensions) {
		Assertion.checkNotNull(extensions);
		//---------------------------------------------------------------------
		this.extensions = extensions.split(",");
	}

	private static String getContent(final KFile file) throws Exception {

		final InputStream inputStream = file.createInputStream();
		try {
			try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				final int length = file.getLength().intValue();
				final StringBuilder content = new StringBuilder("");
				content.ensureCapacity(Math.min(length, MAX_CONTENT_LENGTH));
				String line = reader.readLine();
				while (line != null && content.length() + line.length() <= MAX_CONTENT_LENGTH) {
					content.append(line);
					line = reader.readLine();
				}
				return content.toString();
			}
		} finally {
			inputStream.close();
		}
	}

	/** {@inheritDoc} */
	@Override
	public MetaDataContainer extractMetaData(final KFile file) throws Exception {
		Assertion.checkNotNull(file);
		//----------------------------------------------------------------------
		return new MetaDataContainerBuilder()//
				.withMetaData(TxtMetaData.CONTENT, getContent(file))//
				.build();
	}

	/** {@inheritDoc} */
	@Override
	public boolean accept(final KFile file) {
		Assertion.checkNotNull(file);
		//---------------------------------------------------------------------
		for (final String _extension : extensions) {
			final String fileExtension = FileUtil.getFileExtension(file.getFileName());
			if (_extension.trim().equalsIgnoreCase(fileExtension)) {
				return true;
			}
		}
		return false;
	}
}
