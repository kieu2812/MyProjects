
/**********************
    ***********************/
   var shoppingCart = (function () {

    //private properties
    var cart = [];
    var productList=[

            {
              "id": 1,
              "img": "./imgs/watch1.jpg",
              "productTitle": "Chronograph",
              "price": 500.01,
              "inventory": 2
            },
            {
              "id": 2,
              "img": "./imgs/watch2.jpg",
              "productTitle": "Quartz",
              "price": 10.99,
              "inventory": 10
            },
            {
              "id": 3,
              "img": "./imgs/watch3.jpg",
              "productTitle": "Weekender",
              "price": 19.99,
              "inventory": 5
            }
    ];

    class Product {
           constructor(id, productTitle, img, price, quantity, inventory) {
               this.id = id;
               this.img = img;
               this.productTitle = productTitle;
               this.price = price;
               this.quantity = quantity;
               this.inventory = inventory;
           }
       }
;

    // saveCart()  save cookies
    /* localStorage is properties of Window object
    . Local storage saves data in the browser so your scripts can retrieve it later.
     This is important here so the cart can be passed around between different html pages,
    and it allows the cart to persist between browsing sessions.
    */

    function saveCart() {
        localStorage.setItem("shoppingCart", JSON.stringify(cart));

    };


    // loadCart() -> load local cookies
    function loadCart() {
        var cartStorage = localStorage.getItem("shoppingCart");
        if(cartStorage== null){
            console.log('Cart is empty');
        }
        else{
            cart= JSON.parse(cartStorage);
        }
    };

    function updateInventoryProductList(id, inventory){
        for( var i in productList){


            if(productList[i].id=== id){
                console.log(productList[i].productTitle, productList[i].inventory);
                productList[i].inventory = inventory;
            }
           // console.log(productList[i].productTitle, productList[i].inventory);
        }
    };
    //Public method and properties
    var obj= {};

    obj.getProductList = function(){
        //console.log(productList);
        return productList;
    };

    ///////add Product to Cart (productTitle, price, quantity)

    obj.addProductToCart = function (id) {

        var product = productList.find( function(pro) {
           return pro.id ===id;
        });

        // check Product in cart first, if exists, just increase the quantity
        for (var i in cart) {
            if (cart[i].id === id) {
                cart[i].productTitle = product.productTitle;
                cart[i].quantity ++;
                cart[i].inventory --;
                cart[i].img= product.img;
               // updateInventoryProductList(id, cart[i].inventory);
                saveCart();
                displayProductList();
                return;
            }
        }
        // else, add new Product to Cart.

        product.quantity=1;
        product.inventory--;

        cart.push(product);

        saveCart();
        displayProductList();

    };
    obj.addMoreToCart= function(id){
        console.log("btn add to cart click. Product List",productList);
        var product = productList.find( function(pro) {
           return pro.id ===id;
        });
        // check Product in cart first, if exists, just increase the quantity
        for (var i in cart) {
            if (cart[i].id === id) {
                cart[i].productTitle = product.productTitle;
                cart[i].quantity ++;
                cart[i].inventory --;
                cart[i].img= product.img;

                saveCart();

                return;
            }
        }
    };
    ///////Remove Product from Cart (productTitle)
    obj.removeProductFromCart = function (id) {
        for (var i in cart) {
            if (cart[i].id === id) {
                cart[i].quantity--;
                cart[i].inventory++;

                if (cart[i].quantity == 0) {
                    cart.splice(i, 1);
                }
                break;
            }
        }
        saveCart();

    };
    // remove product  from Cart all (productTitle) // remove all item productTitle
    obj.removeProductCartAll = function (id) {
        console.log("removeProductCartAll click",id);
        for (var i in cart) {
            if (cart[i].id === id) {
                cart.splice(i, 1);
                break;
            }
        }
        saveCart();
        this.displayYourCart();
    };

    // countCart() // how many product in Cart.
    obj.countCart = function () {
        var totalCount = 0;
        for (var i in cart) {
            totalCount += cart[i].quantity;
        }
        return totalCount;
    };

    // toTalCart() // return total cost
    obj.subTotalCart = function (id) {

        return cart.filter((product) => product.id===id)
                    .map(product => product.price * product.quantity);

        // round the numeric values to a fixed number of decimal places.
    };
    obj.totalCart = function () {
        var totalCost = 0;
        return cart.map(product => product.price * product.quantity)
                    .reduce((totalCost, productCost) => totalCost +productCost)
                   .toFixed(2);
        // round the numeric values to a fixed number of decimal places.
    };
    //listCart -> array of item
    obj.listCart = function () {
        var cartCopy = [];
        for (var i in cart) {
            var product = cart[i];
            var productCopy = {};
            for (var p in product) {
                productCopy[p] = product[p];

            }
            productCopy.total = (productCopy.price * productCopy.quantity).toFixed(2);
            cartCopy.push(productCopy);
        }
        return cartCopy;

    };


    obj.setQuantityForProduct = function (id, quantity) {
            for (var i in cart) {
                if (cart[i].id === id) {
                    cart[i].inventory = cart[i].inventory - (quantity- cart[i].quantity);
                    cart[i].quantity = quantity;
                    saveCart();
                    break;
                }
            }
    };


    function displayProductList(){
    //console.log('Display product List');
        var output ="";
        if(cart.length>0){
            document.getElementById("link-your-cart").innerHTML= '<i class="fas fa-shopping-cart"></i>'+"Your Cart";
        }
        shoppingCart.getProductList().forEach(function(product){

            var isDisabled ="";
            var textInventory="";

            if(product.inventory>0){
                  textInventory= ' <p>'+product.inventory +  ' REMAINING</p>' ;
            }
            else{
                isDisabled="disabled";
                textInventory = "<p> Sold out </p>";
            }
            console.log(textInventory);

            output +=
            '<div class="product">' +
                '<div class="card mb-5">' +
                        '<div class="row">' +
                            '<div class="col-md-4 col-sm-12">' +
                                '<img  src="'+product.img+'">' +
                            '</div>' +
                            '<div class="col-md-4 col-sm-6 col-xm-8" id="productDetails"> ' +
                                '<div class="top">' +
                                    '<h3 class="productTitle" >'+product.productTitle + ' </h3>' +
                                 textInventory.toString() +
                                '</div>' +
                                    '<button class="btnAddToCart"  onclick="shoppingCart.addProductToCart('+
                                        product.id+')"'+ isDisabled  +'>ADD TO CART</button>' +

                            ' </div>' +
                            '<div class="col-md-4 col-sm-6 col-xs-4">' +
                                '<p class="price">'+product.price+ '</p>' +
                            '</div>' +
                        '</div>' +
                '</div>' +
             '</div>';

        });

        document.getElementById("productList").innerHTML= output;

    };

    obj.displayYourCart=function(){

        loadCart();

        if(cart.length==0){
            console.log("Cart is empty");
            document.getElementById("empty-cart").style.display = "block";
            document.getElementById("card-list").style.display = "none";

        }else{

            document.getElementById("empty-cart").style.display = "none";
            document.getElementById("card-list").style.display = "block";
            var output ='<div class="card-list my-5">';
            var totalCost =0;
            cart.forEach(function(product){
                output +='<div class="card-item">' +
                        '<div class="row">' +
                           '<div class="col s6 mb-2">' +
                                '<img src="' + product.img+'" class="img-responsive">'+
                            '</div>' +

                            '<div class="col s6 mb-2">' +
                                '<h4>' + product.productTitle+'</h4>' +
                                '<p class="price">' + product.price+'</p>' +
                                '<button  onclick="shoppingCart.removeProductCartAll(' + product.id+')"'+
                                     'class="remove-item">Remove</button>' +
                            '</div>' +
                       '</div>' +
                        '<form>' +
                            '<div class="form-row align-items-center">' +
                                '<div class="col s4  form-group">' +
                                    '<button class="btn btn-secondary form-control" onclick="shoppingCart.removeProductFromCart(' + product.id+')">-</button>' +
                                '</div>' +
                                '<div class="col s4  form-group">' +
                                    '  <input class="qtyItem btn btn-secondary" type="number" value="'+ product.quantity +
                                    '" step="1" min="0" max="'+ product.inventory +'" >' +
                                  '</div>' +
                                '<div class="col s4 form-group">' +
                                    '<button class="btn btn-secondary form-control"' +
                                        'onclick="shoppingCart.addMoreToCart(' + product.id+')"'+ isAddMore +'></button>' +
                                '</div>' +
                            '</div>' +
                        '</form>' +

                    '</div>';

            var subTotalCost =  Number(shoppingCart.subTotalCart(product.id)).toFixed(2);
            var tax = Number(subTotalCost* 0.125).toFixed(2);

            output +=  '  <div class="card-summary">'+
                '<hr>'+
                '<div class="row">'+

                    '<div class="col s6">'+
                        '<p> Subtotal</p>'+
                        '<p> Tax</p>'+
                    '</div>'+

                    '<div class="col s6">'+
                        '<p>'+ subTotalCost + '</p>'+
                        '<p>'+ tax+ '</p>'+
                    '</div>'+
                '</div>'+
                '<hr>'+
                '</div>';
            });
            ////end forEach
            totalCost = shoppingCart.totalCart();
            output +=
                '<div class="row">'+
                        '<div class="col s6">Total</div>'+
                        '<div class="col s6">'+totalCost+'</div>'+
                    '</div>'+
                '</div>';
        output += ' <button id="btnCheckOut" class="btn btn-primary" onclick="shoppingCart.checkOut()"> Check Out</button>';
        document.getElementById("your-cart").innerHTML= output;
       // console.log(output);
        }
    };


    return obj;
})();
$(".qtyItem").change(function(event){
  var quantity = Number($(this).val());
  var id = $(this).attr("productId");
  console.log('quantity', quantity);
  console.log('id',id);
  shoppingCart.setQuantityForProduct(id, quantity);
  shoppingCart.displayYourCart();
});
