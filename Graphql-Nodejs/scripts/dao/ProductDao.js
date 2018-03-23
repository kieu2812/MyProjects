var mysql = require('mysql');
var deasync = require('deasync');

var dbConfig = require('../config/config.js');
var Product = require('../domain/Product.js');

module.exports = class ProductDao {

	getProduct(id) {
		
		var v_product = null; 
		
		var con = mysql.createConnection({
			host: dbConfig.host,
			user: dbConfig.user,
			password: dbConfig.password,
			database: dbConfig.database
		});
		
		con.connect(function(err){
		  if(err){console.log('Error connecting to Db');return;}
		  console.log('Connection established');
		});	
		
		var strsql = "SELECT product_id, product_name, price, imageurl, brand, link FROM products WHERE product_id = ?";
		
		con.query(strsql,[id],function(err,result){
			if(err) {console.log(err.message);return;}

			console.log('Data received from Db:\n');
			console.log(result[0].product_name);
			v_product = new Product(result[0].product_id, result[0].product_name, result[0].price, result[0].imageurl, result[0].brand, result[0].link);
		  
			console.log(v_product);
			//doRelease(connection);
		});
		
		while(v_product == null){ deasync.runLoopOnce(); }
		
		console.log(v_product);
		return v_product;
	}
	
	getProducts() {
		
		var v_products = null; 
		
		var con = mysql.createConnection({
			host: dbConfig.host,
			user: dbConfig.user,
			password: dbConfig.password,
			database: dbConfig.database
		});
		
		con.connect(function(err){
		  if(err){console.log('Error connecting to Db');return;}
		  console.log('Connection established');
		});	
		
		var strsql = "SELECT product_id, product_name, price, imageurl, brand, link FROM products";
		
		con.query(strsql,function(err,result){
			if(err) {console.log(err.message);return;}

			console.log('Data received from Db:\n');
			//console.log(result[0].product_name);
			
			var data = [];
			for (var i in result) {
				var v_product = new Product(result[i].product_id, result[i].product_name, result[i].price, result[i].imageurl, result[i].brand, result[i].link);
				data[i] = v_product;
			}
			
			v_products = data;
		  
			//console.log(v_products);
			//doRelease(connection);
		});
		
		while(v_products == null){ deasync.runLoopOnce(); }
		
		console.log(v_products);
		return v_products;
	}
	
	createProduct(v_product) {
		
		var con = mysql.createConnection({
			host: dbConfig.host,
			user: dbConfig.user,
			password: dbConfig.password,
			database: dbConfig.database
		});
		
		con.connect(function(err){
		  if(err){console.log('Error connecting to Db');return;}
		  console.log('Connection established');
		});	
		
		//var strsql = "insert into products(product_id, product_name, price, imageurl, brand, link) values ?"; //multiple inserts
		var strsql = "insert into products set ?";
		
		con.query(strsql,v_product,function(err,result){
			if(err) {console.log(err.message);return;}

			console.log('Data received from Db:\n');
			console.log(result);		
			
			//doRelease(connection);
		});
		
		//while(v_products == null){ deasync.runLoopOnce(); }
	}
	
	doRelease(connection) {
	  connection.release(function(err) {
		if (err) {
		  console.error(err.message);
		  return callback(err);
		}
	  });
	}
};