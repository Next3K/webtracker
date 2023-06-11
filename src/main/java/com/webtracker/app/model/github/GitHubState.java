package com.webtracker.app.model.github;

import com.webtracker.app.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * Representation of GitHub of given user
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class GitHubState extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_account_description")
    private String gitHubAccountDescription;

    @ManyToOne(optional = false)
    @JoinColumn(name = "github_owner_id", nullable = false)
    private GitHubOwner owner;

    @OneToMany(orphanRemoval = true)
    private List<GitHubRepository> repositories;

}
