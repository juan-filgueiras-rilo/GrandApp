package com.udc.grandserver.rest.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
	
	public interface AllValidations {}
	
	public interface UpdateValidations {}

	private Long id;
	private String userName;
	private String password;
	private String email;
	private String role;

	public UserDto() {}

	public UserDto(Long id, String userName, String email, String role) {

		this.id = id;
		this.userName = userName != null ? userName.trim() : null;
		this.email = email.trim();
		this.role = role;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(groups={AllValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class})
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	@NotNull(groups={AllValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class})
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	@Email(groups={AllValidations.class, UpdateValidations.class})
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
