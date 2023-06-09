package com.webtracker.app.repository;

import com.webtracker.app.model.Client;
import java.util.ArrayList;
import java.util.List;


public class ClientRepository {

    private static final List<Client> repository = new ArrayList<>();

    static {
        repository.addAll(List.of(
                new Client(1, "myclient@gmail.com"),
                new Client(2, "myclient@gmail.com")));
    }

    public static List<Client> getAll() {
        return new ArrayList<>(repository);
    }

    public static void addALl(List<Client> observerList) {
        repository.addAll(observerList);
    }
}
