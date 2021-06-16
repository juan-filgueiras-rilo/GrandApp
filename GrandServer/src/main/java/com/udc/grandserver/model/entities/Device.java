package com.udc.grandserver.model.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.udc.grandserver.rest.dtos.DeviceDto;

@Entity
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDevice;
	private String name;
	private String description;
	private Long userId;
	private String url;
	private Long puerto;
	private String tipo;
	
	@ManyToMany (mappedBy = "deviceList")
	private List<Routine> routines;
	
	public Device() {
		super();
	}

	public Device(
			final Long id,
			final String name,
			final String description,
			final Long userId,
			final String url,
			final Long puerto,
			final String tipo) {
		super();
		this.idDevice = id;
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.url = url;
		this.puerto = puerto;
		this.tipo = tipo;
	}

	public Device(DeviceDto device) {
		super();
		this.idDevice = device.getId();
		this.name = device.getName();
		this.description = device.getDescription();
		this.userId = device.getUserId();
		this.url = device.getUrl();
		this.puerto = device.getPuerto();
		this.tipo = device.getTipo();		
	}

	public Long getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(Long idDevice) {
		this.idDevice = idDevice;
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

	public List<Routine> getRoutines() {
		return routines;
	}

	public void setRoutines(List<Routine> routines) {
		this.routines = routines;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPuerto() {
		return puerto;
	}

	public void setPuerto(Long puerto) {
		this.puerto = puerto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
