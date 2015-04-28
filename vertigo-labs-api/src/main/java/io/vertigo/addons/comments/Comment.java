package io.vertigo.addons.comments;

import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

public final class Comment {
	private final URI<VUserProfile> author;
	private final String msg;

	//	private final Date creationDate;

	public Comment(final URI<VUserProfile> author, final String msg) {
		Assertion.checkNotNull(author);
		Assertion.checkArgNotEmpty(msg);
		//-----
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
}
