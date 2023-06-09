package com.webtracker.app.repository;


import com.webtracker.app.model.Client;
import com.webtracker.app.model.observers.GitHubReposObserver;
import com.webtracker.app.model.github.CodingLanguage;
import com.webtracker.app.model.github.GitHubOwner;
import com.webtracker.app.model.github.GitHubState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GitHubObserverRepository  {

    private static final List<GitHubReposObserver> repository = new ArrayList<>();


    static {
        Set<CodingLanguage> languages1 = Set.of(CodingLanguage.Cpp, CodingLanguage.C);
        Set<CodingLanguage> languages2 = Set.of(CodingLanguage.Python, CodingLanguage.Scala);
        Set<CodingLanguage> languages3 = Set.of(CodingLanguage.Java, CodingLanguage.Scala, CodingLanguage.Clojure);

        var github1 = new GitHubState(
                1,
                "CS scientist",
                new GitHubOwner(2, "jack", "smith", "Next3K", "js@gmail.com"),
                new ArrayList<>(1));

        var github2 = new GitHubState(
                2,
                "data scientist",
                new GitHubOwner(4, "john", "moe", "Pevth", "moe@gmail.com"),
                new ArrayList<>(1));

        Client exampleClient = ClientRepository.getAll().get(0); // get some random client

        GitHubReposObserver firstObserver = new GitHubReposObserver(exampleClient, github1, languages1);
        GitHubReposObserver secondObserver = new GitHubReposObserver(exampleClient, github2, languages2);
        GitHubReposObserver thirdObserver = new GitHubReposObserver(exampleClient, github2, languages3);

        repository.addAll(List.of(firstObserver, secondObserver, thirdObserver));
    }

    public static List<GitHubReposObserver> getAll() {
        return new ArrayList<>(repository);
    }

    public static void addALl(List<GitHubReposObserver> observerList) {
        repository.addAll(observerList);
    }
}
