package com.test3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.NaverService;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		
		Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		NaverService ns = new NaverService();
		String result = "{}";
		result = ns.collectProductList("진공포장기");
		
	}
}
