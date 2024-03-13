package com.org.Blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.Blog.payloads.ApiResponse;
import com.org.Blog.payloads.CommendDto;
import com.org.Blog.service.CommentService;
import com.org.Blog.service.PostService;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private PostService postService;

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommendDto> createComment(@RequestBody CommendDto commendDto, @PathVariable Long postId) {
		CommendDto createComment = this.commentService.createComment(commendDto, postId);
		return new ResponseEntity<CommendDto>(createComment, HttpStatus.CREATED);

	}

	@DeleteMapping("/commenst/{commentID}")
	public ResponseEntity<ApiResponse> deletedComment(@PathVariable Long commentID) {
		this.commentService.deleteComment(commentID);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully !!", true), HttpStatus.OK);
	}

}
