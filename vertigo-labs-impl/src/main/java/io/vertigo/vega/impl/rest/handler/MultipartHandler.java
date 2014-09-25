package io.vertigo.vega.impl.rest.handler;

import javax.jws.HandlerChain;

/**
 * Plugin d'upload de fichier, par la librairie org.apache.commons.upload.
 * 
 * @author npiedeloup
 * @version $Id: ApacheFileUploadPlugin.java,v 1.11 2013/06/25 10:57:08 pchretien Exp $
 */
public final class MultipartHandler implements RouteHandler {

	public Object handle(final Request request, final Response response, final RouteContext routeContext, final HandlerChain chain) throws SessionException, VSecurityException {
		if (ApacheMultipartHelper.isMultipart(request)) {
			final Request wrappedRequest = ApacheMultipartHelper.createRequestWrapper(request);
			return chain.handle(wrappedRequest, response, routeContext);
		}
		//TODO voir si l'on peut détecter l'oublie de contentType = "multipart/form-data" sur le form, c'est une erreur standard
		return chain.handle(request, response, routeContext);
	}
}
