package com.everis.d4i.tutorial.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.entities.Award;
import com.everis.d4i.tutorial.entities.Category;
import com.everis.d4i.tutorial.entities.TvShow;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.AwardRest;
import com.everis.d4i.tutorial.json.TvShowRest;
import com.everis.d4i.tutorial.repositories.CategoryRepository;
import com.everis.d4i.tutorial.repositories.TvShowRepository;
import com.everis.d4i.tutorial.services.TvShowService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
public class TvShowServiceImpl implements TvShowService {

	@Autowired
	private TvShowRepository tvShowRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	private ModelMapper modelMapper = new ModelMapper();

//	@Override
//	public List<TvShowRest> getTvShowsByCategory(Long categoryId) throws NetflixException {
//
//		return tvShowRepository.findByCategoryId(categoryId).stream()
//				.map(tvShow -> modelMapper.map(tvShow, TvShowRest.class)).collect(Collectors.toList());
//
//	}

	@Override
	public TvShowRest getTvShowById(Long id) throws NetflixException {

		try {
			return modelMapper.map(tvShowRepository.getOne(id), TvShowRest.class);
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new NotFoundException(entityNotFoundException.getMessage());
		}

	}

	@Override
	public TvShowRest insertNewCategory(Long idShow, Long idCategory) throws NetflixException {
		try {

			TvShow tv = tvShowRepository.findById(idShow).orElse(null);
			Category cat = categoryRepository.findById(idCategory).orElse(null);
			List<Category> list = tv.getCategories();

			if (!list.contains(cat)) {

				list.add(cat);
				tv.setCategories(list);
				tvShowRepository.save(tv);
				return modelMapper.map(tv, TvShowRest.class);

			} else {

				throw new NotFoundException("The TvShow already has the category");
			}

		} catch (EntityNotFoundException entityNotFoundException) {

			throw new NotFoundException(entityNotFoundException.getMessage());
		}
	}

	@Override
	public TvShowRest updateNameTvShow(Long idShow, String name) throws NetflixException {

		TvShow tv = tvShowRepository.findById(idShow)
				.orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW));
		tv.setName(name);
		tvShowRepository.save(tv);
		return modelMapper.map(tv, TvShowRest.class);
	}

	@Override
	public Boolean deleteTvShow(Long idShow) throws NetflixException {

		TvShow tv = tvShowRepository.findById(idShow)
				.orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW));
		tvShowRepository.delete(tv);

		if (tvShowRepository.findById(idShow).orElse(null) != null) {
			return false;

		}
		return true;
	}

	@Override
	public List<AwardRest> getAwardsByTvShowId(Long idShow) throws NetflixException {
		TvShow tv = tvShowRepository.findById(idShow)
				.orElseThrow(() -> new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_TVSHOW));

		List<Award> listA = tv.getAwards();
		return listA.stream().map(award -> modelMapper.map(award, AwardRest.class)).collect(Collectors.toList());

	}

}
