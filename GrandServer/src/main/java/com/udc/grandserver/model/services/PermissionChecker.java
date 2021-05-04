package com.udc.grandserver.model.services;

import com.udc.grandserver.model.entities.User;
import com.udc.grandserver.model.exceptions.InstanceNotFoundException;

public interface PermissionChecker {
	
	void checkUserExists(Long userId) throws InstanceNotFoundException;
	
	User checkUser(Long userId) throws InstanceNotFoundException;
	
}
