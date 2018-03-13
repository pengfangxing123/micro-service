package com.genius.znzx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.genius.znzx.service.CommentService;
import com.genius.znzx.test.MongondbTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {
	
	 @Autowired
	 private MongondbTest mongondbTest;
	 
	 @Autowired
	 private CommentService commentService;
	 
	 @Test
	 public void testSaveUser() throws Exception {
		 mongondbTest.saveUser();
	 };
	 @Test
	 public void testQuery(){
		 mongondbTest.queryUser();
	 };
	 
	 @Test
	 public void testComment(){
		 commentService.getCommentListById();
	 }
}
