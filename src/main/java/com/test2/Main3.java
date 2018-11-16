package com.test2;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.ProductVO;
import com.selenium.WebDriverConfig;
import com.util.ImageDiff;

public class Main3 {
	public static void main(String[] args) throws InterruptedException, IOException {
		Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<ProductVO> syncList = Collections.synchronizedList( new ArrayList<ProductVO>() );
		
		String searchKeyword = URLEncoder.encode("진공포장기", "UTF-8");  
		StringBuilder sb = new StringBuilder();
		sb.append("https://search.shopping.naver.com/search/all.nhn");
		sb.append("?origQuery=%s&pagingIndex=1&pagingSize=80&viewType=list&sort=price_asc&frm=NVSHATC&sps=Y&query=%s");
		String url = String.format(sb.toString(), searchKeyword, searchKeyword);
		EventFiringWebDriver eDriver = new WebDriverConfig().getWebDriver(); 
		System.out.println("객체생성 완료");
		eDriver.get(url);
		List<WebElement> listGoods = eDriver.findElements(By.className("_itemSection"));
		String recommendedText = eDriver.findElement(By.className("info_align_low")).getText();
		int recommendedPrice = Integer.parseInt(recommendedText.replaceAll("[^0-9]", "") + "0000");
		System.out.println(recommendedText);
		System.out.println("상품 수 : " + listGoods.size());
		
		for (int i = 0; i < 10; i++) {
			sb = new StringBuilder();
			sb.append("https://search.shopping.naver.com/search/all.nhn?");
			sb.append("?origQuery=%s&pagingIndex=1&pagingSize=80&productSet=hotdeal&viewType=list&sort=price_asc&minPrice=%d&maxPrice=%d&frm=NVSHPAG&sps=Y&query=%s");
			url = String.format(sb.toString(), searchKeyword, recommendedPrice - (1000 * i) - 1000, recommendedPrice - (1000 * i), searchKeyword);
			executorService.execute(new WebRunnable(url, syncList));
		}

		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		System.out.println("작업 종료");
		ImageDiff imageDiff = new ImageDiff();
		for (ProductVO vo : syncList) {
			imageDiff.getWebImg(vo.getImgUrl());
		}
		System.out.println("이미지 버퍼 저장 완료");
		
	}
}
