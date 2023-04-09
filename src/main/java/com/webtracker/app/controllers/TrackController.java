package com.webtracker.app.controllers;

import com.webtracker.app.dto.track.DeleteUserDto;
import com.webtracker.app.dto.track.TrackUserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/track")
public class TrackController {


    @PostMapping()
    public ResponseEntity<?> addUserToTrack(@Valid @RequestBody TrackUserDto trackUserDto) {
        return ResponseEntity.ok("User + " + trackUserDto.getGithubUsername() + " added to track");
    }

    @DeleteMapping()
    public ResponseEntity<?> removeUserFromTrack(@Valid @RequestBody DeleteUserDto deleteUserDto) {
        return ResponseEntity
                .ok("User + " + deleteUserDto.getGithubUsername() + " removed from track");
    }


}
