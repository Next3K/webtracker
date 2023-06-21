package com.webtracker.observers;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.observers.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {

    private Observer observer;

    @BeforeEach
    void setUp() {
        observer = new Observer() {
            @Override
            protected List<Event> detectEvents(Object newState) {
                return null;
            }
        };
    }

    @Test
    void popCollectedEvents() {
        String EMAIL_TO_SEND_EVENT = "example@gmail.com";
        String GITHUB_USERNAME = "exampleUsername";
        String EVENT_TITLE = "Example title";
        String EVENT_DESC = "Example description";
        String LINK = "https://github.com/exampleUsername/repoName/issues/1";

        Event event1 = new Event(EMAIL_TO_SEND_EVENT, GITHUB_USERNAME, EVENT_TITLE, EVENT_DESC, LINK);
        Event event2 = new Event(EMAIL_TO_SEND_EVENT, GITHUB_USERNAME, EVENT_TITLE, EVENT_DESC, LINK);

        observer.getCollectedEvents().add(event1);
        observer.getCollectedEvents().add(event2);

        assertEquals(2, observer.getCollectedEvents().size());

        List<?> poppedEvents = observer.popCollectedEvents();

        assertEquals(2, poppedEvents.size());
        assertEquals(0, observer.getCollectedEvents().size());
    }
}
