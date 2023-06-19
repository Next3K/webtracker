package com.webtracker.app.dto;

import com.webtracker.app.model.Client;
import com.webtracker.app.model.github.CodingLanguage;
import com.webtracker.app.model.observers.ObserverType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackUserDto {

    @NotBlank
    private String githubUsername;

    @NotNull
    private Set<CodingLanguage> technologies;

    @NotNull
    private ClientDto client;

    @NotNull
    private String observerType;

}
