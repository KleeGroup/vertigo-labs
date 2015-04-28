package io.vertigo.addons.impl.comments;

import io.vertigo.addons.comments.Comment;
import io.vertigo.dynamo.domain.model.DtSubject;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Plugin;

import java.util.List;

public interface CommentsPlugin extends Plugin {
	void emit(CommentEvent commentEvent);

	<S extends DtSubject> List<Comment> getComments(URI<S> subjectURI);
}
