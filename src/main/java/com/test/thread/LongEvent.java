package com.test.thread;

import java.util.List;

import org.openqa.selenium.WebElement;

public class LongEvent {
	
	List<WebElement> list;
	
	String url;
	
	public List<WebElement> getList() {
		return list;
	}
	public void setList(List<WebElement> list) {
		this.list = list;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
