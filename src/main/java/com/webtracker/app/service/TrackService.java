package com.webtracker.app.service;

import com.webtracker.app.model.events.EventProvider;
import com.webtracker.app.model.states.github.GitHubState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackService {

    final EventProvider<GitHubState> eventProvider;

    //tu beda przynajmniej dwie metody - jedna ktora tworzy nowy github state kiedy jeszcze nie ma
    //a druga odpowiada za porownanie istniejacego i zapisanego w bazie github state z nowym (pobranym z api)

    //i bardziej dokladny opis:

    //metoda1:
    //z rest controllera przychodzi request z nazwa uzytkownika,
    //jezykiem programowania i mail osoby ktora chce sledzic

    //na podstawie tych danych robimy request do api githuba
    //i z otrzymanych informacji tworzony jest github state,
    // ktory zapisujemy w bazie danych

    //metoda2 (wywolywana cyklicznie):
    //dla kazdego githubState ktore jest zapisane w bazie danych
    //wykonaj zadanie do api githuba i z pobranych informacji utworz nowy github state
    //porownaj go z poprzednim i jesli sa jakies zmiany to wysylamy maila
    //(w mail wysylany jest obieky klasy event / ja juz ogarnalem wysylanie maila -
    //wystarczy wykorzystac publish new event z klasy MailSenderPublisher)
    //i nowy githubState nadpisuje w bazie danych wczesniejszy

    //P.S. nie zmienialem innych klas ktore wczesniej wstawil Pawel,
    //ale raczej czesc trezba bedzie zmienic jesli bedziemy to robic jak tu opisuje


}
