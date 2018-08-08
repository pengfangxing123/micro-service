package com.genius.znzx.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class testJdk8 {
	
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
		
		Map<String,String> fundParamMap=new HashMap<String,String>(){
		    {
		    	put("ODT_GR", "日涨幅");
		    	put("OWK_GR", "近1周");
				put("OMTH_GR", "近1个月");
				put("QUA_GR", "近3个月");
				put("HLFYR_GR", "近6个月");
				put("OYR_GR", "近1年");
		    }
		};
		
		//1111111111111
		//numbers=numbers.stream().filter(p -> p!=3).collect(Collectors.toList());
		
		//numbers=numbers.stream().distinct().collect(Collectors.toList());
		
		//numbers=numbers.stream().map(p ->p+5).collect(Collectors.toList());
		
//		numbers.stream().limit(2).forEach(p->{
//			System.out.print(p);
//		});
		
//		numbers.stream().sorted().forEach(p->{
//			System.out.print(p);
//		});;
		
//		numbers.forEach(p->{
//			System.out.print(p);
//		});
		
		fundParamMap.forEach((x,y)->{
			System.out.print(x);
			System.out.println(y);
		});
		
	}
}
