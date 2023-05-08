package com.everis.d4i.tutorial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.everis.d4i.tutorial.entities.TvShow;

@Repository
public interface TvShowRepository extends JpaRepository<TvShow, Long> {

	//List<TvShow> findByCategoryId(Long categoryId);

}
