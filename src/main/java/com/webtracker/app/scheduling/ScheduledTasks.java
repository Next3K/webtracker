package com.webtracker.app.scheduling;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.observers.GitHubReposObserver;
import com.webtracker.app.model.observers.Observer;
import com.webtracker.app.model.github.GitHubState;
import com.webtracker.app.repository.EventRepository;
import com.webtracker.app.repository.GitHubObserverRepository;
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
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(fixedRate = 60_000) // check stats every 1 minute
    public void detectEvents() {
        log.info("Started updating observers");
        List<GitHubReposObserver> observerList = GitHubObserverRepository.getAll();

        // which repos to which observers
        Map<GitHubState, List<GitHubReposObserver>> collect =
                observerList.stream().collect(Collectors.groupingBy(Observer::getOldState));

        Map<GitHubState, GitHubState> oldNewStatesMapping = new HashMap<>();

        // get a new state for every repo that is interesting
        for (var oldGitHubState : collect.keySet()) {
            // TODO uncomment when GitHubApi works fine
            System.out.println("Calling GitHubApi, sending requests");
//            oldNewStatesMapping.put(oldGitHubState, GitHubApi.callApi(oldGitHubState.getOwner()));
        }

        List<Event> events = new ArrayList<>();
        for (var oldState : collect.keySet()) {
            for (var observer : collect.get(oldState)) {
                var newState = oldNewStatesMapping.get(oldState);
                // TODO remove when GitHubApi works fine
                if (newState == null) continue;
                observer.update(newState);
                events.addAll(observer.popCollectedEvents());
            }
        }

        EventRepository.addAll(events);
        log.info("Finished updating observers");
    }

    @Scheduled(fixedRate = 300_000) // send events every 5 minutes
    public void sendEvents() {
        log.info("Started Sending Events");
        List<Event> eventsToSend = EventRepository.popAll();
        // TODO use sender to send events (mail)
        log.info("Finished Sending Events");
    }
}
