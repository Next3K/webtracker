package com.webtracker.app.model.github;

import com.webtracker.app.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


/**
 * Representation of GitHub of given user
 */
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "github_state")
@Getter
@Setter
@Entity
@ToString
public class GitHubState extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_account_description")
    private String gitHubAccountDescription;

    @ManyToOne(optional = false)
    @JoinColumn(name = "github_owner_id", nullable = false)
    private GitHubOwner owner;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "github_state_id", referencedColumnName = "id")
    private List<GitHubRepository> repositories;

}
