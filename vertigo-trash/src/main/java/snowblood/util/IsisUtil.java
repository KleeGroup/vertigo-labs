package snowblood.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Helper Isis.
 *
 * @author hsellami-ext
 */
public final class IsisUtil {

	/**
	 * Méthode pour déserialiser une chaine de caractère au format json.
	 * Ne supporte que si les paramètres sont des strings et uniquement les variables de premier niveau.
	 *
	 * @param jsonString la chaine de caractère au format json.
	 * @return une map représentant les données.
	 */
	public static Map<String, String> deserializeJson(final String jsonString) {
		final String jsonStringToParse = jsonString.replace("\\", "\\\\");
		final Map<String, String> map = new HashMap<>();
		final JsonParser parser = new JsonParser();
		final JsonElement element = parser.parse(jsonStringToParse);
		final Set<Entry<String, JsonElement>> children = element.getAsJsonObject().entrySet();
		for (final Entry<String, JsonElement> entry : children) {
			map.put(entry.getKey(), entry.getValue().getAsString());
		}
		return map;
	}

}
