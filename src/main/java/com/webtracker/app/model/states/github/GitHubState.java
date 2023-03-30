package com.webtracker.app.model.states.github;

import com.webtracker.app.model.observers.ObserverManager;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Representation of GitHub of given user
 */
@Getter
@ToString
public class GitHubState {
    private int gitHubStateId;
    private GitHubOwner owner;
    private List<GitHubRepository> repositories;
    // holds observers
    private ObserverManager<GitHubState> observerManager;

    public void setRepositories(List<GitHubRepository> newRepositories) {
        this.repositories = newRepositories;
        this.observerManager.notify(ObserverManager.EventType.ADDITION, this);
    }
}
