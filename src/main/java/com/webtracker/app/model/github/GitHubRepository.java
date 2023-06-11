package com.webtracker.app.model.github;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * Representation of GitHubRepository
 */
@Getter
@Setter
@ToString
public class GitHubRepository {
    private List<GitHubCommit> commits;
    private List<CodingLanguage> codingLanguages;
    private String description;
    private Integer repositoryID;
    private String url;
}
