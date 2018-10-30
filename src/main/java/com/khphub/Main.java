package com.khphub;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class Main {

	static WebDriver driver;
	static EventFiringWebDriver e_driver;
	
	static WebEventListener eventListener;

	static String parentWindowHandler;
	static String subWindowHandler;
	static DateFormat dateFormat;
	static Date date;

	static Logger log = Logger.getLogger("com.gargoylesoftware");

	public static void main(String[] args) throws Exception {

		log.setLevel(Level.OFF);

		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();

		System.setProperty("webdriver.chrome.driver",
				"C:\\dev\\workspace\\Test\\src\\main\\resources\\chromedriver.exe");

		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		e_driver = new EventFiringWebDriver(driver); // 이벤트 등록
		
		eventListener = new WebEventListener();
		e_driver.register(eventListener);

		e_driver.manage().window().maximize(); // 최대화
		e_driver.get("https://shopping.naver.com/home/p/index.nhn");
		
		synchronized (e_driver) {
			try {
				System.out.println("브라우저가 열리고 홈페이지가 열린 후 3초 대기");
				e_driver.wait(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		parentWindowHandler = e_driver.getWindowHandle(); // Store your parent window

		WebElement element = e_driver.findElement(By.name("query"));
		
		element.sendKeys("진공포장기");
		element.sendKeys(Keys.RETURN);
//		click(By.cssSelector(".co_srh_btn"));
		
		click(By.id("_sort_price_asc"));// 낮은가격순
		
		click(By.cssSelector(".btn_align")); // 적용기준
		 
		// 네이버 추천 가장 낮은 가격
		System.out.println(e_driver.findElement(By.cssSelector(".info_align_low")).getText());
	}

	/**
	 * ajax load 할때 blockUI 가 클릭 할 부분을 막아서 wait 처리함. css fade in 효과 때문 인것 같음... 여튼
	 * 클릭할때 에러 발생함...
	 * 
	 * @param by
	 * @throws InterruptedException
	 */
	public static void click(By by) throws InterruptedException {
		try {
			e_driver.findElement(by).click();
		} catch (Exception e) {
			 e.printStackTrace();
			synchronized (e_driver) {
				e_driver.wait(1000);
			}
			click(by);
		}
	}

	public static void click(WebElement we) throws InterruptedException {
		try {
			we.click();
		} catch (Exception e) {
			// e.printStackTrace();
			synchronized (e_driver) {
				e_driver.wait(1000);
			}
			click(we);
		}
	}

	/**
	 * 새로 생긴 팝업을 찾는다. 팝업이 여러개 떠있으면 에러 생길수 있음.
	 * 
	 * @throws InterruptedException
	 */
	public static void detectPopup() throws InterruptedException {
		Set<String> handles = e_driver.getWindowHandles(); // get all window handles
		Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
		}
	}

	/**
	 * date 를 오늘 날짜로 select 한다.
	 * 
	 * @throws InterruptedException
	 */
	public static void selectDate() throws InterruptedException {
		WebElement dateWidget = e_driver.findElement(By.id("ui-datepicker-div"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals(new SimpleDateFormat("dd").format(date))) {
				click(cell);
				break;
			}
		}
	}

}
