package com.webtracker.app.model.events;

import com.webtracker.app.model.states.github.GitHubCommit;
import com.webtracker.app.model.states.github.GitHubOwner;
import com.webtracker.app.model.states.github.GitHubRepository;
import com.webtracker.app.model.states.github.GitHubState;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
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

    public static List<GitHubCommit> getCommitsInfo(String user, String repoName){
        List<GitHubCommit> commitsList = new ArrayList<>();
        String commitResponse = call("https://api.github.com/repos/"+user+"/"+repoName+"/commits");
        JSONArray commits = new JSONArray(commitResponse);
        for (int i = 0; i < commits.length(); i++) {
            JSONObject commit = commits.getJSONObject(i);
            String committerName = commit.getJSONObject("commit").getJSONObject("committer").getString("name");
            String commitMsg = commit.getJSONObject("commit").getString("message");
            String sha = commit.getString("sha");
            String url = commit.getString("html_url");
            GitHubCommit gitHubCommit = new GitHubCommit();
            gitHubCommit.setCommitterName(committerName);
            gitHubCommit.setCommitMessage(commitMsg);
            gitHubCommit.setCommitSha(sha);
            gitHubCommit.setUrl(url);
            commitsList.add(gitHubCommit);
        }
        return commitsList;
    }

    public static GitHubRepository getRepoInfo(String user, String repoName){
        GitHubRepository repository = new GitHubRepository();
        String repoResponse = call("https://api.github.com/repos/"+user+"/"+repoName);
        JSONObject repObject=new JSONObject(repoResponse);
        String langResponse = call("https://api.github.com/repos/"+user+"/"+repoName+"/languages");
        JSONObject langs = new JSONObject(langResponse);
        List<String> languages = new ArrayList<>(langs.keySet());
        repository.setDescription(repObject.get("description").toString());
        repository.setRepositoryID(repObject.getInt("id"));
        repository.setUrl(repObject.getString("html_url"));
        repository.setCodingLanguages(null);
        repository.setCommits(getCommitsInfo(user, repoName));
        return repository;
    }
}
