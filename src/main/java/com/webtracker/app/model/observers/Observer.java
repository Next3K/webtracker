package com.webtracker.app.model.observers;

import com.webtracker.app.common.AbstractEntity;
import com.webtracker.app.model.Client;
import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.github.CodingLanguage;
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
public abstract class Observer<T> extends AbstractEntity {

    protected Observer(Set<CodingLanguage> interestingLanguages, T oldState, Client client) {
        this.interestingLanguages = interestingLanguages;
        this.oldState = oldState;
        this.client = client;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    @Enumerated(EnumType.STRING)
    protected Set<CodingLanguage> interestingLanguages = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "old_state_id")
    protected T oldState;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
            orphanRemoval = true)
    @JoinColumn(name = "observer_id")
    protected List<Event> collectedEvents = new ArrayList<>();

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    protected Client client; // for whom this observer "works"

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
