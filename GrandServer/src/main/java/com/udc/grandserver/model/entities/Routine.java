package com.udc.grandserver.model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.udc.grandserver.rest.dtos.DeviceConversor;
import com.udc.grandserver.rest.dtos.DeviceDto;
import com.udc.grandserver.rest.dtos.RoutineDto;

@Entity
public class Routine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRoutine;
	private String name;
	private String description;
	private Long userId;
	private Integer hour;
	private Integer minute;
	
	@ElementCollection(targetClass=DiaEnum.class)
    @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
    @CollectionTable(name="routine_dia")
    @Column(name="dias") // Column name in person_interest
	private List<DiaEnum> dias;
	
	@JoinTable(
			  name = "Routine_Device", 
			  joinColumns = @JoinColumn(name = "idRoutine"), 
			  inverseJoinColumns = @JoinColumn(name = "idDevice"))
	@ManyToMany(cascade = CascadeType.MERGE)
	private List<Device> deviceList;
	
	
	public Routine() {
		super();
	}

	public Routine(Long id, String name, String description, List<Device> deviceList, Long userId, Integer hour, Integer minute, List<DiaEnum> dias) {
		super();
		this.idRoutine = id;
		this.name = name;
		this.description = description;
		this.deviceList = deviceList;
		this.userId = userId;
		this.hour = hour;
		this.minute = minute;
		this.dias = dias;
	}

	public Routine(RoutineDto routine) {
		super();
		this.idRoutine = routine.getId();
		this.name = routine.getName();
		this.description = routine.getDescription();
		ArrayList<Device> list = new ArrayList<Device>();
		for (DeviceDto dev : routine.getDeviceList()) {
			list.add(DeviceConversor.toDevice(dev));
		}
		this.deviceList = list;
		this.userId = routine.getUserId();
		this.hour = routine.getHour();
		this.minute = routine.getMinute();
		this.dias = routine.getDias();
	}

	public Long getIdRoutine() {
		return idRoutine;
	}

	public void setIdRoutine(Long idRoutine) {
		this.idRoutine = idRoutine;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
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
