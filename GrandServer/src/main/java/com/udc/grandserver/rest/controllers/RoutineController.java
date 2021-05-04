package com.udc.grandserver.rest.controllers;

import static com.udc.grandserver.rest.dtos.RoutineConversor.toRoutine;
import static com.udc.grandserver.rest.dtos.RoutineConversor.toRoutineDto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udc.grandserver.model.entities.Routine;
import com.udc.grandserver.model.exceptions.PermissionException;
import com.udc.grandserver.model.services.RoutineService;
import com.udc.grandserver.rest.dtos.RoutineDto;

@RestController
@RequestMapping("/routines")
public class RoutineController {

	@Autowired
	private RoutineService routineService;
	
	@GetMapping("/getRoutinesByUserId/{id}")
	public List<RoutineDto> getRoutinesByUserId(
			@RequestAttribute Long userId,
			@PathVariable Long id) throws PermissionException {
		
		if (!id.equals(userId)) {
			throw new PermissionException();
		}
		
		List<RoutineDto> retval = new ArrayList<RoutineDto>();
		List<Routine> list = this.routineService.getRoutinesByUserId(id);
		
		for (Routine rout : list) {
			retval.add(toRoutineDto(rout));
		}
		return retval;
	}
	
	@PostMapping("/create")
	public RoutineDto createRoutine(
			@Validated({RoutineDto.AllValidations.class}) @RequestBody RoutineDto routineDto) {
		return toRoutineDto(this.routineService.createRoutine(toRoutine(routineDto)));		
	}
	
	@PutMapping()
	public RoutineDto updateRoutine(
			@RequestAttribute Long userId,
			@Validated({RoutineDto.UpdateValidations.class}) @RequestBody RoutineDto routineDto) throws PermissionException {
		
		if (!routineDto.getUserId().equals(userId)) {
			throw new PermissionException();
		}
		
		return toRoutineDto(this.routineService.updateRoutine(routineDto.getId(), routineDto.getName(), routineDto.getDescription(), routineDto.getDeviceList()));
	}
	
	@DeleteMapping()
	public void deleteRoutine(
			@RequestAttribute Long userId,
			@Validated({RoutineDto.UpdateValidations.class}) @RequestBody RoutineDto routineDto) throws PermissionException {
		if (!routineDto.getUserId().equals(userId)) {
			throw new PermissionException();
		}
		
		this.routineService.deleteRoutine(routineDto.getId());
	}
	
}
