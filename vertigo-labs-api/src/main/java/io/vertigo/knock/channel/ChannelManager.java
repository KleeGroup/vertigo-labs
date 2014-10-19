package io.vertigo.knock.channel;

import io.vertigo.knock.channel.metadefinition.ChannelDefinition;
import io.vertigo.knock.processors.DocumentPostProcessor;
import io.vertigo.lang.Manager;

import java.util.List;

/**
 * Manager de Channel.
 * @author npiedeloup
 * @version $Id: ChannelManager.java,v 1.15 2014/02/17 17:55:57 npiedeloup Exp $
 */
public interface ChannelManager extends Manager {

	List<ChannelDefinition> getChannelDefinitions();

	/**
	 * @param channelDefinition Channel
	 * * @return L'�tat courant du Channel
	 */
	String getChannelState(ChannelDefinition channelDefinition);

	/**
	 * Index les documents d�j� crawl�s.
	 * @param channelDefinition Channel � traiter
	 */
	void indexChannel(ChannelDefinition channelDefinition);

	/**
	 * Crawl les documents.
	 * @param channelDefinition Channel � traiter
	 */
	void crawlChannel(ChannelDefinition channelDefinition);

	/**
	 * Crawl, process puis index les documents.
	 * @param channelDefinition Channel � traiter
	 */
	void crawlAndIndexChannel(ChannelDefinition channelDefinition);

	/**
	 * Lance les traitements d'enrichissement automatique des m�tadonn�es sur les documents d�j� crawl�s.
	 * @param channelDefinition Channel � traiter
	 */
	void processChannel(ChannelDefinition channelDefinition);

	/**
	 * @param documentConverterId Identifiant du plugin
	 * @return Convertisseur de Document en �l�ment de l'index
	 */
	DocumentConverter getDocumentConverter(final String documentConverterId);

	/**
	 * @return Liste des postProcessor a appliquer
	 */
	List<DocumentPostProcessor> getDocumentPostProcessors();

	/**
	 * @return Si un traitement est en cours
	 */
	boolean isRunning();
}
