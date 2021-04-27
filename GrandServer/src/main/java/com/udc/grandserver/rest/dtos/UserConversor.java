package com.udc.grandserver.rest.dtos;

import com.udc.grandserver.model.entities.User;

public class UserConversor {
	
	private UserConversor() {}
	
	public static final UserDto toUserDto(User user) {
		return new UserDto(user.getId(), user.getUserName(), user.getEmail(), user.getRole().toString());
	}
	
	public static final User toUser(UserDto userDto) {
		
		return new User(userDto.getUserName(), userDto.getPassword(), userDto.getEmail());
	}
	
	public static final AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {
		
		return new AuthenticatedUserDto(serviceToken, toUserDto(user));
		
	}

}
