package com.webtracker.app.model.observers.manager;

import com.webtracker.app.model.observers.observer.GitHubCommitObserver;
import com.webtracker.app.model.observers.observer.GitHubRepoObserver;
import com.webtracker.app.model.observers.observer.Observer;
import com.webtracker.app.model.states.github.GitHubState;


import java.util.List;
import java.util.Map;
import java.util.Set;

public class GitHubObserverManager extends ObserverManager<GitHubState, GitHubObserverManager.GitHubConsideredActions> {

    public enum GitHubConsideredActions {
        REPOSITORY_CHANGE, COMMIT_CHANGE, DESCRIPTION_CHANGE
    }

    // what actions to consider
    private static final Set<GitHubConsideredActions> setOfActions = Set.of(
            GitHubConsideredActions.REPOSITORY_CHANGE,
            GitHubConsideredActions.COMMIT_CHANGE,
            GitHubConsideredActions.DESCRIPTION_CHANGE);

    // what observers for each action
    private static final Map<GitHubConsideredActions, List<Observer<GitHubState>>> map =
            Map.of(GitHubConsideredActions.REPOSITORY_CHANGE, List.of(new GitHubRepoObserver()),
                    GitHubConsideredActions.COMMIT_CHANGE, List.of(new GitHubCommitObserver()));


    public GitHubObserverManager() {
        super(map, setOfActions);
    }

}
