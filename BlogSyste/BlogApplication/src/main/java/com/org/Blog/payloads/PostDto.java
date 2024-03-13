package com.org.Blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostDto {

	private Long postId;

	private String title;

	private String content;

	private String imageName;

	private Date addDate;

	private UserDto user;

	private CategoryDto category;

	private Set<CommendDto> commnents = new HashSet<>();

}
