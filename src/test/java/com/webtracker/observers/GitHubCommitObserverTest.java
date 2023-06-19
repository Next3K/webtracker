package com.webtracker.observers;

import com.webtracker.app.AppApplication;
import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.observers.observer.GitHubCommitObserver;
import com.webtracker.app.model.states.github.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AppApplication.class)
class GitHubCommitObserverTest {

    //Static method to provide ready arguments for testing purposes
    static Stream<Arguments> generateTestData() {

        Set<CodingLanguage> interestingLanguages = new HashSet<>();
        interestingLanguages.add(CodingLanguage.Java);

        List<CodingLanguage> languages = new ArrayList<>();
        languages.add(CodingLanguage.Java);

        GitHubRepository githubRepo1 = new GitHubRepository();
        githubRepo1.setCodingLanguages(languages);

        GitHubCommit commit1 = new GitHubCommit("commiterExampleName", "first commit", "sha1", "url", 50, 10);
        GitHubCommit commit2 = new GitHubCommit("commiterExampleName", "second commit", "sha12", "url", 20, 5);
        GitHubCommit commit3 = new GitHubCommit("commiterExampleName", "third commit", "sha123", "url", 35, 15);

        List<GitHubCommit> commits1 = new ArrayList<>();

        commits1.add(commit1);
        commits1.add(commit2);

        githubRepo1.setCommits(commits1);

        GitHubRepository githubRepo2 = new GitHubRepository();
        githubRepo2.setCodingLanguages(languages);

        List<GitHubCommit> commits2 = new ArrayList<>();

        commits2.add(commit1);
        commits2.add(commit2);
        commits2.add(commit3);

        githubRepo2.setCommits(commits2);

        List<GitHubRepository> repositories = new ArrayList<>();
        repositories.add(githubRepo1);
        repositories.add(githubRepo2);

        int i=0;
        for (GitHubRepository repo : repositories) {
            repo.setRepositoryID(i);
            i++;
        }

        return Stream.of(
                Arguments.of(interestingLanguages, githubRepo1, githubRepo2)
        );
    }

    //Test for detecting changes between different old state and new state
    @ParameterizedTest
    @MethodSource("generateTestData")
    void commitObserverDetectChanges(Set<CodingLanguage> interestingLanguages, GitHubRepository githubrepo1, GitHubRepository githubrepo2) {

        GitHubState oldState = new GitHubState();
        GitHubState newState = new GitHubState();

        GitHubCommitObserver gitHubCommitObserver = new GitHubCommitObserver(interestingLanguages);

        oldState.setGitHubAccountDescription("blah");
        oldState.setOwner(new GitHubOwner("a", "b", "exampleusername", "examplewow09876@gmail.com"));
        oldState.setObservatorEmail("examplewow09876@gmail.com");
        List<GitHubRepository> oldRepositories = new ArrayList<>();

        oldRepositories.add(githubrepo1);

        oldState.setRepositories(oldRepositories);
        gitHubCommitObserver.setOldState(oldState);

        List<GitHubRepository> newRepositories = new ArrayList<>();

        newRepositories.add(githubrepo1);
        newRepositories.add(githubrepo2);

        newState.setGitHubAccountDescription("blah");
        newState.setOwner(new GitHubOwner("a", "b", "exampleusername", "examplewow09876@gmail.com"));
        newState.setObservatorEmail("examplewow09876@gmail.com");

        newState.setRepositories(newRepositories);

        List<Event> changes =  gitHubCommitObserver.detectEvents(newState);
        assertFalse(changes.isEmpty());
    }

    //Test for detecting changes between different old state and new state
    @ParameterizedTest
    @MethodSource("generateTestData")
    void commitObserverNoChangesDetected(Set<CodingLanguage> interestingLanguages, GitHubRepository githubrepo1) {

        GitHubState oldState = new GitHubState();
        GitHubState newState = new GitHubState();

        GitHubCommitObserver gitHubCommitObserver = new GitHubCommitObserver(interestingLanguages);

        oldState.setGitHubAccountDescription("blah");
        oldState.setOwner(new GitHubOwner("a", "b", "exampleusername", "examplewow09876@gmail.com"));
        oldState.setObservatorEmail("examplewow09876@gmail.com");
        List<GitHubRepository> oldRepositories = new ArrayList<>();

        oldRepositories.add(githubrepo1);

        oldState.setRepositories(oldRepositories);
        gitHubCommitObserver.setOldState(oldState);

        List<GitHubRepository> newRepositories = new ArrayList<>();

        newRepositories.add(githubrepo1);

        newState.setGitHubAccountDescription("blah");
        newState.setOwner(new GitHubOwner("a", "b", "exampleusername", "examplewow09876@gmail.com"));
        newState.setObservatorEmail("examplewow09876@gmail.com");

        newState.setRepositories(newRepositories);

        List<Event> changes =  gitHubCommitObserver.detectEvents(newState);

        assertTrue(changes.isEmpty());
    }

}