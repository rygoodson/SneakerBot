package main.java.sneakerbot.atc;

import main.java.sneakerbot.loaders.Credentials.CredentialObject;
import main.java.sneakerbot.loaders.Proxy.ProxyObject;

public class Adidas implements Runnable {
	
	public Adidas(ProxyObject proxy, CredentialObject credentials, String url, boolean splash, boolean manual, double[] sizes) {
		super();
		
		this.proxy = proxy;
		this.url = url;
		this.splash = splash;
		this.manual = manual;
		this.sizes = sizes;
		carted = false;
	}

	@Override
	public void run() {
					
	} 
	public void print(String text) {
		System.out.println("[" + Thread.currentThread().getName() + "] " + text);
	}
	
	ProxyObject proxy;
	CredentialObject credentials;
	String url;
	String hmac;
	String siteKey;
	String clientId;
	double[] sizes;
	boolean splash;
	boolean carted;
	boolean manual;

}
