package com.webtracker.app.api;

import com.webtracker.app.model.github.*;
import com.webtracker.app.service.GitHubApi;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ApiTest {


    @Test

    public void commitInfoWorksGood(){
        try(MockedStatic<GitHubApi> api = Mockito.mockStatic(GitHubApi.class)){
            api.when(()->GitHubApi.getUserRepos("user")).thenReturn("[{\"name\": \"repoName\"}]");
            api.when(()->GitHubApi.getRepo("user","repoName")).thenReturn("{\"description\": \"test description\",\"id\":1,\"html_url\": \"testUrl\"}");
            api.when(()->GitHubApi.getLangs("user","repoName")).thenReturn("{\"Java\": 1}");
            api.when(()->GitHubApi.getCommitList("user","repoName")).thenReturn("[{\"author\":{\"login\": \"login\"},\"commit\":{\"commiter\":{\"name\": \"name\"},\"message\": \"message\"},\"sha\": \"abc\",\"html_url\":\"htmlURL\",\"url\":\"url\" }]");
            api.when(()->GitHubApi.getStats("url")).thenReturn("{\"stats\": { \"total\": 2,\"additions\":1,\"deletions\":1}}");
            api.when(()->GitHubApi.getAccountDescription("user")).thenReturn("account");
        }
        GitHubCommit commit = new GitHubCommit("name","message","abc","url",1,1);
        List<GitHubCommit> commits = new ArrayList<>();
        commits.add(commit);
        List<CodingLanguage> langs = new ArrayList<>();
        CodingLanguage enumValue = CodingLanguage.valueOf("Java");
        langs.add(enumValue);
        GitHubRepository repo = new GitHubRepository(1L,commits,langs,"test description",1,"testUrl");
        List <GitHubRepository> repos = new ArrayList<>();
        repos.add(repo);
        GitHubOwner owner = new GitHubOwner(1L,"name","surname","user","abc@abc.xyz");
        GitHubState state = new GitHubState(1L,"account",owner,repos);



    }

}
