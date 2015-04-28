package io.vertigo.addons.comments;

import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;

public final class CommentBuilder implements Builder<Comment> {
	private String myMsg;
	private URI<VUserProfile> myAuthor;

	public CommentBuilder withAuthor(final URI<VUserProfile> author) {
		Assertion.checkArgument(myAuthor == null, "author already set");
		Assertion.checkNotNull(author);
		//-----
		this.myAuthor = author;
		return this;
	}

	public CommentBuilder withMsg(final String msg) {
		Assertion.checkArgument(myMsg == null, "msg already set");
		Assertion.checkArgNotEmpty(msg);
		//-----
		this.myMsg = msg;
		return this;
	}

	@Override
	public Comment build() {
		return new Comment(myAuthor, myMsg);
	}
}
