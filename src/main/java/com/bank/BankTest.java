package com.bank;

import java.util.Arrays;
import java.util.Set;

import javax.servlet.ServletException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public final class BankTest {
	 
	static ChromeDriver driver;
	static EventFiringWebDriver e_driver;
	
	public static void main(String[] args) throws ServletException {
		
		if(driver == null) {
        	WebDriverManager.getInstance(DriverManagerType.CHROME).forceCache().setup();
        	driver = new ChromeDriver();
        	e_driver = new EventFiringWebDriver(driver); // 이벤트 등록
        	e_driver.register(new WebEventListener());
        }
		
        StringBuilder sb = new StringBuilder();

		sb.append("https://spib.wooribank.com/pib/Dream?withyou=CMLGN0001");
		String url = String.format(sb.toString());

		e_driver.get(url);
		
		timeWait(5000);
		
		while(true) {
			try {
				e_driver.findElement(By.cssSelector(".pr_log_2")).click();
				System.out.println("클릭");
				break;
			} catch (UnhandledAlertException e) {
				try {
					Alert alert = driver.switchTo().alert();
					alert.dismiss();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			} catch (ElementClickInterceptedException e) {
				e.printStackTrace();
			} finally {
				timeWait(1000);
			}
		}
		
		e_driver.findElement(By.id("USER_ID")).sendKeys("keehyun2");
		timeWait(1000);
//		e_driver.findElement(By.id("PWD")).sendKeys("a");
		
		e_driver.findElement(By.id("PWD")).click();
//		e_driver.getKeyboard().pressKey("a");
//		e_driver.getKeyboard().releaseKey("a");
		timeWait(10000);
//		e_driver.findElement(By.id("PWD")).sendKeys("s");
		
//		e_driver.findElement(By.id("PWD")).sendKeys("d");
//		e_driver.findElement(By.id("PWD")).sendKeys("f");
//		e_driver.findElement(By.id("PWD")).sendKeys("d");
//		e_driver.findElement(By.id("PWD")).sendKeys("g");
		
//		e_driver.executeScript(script, args)
		Set<Cookie> allCookies = e_driver.manage().getCookies();
		ChromeOptions op = new ChromeOptions();
		op.addArguments(Arrays.asList("headless", "window-size=1920x1080", "disable-gpu"));
		ChromeDriver driver1 = new ChromeDriver();
		EventFiringWebDriver e_driver1 = new EventFiringWebDriver(driver1); // 이벤트 등록
    	e_driver1.register(new WebEventListener());

		url = "https://spib.wooribank.com/pib/Dream?withyou=PSINQ0028";
		
		e_driver1.get(url);
		
		timeWait(1000);
		
		//restore all cookies from previous session
        for(Cookie cookie : allCookies) {
        	e_driver1.manage().addCookie(cookie);
        }
        
//        e_driver1.
		
		e_driver.close();
	}
	
	static public void timeWait(int second) {
		synchronized (e_driver) { 
			try {
				e_driver.wait(second);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	} 
}