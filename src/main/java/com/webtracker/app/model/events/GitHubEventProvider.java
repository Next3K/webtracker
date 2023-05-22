//package com.webtracker.app.model.events;
//
//import com.webtracker.app.model.states.github.GitHubState;
//
//import java.util.List;
//import org.springframework.stereotype.Service;
//
//@Service
//public class GitHubEventProvider implements EventProvider<GitHubState> {
//    @Override
//    public List<Event> getEvents(GitHubState oldState) {
//        GitHubState newState = getNewState(oldState);
//        return newState.getObserver().getCollectedEvents();
//    }
//
//    private GitHubState getNewState(GitHubState oldState) {
//        return GitHubApi.callApi(oldState.getOwner().getName());
//    }
//}
