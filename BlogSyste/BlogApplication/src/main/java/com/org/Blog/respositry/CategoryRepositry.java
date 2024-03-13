package com.org.Blog.respositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.Blog.entity.Category;

@Repository
public interface CategoryRepositry extends JpaRepository<Category, Long> {

}
