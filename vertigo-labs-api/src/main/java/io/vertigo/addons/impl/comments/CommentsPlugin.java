package io.vertigo.addons.impl.comments;

import io.vertigo.addons.comments.Comment;
import io.vertigo.dynamo.domain.model.KeyConcept;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Plugin;

import java.util.List;

/**
 * @author pchretien
 */
public interface CommentsPlugin extends Plugin {
	void emit(CommentEvent commentEvent);

	<S extends KeyConcept> List<Comment> getComments(URI<S> subjectURI);
}
