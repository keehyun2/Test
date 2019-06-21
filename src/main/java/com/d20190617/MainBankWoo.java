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
import org.openqa.selenium.JavascriptExecutor;
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

public class MainBankWoo {

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

		WebDriverManager.getInstance(DriverManagerType.FIREFOX).forceCache().setup();
		driver = new FirefoxDriver();

		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		e_driver = new EventFiringWebDriver(driver); // 이벤트 등록

//		eventListener = new WebEventListener();
//		e_driver.register(eventListener);

		StringBuilder sb = new StringBuilder();

		sb.append("https://spib.wooribank.com/pib/Dream?withyou=CMLGN0001");
		String url = String.format(sb.toString());

		e_driver.get(url);
		synchronized (e_driver) {
			e_driver.wait(2000);
		}
		
		System.out.println("60초 안에 로그인");
		
		// 로그인 될때까지 60 초 대기 로그인 안하면 에러 
		WebDriverWait wait = new WebDriverWait(e_driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".login-name"))); // b049338, ol_after_hd
		System.out.println("로그인 성공");
		
		synchronized (e_driver) { e_driver.wait(2000); }
		
		e_driver.get("https://spib.wooribank.com/pib/Dream?withyou=PSINQ0028"); // 상세내역 화면
		
		// 6개월 조회
		e_driver.findElement(By.id("m06toggle")).click();
		synchronized (e_driver) { e_driver.wait(2000); }
		
		// 로딩팝업 사라질때까지 대기 
//		wait = new WebDriverWait(e_driver, 60);
//		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".pop-content"))); // b049338, ol_after_hd
		while(driver.findElements(By.cssSelector(".pop-content")).size() > 0) {
			synchronized (e_driver) { e_driver.wait(1000); }
		}
		
		System.out.println("조회 성공");
		
		List<PayVO> syncList = new ArrayList<PayVO>();
		synchronized (e_driver) { e_driver.wait(2000); }
		
		// 조회내역
		List<WebElement> payList = e_driver.findElements(By.cssSelector(".gridHeaderTableDefault .grid_body_row"));
		System.out.println("리스트 파싱 완료 " + payList.size());
		
		int listSize = payList.size();
		
		for (int i =0; i < ((listSize / 10) + 1); i++) {  
			
			// 10개 단위로 스크롤 내림
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript("document.getElementById('grid_scrollY_div').scrollTop = " + (i * 200) + ";");
				synchronized (e_driver) { e_driver.wait(100); }
			}
			
			for (int j = 0; j < 10; j++) {	// 화면에 나온 10개 데이터 
				WebElement webElement = e_driver.findElement(By.cssSelector(".gridHeaderTableDefault .grid_body_row:nth-child(" + (j+1) + ")"));
				PayVO payVO = new PayVO();
				payVO.setNo(webElement.findElement(By.cssSelector("td:nth-child(1)")).getText()); 
				payVO.setPayDt(webElement.findElement(By.cssSelector("td:nth-child(2)")).getText());
				payVO.setBriefs(webElement.findElement(By.cssSelector("td:nth-child(3)")).getText());
				payVO.setMemo(webElement.findElement(By.cssSelector("td:nth-child(4)")).getText());
				payVO.setOutAmt(webElement.findElement(By.cssSelector("td:nth-child(5)")).getText());
				payVO.setInAmt(webElement.findElement(By.cssSelector("td:nth-child(6)")).getText());
				payVO.setBalance(webElement.findElement(By.cssSelector("td:nth-child(7)")).getText());
				payVO.setPoint(webElement.findElement(By.cssSelector("td:nth-child(8)")).getText());
				syncList.add(payVO); // 중복되는건 추가 않도록 해야함... 
			}
		}
		
		System.out.println(gson.toJson(syncList));
//		
////		for (WebElement webElement : payList) {
//		for (int i =0; i < payList.size(); i++) {
//			
//			
//			WebElement webElement = payList.get(i);
//			PayVO payVO = new PayVO();
//			payVO.setNo(webElement.findElement(By.cssSelector("td:nth-child(1)")).getText()); 
//			payVO.setPayDt(webElement.findElement(By.cssSelector("td:nth-child(2)")).getText());
//			payVO.setBriefs(webElement.findElement(By.cssSelector("td:nth-child(3)")).getText());
//			payVO.setMemo(webElement.findElement(By.cssSelector("td:nth-child(4)")).getText());
//			payVO.setOutAmt(webElement.findElement(By.cssSelector("td:nth-child(5)")).getText());
//			payVO.setInAmt(webElement.findElement(By.cssSelector("td:nth-child(6)")).getText());
//			payVO.setBalance(webElement.findElement(By.cssSelector("td:nth-child(7)")).getText());
//			payVO.setPoint(webElement.findElement(By.cssSelector("td:nth-child(8)")).getText());
//			syncList.add(payVO);
//		}
//		
//		System.out.println(gson.toJson(syncList));
		
	}

}
