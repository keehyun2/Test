package com.d20190617;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khphub.WebEventListener;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MainBankKB {

	static WebDriver driver;
	static EventFiringWebDriver e_driver;

	static WebEventListener eventListener;

	static DateFormat dateFormat;
	static Date date;

	static Logger log = Logger.getLogger("com.gargoylesoftware");

	static Logger logger = Logger.getLogger("com.khphub");

	public static void main(String[] args) throws Exception {

		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		
		log.setLevel(Level.OFF);

		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();

//		System.setProperty("webdriver.chrome.driver", "D:\\chrome_driver.exe");
//		driver = new Chrome_driver();
//		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
		WebDriverManager.getInstance(DriverManagerType.FIREFOX).forceCache().setup();
		driver = new FirefoxDriver();

//		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
//		((HtmlUnitDriver)driver).setJavascriptEnabled(true); 

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		e_driver = new EventFiringWebDriver(driver); // 이벤트 등록

		eventListener = new WebEventListener();
		e_driver.register(eventListener);

//		e_driver.manage().window().maximize(); // 최대화

		StringBuilder sb = new StringBuilder();

		sb.append("https://kbstar.com");
//		sb.append("http://khphub.com");
		String url = String.format(sb.toString());

		e_driver.get(url);
		synchronized (e_driver) {
			e_driver.wait(2000);
		}
		e_driver.findElement(By.linkText("로그인")).click();
		System.out.println("60초 안에 로그인");
//		synchronized (e_driver) { waitForPageToLoad(e_driver); }
		WebDriverWait wait = new WebDriverWait(e_driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("b049338"))); // b049338, ol_after_hd
		System.out.println("로그인 성공");
		synchronized (e_driver) { e_driver.wait(2000); }
		// 조회
		e_driver.findElement(By.cssSelector(".area3 .btn-sub1")).click();
		synchronized (e_driver) { e_driver.wait(2000); }
		// 상세조회
		e_driver.findElement(By.cssSelector(".toggleBtn li:nth-child(2) a")).click();
		synchronized (e_driver) { e_driver.wait(1000); }
		
		// SJDATE - 조회 시작날짜 , JRDATE - 조회 마지막
		e_driver.findElement(By.id("SJDATE")).clear();
		e_driver.findElement(By.id("SJDATE")).sendKeys("20180718");
		synchronized (e_driver) { e_driver.wait(1000); }
//		e_driver.findElement(By.id("JRDATE")).clear();
//		e_driver.findElement(By.id("JRDATE")).sendKeys("20170818");
//		synchronized (e_driver) { e_driver.wait(1000); }
		
		// 조회 버튼
		e_driver.findElement(By.cssSelector(".area2 .btnArea button:nth-child(1)")).click();
		synchronized (e_driver) { e_driver.wait(1000); }
		
		// 더불러오기
		/*
		 * while(e_driver.findElements( By.id("INI_G1248PAGING_BTN_MORE") ).size() != 0)
		 * { e_driver.findElements( By.id("INI_G1248PAGING_BTN_MORE") ).get(0).click();
		 * synchronized (e_driver) { e_driver.wait(2000); } }
		 */
		
		List<PayVO> syncList = new ArrayList<PayVO>();
		synchronized (e_driver) { e_driver.wait(2000); }
		// 조회내역
		List<WebElement> payList = e_driver.findElements(By.cssSelector(".ini_GSDFGy.ini_table tbody tr"));
		
//		System.out.println(payList.get(0).getAttribute("innerHTML"));
//		System.out.println(payList.get(0).findElement(By.cssSelector("td:nth-child(1)")).getText());
		System.out.println("리스트 파싱 완료");
		for (WebElement webElement : payList) {
			PayVO payVO = new PayVO();
			payVO.setPayDt(webElement.findElement(By.cssSelector("td:nth-child(1)")).getText()); // cX1
			payVO.setBriefs(webElement.findElement(By.cssSelector("td:nth-child(2)")).getText());
//			payVO.setSender(webElement.findElement(By.cssSelector("td:nth-child(3)")).getText());
			payVO.setMemo(webElement.findElement(By.cssSelector("td:nth-child(4)")).getText());
			payVO.setOutAmt(webElement.findElement(By.cssSelector("td:nth-child(5)")).getText());
			payVO.setInAmt(webElement.findElement(By.cssSelector("td:nth-child(6)")).getText());
			payVO.setBalance(webElement.findElement(By.cssSelector("td:nth-child(7)")).getText());
			payVO.setPoint(webElement.findElement(By.cssSelector("td:nth-child(8)")).getText());
//			payVO.setKind(webElement.findElement(By.cssSelector("td:nth-child(9)")).getText());
			syncList.add(payVO);
		}
		
		System.out.println(gson.toJson(syncList));
		
	}

//	public static void waitForPageToLoad(WebDriver driver) {
//	    ExpectedCondition < Boolean > pageLoad = new
//	    ExpectedCondition < Boolean > () {
//	        public Boolean apply(WebDriver driver) {
//	            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//	        }
//	    };
//
//	    Wait < WebDriver > wait = new WebDriverWait(driver, 60);
//	    try {
//	        wait.until(pageLoad);
//	    } catch (Throwable pageLoadWaitError) {
//	        throw new TimeoutException("Timeout during page load");
//	    }
//	}
}
