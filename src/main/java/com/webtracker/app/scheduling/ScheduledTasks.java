package com.webtracker.app.scheduling;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.events.GitHubApi;
import com.webtracker.app.model.observers.observer.GitHubRepoObserver;
import com.webtracker.app.model.states.github.GitHubOwner;
import com.webtracker.app.model.states.github.GitHubState;
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

    // detect events evey onr minute
    @Scheduled(fixedRate = 60_000)
    public void detectEvents() {
        log.info("Started updating observers");
        List<GitHubRepoObserver> observerList = GitHubObserverRepository.getGitHubRepoObservers();

        // which repos to which observers
        Map<GitHubOwner, List<GitHubRepoObserver>> collect =
                observerList.stream().collect(Collectors.groupingBy(e -> e.getOldState().getOwner()));

        Map<GitHubOwner, GitHubState> states = new HashMap<>();

        // get a new state for every repo that is interesting
        for (var owner : collect.keySet()) {
            states.put(owner, GitHubApi.callApi(owner));
        }

        List<Event> events = new ArrayList();
        for (var entry : collect.entrySet()) {
            var state = states.get(entry.getKey());
            for (var observer : entry.getValue()) {
                observer.update(state);
                events.addAll(observer.popCollectedEvents());
            }
        }
        EventRepository.addAll(events);
        log.info("Finished updating observers");
    }

    // send collected events evey 5 minutes
    @Scheduled(fixedRate = 300_000)
    public void sendEvents() {
        log.info("Started Sending Events");
        List<Event> eventsToSend = EventRepository.popAll();
        // TODO use sender to send events (mail)
        log.info("Finished Sending Events");
    }
}
