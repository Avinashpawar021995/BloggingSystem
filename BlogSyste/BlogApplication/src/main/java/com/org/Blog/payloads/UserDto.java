package com.org.Blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private Long id;

	@NotEmpty
	@Size(min = 4, message = "user must be min of  4  characters")
	private String name;

	@Email(message = "Email address is in  valid !! ")
	@NotEmpty(message = "email is  required...")
	private String email;

	@NotEmpty
	private String about;

	// @Pattern
	@Size(min = 4, max = 15, message = "password must  be min of3 chars and max of 18 chars !!!")
	private String password;

	private Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
}
