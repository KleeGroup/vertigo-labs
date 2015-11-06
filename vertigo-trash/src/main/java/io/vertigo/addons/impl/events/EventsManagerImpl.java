package io.vertigo.addons.impl.events;

import io.vertigo.addons.events.Event;
import io.vertigo.addons.events.EventListener;
import io.vertigo.addons.events.EventsManager;
import io.vertigo.lang.Assertion;

import javax.inject.Inject;

/**
 * @author pchretien
 */
public final class EventsManagerImpl implements EventsManager {
	private final EventsPlugin eventsPlugin;

	@Inject
	public EventsManagerImpl(final EventsPlugin eventsPlugin) {
		Assertion.checkNotNull(eventsPlugin);
		//-----
		this.eventsPlugin = eventsPlugin;
	}

	@Override
	public void fire(final String channel, final Event event) {
		eventsPlugin.emit(channel, event);
	}

	@Override
	public void register(final String channel, final EventListener eventListener) {
		eventsPlugin.register(channel, eventListener);
	}

}
