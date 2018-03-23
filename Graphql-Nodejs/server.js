var express = require('express');
var graphqlHTTP = require('express-graphql');
var { buildSchema } = require('graphql');

var schemadefs = require('./schema.js');
var resolver = require('./scripts/resolver/Resolver.js');

 
// Construct a schema, using GraphQL schema language
var schema = buildSchema(schemadefs);

var app = express();
app.use('/graphql', graphqlHTTP({
  schema: schema,
  rootValue: resolver,
  graphiql: true,
}));
app.listen(4000, () => {
  console.log('Running a GraphQL API server at localhost:4000/graphql');
});