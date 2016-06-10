package allocine;

import io.vertigo.lang.JsonExclude;
import io.vertigo.lang.Option;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author pchretien, npiedeloup
 */
public final class GoogleJsonEngine {
	private final Gson gson = createGson();

	public String toJson(final Object data) {
		return gson.toJson(data);
	}

	public <D> D fromJson(final String json, final Class<D> clazz) {
		return gson.fromJson(json, clazz);
	}

	private static final class JsonExclusionStrategy implements ExclusionStrategy {
		/** {@inheritDoc} */
		@Override
		public boolean shouldSkipField(final FieldAttributes arg0) {
			if (arg0.getAnnotation(JsonExclude.class) != null) {
				return true;
			}
			return false;
		}

		@Override
		public boolean shouldSkipClass(final Class<?> arg0) {
			return false;
		}
	}

	private static final class ClassJsonSerializer implements JsonSerializer<Class> {
		/** {@inheritDoc} */
		@Override
		public JsonElement serialize(final Class src, final Type typeOfSrc, final JsonSerializationContext context) {
			return new JsonPrimitive(src.getName());
		}
	}

	private static final class OptionJsonSerializer implements JsonSerializer<Option> {
		/** {@inheritDoc} */
		@Override
		public JsonElement serialize(final Option src, final Type typeOfSrc, final JsonSerializationContext context) {
			if (src.isDefined()) {
				return context.serialize(src.get());
			}
			return null; //rien
		}
	}

	private static final class MapJsonSerializer implements JsonSerializer<Map> {
		/** {@inheritDoc} */
		@Override
		public JsonElement serialize(final Map src, final Type typeOfSrc, final JsonSerializationContext context) {
			if (src.isEmpty()) {
				return null;
			}
			return context.serialize(src);
		}
	}

	private static final class ListJsonSerializer implements JsonSerializer<List> {

		/** {@inheritDoc} */
		@Override
		public JsonElement serialize(final List src, final Type typeOfSrc, final JsonSerializationContext context) {
			if (src.isEmpty()) {
				return null;
			}
			return context.serialize(src);
		}
	}

	private static Gson createGson() {
		return new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				.setPrettyPrinting()
				.registerTypeAdapter(List.class, new ListJsonSerializer())
				.registerTypeAdapter(Map.class, new MapJsonSerializer())
				.registerTypeAdapter(Option.class, new OptionJsonSerializer())
				.registerTypeAdapter(Class.class, new ClassJsonSerializer())
				.addSerializationExclusionStrategy(new JsonExclusionStrategy())
				.create();
	}
}
