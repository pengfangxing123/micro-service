package com.genius.znzx.dao;

import java.util.List;
import java.util.Map;

import com.genius.znzx.entity.Comment;

public interface CommentMapper {
	
	List<Comment> getCommentListById(Map map);
	
	List<Comment> getAllTheSub(Map map);

	void insertDataSource2(String uuid);
	
}
