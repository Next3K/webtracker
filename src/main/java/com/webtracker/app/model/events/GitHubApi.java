package com.webtracker.app.model.events;

import com.webtracker.app.dto.track.TrackType;
import com.webtracker.app.dto.track.TrackUserDto;
import com.webtracker.app.model.observers.observer.GitHubCommitObserver;
import com.webtracker.app.model.observers.observer.GitHubRepoObserver;
import com.webtracker.app.model.observers.observer.Observer;
import com.webtracker.app.model.states.github.*;
import com.webtracker.app.repo.GitHubStateRepo;
import com.webtracker.app.repo.ObserverRepo;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

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

@Service
@RequiredArgsConstructor
public class GitHubApi {

    final GitHubStateRepo gitHubStateRepo;
    final ObserverRepo observerRepo;

    private final String USERNAME = "";
    private final String TOKEN = "";

    public GitHubState callApi(TrackUserDto trackUserDto) {
        List<GitHubRepository> repositoriesList = new ArrayList<>();
        String repositoriesResponse = call("https://api.github.com/users/" + trackUserDto.getGithubUsername() + "/repos");
        JSONArray repositories = new JSONArray(repositoriesResponse);
        for (int i = 0; i < repositories.length(); i++) {
            JSONObject repo = repositories.getJSONObject(i);
            String repoName = repo.getString("name");
            repositoriesList.add(getRepoInfo(trackUserDto.getGithubUsername(), repoName));
            List<GitHubCommit> commits = this.getCommitsInfo(trackUserDto.getGithubUsername(), repoName);
            repositoriesList.get(i).setCommits(commits);

        }
        GitHubState gitHubState = new GitHubState();
        gitHubState.setObservatorEmail(trackUserDto.getEmail());
        gitHubState.setOwner(new GitHubOwner(trackUserDto.getGithubUsername()));
        gitHubState.setGitHubAccountDescription(getAccountDescription(trackUserDto.getGithubUsername()));
        gitHubState.setRepositories(repositoriesList);

        if (trackUserDto.getTrackType().equals(TrackType.REPOSITORY)) {
            Observer<GitHubState> repoObserver = new GitHubRepoObserver();
            repoObserver.setInterestingLanguages(trackUserDto.getTechnologies());
            repoObserver.setOldState(gitHubState);
            observerRepo.save(repoObserver);
        } else {
            Observer<GitHubState> repoObserver = new GitHubCommitObserver();
            repoObserver.setInterestingLanguages(trackUserDto.getTechnologies());
            repoObserver.setOldState(gitHubState);
            observerRepo.save(repoObserver);
        }

        gitHubStateRepo.save(gitHubState);

        return gitHubState;
    }

    public String getAccountDescription(String username) {
        String userResponse = call("https://api.github.com/users/" + username);
        JSONObject user = new JSONObject(userResponse);
        return user.getString("bio");
    }

    public String call(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Authorization", "Basic " + encodeCredentials(USERNAME, TOKEN))
                .method("GET", HttpRequest.BodyPublishers.noBody()).build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(response).body();
    }

    public List<GitHubCommit> getCommitsInfo(String username, String repoName) {
        List<GitHubCommit> commitsList = new ArrayList<>();
        String commitResponse = call("https://api.github.com/repos/" + username + "/" + repoName + "/commits");
        JSONArray commits = new JSONArray(commitResponse);
        for (int i = 0; i < commits.length(); i++) {
            JSONObject commitObject = commits.getJSONObject(i);
            if (commitObject.has("author") && !commitObject.isNull("author")) {
                String authorName = commitObject.getJSONObject("author").getString("login");
                if (!authorName.equals(username)) continue;
            } else {
                continue;
            }
            String committerName = commitObject.getJSONObject("commit").getJSONObject("committer").getString("name");
            String commitMsg = commitObject.getJSONObject("commit").getString("message");
            String sha = commitObject.getString("sha");
            String html_commmit_url = commitObject.getString("html_url");
            String api_commit_url = commitObject.getString("url");

            String individualCommitResponse = call(api_commit_url);
            JSONObject individualCommitObject = new JSONObject(individualCommitResponse);
            int addedLines = individualCommitObject.getJSONObject("stats").getInt("additions");
            int deletedLines = individualCommitObject.getJSONObject("stats").getInt("deletions");

            GitHubCommit gitHubCommit = new GitHubCommit(committerName, commitMsg, sha, html_commmit_url, addedLines, deletedLines);
            commitsList.add(gitHubCommit);
        }
        return commitsList;
    }

    public GitHubRepository getRepoInfo(String username, String repoName) {
        GitHubRepository gitHubRepository = new GitHubRepository();
        String repositoryResponse = call("https://api.github.com/repos/" + username + "/" + repoName);
        JSONObject repositoryObject = new JSONObject(repositoryResponse);
        String languagesResponse = call("https://api.github.com/repos/" + username + "/" + repoName + "/languages");
        JSONObject languagesObject = new JSONObject(languagesResponse);
        List<String> languages = new ArrayList<>(languagesObject.keySet());
        List<CodingLanguage> enumLanguages = new ArrayList<>();
        for (String lang : languages) {
            switch (lang) {
                case "C++":
                    lang = "Cpp";
                case "C#":
                    lang = "Csharp";
                case "Objective-C":
                    lang = "Objective_C";
            }
            try {
                CodingLanguage enumValue = CodingLanguage.valueOf(lang);
                enumLanguages.add(enumValue);
            } catch (Exception ignored) {
            }
        }
        gitHubRepository.setDescription(repositoryObject.get("description").toString());
        gitHubRepository.setRepositoryID(repositoryObject.getInt("id"));
        gitHubRepository.setUrl(repositoryObject.getString("html_url"));
        gitHubRepository.setCodingLanguages(enumLanguages);
        gitHubRepository.setCommits(getCommitsInfo(username, repoName));
        return gitHubRepository;
    }

    private String encodeCredentials(String username, String token) {
        String credentials = username + ":" + token;
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(credentialsBytes);
    }
}
