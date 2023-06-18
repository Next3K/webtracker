package com.webtracker.app.model.github;


import com.webtracker.app.common.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


/**
 * Representation of GitHubRepository
 */
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "github_repository")
@Getter
@Setter
@Entity
@ToString
public class GitHubRepository extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "repository_id", referencedColumnName = "id")
    private List<GitHubCommit> commits;

    @Enumerated(EnumType.STRING)
    private List<CodingLanguage> codingLanguages;

    private String description;

    @NotNull(message = "Repository ID cannot be null")
    @Column(name = "repository_id")
    private Integer repositoryID;

    @NotBlank(message = "Repository url cannot be blank")
    private String url;
}
