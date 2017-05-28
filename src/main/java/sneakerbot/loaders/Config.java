package main.java.sneakerbot.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Config {
	
	public static ConfigObject load(String name) {
        File file = new File(name); 
        
        if(!file.exists()) {
        	System.out.println(name + " does not exist; One has been created for you.");
        	create(name);
        	return null;
        }
        
		try {
			return new GsonBuilder().create().fromJson(new FileReader(name), ConfigObject.class);
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void create(String name) {
		
	       try (FileWriter writer = new FileWriter(name)) {
	    	   new GsonBuilder().enableComplexMapKeySerialization()
	           .setPrettyPrinting().create().toJson(new ConfigObject("http://adidas.com/yeezy", true, new double[] {8, 8.5, 11, 12.5}, 10), writer);
	        } catch (IOException e) { e.printStackTrace(); }
	}
	
	public static class ConfigObject {
		
		public ConfigObject(String link, boolean splash, double[] sizes, int tasks) {
			super();
			this.link = link;
			this.splash = splash;
			this.sizes = sizes;
			this.tasks = tasks;
		}
		
		public String getLink() {
			return link;
		}
		
		public boolean isSplash() {
			return splash;
		}
		
		public double[] getSizes() {
			return sizes;
		}
		
		public int getTasks() {
			return tasks;
		}

		@Override
		public String toString() {
			return "ConfigObject [link=" + link + ", splash=" + splash + ", sizes=" + Arrays.toString(sizes)
					+ ", tasks=" + tasks + "]";
		}

		private String link;
		private boolean splash;
		private double[] sizes;
		private int tasks;
	}
}
