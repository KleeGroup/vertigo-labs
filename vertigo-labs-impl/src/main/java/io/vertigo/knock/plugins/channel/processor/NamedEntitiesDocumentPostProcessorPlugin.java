package io.vertigo.knock.plugins.channel.processor;

import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.enrichment.EnrichmentManager;
import io.vertigo.knock.enrichment.NamedEntity;
import io.vertigo.knock.impl.channel.DocumentPostProcessorPlugin;
import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.knock.metadata.MetaDataContainerBuilder;
import io.vertigo.lang.Assertion;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by sbernard on 30/12/2014.
 */
public class NamedEntitiesDocumentPostProcessorPlugin implements DocumentPostProcessorPlugin {
    private EnrichmentManager enrichmentManager;

    @Inject
    public NamedEntitiesDocumentPostProcessorPlugin(final EnrichmentManager enrichmentManager) {
        Assertion.checkNotNull(enrichmentManager);
        this.enrichmentManager = enrichmentManager;
    }

    @Override
    public MetaDataContainer extract(Document document) {
        Assertion.checkNotNull(document);
        //-------------------------------------------------------------
        final Set<NamedEntity> namedEntities = enrichmentManager.enrich(document.getContent());

        // Concatenation des entités trouvées pour l'indexation
        StringBuilder stringBuilder = new StringBuilder();
        for (final NamedEntity namedEntity : namedEntities) {
            stringBuilder.append(namedEntity.getName());
            stringBuilder.append(", ");
        }
        // Suppression de la dernière virgule
        stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.length() - 1);
        return new MetaDataContainerBuilder()
                .withMetaData(NamedEntitiesMetaData.NAMED_ENTITIES, stringBuilder.toString())
                .build();
    }
}
