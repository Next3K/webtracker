package com.webtracker.app.mail;

import com.webtracker.app.model.events.Event;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSenderPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishNewEvent(String mailToSendEvent, String githubUsername, String eventTitle,
                                String eventDescription,
                                String link) {
        applicationEventPublisher.publishEvent(
                new Event(mailToSendEvent, githubUsername, eventTitle, eventDescription, link));
    }
}
