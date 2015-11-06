package io.vertigo.addons.events;

import io.vertigo.lang.Component;

/**
 * @author pchretien
 */
public interface EventsManager extends Component {
	void fire(String channel, Event event);

	void register(String channel, EventListener eventListener);
}
