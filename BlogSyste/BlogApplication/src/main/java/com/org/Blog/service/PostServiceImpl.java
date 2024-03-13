package com.org.Blog.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.org.Blog.entity.Category;
import com.org.Blog.entity.Post;
import com.org.Blog.entity.User;
import com.org.Blog.exceptions.ResourceNotFoundException;
import com.org.Blog.payloads.PostDto;
import com.org.Blog.payloads.PostResponse;
import com.org.Blog.respositry.CategoryRepositry;
import com.org.Blog.respositry.PostRepository;
import com.org.Blog.respositry.UserRespository;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRespository userRespository;
	@Autowired
	private CategoryRepositry categoryRepositry;

	@Override
	public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
		User user = this.userRespository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));
		Category category = this.categoryRepositry.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepository.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postId) {

		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
		Category category = this.categoryRepositry.findById(postDto.getCategory().getCategoryId()).get();

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatePost = this.postRepository.save(post);
		post.setCategory(category);
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void detelePost(Long postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));

		this.postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
		/*
		 * if(sortDir.equalsIgnoreCase("asc")) { sort =Sort.by(sortBy).ascending(); }
		 * else if(sortDir.equalsIgnoreCase("desc")) {
		 * sort=Sort.by(sortBy).descending(); }
		 */
		Pageable p = PageRequest.of(pageNumber, pageSize, sort); // here page
		// sortBy can you apply anscending and descending methods apply
		Page<Post> pagePost = this.postRepository.findAll(p);

		List<Post> allPosts = pagePost.getContent();
		// List<Post> posts=this.postRepository.findAll();
		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Long postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));

		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Long catgoryId) {
		Category category = this.categoryRepositry.findById(catgoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category ", "catagory_id", catgoryId));
		List<Post> postList = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = postList.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUSer(Long userId) {
		User user = this.userRespository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "user_id", userId));

		List<Post> postList = this.postRepository.findByUser(user);
		List<PostDto> postDtos = postList.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {

		List<Post> posts = this.postRepository.searchByTitle("%" + keyword + "%");
		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

}
