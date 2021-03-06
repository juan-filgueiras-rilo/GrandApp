package com.udc.grandserver.rest.controllers;

import static com.udc.grandserver.rest.dtos.DeviceConversor.toDevice;
import static com.udc.grandserver.rest.dtos.DeviceConversor.toDeviceDto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udc.grandserver.model.entities.Device;
import com.udc.grandserver.model.services.DeviceService;
import com.udc.grandserver.rest.dtos.DeviceDto;
import com.udc.grandserver.rest.dtos.IdDto;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;
	
	@GetMapping("/getDevicesByUserId")
	public List<DeviceDto> getDevicesByUserId(
			@RequestAttribute Long userId) {
				
		List<DeviceDto> retval = new ArrayList<DeviceDto>();
		List<Device> list = this.deviceService.getDevicesByUserId(userId);
		
		for (Device dev : list) {
			retval.add(toDeviceDto(dev));
		}
		
		return retval;
	}
	
	@PostMapping("/create")
	public DeviceDto createDevice(
			@RequestAttribute Long userId,
			@Validated({DeviceDto.AllValidations.class}) @RequestBody DeviceDto deviceDto) {
		deviceDto.setUserId(userId);
		return toDeviceDto(this.deviceService.createDevice(toDevice(deviceDto)));
		
	}
	
	@PutMapping()
	public DeviceDto updateDevice(
			@RequestAttribute Long userId,
			@Validated({DeviceDto.UpdateValidations.class}) @RequestBody DeviceDto deviceDto) {
		
		return toDeviceDto(this.deviceService.updateDevice(deviceDto.getId(), deviceDto.getName(), deviceDto.getDescription()));
		
	}
	
	@DeleteMapping()
	public void deleteDevice(
			@RequestAttribute Long userId,
			@RequestBody IdDto idDto) {
		
		this.deviceService.deleteDevice(idDto.getId());
	}
}
