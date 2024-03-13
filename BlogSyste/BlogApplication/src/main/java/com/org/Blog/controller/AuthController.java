package com.org.Blog.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.Blog.entity.Role;
import com.org.Blog.entity.User;
import com.org.Blog.exceptions.ApiException;
import com.org.Blog.payloads.JwtAuthRequest;
import com.org.Blog.payloads.JwtAuthResponse;
import com.org.Blog.payloads.UserDto;
import com.org.Blog.respositry.RoleRepository;
import com.org.Blog.respositry.UserRespository;
import com.org.Blog.security.JwtTokenHelper;
import com.org.Blog.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRespository userRespository;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception

	{
		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUserDto(this.mapper.map((User) userDetails, UserDto.class));

		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new ApiException("user is  disabled");
		}
	}

	// register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> resterUser(@Valid @RequestBody UserDto userDto) {
		UserDto regsiteredUser = this.userService.registerNewUSer(userDto);
		return new ResponseEntity<UserDto>(regsiteredUser, HttpStatus.CREATED);
	}

//	//@Autowired
//	//private ModelMapper mapper;

	@GetMapping("/current-user/")
	public ResponseEntity<UserDto> getUser(Principal principal) {
		// System.out.println(principal.getName());
		User user = this.userRespository.findByEmail(principal.getName()).get();

		return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
	}

}
