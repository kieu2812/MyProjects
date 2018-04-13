
//console.log('file mainjs load')

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
              "currency":"USD",
              "inventory": 2
            },
            {
              "id": 2,
              "img": "./imgs/watch2.jpg",
              "productTitle": "Quartz",
              "price": 10.99,
              "currency":"USD",
              "inventory": 10
            },
            {
              "id": 3,
              "img": "./imgs/watch3.jpg",
              "productTitle": "Weekender",
              "price": 19.99,
              "currency":"USD",
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
   var obj= {};
    obj.saveCart= function() {
        localStorage.setItem("shoppingCart", JSON.stringify(cart));

    };


    // loadCart() -> load local cookies
    obj.loadCart= function() {
        var cartStorage = localStorage.getItem("shoppingCart");
        if(cartStorage== null){
            console.log('Cart is empty');
        }
        else{
            cart= JSON.parse(cartStorage);
            
        }
    };
    obj.getCart = function(){
        return cart;
    }
    
    obj.addToProductList= function(product){
        productList.push(product);
       
    };

    
    obj.getProductList = function(){ 
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
                cart[i].currency = product.currency;
               // updateInventoryProductList(id, cart[i].inventory);
                this.saveCart();
                displayProductList();
                return;
            }
        }
        // else, add new Product to Cart.

        product.quantity=1;
        product.inventory--;

        cart.push(product);

        this.saveCart();
        displayProductList();

    };
    obj.addMoreToCart= function(id){
       // console.log("addMoreToCart click");
        for (var i in cart) {
            if (cart[i].id === id ) {
                //console.log("cart inventory: ",cart[i].inventory);
                cart[i].quantity ++;
                cart[i].inventory --;
                this.saveCart();
                displayYourCart();
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
        this.saveCart();

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
        this.saveCart();
        displayYourCart();
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
        if(cart.length>0)
            return  cart.map(product => product.price * product.quantity)
                    .reduce((totalCost, productCost) => totalCost +productCost)
                    .toFixed(2);
        else return 0;

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
        console.log('In setQuantityForProduct method')
        for (var i in cart) {        
            if (cart[i].id == id) {    
                cart[i].inventory = cart[i].inventory - (quantity- cart[i].quantity);   
                cart[i].quantity = quantity;
                this.saveCart();
                break;
            }
        }
    };
    
    obj.clearCart= function(){
        cart= [];
        this.saveCart();
    };
 
   
   
    return obj;
})();

