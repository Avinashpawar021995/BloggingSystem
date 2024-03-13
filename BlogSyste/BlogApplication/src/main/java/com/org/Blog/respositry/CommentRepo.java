package com.org.Blog.respositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.Blog.entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

}
