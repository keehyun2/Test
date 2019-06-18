package com.d20190617;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.khphub.WebEventListener;

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
		
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();

//		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
//		driver = new ChromeDriver();
		System.setProperty("webdriver.gecko.driver","D:\\geckodriver.exe");
        driver = new FirefoxDriver();
		
//		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
//		((HtmlUnitDriver)driver).setJavascriptEnabled(true); 

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		e_driver = new EventFiringWebDriver(driver); // 이벤트 등록
		
		eventListener = new WebEventListener();
		e_driver.register(eventListener);

//		e_driver.manage().window().maximize(); // 최대화
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("https://naver.com");
		//sb.append("?origQuery=%s&pagingIndex=1&pagingSize=40&viewType=list&sort=price_asc&frm=NVSHATC&sps=N&query=%s");
		String url = String.format(sb.toString());
		
		e_driver.get(url);
		synchronized (e_driver) { e_driver.wait(1000); }
		e_driver.findElement(By.cssSelector(".ico_local_login.lang_ko")).click();
		synchronized (e_driver) { e_driver.wait(1000); }
		e_driver.findElement(By.cssSelector("#id")).sendKeys("keehyun21");
		synchronized (e_driver) { e_driver.wait(3000); }
		e_driver.findElement(By.cssSelector("#pw")).sendKeys("sc");
		e_driver.findElement(By.cssSelector("#pw")).sendKeys("0314");
		e_driver.findElement(By.cssSelector("#pw")).sendKeys("EC$");
		synchronized (e_driver) { e_driver.wait(3000); }
		e_driver.findElement(By.cssSelector(".btn_global")).click();
		
		// 20% 이상 차이나는 이미지들만 검색
		
	}
}
