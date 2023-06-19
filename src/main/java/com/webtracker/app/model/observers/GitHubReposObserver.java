package com.webtracker.app.model.observers;

import com.webtracker.app.model.Client;
import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.github.CodingLanguage;
import com.webtracker.app.model.github.GitHubRepository;
import com.webtracker.app.model.github.GitHubState;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Observes changes in repositories, creates a list of events by comparing old and new states
 */
@Log
@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("repo_observer")
@Table(name = "github_repo_observer")
public class GitHubReposObserver extends Observer<GitHubState> {

    public GitHubReposObserver(Set<CodingLanguage> interestingLanguages, GitHubState oldState, Client client) {
        super(interestingLanguages, oldState, client);
    }


    @Override
    protected List<Event> detectEvents(GitHubState newState) {
        // check what has changed
        List<GitHubRepository> newRepositories = newState.getRepositories();
        List<GitHubRepository> oldRepositories = this.oldState.getRepositories();

        // create events list based on the changes
        List<Event> whatHappened = new ArrayList<>();

        Set<Integer> oldRepositoryIds = oldRepositories.stream()
                .map(GitHubRepository::getRepositoryID)
                .collect(Collectors.toSet());

        newRepositories = newRepositories.stream().filter(gitHubRepository -> !oldRepositoryIds.contains(gitHubRepository.getRepositoryID())).toList();

        if (!newRepositories.isEmpty()) {
            log.info("Changes in repository list detected");

            List<GitHubRepository> filteredRepositories = newRepositories.stream()
                    .filter(repo -> repo.getCodingLanguages().stream().anyMatch(interestingLanguages::contains))
                    .toList();

            for (GitHubRepository repo : filteredRepositories) {
                Event event = Event.builder()
                        .emailToSendEvent(client.getClientMail())
                        .eventTitle("New Repository created")
                        .githubUsername(newState.getOwner().getUsername())
                        .eventDescription(String.format("Repository %s, written in %s, has been created", repo.getDescription(), repo.getCodingLanguages()))
                        .build();
                whatHappened.add(event);
            }
            return whatHappened;
        }
        log.info("Nothing happened");
        return whatHappened;
    }
}
