package com.graphql.example.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.graphql.example.domain.*;
import java.sql.Connection; 

public class LinkDao {
	private final List<Link> links;

    public LinkDao() {
        links = new ArrayList<>();
    }
    
    public List<Link> getAllLinks() {
    	
    	try {
    		Connection conn = DatabaseConnection.getConnection();
        	Statement st = conn.createStatement();
        	

        	String query = "select * from links";
        	ResultSet rs = st.executeQuery(query);
        	
        	while (rs.next())
            {
        		String id = rs.getString("linkid");
              String url = rs.getString("url");
              String description = rs.getString("description");
              String userId = rs.getString("userid");
              
              Link link = new Link(id, url, description, userId);
              
              links.add(link);
            }
            st.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    	return links;
    }
    
    public List<Link> findByUserId(String userid) {
    	
    	List<Link> ls = new ArrayList<>();
    	
    	try {
    		Connection conn = DatabaseConnection.getConnection();
        	String query = "select * from links where userid = ?";
        	
        	PreparedStatement  pstmt = conn.prepareStatement(query); // create a statement
        	pstmt.setInt(1, Integer.parseInt(userid)); // set input parameter
        	ResultSet rs = pstmt.executeQuery();
            
        	while (rs.next())
            {
        	  String id = rs.getString("linkid");
              String url = rs.getString("url");
              String description = rs.getString("description");
              //String str_userid = rs.getString("userid");
              
              Link link = new Link(id, url, description, userid);
              
              ls.add(link);
            }
        	pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
    	return ls;
    }
    
    public void saveLink(Link link) {
    	
    	try {
    		Connection conn = DatabaseConnection.getConnection();
        	String query = " insert into links (url, description, userid)" + " values (?, ?, ?)";
        	
        	PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, link.getUrl());
            preparedStmt.setString (2, link.getDescription());
            preparedStmt.setInt (3, link.getUserId()==null?null:Integer.parseInt(link.getUserId()));
            
            // execute the preparedstatement
            preparedStmt.execute();
            
            conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
    	
    }
}
