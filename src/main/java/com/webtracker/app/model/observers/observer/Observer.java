package com.webtracker.app.model.observers.observer;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.states.github.CodingLanguage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "observer_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Observer<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    protected T oldState;

    protected Set<CodingLanguage> interestingLanguages = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    protected List<Event> collectedEvents = new ArrayList<>();

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
