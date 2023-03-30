package com.webtracker.app.model.observers.manager;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.observers.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObserverManager<T, E> {
    Map<E, List<Observer<T>>> observersByType;
    Set<E> enumValues;

    public ObserverManager(Map<E, List<Observer<T>>> observersByType, Set<E> enumValues) {
        this.observersByType = observersByType;
        this.enumValues = enumValues;
    }

    public void subscribe(E eventType, Observer<T> observer) {
        List<Observer<T>> observerList = this.observersByType.get(eventType);
        observerList.add(observer);
    }

    public void unsubscribe(E eventType, Observer<T> observer) {
        List<Observer<T>> observerList = this.observersByType.get(eventType);
        observerList.remove(observer);
    }

    public void notify(E eventType, T newState) {
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
