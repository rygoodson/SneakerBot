package main.java.sneakerbot;

import java.util.ArrayList;

import main.java.sneakerbot.atc.Adidas;
import main.java.sneakerbot.loaders.Proxy;
import main.java.sneakerbot.loaders.Proxy.ProxyObject;
import main.java.sneakerbot.thread.ThreadPool;

public class Bot {
	
	static ThreadPool pool;
	static ArrayList<ProxyObject> proxies;
	
	public static void init() {
		proxies = Proxy.load("proxies.txt");
	}

	public static void main(String[] args) {
		init();
		
		pool = new ThreadPool(proxies.size());
		proxies.stream().forEach(p -> {
			pool.run(new Adidas(p, false));	
		});	
		pool.flush();
		
	
	}
	
	static {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
	}
}
