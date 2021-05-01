package com.udc.grandserver.rest.dtos;

import java.util.List;

public class RoutineDto {

	private Long id;
	private String name;
	private String description;
	private List<DeviceDto> deviceList;
	
	public RoutineDto() {
		super();
	}
	
	public RoutineDto(Long id, String name, String description, List<DeviceDto> deviceList) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.deviceList = deviceList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descriptionm) {
		this.description = descriptionm;
	}

	public List<DeviceDto> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<DeviceDto> deviceList) {
		this.deviceList = deviceList;
	}

	
	
}
