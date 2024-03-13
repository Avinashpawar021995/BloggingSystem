package com.org.Blog.service;

import java.util.List;

import com.org.Blog.payloads.PostDto;
import com.org.Blog.payloads.PostResponse;

public interface PostService {

	// create

	PostDto createPost(PostDto postDto, Long UserId, Long categoryId);

	// update
	PostDto updatePost(PostDto postDto, Long postId);

	// delete
	void detelePost(Long postId);

	// get all posts
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get single post
	PostDto getPostById(Long postId);

	// get all post by category

	List<PostDto> getPostByCategory(Long categoryId);

	// get all post by user
	List<PostDto> getPostByUSer(Long userId);

	// get search post details
	List<PostDto> searchPosts(String keyword);

}
