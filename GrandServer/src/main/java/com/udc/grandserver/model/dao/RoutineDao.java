package com.udc.grandserver.model.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.udc.grandserver.model.entities.Routine;

public interface RoutineDao extends PagingAndSortingRepository<Routine, Long> {

	List<Routine> findByUserId(Long userId);
	
}