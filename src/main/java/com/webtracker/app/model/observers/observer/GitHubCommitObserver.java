package com.webtracker.app.model.observers.observer;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.states.github.GitHubState;

import java.util.ArrayList;
import java.util.List;

public class GitHubCommitObserver extends Observer<GitHubState> {
    @Override
    protected List<Event> detectEvents(GitHubState newState) {
        // check what has changed

        // create events based on the changes
        List<Event> whatHappened = new ArrayList<>();

        // add events to the list
        collectedEvents.addAll(whatHappened);
        return null;
    }
}
