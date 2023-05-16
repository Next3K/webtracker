package com.webtracker.app.model.events;

import com.webtracker.app.model.states.github.GitHubOwner;
import com.webtracker.app.model.states.github.GitHubState;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class GitHubApi {

    // call git hub api for given username
    public static GitHubState callApi(GitHubOwner owner){
//        String username = owner.username();
        return new GitHubState();
    }
    public static String call(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .method("GET",HttpRequest.BodyPublishers.noBody()).build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(response).body();
    }

}
