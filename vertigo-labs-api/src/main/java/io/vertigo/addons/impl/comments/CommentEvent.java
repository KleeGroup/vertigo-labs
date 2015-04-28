package io.vertigo.addons.impl.comments;

import io.vertigo.addons.comments.Comment;
import io.vertigo.dynamo.domain.model.DtSubject;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

public final class CommentEvent<S extends DtSubject> {
	private final Comment comment;
	public final URI<S> subjectURI;

	CommentEvent(final Comment comment, final URI<S> subjectURI) {
		Assertion.checkNotNull(comment);
		Assertion.checkNotNull(subjectURI);
		//-----
		this.comment = comment;
		this.subjectURI = subjectURI;
	}

	public Comment getComment() {
		return comment;
	}

	public URI<S> getSubjectURI() {
		return subjectURI;
	}
}
