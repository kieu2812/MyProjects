package com.graphql.example.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.graphql.example.dao.LinkDao;
import com.graphql.example.dao.UserDao;
import com.graphql.example.domain.Link;
import com.graphql.example.domain.User;

public class Query implements GraphQLRootResolver {
    
    private final LinkDao linkDao;
    private final UserDao userDao;

    public Query(LinkDao linkDao, UserDao userDao) {
        this.userDao = userDao;
        this.linkDao = linkDao;
    }

    public List<Link> allLinks() {
        return linkDao.getAllLinks();
    }
    
    public User user(String name) {
        return userDao.findByName(name);
    }
}
