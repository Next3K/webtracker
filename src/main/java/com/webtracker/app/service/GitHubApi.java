package com.webtracker.app.service;

import com.webtracker.app.model.github.*;
import lombok.extern.java.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

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


@RestController
public class GitHubApi {
    private static String USERNAME;
    private static String TOKEN;
    @Value("${credentials.github.username}")
    public void setUsernameStatic(String username) {
        GitHubApi.USERNAME = username;
    }
    @Value("${credentials.github.token}")
    public void setTokenStatic(String token) {
        GitHubApi.TOKEN = token;
    }

    public static String getUserRepos(String username){
        return call("https://api.github.com/users/"+username+"/repos");
    }

    public static String getAccountDescription(String username) {
        String userResponse = call("https://api.github.com/users/"+username);
        JSONObject user = new JSONObject(userResponse);
        return user.getString("bio");
    }

    public static String getCommitList(String username, String repoName){
       return call("https://api.github.com/repos/"+username+"/"+repoName+"/commits");
    }

    public static String getRepo(String username, String repoName){
        return call("https://api.github.com/repos/"+username+"/"+repoName);
    }

    public static String getLangs(String username, String repoName){
       return call("https://api.github.com/repos/"+username+"/"+repoName+"/languages");
    }

    public static String getStats(String url){
       return call(url);
    }
    public static GitHubState callApi(GitHubOwner owner){
        List<GitHubRepository> repositoriesList = new ArrayList<>();
        String username = owner.getUsername();
        String repositoriesResponse = getUserRepos(username);
        JSONArray repositories = new JSONArray(repositoriesResponse);
        for (int i = 0; i < repositories.length(); i++) {
            JSONObject repo = repositories.getJSONObject(i);
            String repoName = repo.getString("name");
            repositoriesList.add(getRepoInfo(username,repoName));
        }
        GitHubState gitHubState = new GitHubState();
        gitHubState.setOwner(owner);
        gitHubState.setGitHubAccountDescription(getAccountDescription(username));
        gitHubState.setRepositories(repositoriesList);
        return gitHubState;
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

    public static List<GitHubCommit> getCommitsInfo(String username, String repoName){
        List<GitHubCommit> commitsList = new ArrayList<>();
        String commitResponse = getCommitList(username,repoName);
        System.out.println("sdadsaadsa");
        JSONArray commits = new JSONArray(commitResponse);
        for (int i = 0; i < commits.length(); i++) {
            JSONObject commitObject = commits.getJSONObject(i);
            if (commitObject.has("author") && !commitObject.isNull("author")) {
                String authorName = commitObject.getJSONObject("author").getString("login");
                if (!authorName.equals(username)) continue;
            }
            else {
                continue;
            }
            String committerName = commitObject.getJSONObject("commit").getJSONObject("committer").getString("name");
            String commitMsg = commitObject.getJSONObject("commit").getString("message");
            String sha = commitObject.getString("sha");
            String html_commmit_url = commitObject.getString("html_url");
            String api_commit_url = commitObject.getString("url");

            String individualCommitResponse = getStats(api_commit_url);
            JSONObject individualCommitObject = new JSONObject(individualCommitResponse);
            Integer addedLines = individualCommitObject.getJSONObject("stats").getInt("additions");
            Integer deletedLines = individualCommitObject.getJSONObject("stats").getInt("deletions");

            GitHubCommit gitHubCommit = new GitHubCommit(committerName, commitMsg, sha, html_commmit_url, addedLines, deletedLines);
            commitsList.add(gitHubCommit);
        }
        return commitsList;
    }

    public static GitHubRepository getRepoInfo(String username, String repoName){
        GitHubRepository gitHubRepository = new GitHubRepository();
        String repositoryResponse = getRepo(username,repoName);
        JSONObject repositoryObject = new JSONObject(repositoryResponse);
        String languagesResponse = getLangs(username,repoName);
        JSONObject languagesObject = new JSONObject(languagesResponse);
        List<String> languages = new ArrayList<>(languagesObject.keySet());
        List<CodingLanguage> enumLanguages = new ArrayList<>();
        for (String lang : languages) {
            switch (lang){
                case "C++": lang = "Cpp";
                break;
                case "C#": lang = "Csharp";
                break;
                case "Objective-C": lang = "Objective_C";
                break;
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
        gitHubRepository.setCodingLanguages(enumLanguages);
        gitHubRepository.setCommits(getCommitsInfo(username, repoName));
        return gitHubRepository;
    }

    private static String encodeCredentials(String username, String token) {
        String credentials = username + ":" + token;
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(credentialsBytes);
    }
}
