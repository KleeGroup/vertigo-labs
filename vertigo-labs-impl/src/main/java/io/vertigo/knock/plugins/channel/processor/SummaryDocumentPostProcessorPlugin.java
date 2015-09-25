package io.vertigo.knock.plugins.channel.processor;

import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.impl.channel.DocumentPostProcessorPlugin;
import io.vertigo.knock.metadata.MetaDataContainer;
import io.vertigo.knock.metadata.MetaDataContainerBuilder;

import java.util.regex.Pattern;

public final class SummaryDocumentPostProcessorPlugin implements DocumentPostProcessorPlugin {
	private static final int MAX_LINES = 4;
	private static final int MAX_LINE_LENGTH = 100;
	private static final int MAX_SUMMARY_LENGTH = MAX_LINES * MAX_LINE_LENGTH;
	private static final boolean COLLAPSE_WHITE_SPACES = true;
	private static final boolean REMOVE_EMPTY_LINES = true;

	//Le pattern de collusion des espaces :
	//[\\x00-\\x09\\x0B-\\x20\\x7F] : caract�res de contr�le sauf le \n (ie : \x0A)
	//{2,} : au moins deux caract�res
	private static final String SPACES_BUT_LF_REGEXP = "[\\x00-\\x09\\x0B-\\x20\\x7F]";
	private static final String SPACES_COLLAPSE_REGEXP = SPACES_BUT_LF_REGEXP + "{2,}";
	private static final String REMOVE_LINES_REGEXP = "(\\n" + SPACES_BUT_LF_REGEXP + "?){2,}";
	private static final String TRUNCATE_LINES_REGEXP = "(\\n[^\\n]{" + (MAX_LINE_LENGTH - 3) + "})([^\\n]+)\\n";

	private static final Pattern SPACES_COLLAPSE_PATTERN = Pattern.compile(SPACES_COLLAPSE_REGEXP);
	private static final Pattern LINE_CHAR_PATTERN = Pattern.compile("\\r\\n");
	private static final Pattern REMOVE_LINES_PATTERN = Pattern.compile(REMOVE_LINES_REGEXP);
	private static final Pattern TRUNCATE_LINES_PATTERN = Pattern.compile(TRUNCATE_LINES_REGEXP);

	/** {@inheritDoc} */
	@Override
	public MetaDataContainer extract(final Document document) {
		//-----
		final String summary = extractSummary(document.getContent());

		return new MetaDataContainerBuilder()//
				.withMetaData(DemoDocumentMetaData.SUMMARY, summary)//
				.build();
	}

	private static String extractSummary(final String content) {
		//MAX_LINE premieres lignes trim�s, MAX_SUMMARY_LENGTH caract�res max
		final int contentLength = content.length();
		final String subContent = content.substring(0, Math.min(contentLength, MAX_SUMMARY_LENGTH * 3));
		String summary;
		summary = collapse(subContent);
		summary = truncateNbLines(summary);
		summary = truncateLength(summary);
		return summary.toString();
	}

	private static String collapse(final String fullSummary) {
		String summary = fullSummary;
		if (COLLAPSE_WHITE_SPACES) {
			summary = SPACES_COLLAPSE_PATTERN.matcher(summary).replaceAll(" ");
		}
		if (REMOVE_EMPTY_LINES) {
			//Certains docs utilisent \r seul comme saut de ligne. On les remplacent par \n si ils sont seul.
			summary = LINE_CHAR_PATTERN.matcher(summary).replaceAll("\n");
			summary = summary.replace('\r', '\n');
			summary = REMOVE_LINES_PATTERN.matcher(summary).replaceAll("\n");
		}
		return summary;
	}

	private static String truncateNbLines(final String fullSummary) {
		int lineCount = 0;
		int indexLine = 0;
		//On boucle tant qu'on a pas le nombre de libre, que l'on trouve un \n et que l'on n'est pas � la fin
		while (lineCount < MAX_LINES && indexLine > -1 && indexLine < fullSummary.length()) {
			lineCount++;
			indexLine = fullSummary.indexOf('\n', indexLine + 1);
		}
		if (lineCount >= MAX_LINES && indexLine > -1) {
			return fullSummary.substring(0, indexLine);
		}
		return fullSummary;
	}

	private static String truncateLength(final String fullSummary) {
		String summary;
		summary = TRUNCATE_LINES_PATTERN.matcher(fullSummary).replaceAll("$1...\n");
		final StringBuilder sb = new StringBuilder(summary);
		if (sb.length() > MAX_SUMMARY_LENGTH) {
			sb.setLength(MAX_SUMMARY_LENGTH);
			sb.append("...");
		}
		return sb.toString();
	}
}
