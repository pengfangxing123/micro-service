package com.genius.znzx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.genius.znzx.common.DataSourceInstances;
import com.genius.znzx.common.DataSourceSwitch;
import com.genius.znzx.common.IdGen;
import com.genius.znzx.dao.CommentMapper;
import com.genius.znzx.entity.Comment;
import com.genius.znzx.service.CommentService;


@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public List<Comment> getCommentListById() {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        List<Comment> commentListById = commentMapper.getCommentListById(map);
        for(int i=0;i<commentListById.size();i++){
        	Comment cur=commentListById.get(i);
        	if(cur.getIs_sub()!=0){
        		List<Comment> allTheSub = getAllTheSub(cur.getId());
        		cur.setReplyList(allTheSub);
        	}
        }
        System.out.println(commentListById);
		return commentListById;
	}
	
	public List<Comment> getAllTheSub(int id){
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("parent_id", id);
        List<Comment> commentListById = commentMapper.getAllTheSub(map);
        for(int i=0;i<commentListById.size();i++){
        	Comment cur=commentListById.get(i);
        	if(cur.getIs_sub()!=0){
        		List<Comment> allTheSub = getAllTheSub(cur.getId());
        		cur.setReplyList(allTheSub);
        	}
        }
        return commentListById;
	}

	
	/**
	 * 事务只能回滚一个第一个数据源，因为该方法属于一个事务，同一个事务只有一个datasource，
	 * 使用第二个数据源的操作会直接提交，想实现两个数据源都回滚得使用分布式事务管理
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public List<Comment> switchDataSource() {
/*		Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        List<Comment> commentListById = commentMapper.getCommentListById(map);
        for(int i=0;i<commentListById.size();i++){
        	Comment cur=commentListById.get(i);
        	if(cur.getIs_sub()!=0){
        		List<Comment> allTheSub = getAllTheSub(cur.getId());
        		cur.setReplyList(allTheSub);
        	}
        }*/
//               
        DataSourceSwitch.setDataSourceType(DataSourceInstances.DATASOURCE2);
        commentMapper.insertDataSource2(IdGen.uuid());

        //DataSourceSwitch.setDataSourceType(DataSourceInstances.DATASOURCE1);
        System.out.println(DataSourceSwitch.getDataSourceType());
       
       int i=1/0;
       return null;
	}

}
