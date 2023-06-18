package com.webtracker.app.repository;


import com.webtracker.app.model.observers.GitHubCommitObserver;
import com.webtracker.app.model.observers.GitHubReposObserver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitHubCommitObserverRepository extends JpaRepository<GitHubCommitObserver, Long> {

}
