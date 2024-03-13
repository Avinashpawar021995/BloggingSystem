package com.org.Blog.service;

import com.org.Blog.payloads.CommendDto;

public interface CommentService {
	CommendDto createComment(CommendDto commendDto, Long postId);

	void deleteComment(Long CommendId);

}
