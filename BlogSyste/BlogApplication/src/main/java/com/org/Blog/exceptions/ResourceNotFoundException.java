package com.org.Blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String findName;
	Long fieildValue;
	String username;

	public ResourceNotFoundException(String resourceName, String findName, Long fieildValue) {
		super(String.format("%s not  found with %s : %s", resourceName, findName, fieildValue));
		this.resourceName = resourceName;
		this.findName = findName;
		this.fieildValue = fieildValue;
	}

	public ResourceNotFoundException(String resourceName, String findName, String username) {
		super(String.format("%s not  found with %s : %s", resourceName, findName, username));
		this.resourceName = resourceName;
		this.findName = findName;
		this.username = username;

	}
}
