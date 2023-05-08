package com.everis.d4i.tutorial.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.d4i.tutorial.entities.Actor;
import com.everis.d4i.tutorial.entities.Chapter;
import com.everis.d4i.tutorial.exceptions.InternalServerErrorException;
import com.everis.d4i.tutorial.exceptions.NetflixException;
import com.everis.d4i.tutorial.exceptions.NotFoundException;
import com.everis.d4i.tutorial.json.ActorRest;
import com.everis.d4i.tutorial.json.ChapterRest;
import com.everis.d4i.tutorial.repositories.ActorRepository;
import com.everis.d4i.tutorial.repositories.ChapterRepository;
import com.everis.d4i.tutorial.services.ActorService;
import com.everis.d4i.tutorial.utils.constants.ExceptionConstants;

@Service
public class ActorServiceImpl implements ActorService {

	@Autowired
    private ActorRepository actorRepository;
	
	@Autowired
    private ChapterRepository chapterRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<ActorRest> getActors() throws NetflixException {
		
		try {
            return actorRepository.findAll().stream().map(actor -> modelMapper.map(actor, ActorRest.class))
                    .collect(Collectors.toList());

        } catch (final Exception e) {
            throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
        }
	}

	@Override
	public ActorRest getActor(Long id) throws NetflixException {
		try {
			return modelMapper.map(actorRepository.getOne(id), ActorRest.class);
		} catch (EntityNotFoundException entityNotFoundException) {
			throw new NotFoundException(entityNotFoundException.getMessage());
		}
	}

	@Override
	public ActorRest createActor(ActorRest actorRest) throws NetflixException {
		
		Actor actor = modelMapper.map(actorRest, Actor.class);
        try {
            actor = actorRepository.save(actor);
        } catch (final Exception e) {
            throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
        }
        return modelMapper.map(actor, ActorRest.class);
	}

	@Override
	public ActorRest updateActor(Long id, ActorRest actorRest) throws NetflixException {
		
		Actor actor = actorRepository.findById(id).orElse(null);
		
		if (actorRest.getName() != null || !actorRest.getName().equals("") ) {
			actor.setName(actorRest.getName());
		}
        if (actorRest.getSurname() != null) {
        	actor.setSurname(actorRest.getSurname());
        }
        if (actorRest.getAge() != null) {
        	actor.setAge(actorRest.getAge());   	
        }
        if (actorRest.getNationality() != null) {
        	actor.setNationality(actorRest.getNationality());
        	
        }
        

		actorRepository.save(actor);
		
		return modelMapper.map(actor, ActorRest.class);
	}

	@Override
	public Boolean deleteActor(Long id) throws NetflixException {
		Actor actor = actorRepository.findById(id).orElseThrow(() -> 
			new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_ACTOR));
		actorRepository.delete(actor);
        
        if(actorRepository.findById(id).orElse(null) != null) {
        	return false;
        	
        }
		return true;
		
	}

	
	/**
	 * Añade un capitulo a la lista del actor
	 * Comprueba si ambos existen, si la lista esta a nulo crea una nueva
	 * Compreba si la lista ya contiene el capitulo
	 */
	@Override
	public ChapterRest addChapterActor(Long idActor, Long idChapter) throws NetflixException {
		Actor actor = actorRepository.findById(idActor).orElseThrow(() -> 
			new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_ACTOR));	
		Chapter chapter = chapterRepository.findById(idChapter).orElseThrow(() -> 
			new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_CHAPTER));
		
		
		if(chapter.getActors() == null) {
			List<Actor> actors = new ArrayList<Actor>();
			chapter.setActors(actors);
		}
		if(!chapter.getActors().contains(actor)) {
			
			chapter.getActors().add(actor);
		}
		
		chapterRepository.save(chapter);
		
		return modelMapper.map(chapter, ChapterRest.class);
	}

	@Override
	public Map<String, List<ChapterRest>> getTvShowAndChapterOfAnActor(Long idActor) throws NetflixException {
		
		Actor actor = actorRepository.findById(idActor).orElseThrow(() -> 
		new NotFoundException(ExceptionConstants.MESSAGE_INEXISTENT_ACTOR));
		Map<String, List<ChapterRest>> list = new HashMap<>();
		
		if(actor.getChapters() == null) {
			 List<Chapter> c = new ArrayList<Chapter>();
			 actor.setChapters(c);
		}
		
        List<Chapter> chapters = actor.getChapters();
        
        for(Chapter chap : chapters){
            //TvShowRest tvSR =  modelMapper.map(chap.getSeason().getTvShow(), TvShowRest.class);
        	String nameTvShow = chap.getSeason().getTvShow().getName();
            if(list.containsKey(nameTvShow)){
                list.get(nameTvShow).add(modelMapper.map(chap, ChapterRest.class));
            }else{
                List<ChapterRest> listChapters = new ArrayList<>();
                listChapters.add(modelMapper.map(chap, ChapterRest.class));
                list.put(nameTvShow , listChapters);
            }

        }

        return list;
	}

}
