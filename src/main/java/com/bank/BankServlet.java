package com.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

//@WebServlet(
//        name = "BankServlet", 
//        urlPatterns = {"/bank"}
//    )
public class BankServlet extends HttpServlet {
	
	static WebDriver driver;
	static EventFiringWebDriver e_driver;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if(driver == null) {
        	WebDriverManager.getInstance(DriverManagerType.FIREFOX).forceCache().setup();
        	driver = new FirefoxDriver();
        	e_driver = new EventFiringWebDriver(driver); // 이벤트 등록
        	e_driver.register(new WebEventListener());
        }

        StringBuilder sb = new StringBuilder();

		sb.append("https://spib.wooribank.com/pib/Dream?withyou=CMLGN0001");
		String url = String.format(sb.toString());

		e_driver.get(url);
		timeWait(2000);
		
		// 로그인 될때까지 대기
		while(true) {
			try {
				if(e_driver.findElement(By.cssSelector(".login-name")).isDisplayed()) {
					System.out.println("로그인 성공");
					break;
				}else {
					timeWait(2000);
				}
	 		} catch (Exception e) {
	 			System.out.println("로그인 해주세요");
	 			timeWait(2000);
	 		}
	    }
		
		timeWait(1000);
		e_driver.get("https://spib.wooribank.com/pib/Dream?withyou=PSINQ0028"); // 상세내역 화면
		
		if (e_driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) e_driver).executeScript("$('body').append('<div id=\"k-shield\" style=\"position: fixed; color: white; width: 100%;height: 100%;background: rgba(255, 0, 0, 0.6);top: 0;text-align: center; padding-top:250px; font-size: 30px; font-weight: bold; z-index: 1000;\" >만지지마세요</div>');");
		}
		
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("{}");
		out.flush();
		out.close();
    }
    
 // 6개월치 조회 post 
 	@Override
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		
 		response.setContentType("application/json; charset=UTF-8");
 		response.setCharacterEncoding("UTF-8");
 		
 		PrintWriter out = response.getWriter();
 		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
 		
 		if (e_driver instanceof JavascriptExecutor) {
 			((JavascriptExecutor) e_driver).executeScript("$('#k-shield').remove();");
 			timeWait(1000);
 		}
 		
 		// 6개월 조회
 		e_driver.findElement(By.id("m06toggle")).click();
 		timeWait(1000);
 		((JavascriptExecutor) e_driver).executeScript("window.scroll(0,1000);");
 		timeWait(1000);
 		
 		try {
 			while(e_driver.findElement(By.cssSelector(".pop-content")).isDisplayed()) {
 				timeWait(1000);
 			}
 		} catch (Exception e) {
 			// TODO: handle exception
 			System.out.println("조회 성공");
 		}
 		
 		List<PayVO> syncList = new ArrayList<PayVO>();
 		
 		HashSet<String> hSet = new HashSet<String>();
 		// 스크롤을 조금씩 내리면서 마지막 번호가 계속 같으면 while 문 종료 
 		boolean flag = true;
 		int i = 0;
 		while (flag) {  
 			
 			// 10개 단위로 스크롤 내림
 			if (e_driver instanceof JavascriptExecutor) {
 				((JavascriptExecutor) e_driver).executeScript("document.getElementById('grid_scrollY_div').scrollTop = " + (i++ * 200) + ";");
 				timeWait(100);
 			}
 			
 			for (int j = 0; j < 10; j++) {	// 화면에 나온 10개 데이터 
 				WebElement webElement = e_driver.findElement(By.cssSelector(".gridHeaderTableDefault .grid_body_row:nth-child(" + (j+1) + ")"));
 				String num = webElement.findElement(By.cssSelector("td:nth-child(1)")).getText();
 				System.out.println(num);
 				if(hSet.contains(num)){
 					flag = false;
 				}else {
 					PayVO payVO = new PayVO();
 					payVO.setNo(num); 
 					payVO.setPayDt(webElement.findElement(By.cssSelector("td:nth-child(2)")).getText());
 					payVO.setBriefs(webElement.findElement(By.cssSelector("td:nth-child(3)")).getText());
 					payVO.setMemo(webElement.findElement(By.cssSelector("td:nth-child(4)")).getText());
 					payVO.setOutAmt(webElement.findElement(By.cssSelector("td:nth-child(5)")).getText());
 					payVO.setInAmt(webElement.findElement(By.cssSelector("td:nth-child(6)")).getText());
 					payVO.setBalance(webElement.findElement(By.cssSelector("td:nth-child(7)")).getText());
 					payVO.setPoint(webElement.findElement(By.cssSelector("td:nth-child(8)")).getText());
 					syncList.add(payVO);
 					hSet.add(payVO.getNo());
 				}
 			}
 		}
 		
 		if (e_driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) e_driver).executeScript("$('body').append('<div id=\"k-shield\" style=\"position: fixed; color: white; width: 100%;height: 100%;background: rgba(255, 0, 0, 0.6);top: 0;text-align: center; padding-top:250px; font-size: 30px; font-weight: bold; z-index: 1000;\" >만지지마세요</div>');");
		}
 		
 		out.println(gson.toJson(syncList));
 		out.flush();
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
