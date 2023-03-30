package com.webtracker.app.model.observers;

import com.webtracker.app.model.events.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class Observer<T> {
    T oldState;
    List<Event> collectedEvents;


    abstract void update(T newState);

    List<Event> popCollectedEvents() {
        ArrayList<Event> tmp = new ArrayList<>(collectedEvents);
        collectedEvents.clear();
        return tmp;
    }
}
