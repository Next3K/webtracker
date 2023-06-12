package com.webtracker.observers;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.observers.observer.GitHubRepoObserver;
import com.webtracker.app.model.states.github.CodingLanguage;
import com.webtracker.app.model.states.github.GitHubOwner;
import com.webtracker.app.model.states.github.GitHubRepository;
import com.webtracker.app.model.states.github.GitHubState;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;

class GitHubRepoObserverTest {

    @Test
    void testObserver() {

        Set<CodingLanguage> interestingLanguages = new HashSet<>();
        interestingLanguages.add(CodingLanguage.Java);

        List<CodingLanguage> languages = new ArrayList<>();
        languages.add(CodingLanguage.Java);

        GitHubState oldState = new GitHubState();
        GitHubState newState = new GitHubState();
        GitHubRepoObserver gitHubRepoObserver = new GitHubRepoObserver(interestingLanguages);

        GitHubRepository gitHubRepository = new GitHubRepository();

        oldState.setGitHubAccountDescription("blah");
        oldState.setOwner(new GitHubOwner("a", "b", "aniaxb", "examplewow09876@gmail.com"));
        oldState.setObservatorEmail("examplewow09876@gmail.com");
//        oldState.getRepositories().get(0).setRepositoryID(1);

        List<GitHubRepository> oldRepositories = new ArrayList<>();
        gitHubRepository.setCodingLanguages(languages);
        oldRepositories.add(gitHubRepository);

//        gitHubRepoObserver.update(oldState);

        oldState.setRepositories(oldRepositories);
        gitHubRepoObserver.setOldState(oldState);
//        System.out.println(oldState.getRepositories());
        int i=0;
        for (GitHubRepository repo : oldState.getRepositories()) {
            repo.setRepositoryID(i);
            i++;
        }

        GitHubRepository gitHubRepository1 = new GitHubRepository();

        List<GitHubRepository> newRepositories = new ArrayList<>();
        languages.add(CodingLanguage.C);
        gitHubRepository1.setCodingLanguages(languages);
        newRepositories.add(gitHubRepository);
        newRepositories.add(gitHubRepository1);

        newState.setGitHubAccountDescription("blah");
        newState.setOwner(new GitHubOwner("a", "b", "aniaxb", "examplewow09876@gmail.com"));
        newState.setObservatorEmail("examplewow09876@gmail.com");

        newState.setRepositories(newRepositories);

        int j=0;
        for (GitHubRepository repo : newState.getRepositories()) {
            repo.setRepositoryID(j);
            j++;
        }

        List<Event> changes =  gitHubRepoObserver.detectEvents(newState);
        System.out.println(changes);
//        gitHubRepoObserver.update(oldState);

        assertFalse(changes.isEmpty());
    }

}