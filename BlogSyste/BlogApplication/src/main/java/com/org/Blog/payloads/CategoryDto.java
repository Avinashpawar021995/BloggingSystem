package com.org.Blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter

public class CategoryDto {
	private Long categoryId;
	@NotBlank
	@NotEmpty
	@Size(min = 4, message = "min size of  categoru title is 4")
	private String categoryTitle;
	@NotBlank
	@NotEmpty
	@Size(min = 10, message = "min size of  category description  10")
	private String categoryDescription;

}
