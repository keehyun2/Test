package com.khphub;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {

	static WebDriver driver;
	static EventFiringWebDriver e_driver;
	
	static WebEventListener eventListener;

	static DateFormat dateFormat;
	static Date date;

	static Logger log = Logger.getLogger("com.gargoylesoftware");
	
	static Logger logger = Logger.getLogger("com.khphub");

	public static void main(String[] args) throws Exception {

		log.setLevel(Level.OFF);
		
		if (args == null || args.length < 1) {
			System.out.println("검색키워드가 입력되지 않았습니다.");
			return;
		}

		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
//		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		((HtmlUnitDriver)driver).setJavascriptEnabled(true); 

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		e_driver = new EventFiringWebDriver(driver); // 이벤트 등록
		
		eventListener = new WebEventListener();
		e_driver.register(eventListener);

		e_driver.manage().window().maximize(); // 최대화
		
		String searchKeyword = URLEncoder.encode("진공포장기", "UTF-8");
		StringBuilder sb = new StringBuilder();
		sb.append("https://search.shopping.naver.com/search/all.nhn");
		sb.append("?origQuery=%s&pagingIndex=1&pagingSize=40&viewType=list&sort=price_asc&frm=NVSHATC&sps=N&query=%s");
		String url = String.format(sb.toString(), searchKeyword, searchKeyword);
		
		e_driver.get(url);
		
		List<WebElement> list_goods = e_driver.findElements(By.className("_itemSection"));
		System.out.println("상품 수 : " + list_goods.size());
		
		MongoClientURI uri = new MongoClientURI("mongodb://product1:product1@ds147723.mlab.com:47723/product?authSource=product");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase("product");
		MongoCollection<Document> collection = db.getCollection("naver"); 
		
		List<Document> docList = new ArrayList<Document>();
		for (WebElement webElement : list_goods) {
			
			Document doc = new Document();
			doc.append("searchKeyword", searchKeyword);
			doc.append("imgUrl", webElement.findElement(By.className("_productLazyImg")).getAttribute("src"));
			doc.append("tit", webElement.findElement(By.className("tit")).getText());
			docList.add(doc);
			
			logger.info(webElement.findElement(By.className("_productLazyImg")).getAttribute("src"));
			logger.info(webElement.findElement(By.className("tit")).getText());
			System.out.println(webElement.findElement(By.className("tit")).getText());
		}
		collection.insertMany(docList);
		System.out.println("입력완료");
		
		// 20% 이상 차이나는 이미지들만 검색
		
	}
}
