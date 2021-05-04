package com.udc.grandserver.model.dao;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.udc.grandserver.model.entities.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	
	boolean existsByUserName(String userName);

	Optional<User> findByUserName(String userName);

	Optional<User> findByEmail(String email);
	
}
