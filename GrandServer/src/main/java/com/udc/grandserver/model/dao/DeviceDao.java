package com.udc.grandserver.model.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.udc.grandserver.model.entities.Device;

public interface DeviceDao extends PagingAndSortingRepository<Device, Long> {

	List<Device> findByUserId(Long userId);
	
}
