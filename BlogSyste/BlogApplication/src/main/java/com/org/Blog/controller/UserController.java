package com.org.Blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.Blog.payloads.ApiResponse;
import com.org.Blog.payloads.UserDto;
import com.org.Blog.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	UserService userService;

	// POST -CREATE USER
	// @PostMapping("/register")
	public ResponseEntity<UserDto> setUser(@Valid @RequestBody UserDto user) {
		UserDto createrUserDto = userService.setUser(user);
		return new ResponseEntity<>(createrUserDto, HttpStatus.CREATED);
	}

	// GET -USER GET
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUser() {

		return ResponseEntity.ok(this.userService.getUser());
	}

	// PUT -USER UPDATE
	@PutMapping("/update{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto user, @PathVariable Long id) {

		UserDto uodateUser = this.userService.updateUser(user, id);
		return ResponseEntity.ok(uodateUser);
	}

	// Get - user get id
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/userId{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	// delete -delete user

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> userDeleted(@PathVariable Long Id) {
		userService.userDeleted(Id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted  successfully", true), HttpStatus.OK);
	}

}
