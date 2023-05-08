package com.everis.d4i.tutorial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.everis.d4i.tutorial.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {

}
