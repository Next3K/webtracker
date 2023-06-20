package com.webtracker.app.scheduling;

import com.webtracker.app.mail.EmailSenderServiceImpl;
import com.webtracker.app.model.events.Event;
import com.webtracker.app.service.GitHubApi;
import com.webtracker.app.model.github.GitHubState;
import com.webtracker.app.model.observers.GitHubCommitObserver;
import com.webtracker.app.model.observers.GitHubReposObserver;
import com.webtracker.app.model.observers.Observer;
import com.webtracker.app.repository.EventRepository;
import com.webtracker.app.repository.GitHubCommitObserverRepository;
import com.webtracker.app.repository.GitHubRepoObserverRepository;
import com.webtracker.app.repository.GithubStateRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private final GitHubRepoObserverRepository gitHubRepoObserverRepository;
    private final GitHubCommitObserverRepository gitHubCommitObserverRepository;
    private final EventRepository eventRepository;
    private final GithubStateRepository githubStateRepository;

    private final EmailSenderServiceImpl emailSenderService;

    @Scheduled(fixedRate = 300_000)
    public void detectEvents() {
        log.info("Started updating observers");
        List<GitHubCommitObserver> observerListCommit = gitHubCommitObserverRepository.findAll();
        List<GitHubReposObserver> observerListRepo = gitHubRepoObserverRepository.findAll();

        Map<GitHubState, List<GitHubCommitObserver>> collectCommit =
                observerListCommit.stream().collect(Collectors.groupingBy(Observer::getOldState));
        Map<GitHubState, List<GitHubReposObserver>> collectRepo =
                observerListRepo.stream().collect(Collectors.groupingBy(Observer::getOldState));

        Map<GitHubState, GitHubState> oldNewStatesMappingCommit = new HashMap<>();
        Map<GitHubState, GitHubState> oldNewStatesMappingRepo = new HashMap<>();

        for (GitHubState oldGitHubState : collectCommit.keySet()) {
            oldNewStatesMappingCommit.put(oldGitHubState, GitHubApi.callApi(oldGitHubState.getOwner()));
        }
        for (GitHubState oldGitHubState : collectRepo.keySet()) {
            oldNewStatesMappingRepo.put(oldGitHubState, GitHubApi.callApi(oldGitHubState.getOwner()));
        }

        List<Event> eventsCommit = new ArrayList<>();
        for (GitHubState oldState : collectCommit.keySet()) {
            for (GitHubCommitObserver observer : collectCommit.get(oldState)) {
                GitHubState newState = oldNewStatesMappingCommit.get(oldState);
                observer.update(newState);
                gitHubCommitObserverRepository.save(observer);
                eventsCommit.addAll(observer.popCollectedEvents());
                githubStateRepository.delete(oldState);
            }
        }
        eventRepository.saveAll(eventsCommit);

        List<Event> eventsRepo = new ArrayList<>();
        for (GitHubState oldState : collectRepo.keySet()) {
            for (GitHubReposObserver observer : collectRepo.get(oldState)) {
                GitHubState newState = oldNewStatesMappingRepo.get(oldState);
                observer.update(newState);
                gitHubRepoObserverRepository.save(observer);
                eventsRepo.addAll(observer.popCollectedEvents());
                githubStateRepository.delete(oldState);
            }
        }

        eventRepository.saveAll(eventsRepo);
        log.info("Finished updating observers");
    }

    @Scheduled(fixedRate = 300_000) // send events every 5 minutes
    public void sendEvents() {
        log.info("Started Sending Events");
        List<Event> eventsToSend = eventRepository.findAll();
        eventsToSend.forEach(event -> log.info(event.toString()));
        eventsToSend.forEach(
                event -> emailSenderService.sendEmail(event.getEmailToSendEvent(), event.getEventTitle(),
                        event.getEventDescription() + "\n" + event.getLink())
        );
        log.info("Finished Sending Events");
    }
}
