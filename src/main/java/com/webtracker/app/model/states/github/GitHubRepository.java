package com.webtracker.app.model.states.github;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


/**
 * Representation of GitHubRepository
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GitHubCommit> commits;

    @Enumerated(EnumType.STRING)
    private List<CodingLanguage> codingLanguages;

    private String description;

    private Integer repositoryID;

    private String url;

}
