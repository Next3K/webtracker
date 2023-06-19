package com.webtracker.app.repository;

import com.webtracker.app.model.github.GitHubOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GitHubOwnerRepository extends JpaRepository<GitHubOwner, Long> {

    GitHubOwner findByUsername(String username);

}

