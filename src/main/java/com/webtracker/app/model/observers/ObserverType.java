package com.webtracker.app.model.observers;

import lombok.Getter;

@Getter
public enum ObserverType {

    CommitsObserver("CommitsObserver"),
    GitHubReposObserver("GitHubReposObserver");

    private final String name;

    ObserverType(String name) {
        this.name = name;
    }

}
