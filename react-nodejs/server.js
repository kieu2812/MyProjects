
var mysql = require("mysql");
var path = require('path');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();


app.set('port', (process.env.PORT || 3000));

app.use('/', express.static(path.join(__dirname, 'public')));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.get('/api/products', function(req, res) {
	// Create a connection to the db
	var con = mysql.createConnection({
	  host: "localhost",
	  user: "",
	  password: "",
	  database: ""
	});	
	con.connect(function(err){
	  if(err){console.log('Error connecting to Db');return;}
	  console.log('Connection established');
	});	
	
	//Search
	var advancedSearch = req.query.advancedSearch;
	console.log(advancedSearch);
	
	var product_name = req.query.product_name;
	console.log(product_name);
	
	var brand = req.query.brand;
	console.log(brand);
	
	//Search based on client inputs
	var strsql = "";
	if (advancedSearch == undefined || advancedSearch == "all") {
		strsql = "SELECT product_id, product_name, price, imageurl, brand, link as plink FROM Products where website='amazon' ORDER BY product_name limit 100";
	} else if (advancedSearch == "top10price") {
		strsql = "SELECT distinct product_id, product_name, price, imageurl, brand, link as plink FROM Products where website='amazon' ORDER BY price DESC limit 10";
	} else {
		var strwhere = "";
		if (product_name != undefined && product_name != '' && brand != undefined && brand != '') {
			strwhere = strwhere + " product_name like '%" + product_name + "%' OR brand = '" + brand + "'";
		} else if (product_name != undefined && product_name != '') {
			strwhere = strwhere + " product_name like '%" + product_name + "%'";
		} else if (brand != undefined && brand != '') {
			strwhere = strwhere + " brand = '" + brand + "'";
		}
		
		if (strwhere != '') {
			strsql = "SELECT distinct product_id, product_name, price, imageurl, brand, link as plink FROM Products WHERE " + strwhere;
		} else {
			strsql = "SELECT product_id, product_name, price, imageurl, brand, link as plink FROM Products where website='amazon' ORDER BY product_name limit 100";
		}
	}
	console.log(strsql);	
	
	con.query(strsql,function(err,rows){
	  if(err) {console.log(err.message);return;}

	  console.log('Data received from Db:\n');
	  console.log(rows);
	  
	  res.setHeader('Cache-Control', 'no-cache');
	  res.json(rows);
	  
	  //closeConnection(connection);
	});
	
});

//connections should always be released
function closeConnection(connection)
{
  connection.close(
    function(err) {
      if (err) {
        console.error(err.message);
      }
    });
}

app.listen(app.get('port'), function() {
  console.log('Server started: http://localhost:' + app.get('port') + '/');
});
