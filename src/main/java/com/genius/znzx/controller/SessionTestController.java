package com.genius.znzx.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.genius.znzx.entity.Comment;
import com.genius.znzx.service.CommentService;

@Controller
public class SessionTestController {
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("/uid")
	@ResponseBody
    public String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
	
	@RequestMapping("/switchDataSource")
	@ResponseBody
    public List<Comment> switchDataSource() {
		List<Comment> switchDataSource = commentService.switchDataSource();
		return switchDataSource;
    }
}
