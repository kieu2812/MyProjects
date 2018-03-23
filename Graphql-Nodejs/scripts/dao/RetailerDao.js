var mysql = require('mysql');
var deasync = require('deasync');

var dbConfig = require('../config/config.js');
var Retailer = require('../domain/Retailer.js');

module.exports = class RetailerDao {

	getRetailerByProduct(id) {
		
		var v_retailers = null; 
		
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
		
		var strsql = "SELECT retailer_id, retailer_name, address, rating, product_id FROM retailer WHERE product_id = ?";
		
		con.query(strsql,[id],function(err,result){
			if(err) {console.log(err.message);return;}

			console.log('Data received from Db:\n');
			//console.log(result[0].product_name);
			var data = [];
			for (var i in result) {
				var v_retailer = new Retailer(result[i].retailer_id, result[i].retailer_name, result[i].address, result[i].rating, result[i].product_id);
				data[i] = v_retailer;
			}
			
			v_retailers = data;
		  
			//doRelease(connection);
		});
		
		while(v_retailers == null){ deasync.runLoopOnce(); }
		
		console.log(v_retailers);
		return v_retailers;
	}
	
	getRetailers() {
		
		var v_retailers = null; 
		
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
		
		var strsql = "SELECT retailer_id, retailer_name, address, rating, product_id FROM retailer";
		
		con.query(strsql,function(err,result){
			if(err) {console.log(err.message);return;}

			console.log('Data received from Db:\n');
			//console.log(result[0].product_name);
			
			var data = [];
			for (var i in result) {
				var v_retailer = new Retailer(result[i].retailer_id, result[i].retailer_name, result[i].address, result[i].rating, result[i].product_id);
				data[i] = v_retailer;
			}
			
			v_retailers = data;
		  
			//doRelease(connection);
		});
		
		while(v_retailers == null){ deasync.runLoopOnce(); }
		
		console.log(v_retailers);
		return v_retailers;
	}
};