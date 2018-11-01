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

import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class Main {

	static WebDriver driver;
	static EventFiringWebDriver e_driver;
	
	static WebEventListener eventListener;

	static DateFormat dateFormat;
	static Date date;

	static Logger log = Logger.getLogger("com.gargoylesoftware");

	public static void main(String[] args) throws Exception {

		log.setLevel(Level.OFF);

		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();

//		System.setProperty("webdriver.chrome.driver", "C:\\dev\\workspace\\Test\\src\\main\\resources\\chromedriver.exe");
//		driver = new ChromeDriver();
		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
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
		
		Nitrite db = Nitrite.builder().compressed().filePath("/tmp/test.db").openOrCreate("test", "1234");

		// Create a Nitrite Collection
		NitriteCollection collection = db.getCollection("test");

		// create a document to populate data
//		Document doc = Document.createDocument("firstName", "John").put("lastName", "Doe").put("birthDay", new Date())
//				.put("data", new byte[] { 1, 2, 3 }).put("fruits", new ArrayList<String>() {
//					{
//						add("apple");
//						add("orange");
//					}
//				}).put("note", "a quick brown fox jump over the lazy dog");
		
		for (WebElement webElement : list_goods) {
			System.out.println(webElement.findElement(By.className("_productLazyImg")).getAttribute("src"));
			System.out.println(webElement.findElement(By.className("tit")).getText());
		}
		
		// 20% 이상 차이나는 이미지들만 검색
		
	}
	
}
