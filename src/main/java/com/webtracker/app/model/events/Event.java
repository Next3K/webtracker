package com.webtracker.app.model.events;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    String emailToSendEvent;

    String githubUsername;

    String eventTitle;

    String eventDescription;

    String link;

    public Event(String emailToSendEvent, String githubUsername, String eventTitle, String eventDescription, String link) {
        this.emailToSendEvent = emailToSendEvent;
        this.githubUsername = githubUsername;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.link = link;
    }
}
