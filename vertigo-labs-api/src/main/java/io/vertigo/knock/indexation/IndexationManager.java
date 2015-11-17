package io.vertigo.knock.indexation;

import io.vertigo.knock.channel.ChannelDefinition;
import io.vertigo.knock.document.model.Document;
import io.vertigo.lang.Component;

/**
 * Created by sbernard on 28/05/2015.
 */
public interface IndexationManager extends Component {
	void pushDocument(final Document document);

	void dropIndex(final ChannelDefinition channelDefinition);
}
