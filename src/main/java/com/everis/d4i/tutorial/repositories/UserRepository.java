package com.everis.d4i.tutorial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.everis.d4i.tutorial.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);

}
