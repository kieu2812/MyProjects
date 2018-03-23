package com.graphql.example.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.graphql.example.dao.LinkDao;
import com.graphql.example.dao.UserDao;
import com.graphql.example.domain.Link;
import com.graphql.example.domain.User;

public class UserResolver implements GraphQLResolver<User> {
    
	LinkDao linkDao;
	
    public UserResolver(LinkDao linkDao) {
        this.linkDao = linkDao;
    }

    public List<Link> posts(User user) {
        if (user.getId() == null) {
            return null;
        }
        
        return linkDao.findByUserId(user.getId());
    }
}
