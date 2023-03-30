package com.webtracker.app;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.model.events.EventProvider;
import com.webtracker.app.model.events.GitHubEventProvider;
import com.webtracker.app.model.states.github.GitHubState;
import com.webtracker.app.model.jobs.GitHubJob;
import com.webtracker.app.model.jobs.Job;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);

        // setup event provider(s)
        EventProvider<GitHubState> gitHubStateEventProvider = new GitHubEventProvider();

        // get job from database
        Job<GitHubState> trackSomeGitHubUserJob = new GitHubJob(new GitHubState(), 10);

        // get target of the job
        GitHubState oldGitHubState = trackSomeGitHubUserJob.getJobTarget();


        // get events
        List<Event> whatHappened = gitHubStateEventProvider.getEvents(oldGitHubState);

        // save these events into the database
        System.out.println(whatHappened);
    }

}
