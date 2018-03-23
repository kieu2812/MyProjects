package com.graphql.example.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
	
	public DatabaseConnection() {
	}
	
	public static Connection getConnection() {
		try{  
			/*
    		Class.forName("com.mysql.jdbc.Driver");  
    		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/nodemysql","root","test123");
    		*/
    		
			Properties prop = new DatabaseConnection().getPropValues();
			
			String myDriver = prop.getProperty("com.mysql.mydriver").trim();
			String myUrl = prop.getProperty("com.mysql.myurl").trim();
			String username = prop.getProperty("com.mysql.myusername").trim();
			String password = prop.getProperty("com.mysql.mypassword").trim();
			
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
    	      
    		return conn;
    	} catch(Exception e){ 
    		System.out.println(e);
    		return null;
    	}
	}
	
	public Properties getPropValues() {
		
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			
			if (inputStream != null) {
				prop.load(inputStream);
			}
			
			return prop;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static void main(String[] args) {
		getConnection();
	}
}
