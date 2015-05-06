package io.vertigo.addons.comments;

import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

import java.util.UUID;

/**
 * @author pchretien
 */
public final class Comment {
	private final UUID uuid;
	private final URI<VUserProfile> author;
	private final String msg;

	//	private final Date creationDate;

	Comment(final UUID uuid, final URI<VUserProfile> author, final String msg) {
		Assertion.checkNotNull(uuid);
		Assertion.checkNotNull(author);
		Assertion.checkArgNotEmpty(msg);
		//-----
		this.uuid = uuid;
		this.author = author;
		this.msg = msg;
		//	this.creationDate = DateUtil.newDateTime();
	}

	public URI<VUserProfile> getAuthor() {
		return author;
	}

	//	public Date getCreationDate() {
	//		return creationDate;
	//	}

	public String getMsg() {
		return msg;
	}

	public UUID getUuid() {
		return uuid;
	}
}