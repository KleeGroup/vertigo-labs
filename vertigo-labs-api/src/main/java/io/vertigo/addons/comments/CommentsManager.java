package io.vertigo.addons.comments;

import io.vertigo.dynamo.domain.model.DtSubject;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

import java.util.List;

public interface CommentsManager extends Component {
	<S extends DtSubject> void publish(Comment comment, URI<S> subjectURI);

	//	<S extends DtSubject> void publishResponse(Comment comment, URI<S> subjectURI, UUID uuid);

	<S extends DtSubject> List<Comment> getComments(URI<S> subjectURI);
}
