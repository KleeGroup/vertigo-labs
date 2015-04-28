package io.vertigo.addons.impl.comments;

import io.vertigo.addons.comments.Comment;
import io.vertigo.addons.comments.CommentsManager;
import io.vertigo.dynamo.domain.model.DtSubject;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

import java.util.List;

import javax.inject.Inject;

public final class CommentsManagerImpl implements CommentsManager {
	private final CommentsPlugin commentsPlugin;

	@Inject
	public CommentsManagerImpl(final CommentsPlugin commentsPlugin) {
		Assertion.checkNotNull(commentsPlugin);
		//-----
		this.commentsPlugin = commentsPlugin;
	}

	@Override
	public <S extends DtSubject> void publish(final Comment comment, final URI<S> subjectURI) {
		final CommentEvent notificationEvent = new CommentEvent(comment, subjectURI);
		commentsPlugin.emit(notificationEvent);
	}

	@Override
	public <S extends DtSubject> List<Comment> getComments(final URI<S> subjectURI) {
		Assertion.checkNotNull(subjectURI);
		//-----
		return commentsPlugin.getComments(subjectURI);
	}
	//
	//	@Override
	//	public <S extends DtSubject> void publishResponse(final Comment comment, final UUID uuid) {
	//		final CommentEvent notificationEvent = new CommentEvent(comment, final UUID uuid subjectURI, );
	//
	//		Assertion.checkNotNull(comment);
	//		Assertion.checkNotNull(subjectURI);
	//		//-----
	//		final CommentEvent notificationEvent = new CommentEvent(comment, subjectURI);
	//		commentsPlugin.emit(notificationEvent);
	//
	//	}
}
