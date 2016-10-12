package com.otchi.domain.events;

public interface DomainEvents {

    void raise(Event event);
}
