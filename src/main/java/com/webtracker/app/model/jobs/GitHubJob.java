package com.webtracker.app.model.jobs;

import com.webtracker.app.model.states.github.GitHubState;

public class GitHubJob extends Job<GitHubState>{

    public GitHubJob(GitHubState gitHubState, int i) {
        this.setJobTarget(gitHubState);
        this.setPeriod(i);
    }

    public GitHubJob() {
    }
}
