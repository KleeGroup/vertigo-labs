package io.vertigo.addons;

import io.vertigo.dynamo.domain.model.DtSubject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;

/**
 * @author pchretien
 */
@DtDefinition
public final class Movie implements DtSubject {
	private static final long serialVersionUID = -3629724099843015770L;

	@Field(domain = "DO_ID", type = "PRIMARY_KEY", notNull = true, label = "id")
	private String id;

	@Field(domain = "DO_NAME", label = "title")
	private String title;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
