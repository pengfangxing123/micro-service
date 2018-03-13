package com.genius.znzx.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genius.znzx.dao.CommentMapper;
import com.genius.znzx.entity.Comment;
import com.genius.znzx.service.CommentService;

@Service
public class CommentServiceImp implements CommentService {
	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public List<Comment> getCommentListById() {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        List<Comment> commentListById = this.commentMapper.getCommentListById(map);
        for(int i=0;i<commentListById.size();i++){
        	Comment cur=commentListById.get(i);
        	if(cur.getIs_sub()!=0){
        		List<Comment> allTheSub = this.getAllTheSub(cur.getId());
        		cur.setReplyList(allTheSub);
        	}
        }
        System.out.println(commentListById);
		return commentListById;
	}
	
	public List<Comment> getAllTheSub(int id){
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("parent_id", id);
        List<Comment> commentListById = this.commentMapper.getAllTheSub(map);
        for(int i=0;i<commentListById.size();i++){
        	Comment cur=commentListById.get(i);
        	if(cur.getIs_sub()!=0){
        		List<Comment> allTheSub = this.getAllTheSub(cur.getId());
        		cur.setReplyList(allTheSub);
        	}
        }
        return commentListById;
	}
}
