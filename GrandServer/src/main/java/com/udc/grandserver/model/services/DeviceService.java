package com.udc.grandserver.model.services;

import java.util.List;

import com.udc.grandserver.model.entities.Device;

public interface DeviceService {

	List<Device> getDevicesByUserId(Long id);

	Device createDevice(Device device);

	Device updateDevice(Long id, String nombre, String descripcion);

	void deleteDevice(Long id);

}
