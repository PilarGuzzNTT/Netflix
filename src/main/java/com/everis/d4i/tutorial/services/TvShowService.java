package com.everis.d4i.tutorial.services;

import java.util.List;

import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.json.AwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;

public interface TvShowService {

	//List<TvShowRest> getTvShowsByCategory(Long categoryId) throws NetflixException;

	TvShowRest getTvShowById(Long id) throws NetflixException;
	
	TvShowRest insertNewCategory(Long idShow, Long idCategory) throws NetflixException;
	
	TvShowRest updateNameTvShow(Long idShow, String name) throws NetflixException;
	
	Boolean deleteTvShow(Long idShow) throws NetflixException;
	
	List<AwardRest> getAwardsByTvShowId(Long id) throws NetflixException;



}
