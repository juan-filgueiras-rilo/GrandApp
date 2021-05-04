package com.udc.grandserver.model.services;

import java.util.List;

import com.udc.grandserver.model.entities.Routine;
import com.udc.grandserver.rest.dtos.DeviceDto;

public interface RoutineService {

	List<Routine> getRoutinesByUserId(Long id);
	
	Routine createRoutine(Routine routine);
	
	Routine updateRoutine(Long id, String name, String description, List<DeviceDto> deviceList);
	
	void deleteRoutine(Long id);
	
}
