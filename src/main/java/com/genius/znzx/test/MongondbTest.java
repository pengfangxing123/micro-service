package com.genius.znzx.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.ReadPreference;


@Component
public class MongondbTest {
	@Resource(name="mongoTemplate")
	private MongoTemplate mmongoTemplate;
	 
	public void saveUser() {
    	HashMap<String, Object> map = new HashMap<String,Object>(); 
    	
		try {
//			MongoClient mongoClient = new MongoClient("172.18.135.18",27017);
//			MongoDatabase database = mongoClient.getDatabase("admin");
//			MongoCollection<Document> collection = database.getCollection("stu");
//			FindIterable<Document> find = collection.find();
//			MongoCursor<Document> cursor = find.iterator();  
//	        while (cursor.hasNext()) {  
//	            Document user = cursor.next(); 
//	            System.out.println(user);
//	        }  
//	        cursor.close();  
	        
			map.put("id","1");
			map.put("name","_name");
//        	Document doc = new Document();
//        	Iterator i = map.entrySet().iterator();
//        	while(i.hasNext()){//只遍历一次,速度快
//	        	Map.Entry e=(Map.Entry)i.next();
//		        doc.append(e.getKey().toString(), e.getValue());
//        	}	        
//        	collection.insertOne(doc);;
			//mmongoTemplate.setReadPreference(ReadPreference.secondaryPreferred());//读写分离
			mmongoTemplate.save(map,"STK_FORM_LIST_MORE");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void queryUser() {
		try {
			Query query=new Query(Criteria.where("name").is("_name"));
			//mmongoTemplate.setReadPreference(ReadPreference.secondaryPreferred());//读写分离
			Map str =  mmongoTemplate.findOne(query ,Map.class,"STK_FORM_LIST_MORE");
			String string = str.toString();
			System.out.println(string);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
}