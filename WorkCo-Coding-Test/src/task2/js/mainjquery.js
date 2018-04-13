$("#card-list").on("change", ".qtyItem", function (event) {

    var quantity = Number($(this).val());
    var id = $(this).attr("productId");

    shoppingCart.setQuantityForProduct(id, quantity);
    displayYourCart();
});


$("#btnCheckOut").click(function (event) {

    shoppingCart.clearCart();
    displayYourCart();

});

/////////////////
/////////       DISPLAY PRODUCT ON PAGE SHOPPING CART
////////////////
function displayProductList() {

    var output = "";
    if (Number(shoppingCart.countCart()) > 0) {
        document.getElementById("link-your-cart").innerHTML = '<i class="fas fa-shopping-cart"></i>' + "Your Cart";
    }
    var list = shoppingCart.getProductList();
    for (var product of list) {

        var isDisabled = "";
        var textInventory = "";

        if (product.inventory > 0) {
            textInventory = ' <p>' + product.inventory + ' REMAINING</p>';

        }
        else {
            isDisabled = "disabled";
            textInventory = "<p> Sold out </p>";
        }
        output +=
            '<div class="product">' +
                '<div class="card mb-2">' +
                    '<div class="row">' +
                        '<div class="col-sm-12 col-md-4 ">' +
                            '<img  src="' + product.img + '" class="img-responsive" alt="' + product.productTitle + '">' +
                        '</div>' +
                        '<div class="col-sm-8 col-md-4 "> ' +
                            '<div class="top">' +
                                '<h5 class="productTitle" >' + product.productTitle + ' </h5>' +
                                    textInventory +
                           '</div>' +
                                '<button class="btnAddToCart"  onclick="shoppingCart.addProductToCart(' +
                                product.id + ')"' + isDisabled + '>ADD TO CART</button>' +

                        ' </div>' +
                        '<div class="col-sm-4 col-md-4">' +
                            '<p class="price">' + product.currency + " " + product.price + '</p>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>';

    }
    document.getElementById("productList").innerHTML = output;
   // console.log(output);

};



//////////////////////
///        DISPLAY YOUR CART
/////////////////////

function displayYourCart() {

    shoppingCart.loadCart();

    if (Number(shoppingCart.countCart()) === 0) {
       // console.log("Cart is empty");
        document.getElementById("empty-cart").style.display = "block";
        document.getElementById("card-list").style.display = "none";
        document.getElementById("btnCheckOut").style.visibility = "hidden";
       
    } else {


        document.getElementById("empty-cart").style.display = "none";
        document.getElementById("card-list").style.display = "block";
        var output = "";
        var totalCost = 0;
        shoppingCart.getCart().forEach(function (product) {
            var isAddMore = (product.inventory > 0 ? "" : "disabled");

            output += 
            '<div class="card-item">' +
                '<div class="row">' +
                    '<div class="col-s6 mb-2">' +
                         '<img src="' + product.img + '" class="img-responsive" alt="' + product.productTitle + '">' +
                    '</div>' +

                    '<div class="col-s6 mb-2 product-Info">' +
                        '<h4>' + product.productTitle + '</h4>' +
                        '<p class="price">' + product.currency + " " + product.price + '</p>' +
                        '<button  onclick="shoppingCart.removeProductCartAll(' + product.id + ')"' +
                        'class="remove-item">Remove</button>' +
                    '</div>' +
                '</div>' +
            '<form>' +
             '<div class="form-row align-items-center">' +
                    '<div class="col-s4  form-group">' +
                        '<button class="btn btn-secondary form-control minus" onclick="shoppingCart.removeProductFromCart(' + product.id + ')">-</button>' +
                    '</div>' +
                '<div class="col-s4  form-group">' +
                    '  <input class="qtyItem input-group" productId="' + product.id +
                    '" type="number" value="' + product.quantity +
                    '" step="1" min="0" max="' + (Number(product.inventory) + Number(product.quantity)) + '" isValid > ' +

                '</div>' +
                '<div class="col-s4 form-group">' +
                    '<button class="btn btn-secondary form-control plus"' +
                    'onclick="shoppingCart.addMoreToCart(' + product.id + ')"' + isAddMore + '>+</button>' +
                '</div>' +
              '</div>' + 
            '</form>' +
            '</div>';

            var subTotalCost = Number(shoppingCart.subTotalCart(product.id)).toFixed(2);
            var tax = Number(subTotalCost * 0.125).toFixed(2);

            output +=
             '  <div class="card-summary">' +
                    '<hr>' +
                    '<div class="row">' +

                        '<div class="col s6">' +
                            '<p> Subtotal</p>' +
                            '<p> Tax</p>' +
                        '</div>' +

                        '<div class="col s6">' +
                            '<p>' + subTotalCost + '</p>' +
                            '<p>' + tax + '</p>' +
                        '</div>' +
                    '</div>' +
                    '<hr>' +
                '</div>';
        });
        ////end forEach
        totalCost = shoppingCart.totalCart();

        output +=
            '<div class="row" id="totalCost" >' +
                '<div class="col s6">Total</div>' +
                '<div class="col s6">' + totalCost + '</div>' +
            '</div>' +
        '</div>';

        document.getElementById("card-list").innerHTML = output;
        document.getElementById("btnCheckOut").style.visibility = "visible";

        document.getElementById("totalCost").style.visibility = "visible";
       //ss console.log(output);
    }
};
/////////////////////
///////     FECTH DATA FROM END POINT
////////////////////
function fetchProductFromEndPoint() {

    fetch('http://tech.work.co/shopping-cart/products.json')
        .then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then(function (data) {

            data.forEach(function (product) {
                var image = "";//(typeof product.img != 'undefined' ?  product.img: "./imgs/missing.jpg") ;

                if (image === "") {
                    switch (product.id) {
                        case 1: image = "./imgs/watch1.jpg"; break;
                        case 2: image = "./imgs/watch2.jpg"; break;
                        case 3: image = "./imgs/watch3.jpg"; break;
                        default: image = "./imgs/missing.jpg";
                    }
                }
                var pro = {
                    id: product.id,
                    img: image,
                    productTitle: product.productTitle,
                    price: product.price.value,
                    inventory: product.inventory,
                    currency: product.price.currency
                };

                shoppingCart.addToProductList(pro);
            })

            displayProductList();

        });
};

