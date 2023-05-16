package com.webtracker.app.model.events;

import com.webtracker.app.model.states.github.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class GitHubApi {

    private static final String USERNAME = "";
    private static final String TOKEN = "";
    public static GitHubState callApi(GitHubOwner owner){
        List<GitHubRepository> repositoriesList = new ArrayList<>();
        String username=owner.username();
        String repositories = call("https://api.github.com/users/"+username+"/repos");
        JSONArray repos = new JSONArray(repositories);
        for (int i = 0; i < repos.length(); i++) {
            JSONObject repo = repos.getJSONObject(i);
            String repoName = repo.getString("name");
            repositoriesList.add(getRepoInfo(username,repoName));
        }
        //Here create new GitHubState and fill it with proper data, then return
        return new GitHubState();
    }
    public static String call(String url){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Authorization", "Basic " + encodeCredentials(USERNAME, TOKEN))
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
        GitHubRepository gitHubRepository = new GitHubRepository();
        String repoResponse = call("https://api.github.com/repos/"+user+"/"+repoName);
        JSONObject repositoryObject=new JSONObject(repoResponse);
        String languagesResponse = call("https://api.github.com/repos/"+user+"/"+repoName+"/languages");
        JSONObject languagesObject = new JSONObject(languagesResponse);
        List<String> languages = new ArrayList<>(languagesObject.keySet());
        List<CodingLanguage> enumLanguages = new ArrayList<>();
        for (String lang : languages) {
            switch (lang){
                case "C++": lang = "Cpp";
                case "C#": lang = "Csharp";
                case "Objective-C": lang = "Objective_C";
            }
            try {
                CodingLanguage enumValue = CodingLanguage.valueOf(lang);
                enumLanguages.add(enumValue);
            }
            catch (Exception ignored) {
            }
        }
        gitHubRepository.setDescription(repositoryObject.get("description").toString());
        gitHubRepository.setRepositoryID(repositoryObject.getInt("id"));
        gitHubRepository.setUrl(repositoryObject.getString("html_url"));
        gitHubRepository.setCodingLanguages(null);
        gitHubRepository.setCommits(getCommitsInfo(user, repoName));
        return gitHubRepository;
    }

    private static String encodeCredentials(String username, String token) {
        String credentials = username + ":" + token;
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(credentialsBytes);
    }
}
