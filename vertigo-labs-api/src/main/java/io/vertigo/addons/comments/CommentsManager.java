package io.vertigo.addons.comments;

import io.vertigo.dynamo.domain.model.DtSubject;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

import java.util.List;

public interface CommentsManager extends Component {
	void publish(Comment comment, URI<? extends DtSubject> subjectURI);

	//	<S extends DtSubject> void publishResponse(Comment comment, URI<S> subjectURI, UUID uuid);

	List<Comment> getComments(URI<? extends DtSubject> subjectURI);
}
