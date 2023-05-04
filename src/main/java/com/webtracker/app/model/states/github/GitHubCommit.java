package com.webtracker.app.model.states.github;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GitHubCommit {
    private String committerName;
    private String commitMessage;
    private String commitSha;
    private String url;
    private List<CodingLanguage> codingLanguages;
}
