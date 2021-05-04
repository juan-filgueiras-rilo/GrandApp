package com.udc.grandserver.rest.dtos;

import com.udc.grandserver.model.entities.Device;

public class DeviceConversor {

	private DeviceConversor() {}
	
	public static final DeviceDto toDeviceDto(Device device) {
		return new DeviceDto(device);
	}
	
	public static final Device toDevice(DeviceDto deviceDto) {		
		return new Device(deviceDto);
	}
	
}
