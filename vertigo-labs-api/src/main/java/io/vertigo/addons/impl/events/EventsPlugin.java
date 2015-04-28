package io.vertigo.addons.impl.events;

import io.vertigo.addons.events.Event;
import io.vertigo.addons.events.EventListener;

public interface EventsPlugin {
	void emit(Event event);

	void register(EventListener eventListener);
}
