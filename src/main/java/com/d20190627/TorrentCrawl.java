package com.d20190627;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.selenium.WebDriverConfig;

public class TorrentCrawl {
	public static void main(String[] args) throws Exception {
		
		MongoClientURI uri = new MongoClientURI(""); // 보안때문에  github 에 안올림.
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase("torrent");
		MongoCollection<Document> collection = db.getCollection("Entertainment"); 
		
		EventFiringWebDriver eDriver = new WebDriverConfig().getWebDriver(); 
		
		String url = "https://www.torrentmap.com/bbs/board.php?bo_table=kr_ent&wr_id="; // 98249
		
		List<Document> docList = new ArrayList<Document>();
		
		for (int i = 1; i < 98249; i++) {
			Document doc = new Document();
			eDriver.get(url + i);
			
			doc.append("id", i );
			doc.append("size", eDriver.findElement(By.xpath("//*[@id=\"bo_v_file\"]/ul/div/div[1]/div")).getText());
			doc.append("title", eDriver.findElement(By.xpath("//*[@id=\"bo_v_title\"]/span[2]")).getText());
			doc.append("magnet", eDriver.findElement(By.xpath("//*[@id=\"bo_v_file\"]/ul/li/div/a")).getAttribute("href"));
			doc.append("dt", eDriver.findElement(By.xpath("//*[@id=\"bo_v_title\"]/div")).getText());
			
//			docList.add(doc);
			collection.insertOne(doc);
			synchronized (eDriver) { eDriver.wait(300); }; // 0.3 초 딜레이 줌.
		}
		
//		db.getCollection("Entertainment").insertMany(docList);
		
	}
	
}
