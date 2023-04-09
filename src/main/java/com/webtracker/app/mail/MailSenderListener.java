package com.webtracker.app.mail;

import com.webtracker.app.model.events.Event;
import com.webtracker.app.service.EmailSenderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailSenderListener {

    private final EmailSenderServiceImpl emailSenderServiceImpl;

    @EventListener
    public void handle(Event event) {
        emailSenderServiceImpl.sendEmail(event.getEmailToSendEvent(), "New update!", "New update for user: " + event.getGithubUsername() +
                "\nCheck the repo on this link: " + event.getLink());
    }
}


