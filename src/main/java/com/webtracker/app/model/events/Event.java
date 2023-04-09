package com.webtracker.app.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    String emailToSendEvent;

    String githubUsername;

    String eventTitle;

    String eventDescription;

    String link;
}
