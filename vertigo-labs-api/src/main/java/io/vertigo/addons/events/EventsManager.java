package io.vertigo.addons.events;

import io.vertigo.lang.Component;

public interface EventsManager extends Component {
	void emit(Event event);

	void register(EventListener eventListener);
}
