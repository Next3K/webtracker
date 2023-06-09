package com.webtracker.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Client {
    private int clientId;
    private String name;
    private String clientMail;
}
