package com.udc.grandserver.rest.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.udc.grandserver.model.entities.Device;

public class DeviceDto {

	public interface AllValidations {}
	
	public interface UpdateValidations {}
	
	private Long id;
	private String name;
	private String description;
	private Long userId;

	public DeviceDto() {
		super();
	}

	public DeviceDto(
			final Long id,
			final String name,
			final String description,
			final Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public DeviceDto(Device device) {
		super();
		this.id = device.getIdDevice();
		this.name = device.getName();
		this.description = device.getDescription();
		this.userId = device.getUserId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
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

	@Override
	public String toString() {
		return "DeviceDto [id=" + id + ", name=" + name + ", description=" + description + ", userId=" + userId + "]";
	}

	
}
