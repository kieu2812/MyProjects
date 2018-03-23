package com.graphql.example.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.example.dao.UserDao;
import com.graphql.example.domain.Link;
import com.graphql.example.domain.User;

public class LinkResolver implements GraphQLResolver<Link> {
    
	UserDao userDao;
	
    public LinkResolver(UserDao userDao) {
        this.userDao = userDao;
    }

    public User postedBy(Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        
        return userDao.findById(link.getUserId());
    }
}
