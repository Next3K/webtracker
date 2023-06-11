package com.webtracker.app.repository;


import com.webtracker.app.model.observers.GitHubReposObserver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitHubObserverRepository extends JpaRepository<GitHubReposObserver, Long> {

}
