package com.genius.znzx.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.genius.znzx.common.upload.UploadConfig.UploadConfigProperties;

/**
 * 最简单文件上传
 * 在UploadConfig控制上传限制
 * @author fangxing.peng
 *
 */
@Controller
//@RestController 返回值是string 时不能用这个 他会直接返回json 而不是跳转页面 =@Controller+@ResponseBody
@RequestMapping("/hello")
public class Upload {
	@Autowired
	private UploadConfigProperties properties;
	
	@RequestMapping(value="/upload.do",method=RequestMethod.POST)
	public ModelAndView testThy(@RequestParam("file") MultipartFile file, HttpServletRequest request){
		ModelAndView model = new ModelAndView("upload");	
		if(!file.isEmpty()){
            try {
               String location = properties.getLocation();
               File f = new File(location+"source/");
               if (!f.exists()) {
            	   f.mkdirs();
               }
               File file2 = new File(f,file.getOriginalFilename());;
               BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file2));
               out.write(file.getBytes());
               out.flush();
               out.close();

            }catch(FileNotFoundException e) {
               e.printStackTrace();
            }catch (IOException e) {
               e.printStackTrace();
            }
            model.addObject("tip", "上传成功");
		}else{
			model.addObject("tip", "上传失败");
		}
		return model;
	}
	
	@RequestMapping(value="/toUpload.do",method=RequestMethod.GET)
	public ModelAndView toUpload(){
		ModelAndView model = new ModelAndView("upload");
		return model;
	}
}
