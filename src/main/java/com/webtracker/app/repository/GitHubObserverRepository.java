package com.webtracker.app.repository;


import com.webtracker.app.model.observers.observer.GitHubRepoObserver;
import com.webtracker.app.model.states.github.CodingLanguage;
import com.webtracker.app.model.states.github.GitHubState;

import java.util.List;
import java.util.Set;

public class GitHubObserverRepository  {

    public static List<GitHubRepoObserver> getGitHubRepoObservers() {
        // TODO get observers from database
        Set<CodingLanguage> languages1 = Set.of(CodingLanguage.Clojure, CodingLanguage.Cpp);
        Set<CodingLanguage> languages2 = Set.of(CodingLanguage.Go);
        GitHubRepoObserver firstObserver = new GitHubRepoObserver(new GitHubState(), languages1);
        GitHubRepoObserver secondObserver = new GitHubRepoObserver(new GitHubState(), languages2);
        // TODO get observers from database
        return List.of(firstObserver, secondObserver);
    }
}
