package main.java.sneakerbot.atc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Adidas implements Runnable {
	
	public Adidas(String proxy) {
		driver = new HtmlUnitDriver();
		this.proxy = proxy;
	}

	@Override
	public void run() {
		driver.get("http://www.adidas.com/yeezy");
		
		//TODO: Get to splash page.
		
		System.out.println(proxy + " -> waiting at splash page!");		
		while(driver.findElement(By.className("sk-fading-circle")).isDisplayed()) {
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println(proxy + " -> passed splash page!");
		
		driver.manage().getCookies().stream().forEach(c -> {
			if(c.getValue().contains("hmac")) {
				hmac = c.getValue();
			}
		});
		sitekey = driver.findElement(By.className("g-recaptcha")).getAttribute("data-sitekey");
	}
	
	WebDriver driver;
	String proxy;
	String hmac;
	String sitekey;

}
