package com.udc.grandserver.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udc.grandserver.rest.dtos.DeviceDto;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	@GetMapping("/getDevicesByUserId/{id}")
	public List<DeviceDto> getDevicesByUserId(@PathVariable Long id) {
		
		DeviceDto device = new DeviceDto(1l, "Bombilla", "Bombilla chachi");
		
		List<DeviceDto> retval = new ArrayList<DeviceDto>();
		retval.add(device);
		
		return retval;
	}
}
