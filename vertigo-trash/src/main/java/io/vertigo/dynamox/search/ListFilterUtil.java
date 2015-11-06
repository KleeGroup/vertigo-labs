package io.vertigo.dynamox.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListFilterUtil {

	private final static String WORD_RESERVERD_PATTERN = "^\\s\\p{Punct}";
	private final static String PREFIX_RESERVERD_PATTERN = "\\+\\-\\!\\*\\?\\~\\^\\=\\>\\<";
	private final static String SUFFIX_RESERVERD_PATTERN = "\\+\\-\\!\\*\\?\\~\\^\\=\\>\\<";
	private final static String CRITERIA_VALUE_WORD_PATTERN_STRING = "[" + PREFIX_RESERVERD_PATTERN + "]*?([" + WORD_RESERVERD_PATTERN + "]+)[" + SUFFIX_RESERVERD_PATTERN + "]*";
	private final static Pattern CRITERIA_VALUE_PATTERN = Pattern.compile(CRITERIA_VALUE_WORD_PATTERN_STRING);

	public String splitText(final String preField, final String fieldName, final String preWord, final String text, final String postWord, final String postField) {
		if (!text.isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			appendIfNotNull(sb, preField);
			sb.append(fieldName);
			sb.append(":(");
			splitText(preWord, text, postWord, sb);
			sb.append(")");
			appendIfNotNull(sb, postField);
		}
		return "";
	}

	public String splitText(final String preWord, final String text, final String postWord) {
		if (!text.isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			splitText(preWord, text, postWord, sb);
			return sb.toString();
		}
		return "";
	}

	private void splitText(final String preWord, final String text, final String postWord, final StringBuilder sb) {
		final Matcher criteriaValueMatcher = CRITERIA_VALUE_PATTERN.matcher(text);
		while (criteriaValueMatcher.find()) {
			appendIfNotNull(sb, preWord);
			sb.append(criteriaValueMatcher.group(1));
			appendIfNotNull(sb, postWord);
			sb.append(" ");
		}
	}

	private StringBuilder appendIfNotNull(final StringBuilder query, final String str) {
		if (str != null) {
			query.append(str);
		}
		return query;
	}

}
