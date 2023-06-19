package com.webtracker.app.repository;

import com.webtracker.app.model.Client;
import com.webtracker.app.model.github.GitHubOwner;
import com.webtracker.app.model.github.GitHubState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubStateRepository extends JpaRepository<GitHubState, Long> {

    GitHubState findByOwner(GitHubOwner owner);

}
