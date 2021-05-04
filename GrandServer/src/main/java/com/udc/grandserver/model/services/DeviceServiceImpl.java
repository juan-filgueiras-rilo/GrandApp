package com.udc.grandserver.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udc.grandserver.model.dao.DeviceDao;
import com.udc.grandserver.model.dao.RoutineDao;
import com.udc.grandserver.model.entities.Device;
import com.udc.grandserver.model.entities.Routine;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	RoutineDao routineDao;
	
	@Override
	public List<Device> getDevicesByUserId(Long id) {
		return this.deviceDao.findByUserId(id);
	}

	@Override
	public Device createDevice(Device device) {
		return this.deviceDao.save(device);
	}

	@Override
	public Device updateDevice(Long id, String nombre, String descripcion) {
		Device dev = this.deviceDao.findById(id).orElseThrow();
		
		dev.setName(nombre);
		dev.setDescription(descripcion);
		
		Device devSaved = this.deviceDao.save(dev);
		
		return devSaved;
	}

	//Magic code do not touch
	@Override
	public void deleteDevice(Long id) {
		List<Routine> routines = this.deviceDao.findById(id).orElseThrow().getRoutines();
		for (Routine routine : routines) {
			List<Device> devices = routine.getDeviceList();
			for (Device dev : devices) {
				if (dev.getIdDevice().equals(id)) {
					routine.getDeviceList().remove(dev);
				}
			}
			this.routineDao.save(routine);
		}
		
		this.deviceDao.deleteById(id);
	}


	
}
