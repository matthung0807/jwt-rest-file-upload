package com.foyatech.iot.web.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value="/api/test")
public class TestController {
	
	public static final String FILE_PATH = "D:/mytemp/test";
	
	/**
	 * 測試用,任何人皆可呼叫
	 * @return
	 */
	@GetMapping(value="/string", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String getString(){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objNode1 = mapper.createObjectNode();
		objNode1.put("test", "hello world");
		
    return objNode1.toString();
  }
	
	/**
	 * 取得上傳檔案並存至指定的檔案路徑,傳入參數file為前端傳來的檔案
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value="/upload", produces=MediaType.TEXT_PLAIN_VALUE)
  public String upload(@RequestParam("file") MultipartFile file) throws IOException{
    if (!file.getOriginalFilename().isEmpty()) {
      BufferedOutputStream outputStream = 
      		new BufferedOutputStream(
            new FileOutputStream(new File(FILE_PATH, file.getOriginalFilename())));
      outputStream.write(file.getBytes());
      outputStream.flush();
      outputStream.close();
    }else{
      return "fail";
    }
		return "success";
  }

}
