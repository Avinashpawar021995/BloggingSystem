package com.org.Blog.service;

import java.util.List;

import com.org.Blog.payloads.CategoryDto;

public interface CategoryService {

	// create
	public CategoryDto createCategory(CategoryDto categoryDto);

	// update
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryID);

	// delete
	public void deleteCategory(Long categoryID);

	// fetch
	public List<CategoryDto> getCategory();

	// fetch one id
	public CategoryDto categoryId(Long categoryID);
}
