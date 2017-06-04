package main.java.sneakerbot.atc;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.ProxyConfig;
import com.machinepublishers.jbrowserdriver.ProxyConfig.Type;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Settings.Builder;
import com.machinepublishers.jbrowserdriver.UserAgent;

import main.java.sneakerbot.loaders.Credentials.CredentialObject;
import main.java.sneakerbot.loaders.Proxy.ProxyObject;

public class Adidas implements Runnable {
	
	public Adidas(ProxyObject proxy, CredentialObject credentials, String url, boolean splash, boolean manual, double[] sizes) {
		super();
	    Builder builder = Settings.builder();
	    
	    if(proxy != null) {
		    builder.proxy(new ProxyConfig(Type.HTTP, proxy.getAddress(), proxy.getPort()));
	    	//String server = proxy.getAddress() + ":" + proxy.getPort();
	        //capability.setCapability(CapabilityType.PROXY, new Proxy().setHttpProxy(server).setFtpProxy(server).setSslProxy(server));
	    }
	    
	    builder.logsMax(0);
	    builder.loggerLevel(Level.OFF);
	    builder.userAgent(UserAgent.CHROME);
        
		driver = new JBrowserDriver(builder.build());
		driver.manage().timeouts().setScriptTimeout(30L, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30L, TimeUnit.SECONDS);
		
		this.proxy = proxy;
		this.url = url;
		this.splash = splash;
		this.manual = manual;
		this.sizes = sizes;
		carted = false;
	}

	@Override
	public void run() {
		
		while(!carted) {
			
			if(splash)
				splash();
			else
				product();
		}			
	} 
	
	public void product() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60L);
			print("Loading webpage: " + url);
			
			driver.get(url);
			
		    wait.until(new Function<WebDriver, Boolean>() {
		        public Boolean apply(WebDriver d) {
		            //print("Current Window State       : "
		             //   + String.valueOf(((JavascriptExecutor) d).executeScript("return document.readyState")));
		            return String
		                .valueOf(((JavascriptExecutor) d).executeScript("return document.readyState"))
		                .equals("complete");
		        }
		    });
		    
		    String timer = driver.findElementById("pdp_timer").getAttribute("style");
		    
		    print(timer);
		    
		} catch (Exception e)
		{
			String name = e.getClass().getName();
			print("[Exception] -> " + name);
			carted = true;
			
			if(name.equals("org.openqa.selenium.TimeoutException"))
				carted = false;
			
		} finally {
			print(carted ? "Closing driver, and ending" : "Failed, Retrying...");
			
			if(carted) {
				driver.quit();
				Thread.currentThread().interrupt();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void splash() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60L);
			print("Loading webpage: " + url);
			
			driver.get(url);
			
		    wait.until(new Function<WebDriver, Boolean>() {
		        public Boolean apply(WebDriver d) {
		            //print("Current Window State       : "
		             //   + String.valueOf(((JavascriptExecutor) d).executeScript("return document.readyState")));
		            return String
		                .valueOf(((JavascriptExecutor) d).executeScript("return document.readyState"))
		                .equals("complete");
		        }
		    });
		    
			//TODO: Get to splash page.
		    boolean displayed = wait.until(x -> x.findElement(By.className("sk-fading-circle"))).isDisplayed();
		    
		    if(displayed) {
				print(proxy != null ? ("[" + proxy.getPassword() + ":" + proxy.getPort() + "] -> ") : "" + "waiting at splash page!");	
				
				while(driver.findElements(By.className("g-recaptcha")).size() == 0) 
					Thread.sleep(5000L);
				
				print(proxy != null ? ("[" + proxy.getPassword() + ":" + proxy.getPort() + "] -> ") : "" + "passed splash page!");
				
				driver.manage().getCookies().stream().forEach(c -> {
					if(c.getValue().contains("hmac")) {
						hmac = c.getValue();
						hmacExpiration = c.getExpiry();
					}
				});
				
				if(driver.findElements(By.className("g-recaptcha")).size() > 0)
					siteKey = driver.findElement(By.className("g-recaptcha")).getAttribute("data-sitekey");
				
				if(driver.findElements(By.id("flashproductform")).size() > 0)
					clientId = driver.findElement(By.id("flashproductform")).getAttribute("action").split("clientId=")[1];
				
				Date timeLeft = new Date(hmacExpiration.getTime() - System.currentTimeMillis());
				print("[Success] -> SiteKey: " + siteKey + " Client ID: " + clientId + " HMAC: " + hmac + " Time Left: " + timeLeft.getMinutes() + "m" + timeLeft.getSeconds() + "s");
				
		    } else
		    	print("Error, Element displayed: " + displayed);
			
			if(manual) {
				
			    FirefoxProfile profile = new FirefoxProfile();
			    
			    if(proxy != null) {
				    profile.setPreference("network.proxy.type", 1);
				    profile.setPreference("network.proxy.http", proxy.getAddress());
				    profile.setPreference("network.proxy.http_port", proxy.getPort());
				    profile.setPreference("network.proxy.ssl", proxy.getAddress());
				    profile.setPreference("network.proxy.ssl_port", proxy.getPort());
			    }
			    
			    //profile.setPreference("general.useragent.override", driver.set);
				WebDriver checkout = new FirefoxDriver(profile);
				
				for (Cookie cookie : driver.manage().getCookies())
					checkout.manage().addCookie(cookie);
				
				checkout.get(driver.getCurrentUrl());
			} 
			else 
				while(!carted && hmacExpiration.getTime() > System.currentTimeMillis()) 
					atc();
			
		} catch (Exception e)
		{
			String name = e.getClass().getName();
			print("[Exception] -> " + name);
			carted = true;
			
			if(name.equals("org.openqa.selenium.TimeoutException"))
				carted = false;
			
		} finally {
			print(carted ? "Closing driver, and ending" : "Failed, Retrying...");
			
			if(carted) {
				driver.quit();
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void atc() {
		WebDriverWait wait = new WebDriverWait(driver, 300L);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='ffSelectButton' and (.//span[text()[contains(.,'Size')]] or .//span[text()[contains(.,'size')]])]")));
		
		int index = new Random().nextInt(sizes.length);
		String sizeToPick = Double.toString(sizes[index]);
		
		for(WebElement e : driver.findElements(By.xpath("//div[@class='ffSelectMenuMid' and .//ul[.//li[.//span[text()[contains(.,'size')]]]]]/ul/li"))) {
			String size = e.getText().trim();
			if(size != null && size.equals(sizeToPick)) {
				e.click();
				break;
			}
		}	
	}
	
	public void print(String text) {
		System.out.println("[" + Thread.currentThread().getName() + "] " + text);
	}
	
	JBrowserDriver driver;
	ProxyObject proxy;
	CredentialObject credentials;
	Date hmacExpiration;
	String url;
	String hmac;
	String siteKey;
	String clientId;
	double[] sizes;
	boolean splash;
	boolean carted;
	boolean manual;

}
