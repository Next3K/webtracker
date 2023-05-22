package com.webtracker.app.model.states.github;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubCommit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    //WSZYSTKO PONIZEJ JESTEM TU
    @Column(nullable = false, name = "committer_name")
    private String committerName;

    @Column(nullable = false, name = "commit_message")
    private String commitMessage;

    @Column(nullable = false, name = "commit_sha")
    private String commitSha;

    @Column(nullable = false, name = "url")
    private String url;

    @Column(nullable = false, name = "added_lines")
    private int addedLines;

    @Column(nullable = false, name = "deleted_lines")
    private int deletedLines;

    @Builder
    public GitHubCommit(String committerName, String commitMessage, String commitSha, String url, int addedLines, int deletedLines) {
        this.committerName = committerName;
        this.commitMessage = commitMessage;
        this.commitSha = commitSha;
        this.url = url;
        this.addedLines = addedLines;
        this.deletedLines = deletedLines;
    }
}
