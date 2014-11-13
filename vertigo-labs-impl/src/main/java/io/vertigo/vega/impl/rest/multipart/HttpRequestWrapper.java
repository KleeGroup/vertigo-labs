package io.vertigo.vega.impl.rest.multipart;

import io.vertigo.dynamo.file.model.KFile;
import io.vertigo.lang.Option;
import io.vertigo.lang.VUserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

/**
 * Wrapping de la requ�te Http pour la gestion des requ�te multipart.
 *
 * @author npiedeloup
 * @version $Id: RequestWrapper.java,v 1.6 2013/06/25 10:57:08 pchretien Exp $
 */
public final class HttpRequestWrapper extends HttpServletRequestWrapper {

	private final Map<String, String[]> parameters;
	private final Map<String, KFile> uploadedFiles;
	private final Map<String, RuntimeException> tooBigFiles;

	/**
	 * Creates a new KRequestWrapper object.
	 *
	 * @param request Requ�te � g�rer.
	 */
	HttpRequestWrapper(final HttpServletRequest request, final Map<String, String[]> parameters, final Map<String, KFile> uploadedFiles, final Map<String, RuntimeException> tooBigFiles) {
		super(request);
		this.parameters = parameters;
		this.uploadedFiles = uploadedFiles;
		this.tooBigFiles = tooBigFiles;
	}

	/**
	 * Donne le fichier t�l�charg�.
	 *
	 * @param fileName Nom du param�tre portant le fichier dans la request
	 * @return Fichier t�l�charg�.
	 */
	public Option<KFile> getUploadedFile(final String fileName) {
		if (tooBigFiles.containsKey(fileName)) {
			throw (VUserException) tooBigFiles.get(fileName);
		}
		return Option.option(uploadedFiles.get(fileName));
	}

	/** {@inheritDoc} */
	@Override
	public String getParameter(final String name) {
		final String[] values = parameters.get(name);
		if (values == null) {
			return null;
		}
		// 1�re occurence.
		return values[0];
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, String[]> getParameterMap() {
		return parameters;
	}

	/** {@inheritDoc} */
	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(parameters.keySet());
	}

	/** {@inheritDoc} */
	@Override
	public String[] getParameterValues(final String name) {
		return parameters.get(name);
	}

	/** {@inheritDoc} */
	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		throw new UnsupportedOperationException("Not implemented : use getPart(name)");
	}

	/** {@inheritDoc} */
	@Override
	public Part getPart(final String name) throws IOException, ServletException {
		final Option<KFile> file = getUploadedFile(name);
		return new Part() {

			@Override
			public InputStream getInputStream() throws IOException {
				return file.get().createInputStream();
			}

			@Override
			public String getContentType() {
				return file.get().getMimeType();
			}

			@Override
			public String getName() {
				return file.get().getFileName();
			}

			@Override
			public long getSize() {
				return file.get().getLength();
			}

			@Override
			public void write(final String paramString) throws IOException {
				throw new UnsupportedOperationException("Not implemented");
			}

			@Override
			public void delete() throws IOException {
				throw new UnsupportedOperationException("Not implemented");
			}

			@Override
			public String getHeader(final String paramString) {
				throw new UnsupportedOperationException("Not implemented");
			}

			@Override
			public Collection<String> getHeaders(final String paramString) {
				throw new UnsupportedOperationException("Not implemented");
			}

			@Override
			public Collection<String> getHeaderNames() {
				throw new UnsupportedOperationException("Not implemented");
			}
		};
	}
}
