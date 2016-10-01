package com.otchi.infrastructure.eventBus;

import com.otchi.domain.events.DomainEvents;
import com.otchi.domain.events.Event;
import com.otchi.domain.events.EventsChannels;
import reactor.bus.EventBus;

public class ReactorDomainEventsBus implements DomainEvents {

    private EventBus eventBus;

    public ReactorDomainEventsBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void raise(EventsChannels topic, Event event) {
        this.eventBus.notify(topic, reactor.bus.Event.wrap(event));
    }
}
