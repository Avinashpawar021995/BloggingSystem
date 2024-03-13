package com.org.Blog.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class JwtAuthResponse {
	private String token;
	private UserDto userDto;
}
