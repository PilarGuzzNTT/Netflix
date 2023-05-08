package com.everis.d4i.tutorial.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everis.d4i.tutorial.controllers.TvShowController;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.AwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;
import com.everis.d4i.tutorial.services.TvShowService;
import com.everis.d4i.tutorial.utils.constants.CommonConstants;
import com.everis.d4i.tutorial.utils.constants.RestConstants;

@RestController
@RequestMapping( RestConstants.RESOURCE_TV_SHOW)
public class TvShowControllerImpl implements TvShowController {

	@Autowired
	private TvShowService tvShowService;

//	@Override
//	@ResponseStatus(HttpStatus.OK)
//	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//	public NetflixResponse<List<TvShowRest>> getTvShowsByCategory(@RequestParam Long categoryId)
//			throws NetflixException {
//		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
//				tvShowService.getTvShowsByCategory(categoryId));
//	}

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = RestConstants.RESOURCE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<TvShowRest> getTvShowById(@PathVariable Long id) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				tvShowService.getTvShowById(id));
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(path = "/{idShow}/addcategory/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<TvShowRest> addCategoryInTvShow(@PathVariable Long idShow, @PathVariable Long idCategory) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                tvShowService.insertNewCategory(idShow, idCategory));
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(path = "/{idShow}/updateName", produces = MediaType.APPLICATION_JSON_VALUE)
	public NetflixResponse<TvShowRest> updateNameTvShow(@PathVariable Long idShow, @RequestBody String name) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
                tvShowService.updateNameTvShow(idShow, name));
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = RestConstants.RESOURCE_ID)
	public NetflixResponse<TvShowRest> deleteTvShow(@PathVariable Long id) throws NetflixException {
		tvShowService.deleteTvShow(id);
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.NO_CONTENT), CommonConstants.OK);
	}

	@Override
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(path = "/{id}/awards")
	public NetflixResponse<List<AwardRest>> getAwardsByTvShowId(@PathVariable Long id) throws NetflixException {
		return new NetflixResponse<>(CommonConstants.SUCCESS, String.valueOf(HttpStatus.OK), CommonConstants.OK,
				tvShowService.getAwardsByTvShowId(id));
	}

}
