package com.org.Blog.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	String uploadImage(String path, MultipartFile file) throws Exception;

	InputStream getResource(String path, String filename) throws FileNotFoundException;

}
