package com.org.Blog.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws Exception {
		// file name
		String name = file.getOriginalFilename();
		// abc.png
		// random name generate file
		String radomID = UUID.randomUUID().toString();
		String fileName1 = radomID.concat(name.substring(name.lastIndexOf('.')));

		// full path
		String filePath = path + File.separator + fileName1;

		// create folder if not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return name;

		// return null;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		String fullPath = path + File.separator + filename;
		InputStream is = new FileInputStream(fullPath);
		// db logic to return input stream

		return is;

	}

}
