package com.webtracker.app.controllers;

import com.webtracker.app.dto.DeleteUserDto;
import com.webtracker.app.dto.TrackUserDto;
import com.webtracker.app.dto.mapper.ClientMapper;
import com.webtracker.app.model.events.GitHubApi;
import com.webtracker.app.model.github.GitHubOwner;
import com.webtracker.app.model.github.GitHubState;
import com.webtracker.app.model.observers.GitHubReposObserver;
import com.webtracker.app.repository.GitHubObserverRepository;
import com.webtracker.app.repository.GitHubOwnerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/track")
public class TrackingController {

    private final GitHubOwnerRepository gitHubOwnerRepository;
    private final GitHubObserverRepository gitHubObserverRepository;

    /**
     * Clients adds tracking task by specifying GitHub user to track and what to track
     *
     * @param trackUserDto - trackUserDto
     * @return Status 201
     */
    @PostMapping()
    public ResponseEntity<String> trackGitHubUser(@Valid @RequestBody TrackUserDto trackUserDto) {

        GitHubOwner byUsername = gitHubOwnerRepository.findByUsername(trackUserDto.getGithubUsername());

        if (byUsername == null) {
            byUsername = new GitHubOwner();
            byUsername.setUsername(trackUserDto.getGithubUsername());
            gitHubOwnerRepository.save(byUsername);
        }

        // get initial state
        GitHubState initialState = GitHubApi.callApi(byUsername);

        // create tracking configuration (observer)
        GitHubReposObserver gitHubReposObserver =
                new GitHubReposObserver(trackUserDto.getTechnologies(), initialState, ClientMapper.mapToClient(trackUserDto.getClient()));

        // save to db
        gitHubObserverRepository.save(gitHubReposObserver);

        return ResponseEntity.status(201)
                .body("GitHub user: + " + trackUserDto.getGithubUsername() + "is now being tracked");
    }

    /**
     * Client removes tracking task
     *
     * @param deleteUserDto - information about tracking task
     * @return Status 201
     */
    @DeleteMapping()
    public ResponseEntity<String> stopTrackingGitHubUser(
            @Valid @RequestBody DeleteUserDto deleteUserDto) {
        return ResponseEntity.status(204)
                .body("User + " + deleteUserDto.getGithubUsername() + " removed from track");
    }


}
