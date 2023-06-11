package com.webtracker.app.model.github;

import lombok.*;

import java.util.List;


/**
 * Representation of GitHub of given user
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GitHubState {
    private int gitHubStateId;
    private String gitHubAccountDescription;
    private GitHubOwner owner;
    private List<GitHubRepository> repositories;
}
