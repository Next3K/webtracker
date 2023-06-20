package com.webtracker.app;

import com.webtracker.app.dto.ClientDto;
import com.webtracker.app.dto.DeleteUserDto;
import com.webtracker.app.dto.TrackUserDto;
import com.webtracker.app.model.github.CodingLanguage;

import java.util.Set;

public class dataForTests {

    public static ClientDto clientDto = ClientDto.builder()
            .name("testName")
            .email("testEmail")
            .build();

    public static TrackUserDto trackUserDto_incorrectObserverType =
            TrackUserDto.builder()
                    .client(dataForTests.clientDto)
                    .githubUsername("testUsername")
                    .technologies(Set.of(CodingLanguage.TypeScript))
                    .observerType("incorrectObserverType")
                    .build();

    public static TrackUserDto trackUserDto_noTechnologies =
            TrackUserDto.builder()
                    .client(dataForTests.clientDto)
                    .githubUsername("testUsername")
                    .observerType("incorrectObserverType")
                    .build();

    public static TrackUserDto trackUserDto_noClient =
            TrackUserDto.builder()
                    .githubUsername("testUsername")
                    .technologies(Set.of(CodingLanguage.TypeScript))
                    .observerType("incorrectObserverType")
                    .build();

    public static TrackUserDto trackUserDto_noGithubUsername =
            TrackUserDto.builder()
                    .client(dataForTests.clientDto)
                    .technologies(Set.of(CodingLanguage.TypeScript))
                    .observerType("incorrectObserverType")
                    .build();

    public static TrackUserDto trackUserDto_noObserverType =
            TrackUserDto.builder()
                    .client(dataForTests.clientDto)
                    .githubUsername("testUsername")
                    .technologies(Set.of(CodingLanguage.TypeScript))
                    .build();

    public static DeleteUserDto deleteUserDto =
            DeleteUserDto.builder()
                    .githubUsername("testUsername")
                    .observerType("testObserverType")
                    .build();

}
