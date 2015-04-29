package io.vertigo.addons.impl.events;

import io.vertigo.addons.events.Event;
import io.vertigo.addons.events.EventListener;
import io.vertigo.lang.Plugin;

public interface EventsPlugin extends Plugin {
	void emit(String channel, Event event);

	void register(String channel, EventListener eventListener);
}
