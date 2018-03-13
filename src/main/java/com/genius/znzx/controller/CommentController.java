package com.genius.znzx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.genius.znzx.entity.Comment;
import com.genius.znzx.service.CommentService;

/**
 * 评论盖楼demo
 * @author fangxing.peng
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@Value("${test.pfx}")  
	private String pfx;
	
	@RequestMapping("/get.shtml")
	public ModelAndView getCommentById(){
		ModelAndView model = new ModelAndView("comment");
		List<Comment> commentList=this.commentService.getCommentListById();
		model.addObject("commentList", commentList);
		return model;
	}
	
	@RequestMapping("/getJson.shtml")
	@ResponseBody
	public List<Comment> getCommentJsonById(){
		List<Comment> commentList=this.commentService.getCommentListById();	
		return commentList;
	}
	

	@RequestMapping("/testRedis.shtml")
	@ResponseBody
	public String testRedis(){
		System.out.println(pfx);
		return "123";
	}
}
