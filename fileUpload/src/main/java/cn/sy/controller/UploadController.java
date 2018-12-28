package cn.sy.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

	/*
	 * request:
	 *     http://localhost:8080/
	 */
    @ResponseBody
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
		System.out.println("UploadController upload. getOriginalFilename=" + file.getOriginalFilename());
		System.out.println("UploadController upload. getName=" + file.getName());
		System.out.println("UploadController upload. getSize=" + file.getSize());

		String dir = "fileserver";
		Path uploadPath = Paths.get("D:", dir, "upload");
		System.out.println("save to path: " + uploadPath.toAbsolutePath());
		Path savedPath = Paths.get(uploadPath.toString(), file.getOriginalFilename());
		try {
			if(Files.exists(uploadPath)) {
				if(Files.isDirectory(uploadPath)) {
				}
				else {
					System.out.println("upload failed. " + uploadPath.toString() + " is not directory");
					return "upload failed." ;
				}
			}
			else {
				Files.createDirectories(uploadPath);
			}
			System.out.println("save to " + savedPath.toAbsolutePath().toString());
			file.transferTo(savedPath.toFile());
			System.out.println("upload success.");
		} catch (Exception e) {
			System.out.println("upload failed.");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
    	return "upload success. save to " + savedPath.toAbsolutePath().toString();
    }


}
