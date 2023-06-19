package com.webtracker.app.model.events;

import com.webtracker.app.common.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(lombok.AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Email cannot be blank")
    @Column(name = "email_to_send_event")
    String emailToSendEvent;

    @NotBlank(message = "Github username cannot be blank")
    @Column(name = "github_username")
    String githubUsername;

    @NotBlank(message = "Event title cannot be blank")
    @Column(name = "event_title")
    String eventTitle;

    @NotBlank(message = "Event description cannot be blank")
    @Column(name = "event_description")
    String eventDescription;

    @Column(name = "event_link")
    String link;

    public Event(String emailToSendEvent, String githubUsername, String eventTitle, String eventDescription, String link) {
        this.emailToSendEvent = emailToSendEvent;
        this.githubUsername = githubUsername;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.link = link;
    }
}
