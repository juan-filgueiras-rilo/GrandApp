package com.udc.grandserver.model.services;

import com.udc.grandserver.model.entities.User;
import com.udc.grandserver.model.exceptions.InstanceNotFoundException;

public interface PermissionChecker {
	
	public void checkUserExists(Long userId) throws InstanceNotFoundException;
	
	public User checkUser(Long userId) throws InstanceNotFoundException;
	
}
