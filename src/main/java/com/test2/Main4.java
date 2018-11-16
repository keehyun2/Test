package com.test2;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.client.utils.URIBuilder;

public class Main4 {
	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
//		sb.append("https://search.shopping.naver.com/search/all.nhn?");
//		sb.append("?origQuery=%s&pagingIndex=1&pagingSize=80&viewType=list&sort=price_asc&minPrice=%d&maxPrice=%d&frm=NVSHPRC&sps=Y&query=%s");
		String searchKeyword = "진공포장기";
		URIBuilder builder = new URIBuilder();
		builder.setCharset(Charset.forName("UTF-8"));
		builder.setScheme("https");
		builder.setHost("search.shopping.naver.com");
		builder.setPath("/search/all.nhn");
		builder.addParameter("origQuery", searchKeyword);
		builder.addParameter("pagingIndex", "1");
		builder.addParameter("pagingSize", "80");
		builder.addParameter("viewType", "list");
		builder.addParameter("sort", "price_asc");
		builder.addParameter("minPrice", "0");
		builder.addParameter("maxPrice", "0");
		builder.addParameter("frm", "NVSHPRC");
		builder.addParameter("sps", "Y");
		builder.addParameter("query", searchKeyword);
		URL url = builder.build().toURL();
		
		System.out.println(url.toString());
	}
}
