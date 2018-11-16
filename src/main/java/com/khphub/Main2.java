package com.khphub;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.ProductVO;
import com.selenium.WebDriverConfig;

public class Main2 {

	public static void main(String[] args) throws Exception {
		Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		
		EventFiringWebDriver eDriver = new WebDriverConfig().getWebDriver(); 
		
		String searchKeyword = "진공포장기";
		StringBuilder sb = new StringBuilder();
		sb.append("https://search.shopping.naver.com/search/all.nhn");
		sb.append("?origQuery=%s&pagingIndex=1&pagingSize=80&viewType=list&sort=price_asc&frm=NVSHATC&sps=Y&query=%s");
		String url = String.format(sb.toString(), searchKeyword, searchKeyword);
		
		System.out.println("객체생성 완료");
		eDriver.get(url);
		List<WebElement> listGoods = eDriver.findElements(By.className("_itemSection"));
		System.out.println("리스트 파싱 완료");
		List<ProductVO> prodList = new ArrayList<ProductVO>();
		for (WebElement webElement : listGoods) {
			ProductVO productVO = new ProductVO();
			productVO.setImgUrl(webElement.findElement(By.className("_productLazyImg")).getAttribute("src"));
			productVO.setTit(webElement.findElement(By.className("tit")).getText());
			productVO.setPrice(Integer.parseInt(webElement.findElement(By.className("_price_reload")).getText().replaceAll(",", ""))); 
			prodList.add(productVO);
		}

	}
}
