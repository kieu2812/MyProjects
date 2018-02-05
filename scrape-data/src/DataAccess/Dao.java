package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import Domain.Product;
import Util.ReadPropertiesFile;

public class Dao {
	//String URL;
	Connection conn;
	Statement state;
	public Connection getConnectDB() throws SQLException, ClassNotFoundException {
		HashMap<String, String> props = ReadPropertiesFile.getProperties();
		
		String driver = props.get("jdbc.driver");
		String url = props.get("jdbc.url");
		String username = props.get("jdbc.username");
		String password = props.get("jdbc.password");
		
//		System.out.println("driver" + ": " + driver);
//		System.out.println("url" + ": " + url);
//		System.out.println("username" + ": " + username);
//		System.out.println("password" + ": " + password);
		
		Class.forName(driver);
		conn= DriverManager.getConnection(url, username, password);
		
		return conn;
		
	}
	public void closeDB() throws SQLException{
		state.close();
		conn.close();		
	}
	
	public void saveProduct(Product product) throws SQLException{
		
		try{  
			
			Connection conn = getConnectDB();
			  
			PreparedStatement stmt=conn.prepareStatement("insert into Products values(?,?,?,?,?,?,?)");  
			stmt.setLong(1,product.getProductId());
			stmt.setString(2,product.getProductName());  
			stmt.setDouble(3,product.getPrice());
			stmt.setString(4,product.getImageUrl());
			stmt.setString(5,product.getBrand());
			stmt.setString(6, product.getLink());
			stmt.setString(7, product.getWebsite());
			int i=stmt.executeUpdate();  
			System.out.println(i+" records inserted");  
			  
			conn.close();  
			  
		}catch(Exception e){ 
			System.out.println(e);
		}  
		  
	}  
		
	public void saveProducts(ArrayList<Product> list) throws SQLException{
		
		try{  
			
			Connection conn = getConnectDB();
			  
			for (Product product:list) {
				
				PreparedStatement stmt=conn.prepareStatement("insert into Products values(?,?,?,?,?,?,?)");  
				
				stmt.setLong(1,product.getProductId());
				stmt.setString(2,product.getProductName());  
				stmt.setDouble(3,product.getPrice());
				stmt.setString(4,product.getImageUrl());
				stmt.setString(5, product.getBrand());
				stmt.setString(6, product.getLink());
				stmt.setString(7, product.getWebsite());
				stmt.executeUpdate();
				
				stmt.close();
			}
			
			conn.close();
			
		}catch(Exception e){ 
			System.out.println(e);
		}  
		  
	}

	public void deleteAllData() throws SQLException{
		try {
			Connection conn = getConnectDB();
			PreparedStatement stmt=conn.prepareStatement("delete from Products");  
			stmt.execute();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}
}
