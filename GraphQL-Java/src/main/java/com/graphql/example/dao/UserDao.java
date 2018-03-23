package com.graphql.example.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.graphql.example.domain.*;
import java.sql.Connection; 

public class UserDao {
	private final List<User> users;

    public UserDao() {
        users = new ArrayList<>();        
    }

    public List<User> getAllUsers() {
    	
    	try {
    		Connection conn = DatabaseConnection.getConnection();
        	Statement st = conn.createStatement();
        	

        	String query = "select * from users";
        	ResultSet rs = st.executeQuery(query);
        	
        	while (rs.next())
            {
        	  String id = rs.getString("userid");
              String name = rs.getString("name");
              String sex = rs.getString("sex");
              String birth = rs.getString("birth");
              
              User user = new User(id, name, sex, birth);
              
              users.add(user);
            }
            st.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    	return users;
    }
    
	public User findById(String userid) {
    	
    	try {
    		Connection conn = DatabaseConnection.getConnection();
        	String query = "select * from users where userid = ?";
        	
        	PreparedStatement  pstmt = conn.prepareStatement(query); // create a statement
        	pstmt.setInt(1, Integer.parseInt(userid)); // set input parameter
        	ResultSet rs = pstmt.executeQuery();
            
        	while (rs.next())
            {
        	  String id = rs.getString("userid");
              String name = rs.getString("name");
              String sex = rs.getString("sex");
              String birth = rs.getString("birth");
              
              User user = new User(id, name, sex, birth);
              
              return user;
            }
        	pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    	return null;
    }
	
	public User findByName(String name) {
    	
    	try {
    		Connection conn = DatabaseConnection.getConnection();
        	String query = "select * from users where name = ?";
        	
        	PreparedStatement  pstmt = conn.prepareStatement(query); // create a statement
        	pstmt.setString(1, name); // set input parameter
        	ResultSet rs = pstmt.executeQuery();
            
        	while (rs.next())
            {
        	  String id = rs.getString("userid");
              //String str_name = rs.getString("name");
              String sex = rs.getString("sex");
              String birth = rs.getString("birth");
              
              User user = new User(id, name, sex, birth);
              
              return user;
            }
        	pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    	return null;
    }
    
    public void saveLink(User user) {
    	try {
    		Connection conn = DatabaseConnection.getConnection();
        	String query = " insert into users (name, sex, birth)" + " values (?, ?, ?)";
        	
        	PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, user.getName());
            preparedStmt.setString (2, user.getSex());
            preparedStmt.setString(3, user.getBirth());
            
            // execute the preparedstatement
            preparedStmt.execute();
            
            conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
    }
}
