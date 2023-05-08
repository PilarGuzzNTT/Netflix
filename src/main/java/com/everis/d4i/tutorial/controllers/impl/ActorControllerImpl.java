package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.ActorController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ChapterRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.ActorService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

@RestController
@RequestMapping( RestConstants.RESOURCE_ACTOR)
public class ActorControllerImpl implements ActorController {
	
	 @Autowired
	 private ActorService actorService;

	 
	@Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<List<ActorRest>> getAllActors() throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				actorService.getActors());
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
    @GetMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ActorRest> getOneActor(@PathVariable Long id) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                actorService.getActor(id));
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ActorRest> createActor(@RequestBody ActorRest actorRest) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.CREATED, String.valueOf(HttpStatus.CREATED), CommonConstants.CREATED,
                actorService.createActor(actorRest));
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
    @PutMapping(value = RestConstants.RESOURCE_ID,produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ActorRest> updateActor(@PathVariable Long id, @RequestBody ActorRest actorRest) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                actorService.updateActor(id, actorRest));
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = RestConstants.RESOURCE_ID,produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ActorRest> deleteActor(@PathVariable Long id) throws NetflixException {
		actorService.deleteActor(id);
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.NO_CONTENT), CommonConstants.OK);
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = RestConstants.RESOURCE_ID,produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<ChapterRest> addChapterActor(@PathVariable Long id, @RequestBody Long idChapter) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                actorService.addChapterActor(id, idChapter));
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
	 @GetMapping(path = "/{idActor}/tvShows", produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<Map<String, List<ChapterRest>>> getTvShowAndChapterByActor(@PathVariable Long idActor)
			throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                actorService.getTvShowAndChapterOfAnActor(idActor));
	}

}
