//package com.webtracker.app.model.observers.manager;
//
//import com.webtracker.app.model.observers.observer.GitHubCommitObserver;
//import com.webtracker.app.model.observers.observer.GitHubRepoObserver;
//import com.webtracker.app.model.observers.observer.Observer;
//import com.webtracker.app.model.states.github.CodingLanguage;
//import com.webtracker.app.model.states.github.GitHubState;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//
//import java.util.*;
//
//@Entity
//@Getter
//@Setter
//public class GitHubObserverManager extends ObserverManager<GitHubState, GitHubObserverManager.GitHubConsideredActions> {
//
//
//
//
//
//    public enum GitHubConsideredActions {
//        REPOSITORY_CHANGE, COMMIT_CHANGE
//    }
//
//    // what actions to consider
//    private static final Set<GitHubConsideredActions> setOfActions = Set.of(
//            GitHubConsideredActions.REPOSITORY_CHANGE,
//            GitHubConsideredActions.COMMIT_CHANGE);
//
//    //define interesting languages to observe repositories with that language
//    private static final Set<CodingLanguage> codingLanguages = new HashSet<>(Collections.singleton(CodingLanguage.Java));
//
//    // what observers for each action
//    private static final Map<GitHubConsideredActions, List<Observer<GitHubState>>> map =
//            Map.of(GitHubConsideredActions.REPOSITORY_CHANGE, List.of(new GitHubRepoObserver(codingLanguages)),
//                    GitHubConsideredActions.COMMIT_CHANGE, List.of(new GitHubCommitObserver(codingLanguages)));
//
//
//    public GitHubObserverManager() {
//        super(map, setOfActions);
//    }
//
//}
