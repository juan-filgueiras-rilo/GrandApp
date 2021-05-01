package com.udc.grandserver.rest.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udc.grandserver.rest.dtos.DeviceDto;
import com.udc.grandserver.rest.dtos.RoutineDto;


@RestController
@RequestMapping("/routines")
public class RoutineController {

	@GetMapping("/getRoutinesByUserId/{id}")
	public List<RoutineDto> getRoutinesByUserId(@PathVariable Long id) {
		
		DeviceDto device = new DeviceDto(1l, "Bombilla", "Bombilla chachi");
		RoutineDto routine = new RoutineDto(1l, "Rutinita", "Rutina guay", Arrays.asList(device));
		
		List<RoutineDto> retval = new ArrayList<RoutineDto>();
		retval.add(routine);
		
		return retval;
	}
}
