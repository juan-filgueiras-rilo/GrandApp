package com.udc.grandserver.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udc.grandserver.model.dao.RoutineDao;
import com.udc.grandserver.model.entities.Device;
import com.udc.grandserver.model.entities.Routine;
import com.udc.grandserver.rest.dtos.DeviceConversor;
import com.udc.grandserver.rest.dtos.DeviceDto;

@Service
@Transactional
public class RoutineServiceImpl implements RoutineService {

	@Autowired
	RoutineDao routineDao;

	@Override
	public List<Routine> getRoutinesByUserId(Long id) {
		return this.routineDao.findByUserId(id);
	}

	@Override
	public Routine createRoutine(Routine routine) {
		return this.routineDao.save(routine);
	}

	
	@Override
	public void deleteRoutine(Long id) {
		this.routineDao.deleteById(id);
	}

	@Override
	public Routine updateRoutine(Long id, String name, String description, List<DeviceDto> deviceList) {
		Routine rout = this.routineDao.findById(id).orElseThrow();

		rout.setName(name);
		rout.setDescription(description);
		
		List<Device> list = new ArrayList<Device>();
		for (DeviceDto device : deviceList) {
			list.add(DeviceConversor.toDevice(device));
		}
		
		rout.setDeviceList(list);
		
		return this.routineDao.save(rout);
	}


}
