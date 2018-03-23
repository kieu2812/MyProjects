module.exports = class Retailer {
  constructor(retailer_id, retailer_name, address, rating, product_id) {
    this.retailer_id = retailer_id;
    this.retailer_name = retailer_name;
    this.address = address;
    this.rating = rating;
	this.product_id = product_id;
  };
  
  productDetail() {
	var data = new global.ProductDao().getProduct(this.product_id);
	return data;
  }
};