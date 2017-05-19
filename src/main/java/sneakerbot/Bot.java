package main.java.sneakerbot;

import java.util.Date;

import main.java.sneakerbot.atc.Adidas;
import main.java.sneakerbot.thread.ThreadPool;

public class Bot {
	
	static ThreadPool pool;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		int count = 1; // proxy count (default=1)
		
		pool = new ThreadPool(count);
		pool.run(new Adidas("1", false));		
		pool.flush();
		
		
	
	}
	
	static {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
	}
}
