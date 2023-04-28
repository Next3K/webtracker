package com.webtracker.app.model.observers.manager;

import com.webtracker.app.model.observers.observer.GitHubCommitObserver;
import com.webtracker.app.model.observers.observer.GitHubRepoObserver;
import com.webtracker.app.model.observers.observer.Observer;
import com.webtracker.app.model.states.github.CodingLanguage;
import com.webtracker.app.model.states.github.GitHubState;


import java.util.*;

public class GitHubObserverManager extends ObserverManager<GitHubState, GitHubObserverManager.GitHubConsideredActions> {

    public enum GitHubConsideredActions {
        REPOSITORY_CHANGE, COMMIT_CHANGE
    }

    // what actions to consider
    private static final Set<GitHubConsideredActions> setOfActions = Set.of(
            GitHubConsideredActions.REPOSITORY_CHANGE,
            GitHubConsideredActions.COMMIT_CHANGE);

    //define interesting languages to observe repositories with that language
    private static final Set<CodingLanguage> codingLanguages = new HashSet<>(Collections.singleton(CodingLanguage.JAVA));

    // what observers for each action
    private static final Map<GitHubConsideredActions, List<Observer<GitHubState>>> map =
            Map.of(GitHubConsideredActions.REPOSITORY_CHANGE, List.of(new GitHubRepoObserver(codingLanguages)),
                    GitHubConsideredActions.COMMIT_CHANGE, List.of(new GitHubCommitObserver(codingLanguages)));


    public GitHubObserverManager() {
        super(map, setOfActions);
    }

}
