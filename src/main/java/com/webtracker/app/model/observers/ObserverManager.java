package com.webtracker.app.model.observers;

import com.webtracker.app.model.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObserverManager<T> {
    Map<EventType, List<Observer<T>>> observersByType;

    public enum EventType {
        ADDITION, REMOVAL, EDITION
    }

    public void subscribe(EventType eventType, Observer<T> observer) {
        List<Observer<T>> observerList = this.observersByType.get(eventType);
        observerList.add(observer);
    }

    public void unsubscribe(EventType eventType, Observer<T> observer) {
        List<Observer<T>> observerList = this.observersByType.get(eventType);
        observerList.remove(observer);
    }

    public void notify(EventType eventType, T newState) {
        var observerList = this.observersByType.get(eventType);
        observerList.forEach(observer -> observer.update(newState));
    }

    public List<Event> collectEvents() {
        List<Event> events = new ArrayList<>();
        for (var value : observersByType.values()) {
            for (var observer : value) {
                events.addAll(observer.popCollectedEvents());
            }
        }
        return events;
    }
}
