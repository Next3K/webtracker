package com.webtracker.app.model.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GitHubOwner {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String mail;
}
