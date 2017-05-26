package com.otchi.infrastructure.eventBus;

import com.google.common.eventbus.EventBus;
import com.otchi.domain.notifications.events.DomainEvents;
import com.otchi.domain.notifications.events.Event;

public class GuavaDomainEventsBus implements DomainEvents {

    private EventBus eventBus;

    public GuavaDomainEventsBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void raise(Event event) {
        eventBus.post(event);
    }
}
