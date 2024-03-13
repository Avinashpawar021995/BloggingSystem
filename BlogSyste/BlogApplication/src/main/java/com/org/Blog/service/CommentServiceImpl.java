package com.org.Blog.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.Blog.entity.Comment;
import com.org.Blog.entity.Post;
import com.org.Blog.exceptions.ResourceNotFoundException;
import com.org.Blog.payloads.CommendDto;
import com.org.Blog.respositry.CommentRepo;
import com.org.Blog.respositry.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommendDto createComment(CommendDto commendDto, Long postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post  Id", postId));
		Comment comment = this.modelMapper.map(commendDto, Comment.class);

		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommendDto.class);
	}

	@Override
	public void deleteComment(Long CommendId) {

		Comment comment = this.commentRepo.findById(CommendId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "commentId", CommendId));

		this.commentRepo.delete(comment);

	}

}
