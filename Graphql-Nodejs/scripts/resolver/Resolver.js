global.ProductDao = require('../dao/ProductDao.js');
global.RetailerDao = require('../dao/RetailerDao.js');
 
var resolver = {

  getProduct: function ({id}) {
	var data = new global.ProductDao().getProduct(id);
	return data;
  },
  
  getProducts: function () {
	var data = new global.ProductDao().getProducts();
	return data;
  },
  
  createProduct: function ({input}) {
	var rt = new global.ProductDao().createProduct(input);
    return input;
  },
  
  getRetailers: function () {
    var data = new global.RetailerDao().getRetailers();
    return data;
  },
};

module.exports = resolver;