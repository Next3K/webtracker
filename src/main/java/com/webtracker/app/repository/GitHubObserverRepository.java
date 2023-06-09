package com.webtracker.app.repository;


import com.webtracker.app.model.Client;
import com.webtracker.app.model.observers.observer.GitHubRepoObserver;
import com.webtracker.app.model.states.github.CodingLanguage;
import com.webtracker.app.model.states.github.GitHubOwner;
import com.webtracker.app.model.states.github.GitHubState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GitHubObserverRepository  {

    private static final List<GitHubRepoObserver> repository = new ArrayList<>();


    static {
        Set<CodingLanguage> languages1 = Set.of(CodingLanguage.Cpp, CodingLanguage.C);
        Set<CodingLanguage> languages2 = Set.of(CodingLanguage.Python, CodingLanguage.Scala);
        Set<CodingLanguage> languages3 = Set.of(CodingLanguage.Java, CodingLanguage.Scala, CodingLanguage.Clojure);

        var github1 = new GitHubState(
                1,
                "CS scientist",
                new GitHubOwner("jack", "smith", "Next3K", "js@gmail.com"),
                new ArrayList<>(1));

        var github2 = new GitHubState(
                2,
                "data scientist",
                new GitHubOwner("john", "moe", "Pevth", "moe@gmail.com"),
                new ArrayList<>(1));

        Client exampleClient = new Client(1, "myclient@gmail.com");

        GitHubRepoObserver firstObserver = new GitHubRepoObserver(exampleClient, github1, languages1);
        GitHubRepoObserver secondObserver = new GitHubRepoObserver(exampleClient, github2, languages2);
        GitHubRepoObserver thirdObserver = new GitHubRepoObserver(exampleClient, github2, languages3);

        repository.addAll(List.of(firstObserver, secondObserver, thirdObserver));
    }

    public static List<GitHubRepoObserver> getAll() {
        return new ArrayList<>(repository);
    }

    public static void addALl(List<GitHubRepoObserver> observerList) {
        repository.addAll(observerList);
    }
}
