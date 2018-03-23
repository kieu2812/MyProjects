module.exports = class Product {
  constructor(product_id, product_name, price, imageurl, brand, link) {
    this.product_id = product_id;
    this.product_name = product_name;
    this.price = price;
    this.imageurl = imageurl;
	this.brand = brand;
	this.link = link;
  }
  
  retailers() {
	var data = new global.RetailerDao().getRetailerByProduct(this.product_id);
	return data;  
  }
};