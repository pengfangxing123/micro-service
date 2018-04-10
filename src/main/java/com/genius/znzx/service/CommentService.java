package com.genius.znzx.service;

import java.util.List;
import java.util.Map;

import com.genius.znzx.entity.Comment;


public interface CommentService {
	public List<Comment> getCommentListById();

	public List<Comment> switchDataSource();

}
