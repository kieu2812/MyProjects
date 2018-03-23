var schema = `
  input ProductInput {
    product_id: Int!
    product_name: String
    price: Float
    imageurl: String
	brand: String
	link: String
  }
 

  type Product {
    product_id: ID!
    product_name: String
    price: Float
    imageurl: String
	brand: String
	link: String
	retailers: [Retailer]
  }
  
  type Retailer {
    retailer_id: ID!
    retailer_name: String
    address: String
    rating: Int
	product_id: Int
	productDetail: Product!
  }

  type Query {
    getProduct(id: ID!): Product
	getProducts: [Product]	
	getRetailers: [Retailer]	
  }
 
  type Mutation {
	createProduct(input: ProductInput): Product
  }
`;


module.exports = schema;