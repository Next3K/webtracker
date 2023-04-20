package com.webtracker.app.model.observers.observer;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.states.github.CodingLanguage;
import com.webtracker.app.model.states.github.GitHubRepository;
import com.webtracker.app.model.states.github.GitHubState;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Observes changes in repositories, saves events to collectedEvents field
 */
@Log
public class GitHubRepoObserver extends Observer<GitHubState> {

    @Override
    protected List<Event> detectEvents(GitHubState newState) {
        // check what has changed
        List<GitHubRepository> newRepositories = newState.getRepositories();
        List<GitHubRepository> oldRepositories = this.oldState.getRepositories();

        // create events list based on the changes
        List<Event> whatHappened = new ArrayList<>();

        List<Integer> oldRepositoryIds = oldRepositories.stream()
                .map(GitHubRepository::getRepositoryID)
                .collect(Collectors.toList());

        newRepositories = newRepositories.stream().filter(gitHubRepository -> !oldRepositoryIds.contains(gitHubRepository.getRepositoryID())).toList();

        if (!newRepositories.isEmpty()) {
            log.info("Changes in repository list detected");

            List<CodingLanguage> codingLanguages = newState.getRepositories().get(newState.getGitHubStateId()).getCodingLanguages();

            List<GitHubRepository> filteredRepositories = newRepositories.stream()
                    .filter(repo -> repo.getCodingLanguages().stream().anyMatch(codingLanguages::contains))
                    .toList();

            for (GitHubRepository repo : filteredRepositories) {
                Event event = Event.builder()
                        .emailToSendEvent(newState.getObservatorEmail())
                        .eventTitle("New Repository created")
                        .githubUsername(newState.getOwner().username())
                        .eventDescription(String.format("Repository %s, written in %s, has been created", repo.getDescription(), repo.getCodingLanguages()))
                        .build();
                whatHappened.add(event);
            }
            collectedEvents.addAll(whatHappened);
            return collectedEvents;
        }
        log.info("Nothing happened");
        return collectedEvents;
    }
}
