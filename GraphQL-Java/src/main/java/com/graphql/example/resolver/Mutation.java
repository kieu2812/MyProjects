package com.graphql.example.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.graphql.example.dao.LinkDao;
import com.graphql.example.dao.UserDao;
import com.graphql.example.domain.Link;
import com.graphql.example.domain.User;

import graphql.schema.DataFetchingEnvironment;

public class Mutation implements GraphQLRootResolver {
	    
	    private final LinkDao linkDao;
	    private final UserDao userDao;
	    
	    public Mutation(LinkDao linkDao, UserDao userDao) {
	        this.linkDao = linkDao;	        
	        this.userDao = userDao;
	    }
	    
	    public Link createLink(String url, String description) {
	        Link newLink = new Link(url, description, null);
	        linkDao.saveLink(newLink);
	        return newLink;
	    }

	    public Link createLink(String url, String description, String userid) {
	        Link newLink = new Link(url, description, userid);
	        linkDao.saveLink(newLink);
	        return newLink;
	    }
	    
	    public User createUser(String name, String sex, String birth) {
	        User newUser = new User(name, sex, birth);
	        userDao.saveLink(newUser);
	        return newUser;
	    }
}
