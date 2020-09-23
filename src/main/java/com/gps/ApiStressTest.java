package com.gps;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApiStressTest {

	// 대한민국 지도에 관한 일반정보의 경도(longitude)범위는 124 – 132, 위도(latitude)범위는 33 – 43
	// 서울 35.960223,128.074422
	
	public static void main(String[] args) {
		
		Random randomGenerator = new Random();

		int startLongitude = 124;
		int endLongitude = 132;
		double rangeLongitude = endLongitude - startLongitude + 1;
		
		int startLatitude = 33;
		int endLatitude = 43;
		double rangeLatitude = endLatitude - startLatitude + 1;
		
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		
        long ut1 = Instant.now().getEpochSecond();
        System.out.println(ut1);
        
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> gpsList = new ArrayList<Map<String, Object>>();
        map.put("empNo", 2006036);
        for (int i = 0; i < 120; i++) {
        	Map<String, Object> gpsMap = new HashMap<String, Object>();
        	gpsMap.put("gpsTime", ut1);
        	gpsMap.put("latitude", Math.round((randomGenerator.nextDouble() * rangeLongitude + startLongitude)*1000000)/1000000.0);
        	gpsMap.put("longitude", Math.round((randomGenerator.nextDouble() * rangeLatitude + startLatitude)*1000000)/1000000.0);
        	gpsList.add(gpsMap);
        	
        	ut1 += 3000;
		}
        map.put("gpsList", gpsList);
        
        try {
            String result = sendPOST("http://3.34.177.229:8080/coway/doctor/reimburse/gpsInfo", gson.toJson(map));
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
    }

    private static String sendPOST(String url, String jsonParam) throws IOException {

        String result = "";
        HttpPost post = new HttpPost(url);
        post.addHeader("content-type", "application/json");
        
        System.out.println(jsonParam);

        // send a JSON data
        post.setEntity(new StringEntity(jsonParam));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }

}
