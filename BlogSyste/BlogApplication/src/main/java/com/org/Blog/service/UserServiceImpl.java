package com.org.Blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.Blog.confign.AppConstants;
import com.org.Blog.entity.Role;
import com.org.Blog.entity.User;
import com.org.Blog.exceptions.ResourceNotFoundException;
import com.org.Blog.payloads.UserDto;
import com.org.Blog.respositry.RoleRepository;
import com.org.Blog.respositry.UserRespository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRespository userRespository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		/*
		 * user.setId(userDto.getId()); user.setUsername(userDto.getName());
		 * user.setEmail(userDto.getEmail()); user.setAbout(userDto.getAbout());
		 * user.setPassword(userDto.getPassword());
		 */
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		/*
		 * userDto.setId(user.getId()); userDto.setName(user.getUsername());
		 * userDto.setEmail(user.getEmail()); userDto.setPassword(user.getPassword());
		 * userDto.setAbout(user.getAbout());
		 */
		return userDto;
	}

	@Override
	public List<UserDto> getUser() {

		List<User> users12 = this.userRespository.findAll();
		List<UserDto> userDtos = users12.stream().map(users -> this.userToDto(users)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public UserDto setUser(UserDto user) {
		User user2 = this.dtoToUser(user);
		User savedUser = this.userRespository.save(user2);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto user, Long id) {
		User userid = this.userRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("USer", "id", id));
		userid.setName(user.getName());
		userid.setEmail(user.getEmail());
		userid.setPassword(user.getPassword());
		userid.setAbout(user.getAbout());
		User updateUser = this.userRespository.save(userid);
		UserDto userDto1 = userToDto(updateUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Long id) {
		User userid = this.userRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("USer", "id", id));
		return this.userToDto(userid);
	}

	@Override
	public void userDeleted(Long id) {
		User user = this.userRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
		this.userRespository.delete(user);

	}

	@Override
	public UserDto registerNewUSer(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		// enconded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepository.findById(AppConstants.NORMLA_USER).get();
		user.getRoles().add(role);
		User userSave = userRespository.save(user);

		return this.modelMapper.map(userSave, UserDto.class);
	}

}
