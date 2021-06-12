package com.udc.grandserver.rest.dtos;

import java.util.ArrayList;
import java.util.List;

import com.udc.grandserver.model.entities.Device;
import com.udc.grandserver.model.entities.DiaEnum;
import com.udc.grandserver.model.entities.Routine;

public class RoutineDto {

	public interface AllValidations {}
	
	public interface UpdateValidations {}
	
	private Long id;
	private String name;
	private String description;
	private List<DeviceDto> deviceList;
	private Long userId;
	private Integer hour;
	private Integer minute;
	private List<DiaEnum> dias;
	
	public RoutineDto() {
		super();
	}
	
	public RoutineDto(Long id, String name, String description, List<DeviceDto> deviceList, Long userId, List<DiaEnum> dias) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.deviceList = deviceList;
		this.userId = userId;
		this.dias = dias;
	}
	
	public RoutineDto(Routine routine) {
		super();
		this.id = routine.getIdRoutine();
		this.name = routine.getName();
		this.description = routine.getDescription();
		ArrayList<DeviceDto> list = new ArrayList<DeviceDto>();
		for (Device dev : routine.getDeviceList()) {
			list.add(DeviceConversor.toDeviceDto(dev));
		}
		this.deviceList = list;
		this.userId = routine.getUserId();
		this.hour = routine.getHour();
		this.minute = routine.getMinute();
		this.dias = routine.getDias();
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public List<DiaEnum> getDias() {
		return dias;
	}

	public void setDias(List<DiaEnum> dias) {
		this.dias = dias;
	}

}
