package com.gps;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

public class Test3 {

	public static void main(String[] args) {
		
		Integer a = 1;
		
		Integer b = 12;
		
		System.out.println(a == b);
		
		System.out.println(Arrays.asList("0", "1").contains("0"));
		
		LocalTime start = LocalTime.parse( "15:10:00" );
		LocalTime stop = LocalTime.parse( "15:11:00" );
		
		Duration duration = Duration.between( start, stop );
		

		System.out.println(start.compareTo(stop) );
	}

}
