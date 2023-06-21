package com.webtracker.app.model.github;

import com.webtracker.app.common.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "github_commit")
@Entity
@ToString
@EqualsAndHashCode
public class GitHubCommit extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Commiter name cannot be blank")
    @Column(name = "committer_name")
    private String committerName;

    @NotBlank(message = "Commit message cannot be blank")
    @Column(name = "commit_message", length = 2500)
    private String commitMessage;

    @NotBlank(message = "Commit sha cannot be blank")
    @Column(name = "commit_sha")
    private String commitSha;

    @NotBlank(message = "Commit url cannot be blank")
    @Column(name = "commit_url")
    private String url;

    @NotNull(message = "Added lines cannot be null")
    @Column(name = "added_lines")
    private Integer addedLines;

    @NotNull(message = "Deleted lines cannot be null")
    @Column(name = "deleted_lines")
    private Integer deletedLines;

    public GitHubCommit(String committerName, String commitMessage, String commitSha, String url, Integer addedLines, Integer deletedLines) {
        this.committerName = committerName;
        this.commitMessage = commitMessage;
        this.commitSha = commitSha;
        this.url = url;
        this.addedLines = addedLines;
        this.deletedLines = deletedLines;
    }
}
