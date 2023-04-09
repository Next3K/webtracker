package com.webtracker.app.model.states.github;

import com.webtracker.app.model.observers.manager.GitHubObserverManager;
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
    private String observatorEmail;
    private GitHubOwner owner;
    private List<GitHubRepository> repositories;
    private String gitHubAccountDescription;

    // holds observers
    private GitHubObserverManager observerManager = new GitHubObserverManager();

    public void setRepositories(List<GitHubRepository> newRepositories) {
        this.repositories = newRepositories;
        this.observerManager.notify(GitHubObserverManager.GitHubConsideredActions.REPOSITORY_CHANGE, this);
    }

    public void setGitHubAccountDescription(String newDescription) {
        this.gitHubAccountDescription = newDescription;
        this.observerManager.notify(GitHubObserverManager.GitHubConsideredActions.DESCRIPTION_CHANGE, this);
    }
}
