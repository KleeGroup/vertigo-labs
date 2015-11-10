package io.vertigo.knock.enhancement;

import io.vertigo.knock.document.model.Document;
import io.vertigo.lang.Component;

/**
 * Created by sbernard on 28/05/2015.
 */
public interface EnhancementManager extends Component {
	Document enhanceDocument(final Document documentToEnhance) throws Exception;
}
