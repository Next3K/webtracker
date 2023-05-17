package com.webtracker.app.model.states.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class GitHubCommit {
    private String committerName;
    private String commitMessage;
    private String commitSha;
    private String url;
    private int addedLines;
    private int deletedLines;
}
