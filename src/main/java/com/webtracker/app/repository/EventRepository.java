package com.webtracker.app.repository;

import com.webtracker.app.model.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

//    public static void addAll(List<Event> events) {
//        // TODO add events to DB
//    }
//
//    public static List<Event> popAll() {
//        // TODO get all events from DB
//        return new ArrayList<>();
//    }

}
