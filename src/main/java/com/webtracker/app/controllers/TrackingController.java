package com.webtracker.app.controllers;

import com.webtracker.app.dto.DeleteUserDto;
import com.webtracker.app.dto.TrackUserDto;
import com.webtracker.app.dto.mapper.ClientMapper;
import com.webtracker.app.exceptions.NotFoundException;
import com.webtracker.app.model.github.GitHubOwner;
import com.webtracker.app.model.github.GitHubState;
import com.webtracker.app.model.observers.GitHubCommitObserver;
import com.webtracker.app.model.observers.GitHubReposObserver;
import com.webtracker.app.model.observers.ObserverType;
import com.webtracker.app.repository.GitHubCommitObserverRepository;
import com.webtracker.app.repository.GitHubOwnerRepository;
import com.webtracker.app.repository.GitHubRepoObserverRepository;
import com.webtracker.app.repository.GithubStateRepository;
import com.webtracker.app.service.GitHubApi;
import com.webtracker.app.exceptions.WrongObserverTypeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/track")
public class TrackingController {

    private final GitHubOwnerRepository gitHubOwnerRepository;
    private final GitHubRepoObserverRepository gitHubRepoObserverRepository;
    private final GitHubCommitObserverRepository gitHubCommitObserverRepository;
    private final GithubStateRepository githubStateRepository;

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

        if (trackUserDto.getObserverType().equals(ObserverType.GitHubReposObserver.getName())) {
            GitHubState initialState = GitHubApi.callApi(byUsername);
            GitHubReposObserver gitHubReposObserver = new GitHubReposObserver(trackUserDto.getTechnologies(), initialState, ClientMapper.mapToClient(trackUserDto.getClient()));
            gitHubRepoObserverRepository.save(gitHubReposObserver);
            return ResponseEntity.status(201).body("GitHub user: + " + trackUserDto.getGithubUsername() + " is now being tracked");

        } else if (trackUserDto.getObserverType().equals(ObserverType.CommitsObserver.getName())) {
            GitHubState initialState = GitHubApi.callApi(byUsername);
            GitHubCommitObserver gitHubCommitObserver = new GitHubCommitObserver(trackUserDto.getTechnologies(), initialState, ClientMapper.mapToClient(trackUserDto.getClient()));
            gitHubCommitObserverRepository.save(gitHubCommitObserver);

            return ResponseEntity.status(201).body("GitHub user: + " + trackUserDto.getGithubUsername() + " is now being tracked");
        } else {
            throw new WrongObserverTypeException("Observer type not supported");
        }

    }

    /**
     * Client removes tracking task
     *
     * @param deleteUserDto - information about tracking task
     * @return Status 201
     */
    @DeleteMapping()
    @Transactional
    public ResponseEntity<String> stopTrackingGitHubUser(@Valid @RequestBody DeleteUserDto deleteUserDto) {

        GitHubOwner byUsername = gitHubOwnerRepository.findByUsername(deleteUserDto.getGithubUsername());
        GitHubState stateToDelete = githubStateRepository.findByOwner(byUsername);

        if (byUsername == null) {
            throw new NotFoundException("User not found");
        } else if (stateToDelete == null) {
            throw new NotFoundException("State not found");
        }

        if (deleteUserDto.getObserverType().equals(ObserverType.GitHubReposObserver.getName())) {
            gitHubRepoObserverRepository.deleteByOldState(stateToDelete);
        } else if (deleteUserDto.getObserverType().equals(ObserverType.CommitsObserver.getName())) {
            gitHubCommitObserverRepository.deleteByOldState(stateToDelete);
        } else {
            throw new WrongObserverTypeException("Observer type not supported");
        }

        return ResponseEntity.status(204).body("User + " + deleteUserDto.getGithubUsername() + " removed from track");
    }


}
