package com.webtracker.app.model.events;

import com.webtracker.app.model.states.github.GitHubOwner;
import com.webtracker.app.model.states.github.GitHubState;

public class GitHubApi {

    // call git hub api for given username
    public static GitHubState callApi(GitHubOwner owner){
        String username = owner.username();
        return new GitHubState();
    }

}
