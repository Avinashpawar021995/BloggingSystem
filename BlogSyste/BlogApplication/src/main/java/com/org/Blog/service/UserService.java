package com.org.Blog.service;

import java.util.List;

import com.org.Blog.payloads.UserDto;

public interface UserService {

	UserDto registerNewUSer(UserDto userDto);

	// User setUser(User user);

	List<UserDto> getUser();

	UserDto setUser(UserDto user);

	UserDto updateUser(UserDto user, Long id);

	UserDto getUserById(Long id);

	void userDeleted(Long id);

}
