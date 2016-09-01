package com.otchi.domain.events;


public interface DomainEvents {

    void raise(EventsChannels topic, Event event);
}
