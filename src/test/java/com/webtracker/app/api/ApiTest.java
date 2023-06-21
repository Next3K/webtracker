package com.webtracker.app.api;

import com.webtracker.app.model.github.CodingLanguage;
import com.webtracker.app.model.github.GitHubCommit;
import com.webtracker.app.model.github.GitHubRepository;
import com.webtracker.app.service.GitHubApi;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ApiTest {
    private JSONObject testJSON;
    private JSONObject statsJSON;
    private JSONObject repositoryJSON;
    private JSONObject langsJSON;

    private List<String> langsList;

    @Before
    public void init() throws IOException, JSONException {
        testJSON = parser.makeObject("src/test/resources/commit.json");
        statsJSON = parser.makeObject("src/test/resources/detailedCommit.json");
        langsJSON = parser.makeObject("src/test/resources/langs.json");
        repositoryJSON = parser.makeObject("src/test/resources/repository.json");
        langsList = new ArrayList<>();
        Iterator<String> iterator = langsJSON.keys();
        while (iterator.hasNext()) {
            langsList.add(iterator.next());
        }
    }

    @Test
    public void checkGitHubCommitFromObjectMethod() {
        GitHubCommit commit = GitHubApi.makeCommitFromObjects(testJSON, statsJSON);
        Assert.assertEquals("GitHub", commit.getCommitterName());
        Assert.assertEquals("Update README.md", commit.getCommitMessage());
        Assert.assertEquals("bc85278e6cb41dcf3f5f31e6c2bfdaf2f7805781", commit.getCommitSha());
        Assert.assertEquals("https://github.com/Next3K/3DES-cipher/commit/bc85278e6cb41dcf3f5f31e6c2bfdaf2f7805781", commit.getUrl());
        Assert.assertEquals((Integer) 0, commit.getAddedLines());
        Assert.assertEquals((Integer) 1, commit.getDeletedLines());
    }

    @Test
    public void checkGitHubRepositoryFromObjectMethod() {
        List<GitHubCommit> commits = new ArrayList<>();
        commits.add(GitHubApi.makeCommitFromObjects(testJSON, statsJSON));
        List<CodingLanguage> langs = new ArrayList<>();
        langs.add(CodingLanguage.Java);
        GitHubRepository repository = GitHubApi.makeRepositoryFromObjects(repositoryJSON, langsList, commits);
        Assert.assertEquals("Huffman coding implementation.", repository.getDescription());
        Assert.assertEquals(Integer.valueOf(479650170), repository.getRepositoryID());
        Assert.assertEquals("https://github.com/Next3K/Huffman-coding", repository.getUrl());
        Assert.assertEquals(langs, repository.getCodingLanguages());
    }

}
