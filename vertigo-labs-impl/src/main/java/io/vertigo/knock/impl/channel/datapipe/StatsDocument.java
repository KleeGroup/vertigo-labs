package io.vertigo.knock.impl.channel.datapipe;

import io.vertigo.knock.document.model.Document;
import io.vertigo.lang.Assertion;

import java.util.HashMap;
import java.util.Map;

/**
 * Object effectuant des stats par type de document.
 * @author npiedeloup
 * @version $Id: StatsDocument.java,v 1.3 2011/08/02 08:19:31 pchretien Exp $
 */
public final class StatsDocument {
	private final static Map<String, Stats> statsPerChannel = new HashMap<>();
	private final String channel;
	private long count = 0;

	/**
	 * Constructeur.
	 * @param channel Nom du channel
	 */
	public StatsDocument(final String channel) {
		Assertion.checkArgNotEmpty(channel);
		//---------------------------------------------------------------------
		this.channel = channel;
	}

	public void addStats(final Document document, final long processTime) {
		Stats stats = statsPerChannel.get(channel);
		if (stats == null) {
			stats = new Stats(channel);
			statsPerChannel.put(channel, stats);
		}
		final String content = document.getContent();
		stats.touch(document.getType(), processTime, document.getSize(), content != null ? content.length() : 0);

		if (count++ % 50 == 0) {
			for (final Stats curentStats : statsPerChannel.values()) {
				System.out.println(curentStats.toString());
			}
		}
	}
}
