package com.udc.grandserver.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udc.grandserver.model.dao.UserDao;
import com.udc.grandserver.model.entities.User;
import com.udc.grandserver.model.exceptions.DuplicateInstanceException;
import com.udc.grandserver.model.exceptions.IncorrectLoginException;
import com.udc.grandserver.model.exceptions.IncorrectPasswordException;
import com.udc.grandserver.model.exceptions.InstanceNotFoundException;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void signUp(User user) throws DuplicateInstanceException {
		
		if (userDao.existsByUserName(user.getUserName())) {
			throw new DuplicateInstanceException("project.entities.user", user.getUserName());
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(User.RoleType.USER);
		
		userDao.save(user);
		
	}

	@Override
	@Transactional(readOnly=true)
	public User login(String email, String password) throws IncorrectLoginException {
		
		Optional<User> user = userDao.findByEmail(email);
		
		if (!user.isPresent()) {
			throw new IncorrectLoginException(email, password);
		}
		
		if (!passwordEncoder.matches(password, user.get().getPassword())) {
			throw new IncorrectLoginException(email, password);
		}
		
		return user.get();
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public User loginFromId(Long id) throws InstanceNotFoundException {
		return permissionChecker.checkUser(id);
	}

	@Override
	public User updateProfile(Long id, String email) throws InstanceNotFoundException {
		
		User user = permissionChecker.checkUser(id);
		
		user.setEmail(email);
		
		return user;

	}

	@Override
	public void changePassword(Long id, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException {
		
		User user = permissionChecker.checkUser(id);
		
		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new IncorrectPasswordException();
		} else {
			user.setPassword(passwordEncoder.encode(newPassword));
		}
		
	}

}
