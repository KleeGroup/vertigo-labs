package io.vertigo.addons.events;

import io.vertigo.lang.Component;

public interface EventsManager extends Component {
	void emit(String channel, Event event);

	void register(String channel, EventListener eventListener);
}
