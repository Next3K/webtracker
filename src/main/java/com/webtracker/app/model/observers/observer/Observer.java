package com.webtracker.app.model.observers.observer;

import com.webtracker.app.model.events.Event;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Observer<T> {
    protected T oldState;
    protected List<Event> collectedEvents = new ArrayList<>();

    public Observer(T oldState) {
        this.oldState = oldState;
    }

    protected abstract List<Event> detectEvents(T newState);

    public void update(T newState) {
        // check differences, collect events
        collectedEvents.addAll(detectEvents(newState));
        // update the state
        this.oldState = newState;
    }

    public List<Event> popCollectedEvents() {
        ArrayList<Event> tmp = new ArrayList<>(collectedEvents);
        collectedEvents.clear();
        return tmp;
    }
}
