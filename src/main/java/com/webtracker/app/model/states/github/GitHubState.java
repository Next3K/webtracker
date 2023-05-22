package com.webtracker.app.model.states.github;

import com.webtracker.app.model.observers.observer.Observer;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representation of GitHub of given user
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GitHubState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    private String observatorEmail;

    @ManyToOne(cascade = CascadeType.ALL)
    private GitHubOwner owner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GitHubRepository> repositories;

    @Column(nullable = false, name = "account_description")
    private String gitHubAccountDescription;


}
