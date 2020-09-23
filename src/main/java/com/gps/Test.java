package com.gps;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public class Test {
	public static void main(String[] args) {
	    
        long ut1 = Instant.now().getEpochSecond();
        System.out.println(ut1);

        long ut2 = System.currentTimeMillis() / 1000L;
        System.out.println(ut2);

        Date now = new Date();
        long ut3 = now.getTime() / 1000L;
        System.out.println("현재 유닉스 타임스탬프" +ut3);
	    
//		String dt = getTimestampToDate(Long.toString(ut3));
		String dt = getTimestampToDate("1594621297");
		System.out.println(dt.subSequence(0, 10));
		System.out.println(dt.subSequence(11, 19));
		
		
		long test_timestamp = 1594621297842L;
		LocalDateTime triggerTime =
		        LocalDateTime.ofInstant(Instant.ofEpochMilli(test_timestamp), 
		                                TimeZone.getDefault().toZoneId());  

		System.out.println(triggerTime);
	}
	
	private static String getTimestampToDate(String timestampStr){
        long timestamp = Long.parseLong(timestampStr);
        Date date = new java.util.Date(timestamp*1000L); 
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9")); 
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
