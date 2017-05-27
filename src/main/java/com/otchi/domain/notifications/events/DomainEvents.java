package com.otchi.domain.notifications.events;

public interface DomainEvents {

    void raise(Event event);
}
