package io.vertigo.knock.impl.channel.datapipe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class de statitique des documents.
 * @author npiedeloup
 * @version $Id: Stats.java,v 1.1 2011/07/19 15:00:32 npiedeloup Exp $
 */
final class Stats implements Serializable {
	private static final long serialVersionUID = 2814338537101583940L;
	private static final int COUNT_INDEX = 0;
	private static final int SIZE_INDEX = 1;
	private static final int CONTENT_SIZE_INDEX = 2;
	private static final int PROCESS_TIME_INDEX = 3;

	private final String channel;

	private long countTotal = 0;
	private long sizeTotal = 0;
	private long contentTotal = 0;
	private long processTimeTotal = 0;
	private final Map<String, Long[]> statsPerType = new HashMap<>();

	//private final Map<String, Long> sizePerType = new HashMap<String, Long>();
	//private final Map<String, Long> contentSizePerType = new HashMap<String, Long>();

	/**
	 * Constructeur.
	 * @param channel Nom du channel
	 */
	Stats(final String channel) {
		this.channel = channel;
	}

	/**
	 * Ajoute les stats du document.
	 * @param type Type de doc
	 * @param processTime Temps de traitement
	 * @param size Taille du doc
	 * @param contentSize Taille du contenu extrait
	 */
	void touch(final String type, final long processTime, final long size, final long contentSize) {
		Long[] stats = statsPerType.get(type);
		if (stats == null) {
			stats = new Long[] { 0L, 0L, 0L, 0L };
			statsPerType.put(type, stats);
		}
		stats[COUNT_INDEX]++;
		stats[SIZE_INDEX] += size;
		stats[CONTENT_SIZE_INDEX] += contentSize;
		stats[PROCESS_TIME_INDEX] += processTime;
		countTotal++;
		sizeTotal += size;
		contentTotal += contentSize;
		processTimeTotal += processTime;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(channel).append(" >>> ");
		sb.append("TOTAL >>> doc:").append(countTotal).append(" ");
		sb.append("size:").append(sizeTotal / 1024).append("Ko ");
		sb.append("contentSize:").append(contentTotal / 1024).append("Ko ");
		sb.append("processTime:").append(processTimeTotal / 1000).append("s ");
		sb.append("\n");
		for (final String type : statsPerType.keySet()) {
			final Long[] stats = statsPerType.get(type);
			sb.append(channel).append(" >>> ");
			final long count = stats[COUNT_INDEX];
			final long size = stats[SIZE_INDEX];
			final long contentSize = stats[CONTENT_SIZE_INDEX];
			final long processTime = stats[PROCESS_TIME_INDEX];
			sb.append(type).append(" count:").append(count).append("(").append(count * 100 / countTotal).append("%)  ");
			if (processTime > 0) {
				sb.append(" processTime:").append(processTime / 1000).append("s (").append(processTime * 100 / processTimeTotal).append("%)  moyenne:").append(processTime / count).append("ms  ");
			} else {
				sb.append(" NO_PROCESS_TIME  ");
			}
			if (size > 0) {
				sb.append(" size:").append(size / 1024).append("Ko (").append(size * 100 / sizeTotal).append("%)  ");
			} else {
				sb.append(" NO_SIZE  ");
			}
			if (contentSize > 0) {
				sb.append(" contentSize:").append(contentSize / 1024).append("Ko (").append(contentSize * 100 / size).append("% du doc)  ");
			} else {
				sb.append(" NO_CONTENT  ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}
}
