package com.webtracker.app.repo;

import com.webtracker.app.model.observers.observer.Observer;
import com.webtracker.app.model.states.github.GitHubState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObserverRepo extends JpaRepository<Observer<GitHubState>, Long> {
}