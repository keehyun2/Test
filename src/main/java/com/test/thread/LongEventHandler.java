package com.test.thread;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.lmax.disruptor.EventHandler;
import com.selenium.WebDriverConfig;

public class LongEventHandler implements EventHandler<LongEvent> {
	
	// 처리 로직
	@Override
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
//		System.out.println(event + " Event: " + event.getValue());
		
		EventFiringWebDriver eDriver = new WebDriverConfig().getWebDriver(); 
		eDriver.get(event.getUrl());
		event.getList().addAll(eDriver.findElements(By.className("_itemSection")));
		
		System.out.println();
	}
}