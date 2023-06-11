package com.webtracker.app.model.observers;

import com.webtracker.app.model.Client;
import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.github.CodingLanguage;
import com.webtracker.app.model.github.GitHubCommit;
import com.webtracker.app.model.github.GitHubRepository;
import com.webtracker.app.model.github.GitHubState;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log
@Entity
@Getter
@Setter
@DiscriminatorValue("commit_observer")
@RequiredArgsConstructor
@Table(name = "github_commit_observer")
public class GitHubCommitObserver extends Observer<GitHubState> {

    public GitHubCommitObserver(Set<CodingLanguage> interestingLanguages, GitHubState oldState, Client client) {
        super(interestingLanguages, oldState, client);
    }

    @Override
    protected List<Event> detectEvents(GitHubState newState) {
        // check what has changed
        List<GitHubCommit> newCommits = new ArrayList<>();
        for (GitHubRepository repo : newState.getRepositories()) {
            newCommits.addAll(repo.getCommits());
        }

        List<GitHubCommit> oldCommits = new ArrayList<>();
        for (GitHubRepository repo : this.oldState.getRepositories()) {
            oldCommits.addAll(repo.getCommits());
        }

        // create events based on the changes
        List<Event> whatHappened = new ArrayList<>();

        Set<String> oldCommitsIds = oldCommits.stream().map(GitHubCommit::getCommitSha).collect(Collectors.toSet());

        newCommits = newCommits.stream().filter(gitHubCommit -> !oldCommitsIds.contains(gitHubCommit.getCommitSha())).toList();

        if (!newCommits.isEmpty()) {
            log.info("New commits detected");

            List<GitHubRepository> filteredRepositories = newState.getRepositories().stream()
                    .filter(repo -> repo.getCodingLanguages().stream().anyMatch(interestingLanguages::contains))
                    .toList();

            List<GitHubCommit> filteredCommits = new ArrayList<>();

            for (GitHubRepository repo : filteredRepositories) {
                filteredCommits.add((GitHubCommit) repo.getCommits());
            }

            for (GitHubCommit commit : filteredCommits) {
                Event event = Event.builder()
                        .emailToSendEvent(client.getClientMail())
                        .eventTitle("New commit")
                        .githubUsername(newState.getOwner().getUsername())
                        .eventDescription(String.format("Commit %s has been pushed", commit.getCommitMessage()))
                        .build();
                whatHappened.add(event);
            }
            return whatHappened;
        }

        log.info("No new commits detected");
        return whatHappened;
    }
}
