package com.everis.d4i.tutorial.controllers;

import java.util.List;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.AwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.responses.NetflixResponse;

public interface TvShowController {

	//NetflixResponse<List<TvShowRest>> getTvShowsByCategory(Long categoryId) throws NetflixException;

	NetflixResponse<TvShowRest> getTvShowById(Long id) throws NetflixException;
	
	NetflixResponse<TvShowRest> addCategoryInTvShow(Long idShow,Long idCategory) throws NetflixException;

	NetflixResponse<TvShowRest> updateNameTvShow(Long id, String name) throws NetflixException;
	
	NetflixResponse<TvShowRest> deleteTvShow(Long id) throws NetflixException;
	
	NetflixResponse<List<AwardRest>> getAwardsByTvShowId(Long id) throws NetflixException;



}
