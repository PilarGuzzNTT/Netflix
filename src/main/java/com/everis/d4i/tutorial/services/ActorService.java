package com.everis.d4i.tutorial.services;

import java.util.List;
import java.util.Map;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ChapterRest;

public interface ActorService {

    List<ActorRest> getActors() throws NetflixException;
    ActorRest getActor(Long id) throws NetflixException;
    ActorRest createActor(ActorRest actorRest) throws NetflixException;
    ActorRest updateActor(Long id, ActorRest actorRest) throws NetflixException;
    ChapterRest addChapterActor(Long idActor, Long idChapter) throws NetflixException;
    Boolean deleteActor(Long id) throws NetflixException;
    Map<String, List<ChapterRest>> getTvShowAndChapterOfAnActor(Long idActor) throws NetflixException;






}
