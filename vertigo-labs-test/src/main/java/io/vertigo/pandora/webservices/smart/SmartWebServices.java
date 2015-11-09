package io.vertigo.pandora.webservices.smart;

import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.pandora.domain.movies.Movie;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

@PathPrefix("/smart")
public class SmartWebServices implements WebServices {

	@GET("/dtDefinition")
	@AnonymousAccessAllowed
	public SmartDataDefinition getDtDefinition() {
		return new SmartDataDefinition(DtObjectUtil.findDtDefinition(Movie.class));
	}

}
