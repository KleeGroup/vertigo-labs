package io.vertigo.knock.channel.metadefinition;

import io.vertigo.core.spaces.definiton.Definition;
import io.vertigo.core.spaces.definiton.DefinitionPrefix;
import io.vertigo.knock.crawler.Crawler;
import io.vertigo.knock.document.DocumentStore;
import io.vertigo.knock.processors.DocumentPostProcessor;
import io.vertigo.lang.Assertion;

import java.util.ArrayList;
import java.util.List;

/**
 * D�finition d'un Channel de recherche.
 *
 * @author npiedeloup
 * @version $Id: ChannelDefinition.java,v 1.13 2012/06/20 13:54:33 pchretien Exp $
 */
@DefinitionPrefix("CHN")
public final class ChannelDefinition implements Definition {
	/** Nom de la d�finition. */
	private final String name;
	private final String label;
	private final Crawler crawler;
	private final DocumentStore documentStore;
	private final List<DocumentPostProcessor> documentPostProcessors;

	//-----
	//	private final DocumentConverter documentConverter;
	//	private final IndexDefinition indexDefinition;
	//	private final FacetedQueryDefinition facetQueryDefinition;

	/**
	 * Constructeur.
	 * @param name Nom du channel
	 * @param label Nom du channel
	 * @param crawler Identifiant du Crawler
	 * param documentConverter Convertisseur de Document en Index
	 * @param documentStore Identifiant du Store de Document
	 * param indexDefinition IndexDefinition
	 * param facetQueryDefinition Nom de la d�finition des facettes
	 */
	public ChannelDefinition(final String name, final String label, final Crawler crawler, final List<DocumentPostProcessor> documentPostProcessors, final DocumentStore documentStore /*final DocumentConverter documentConverter, final IndexDefinition indexDefinition, final FacetedQueryDefinition facetQueryDefinition*/) {
		Assertion.checkArgNotEmpty(name);
		Assertion.checkArgNotEmpty(label);
		Assertion.checkNotNull(crawler);
		Assertion.checkNotNull(documentPostProcessors);
		Assertion.checkNotNull(documentStore);
		//		Assertion.checkNotNull(documentConverter);
		//		Assertion.checkNotNull(indexDefinition);
		//		Assertion.checkNotNull(facetQueryDefinition);
		//-----
		this.name = name;
		this.label = label;
		this.crawler = crawler;
		this.documentPostProcessors = new ArrayList<>(documentPostProcessors);
		this.documentStore = documentStore;
		//		this.documentConverter = documentConverter;
		//		this.indexDefinition = indexDefinition;
		//		this.facetQueryDefinition = facetQueryDefinition;
	}

	/**
	 * @return Nom du channel.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return Crawler.
	 */
	public Crawler getCrawler() {
		return crawler;
	}

	//	/**
	//	 * @return Convertisseur de Document en Index.
	//	 */
	//	public DocumentConverter getDocumentConverter() {
	//		return documentConverter;
	//	}
	//
	//	/**
	//	 * @return Nom de l'indexDefinition.
	//	 */
	//	public IndexDefinition getIndexDefinition() {
	//		return indexDefinition;
	//	}
	//
	//	/**
	//	 * @return D�finition de facette.
	//	 */
	//	public FacetedQueryDefinition getFacetedQueryDefinition() {
	//		return facetQueryDefinition;
	//	}

	/**
	 * @return Liste Post-processors de document.
	 */
	public List<DocumentPostProcessor> getDocumentPostProcessors() {
		return documentPostProcessors;
	}

	/**
	 * @return Identifiant du Store de Document.
	 */
	public DocumentStore getDocumentStore() {
		return documentStore;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return name;
	}
}
