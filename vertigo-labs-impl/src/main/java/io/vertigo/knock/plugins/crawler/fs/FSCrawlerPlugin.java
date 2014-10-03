package io.vertigo.knock.plugins.crawler.fs;

import io.vertigo.core.lang.Assertion;
import io.vertigo.knock.document.DocumentManager;
import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.document.model.DocumentVersion;
import io.vertigo.knock.document.model.DocumentVersionBuilder;
import io.vertigo.knock.impl.crawler.CrawlerPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Crawler de fichier synchrone.
 * @author npiedeloup
 * @version $Id: FSCrawlerPlugin.java,v 1.10 2014/02/17 17:55:57 npiedeloup Exp $
 */
public final class FSCrawlerPlugin implements CrawlerPlugin {
	private final DocumentManager documentManager;

	private final String dataSourceId;
	private final File directory;
	private final String downloadUrl;
	private final Integer maxFiles;
	private final List<Pattern> excludePatterns;

	/**
	 * Constructeur.
	 * @param dataSourceId dataSourceId
	 * @param strDirectory Repertoire source
	 * @param downloadUrl Base des url de download
	 * @param maxFiles Nombre de fichier maximum � crawler, ou null pour aucune limite
	 * @param documentManager Manager de document
	 * @param strExcludePatterns Patterns d'exclusion de fichier
	 */
	@Inject
	public FSCrawlerPlugin(@Named("dataSourceId") final String dataSourceId, //
			@Named("directory") final String strDirectory, //
			@Named("downloadUrl") final String downloadUrl,//
			@Named("maxFiles") final Integer maxFiles, //
			@Named("excludePatterns") final String strExcludePatterns, //
			final DocumentManager documentManager) {
		Assertion.checkArgNotEmpty(dataSourceId);
		Assertion.checkArgNotEmpty(strDirectory);
		Assertion.checkArgNotEmpty(downloadUrl);
		Assertion.checkArgument(!downloadUrl.endsWith("/"), "L'url de download ne doit pas finir par /");
		Assertion.checkArgument(maxFiles == null || maxFiles > 0, "maxFiles est null ou positif");
		//---------------------------------------------------------------------
		this.dataSourceId = dataSourceId;
		directory = new File(strDirectory);
		this.documentManager = documentManager;
		this.downloadUrl = downloadUrl;
		this.maxFiles = maxFiles;
		if (strExcludePatterns != null && !strExcludePatterns.isEmpty()) {
			excludePatterns = new ArrayList<>();
			for (final String strExcludePattern : strExcludePatterns.split(";")) {
				excludePatterns.add(Pattern.compile(strExcludePattern));
			}
		} else {
			excludePatterns = Collections.emptyList();
		}
		//---------------------------------------------------------------------
		Assertion.checkState(directory.isDirectory(), "Le directory ({0}) doit �tre un directory", strDirectory);
	}

	/**
	 * @param startAtUrl Point de d�part du crawling
	 * @return Iterateur de DocumentVersion
	 */
	private Iterator<File> createFileIterator(final String startAtUrl) {
		//<param name="fileReaderClassName" value="kasperimpl.crawler.plugins.fs.FSFileInputPipe"/>
		//final Object[] parameters = new Object[] { directory, startAtUrl, maxFiles };
		//return ClassUtil.newInstance(fileReaderConstructor, parameters);
		return new FSFileIterator(directory, startAtUrl, maxFiles, excludePatterns);
	}

	private static final File getFile(final DocumentVersion documentVersion, final String dataSourceId, final String basePath) {
		//Il s'agit de la m�thode inverse de getRelativeUrl
		Assertion.checkArgument(dataSourceId.equals(documentVersion.getDataSourceId()), "Ce crawler n'est pas sur la m�me dataSource que le document (crawler:{0} document:{1})", dataSourceId, documentVersion.getDataSourceId());
		return new File(basePath + "/" + documentVersion.getUrl());
	}

	/**
	 * @param file Fichier source
	 * @param basePath Chemin de la racine.
	 * @return Url relative du fichier
	 */
	private static String getRelativeUrl(final File file, final String basePath) {
		//Il s'agit de la m�thode inverse de getFile
		final String fullPath = getNormalizedAbsolutePath(file);
		Assertion.checkArgument(fullPath.startsWith(basePath), "Le chemin du fichier {0} ne commence pas par la racine du channel {1}", fullPath, basePath);
		return fullPath.substring(basePath.length());
	}

	/** {@inheritDoc} */
	public Document readDocument(final DocumentVersion documentVersion) {
		final File file = getFile(documentVersion, dataSourceId, getNormalizedAbsolutePath(directory));
		return documentManager.createDocumentFromFile(documentVersion, file);
	}

	/** {@inheritDoc} */
	public String getBaseDownloadUrl() {
		return downloadUrl;
	}

	private static String getNormalizedAbsolutePath(final File file) {
		//normalization pour eviter les disctinctions Windows vs Linux
		return file.getAbsolutePath().replace('\\', '/');
	}

	private static Date getNormalizedLastModified(final File file) {
		//normalization pour eviter les disctinctions Windows vs Linux
		return new Date(file.lastModified() / 1000 * 1000);//on tronque � la seconde
	}

	/** {@inheritDoc} */
	public Iterable<DocumentVersion> crawl(final String startAtUrl) {
		return new Iterable<DocumentVersion>() {
			public Iterator<DocumentVersion> iterator() {
				return new DocumentVersionIterator(directory, dataSourceId, createFileIterator(startAtUrl));
			}
		};
	}

	/** {@inheritDoc} */
	public boolean accept(final String testDataSourceId) {
		return dataSourceId.equals(testDataSourceId);
	}

	private static class DocumentVersionIterator implements Iterator<DocumentVersion> {
		private final String basePath;
		private final String dataSourceId;
		private final Iterator<File> fileIterator;

		/**
		 * Constructeur.
		 * @param baseDirectory Repertoire de base
		 * @param dataSourceId Id de la DataSource
		 * @param fileIterator Source de fichier
		 */
		DocumentVersionIterator(final File baseDirectory, final String dataSourceId, final Iterator<File> fileIterator) {
			Assertion.checkNotNull(baseDirectory);
			Assertion.checkArgNotEmpty(dataSourceId);
			Assertion.checkNotNull(fileIterator);
			//-----------------------------------------------------------------
			basePath = getNormalizedAbsolutePath(baseDirectory);
			this.dataSourceId = dataSourceId;
			this.fileIterator = fileIterator;
		}

		public boolean hasNext() {
			return fileIterator.hasNext();
		}

		public DocumentVersion next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Liste vide");
			}
			final File file = fileIterator.next();
			return new DocumentVersionBuilder()//
					.withSourceUrl(getRelativeUrl(file, basePath))//
					.withDataSourceId(dataSourceId)//
					.withLastModified(getNormalizedLastModified(file))//
					.build();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
