package com.genius.znzx.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

public class jdk8date {

	public static void main(String[] args) {
		LocalDateTime currentTime = LocalDateTime.now();
//		System.out.println(currentTime);

//		LocalDate localDate = currentTime.toLocalDate();
//		System.out.println(localDate);
		
//		LocalTime localTime = currentTime.toLocalTime();
//		System.out.println(localTime);
		
//		Month month = currentTime.getMonth();
//		System.out.println(month);
		
//	    Month month = currentTime.getMonth();
//	    int day = currentTime.getDayOfMonth();
//	    int seconds = currentTime.getSecond();
//	        
//	    System.out.println("月: " + month +", 日: " + day +", 秒: " + seconds);
	    
//      LocalDateTime date2 = currentTime.withDayOfMonth(20).withYear(2012).withMonth(5);
//      System.out.println("date2: " + date2);
		
//	    LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
//	    System.out.println("date3: " + date3);
	    
	    // 解析字符串
//	    LocalTime date5 = LocalTime.parse("20:15:30");
//	    System.out.println("date5: " + date5);
	    
	    DateTimeFormatter f =DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String date_str = currentTime.format(f);
	    System.out.println(date_str);
	    
	}

}
