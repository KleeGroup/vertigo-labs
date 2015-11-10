package io.vertigo.knock.impl.enhancement;

import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.lang.Plugin;

public interface EnhancementPlugin extends Plugin {
	MetaDataContainer extract(final Document document) throws Exception;
}
