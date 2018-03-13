package com.genius.znzx.test;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTs {
	
	@Autowired
	private TranscationTest trasnctionTest;
	
	@Test
    public void executeKxTask() {
		trasnctionTest.insertTest();
	}
	
	@Test
	public void testTranscation(){
		trasnctionTest.testTranscation();
	}
}
