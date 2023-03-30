package com.webtracker.app.model.observers;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.states.github.GitHubRepository;
import com.webtracker.app.model.states.github.GitHubState;

import java.util.ArrayList;
import java.util.List;

/**
 * Observes changes in repositories, saves events to collectedEvents field
 */
public class GitHubRepoObserver extends Observer<GitHubState>{
    @Override
    void update(GitHubState newState) {
        // check what has changed
        List<GitHubRepository> newRepositories = newState.getRepositories();
        List<GitHubRepository> oldRepositories = this.oldState.getRepositories();

        // create events based on the changes
        List<Event> whatHappened = new ArrayList<>();

        // add events to the list
        collectedEvents.addAll(whatHappened);

        // update old state
        this.oldState = newState;
    }
}
