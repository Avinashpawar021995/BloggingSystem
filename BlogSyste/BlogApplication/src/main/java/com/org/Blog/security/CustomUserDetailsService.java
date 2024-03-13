package com.org.Blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.org.Blog.entity.User;
import com.org.Blog.exceptions.ResourceNotFoundException;
import com.org.Blog.respositry.UserRespository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRespository respository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.respository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("user", "email ", username));

		return user;

	}

}
