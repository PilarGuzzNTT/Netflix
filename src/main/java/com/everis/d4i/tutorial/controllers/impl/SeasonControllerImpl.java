package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.SeasonController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.SeasonRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.SeasonService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

@RestController
@RequestMapping( RestConstants.RESOURCE_SEASON)
public class SeasonControllerImpl implements SeasonController {

	@Autowired
	private SeasonService seasonService;

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<List<SeasonRest>> getSeasonsByTvShow(Long tvShowId) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				seasonService.getSeasonsByTvShow(tvShowId));
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_NUMBER, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<SeasonRest> getSeasonByTvShowIdAndSeasonNumber(@PathVariable Long tvShowId,
			@PathVariable short number) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				seasonService.getSeasonByTvShowIdAndSeasonNumber(tvShowId, number));
	}

}
