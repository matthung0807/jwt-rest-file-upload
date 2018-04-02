package com.foyatech.iot.web.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

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

	@PostMapping(value="/json", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
  public String json(@RequestBody String jsonString){
		Type listType = new TypeToken<List<Map<String, String>>>() {}.getType();
		List<Map<String, String>> list = new Gson().fromJson(jsonString, listType);
		
		
		return "success";
  }
	
	@PostMapping(value="/txt", consumes=MediaType.TEXT_PLAIN_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
  public String txt(@RequestBody String text) throws IOException{
		String[] stringArray = text.split(System.lineSeparator());
		List<String> list = Arrays.asList(stringArray);
		for(String s : list) {
			System.out.println(s);
		}
		
		return "success";
  }
//	@PostMapping(value="/xml", produces=MediaType.TEXT_PLAIN_VALUE)
//  public String xml(@RequestParam("file") MultipartFile file) throws IOException{
//    if (!file.getOriginalFilename().isEmpty()) {
//      BufferedOutputStream outputStream = 
//      		new BufferedOutputStream(
//            new FileOutputStream(new File(FILE_PATH, file.getOriginalFilename())));
//      outputStream.write(file.getBytes());
//      outputStream.flush();
//      outputStream.close();
//    }else{
//      return "fail";
//    }
//		return "success";
//  }
//	@PostMapping(value="/excel", produces=MediaType.TEXT_PLAIN_VALUE)
//  public String excel(@RequestParam("file") MultipartFile file) throws IOException{
//    if (!file.getOriginalFilename().isEmpty()) {
//      BufferedOutputStream outputStream = 
//      		new BufferedOutputStream(
//            new FileOutputStream(new File(FILE_PATH, file.getOriginalFilename())));
//      outputStream.write(file.getBytes());
//      outputStream.flush();
//      outputStream.close();
//    }else{
//      return "fail";
//    }
//		return "success";
//  }
	
}
