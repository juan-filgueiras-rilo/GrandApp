package com.udc.grandserver.rest.dtos;

import com.udc.grandserver.model.entities.Routine;

public class RoutineConversor {

private RoutineConversor() {}
	
	public static final RoutineDto toRoutineDto(Routine routine) {
		return new RoutineDto(routine);
	}
	
	public static final Routine toRoutine(RoutineDto routineDto) {		
		return new Routine(routineDto);
	}
}
