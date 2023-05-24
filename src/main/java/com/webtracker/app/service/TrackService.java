package com.webtracker.app.service;

import com.webtracker.app.dto.track.TrackType;
import com.webtracker.app.dto.track.TrackUserDto;
import com.webtracker.app.model.events.GitHubApi;
import com.webtracker.app.model.observers.observer.GitHubCommitObserver;
import com.webtracker.app.model.observers.observer.GitHubRepoObserver;
import com.webtracker.app.model.observers.observer.Observer;
import com.webtracker.app.model.states.github.GitHubState;
import com.webtracker.app.repo.ObserverRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackService {

    final GitHubApi gitHubApi;
    final ObserverRepo observerRepo;


    void addToTrack(TrackUserDto trackUserDto) {

        GitHubState gitHubState = gitHubApi.callApi(trackUserDto.getGithubUsername(), trackUserDto.getEmail());

        if (trackUserDto.getTrackType().equals(TrackType.REPOSITORY)) {
            Observer<GitHubState> repoObserver = new GitHubRepoObserver();
            repoObserver.setInterestingLanguages(trackUserDto.getTechnologies());
            repoObserver.setOldState(gitHubState);
            observerRepo.save(repoObserver);
        } else {
            Observer<GitHubState> repoObserver = new GitHubCommitObserver();
            repoObserver.setInterestingLanguages(trackUserDto.getTechnologies());
            repoObserver.setOldState(gitHubState);
            observerRepo.save(repoObserver);
        }

    }


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
