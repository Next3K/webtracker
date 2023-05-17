package com.webtracker.app.model.states.github;

import com.webtracker.app.model.observers.manager.GitHubObserverManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Representation of GitHub of given user
 */
@Getter
@ToString
public class GitHubState {
    private static int idCounter = 0;
    private int gitHubStateId;
    private String observatorEmail;
    private GitHubOwner owner;
    private List<GitHubRepository> repositories;
    private String gitHubAccountDescription;

    // holds observers
    private GitHubObserverManager observerManager = new GitHubObserverManager();

    public GitHubState() {
        gitHubStateId = idCounter++;
    }
    public void setRepositories(List<GitHubRepository> newRepositories) {
        this.repositories = newRepositories;
        this.observerManager.notify(GitHubObserverManager.GitHubConsideredActions.REPOSITORY_CHANGE, this);
    }
    public void setObservatorEmail(String observatorEmail) {
        this.observatorEmail = observatorEmail;
    }

    public void setOwner(GitHubOwner owner) {
        this.owner = owner;
    }

    public void setGitHubAccountDescription(String gitHubAccountDescription) {
        this.gitHubAccountDescription = gitHubAccountDescription;
    }
}
