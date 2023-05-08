package com.everis.d4i.tutorial.controllers;

import java.util.List;
import java.util.Map;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ChapterRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;

public interface ActorController {
	
	NetflixResponse<List<ActorRest>> getAllActors() throws NetflixException;
    NetflixResponse <ActorRest> getOneActor(Long id) throws NetflixException;

    NetflixResponse<ActorRest> createActor(ActorRest actorRest) throws NetflixException;
    NetflixResponse<ActorRest> updateActor(Long id, ActorRest actorRest) throws NetflixException;
    NetflixResponse<ActorRest> deleteActor(Long id) throws NetflixException;
    NetflixResponse <ChapterRest> addChapterActor(Long idActor, Long idChapter) throws NetflixException;
    NetflixResponse<Map<String, List<ChapterRest>>> getTvShowAndChapterByActor(Long idActor)
            throws NetflixException;

}
