package com.graphql.example.endpoint;

import com.coxautodev.graphql.tools.SchemaParser;
import com.graphql.example.dao.LinkDao;
import com.graphql.example.dao.UserDao;
import com.graphql.example.resolver.LinkResolver;
import com.graphql.example.resolver.Mutation;
import com.graphql.example.resolver.Query;
import com.graphql.example.resolver.UserResolver;

import javax.servlet.annotation.WebServlet;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;


@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        LinkDao linkDao = new LinkDao();
        UserDao userDao = new UserDao();
                
		return SchemaParser.newParser()
					.file("schema.graphqls")
					.resolvers(new Query(linkDao, userDao),
								new Mutation(linkDao, userDao), 
								new LinkResolver(userDao), 
								new UserResolver(linkDao))
					.build()
					.makeExecutableSchema();
    }
}
