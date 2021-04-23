package com.udc.grandserver.model.services;

import com.udc.grandserver.model.entities.User;
import com.udc.grandserver.model.exceptions.DuplicateInstanceException;
import com.udc.grandserver.model.exceptions.IncorrectLoginException;
import com.udc.grandserver.model.exceptions.IncorrectPasswordException;
import com.udc.grandserver.model.exceptions.InstanceNotFoundException;

public interface UserService {
	
	void signUp(User user) throws DuplicateInstanceException;
	
	User login(String email, String password) throws IncorrectLoginException;
	
	User loginFromId(Long id) throws InstanceNotFoundException;
	
	User updateProfile(Long id, String email) throws InstanceNotFoundException;
	
	void changePassword(Long id, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException;

}
