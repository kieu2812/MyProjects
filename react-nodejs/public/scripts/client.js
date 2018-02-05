var ProductBox = React.createClass({
  getInitialState: function() {
	//this will hold all the data being read and posted to the database  
	return {
      data: [],
      currentPage: 1,
      itemsPerPage: 15
    };
    this.handleClick = this.handleClick.bind(this);
    
  },    
  loadProductsFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  componentDidMount: function() {
    this.loadProductsFromServer();
    //... and set an interval to continuously load new data:
    //setInterval(this.loadProductsFromServer, this.props.pollInterval);
  },
  handleProductSubmit: function(product) {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      type: 'GET',
      data: product,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },  
  handleClick(event) {
    this.setState({
      currentPage: Number(event.target.id)
    });
  },  
  render: function() {
	const { data, currentPage, itemsPerPage } = this.state;
    // Declare constants for paging
    const indexOfLast = currentPage * itemsPerPage;
    const indexOfFirst = indexOfLast - itemsPerPage;
    const currentItems = data.slice(indexOfFirst, indexOfLast);
	
	// Display products
    const renderProducts = currentItems.map((data, index) => {

		return (
		  <div className="product">
			<table>
			<tr>
			<td><a href= {data.plink}><img src={data.imageurl} /></a></td>
			<td>
				<table>
					<tr><td><a href= {data.plink}><h5>{data.product_name}</h5></a></td></tr>
					<tr><td>Price: ${data.price}</td></tr>
					<tr><td>By: {data.brand}</td></tr>
					<tr><td>&nbsp;</td></tr>				
				</table>	
			</td>
			</tr>
			</table>
		  </div>
		);
	});

    // Calculate page numbers
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(data.length / itemsPerPage); i++) {
      pageNumbers.push(i);
    }

    const renderPageNumbers = pageNumbers.map(number => {
      return (
		<td key={number} id={number} onClick={this.handleClick}>{number}</td>
      );
    });
	
    // Show the form for new products
    return (
      <div className="productBox">
        <h1>Products</h1>
		<div>
			<ul id="page-numbers"><h4>Pages:</h4>{renderPageNumbers}</ul>
			<ul>{renderProducts}</ul>
			<ul id="page-numbers"><h4>Pages:</h4>{renderPageNumbers}</ul>
		</div>		
		<ProductForm onProductSubmit={this.handleProductSubmit} />
      </div>
	  
    );
  }
});

var ProductList = React.createClass({
  render: function() {
    var productNodes = this.props.data.map(function(product) {
      //map the data to individual products
      return (
        <Product
          product_name={product.product_name}
          key={product.product_id}
          price={product.price}
		  brand={product.brand}
		  plink={product.plink}
        >
          {product.imageurl}
        </Product>
      );
    });
    //print all the nodes in the list
    return (
      <div className="productList">
        {productNodes}
      </div>
    );
  }
});

var Product = React.createClass({
  render: function() {
    //display an individual product
    return (
      <div className="product">
		<table>
		<tr>
		<td><img src={this.props.children.toString()} /></td>
		<td>
			<table>
				<tr><td><h5>{this.props.product_name}</h5></td></tr>
				<tr><td>Price: ${this.props.price}</td></tr>
				<tr><td>By: {this.props.brand}</td></tr>
				<tr><td>{this.props.plink}</td></tr>
				<tr><td>&nbsp;</td></tr>				
			</table>	
		</td>
		</tr>
		</table>
          
      </div>
    );
  }
});

var ProductForm = React.createClass({
  getInitialState: function() {
    return {
      product_name: "",
      price: undefined,
      imageurl: "",      
	  brand: "",      
	  plink: "",      
      advancedSearch: ""
    };
  },
  handleSubmit: function(e) {
    //Prevent the defaul behavior
    e.preventDefault();
    
    //Clean up the data
    var product_name = this.state.product_name.trim();
    var price = this.state.price;
    var imageurl = this.state.imageurl.trim();
	var brand = this.state.brand.trim();
	var plink = this.state.plink.trim();
	var advancedSearch = this.state.advancedSearch.trim();
 
    //Submit search inputs to the parent component
    this.props.onProductSubmit({product_name: product_name, brand: brand, advancedSearch: advancedSearch});
    
    //Now that the form is submitted, we empty all the fields
    this.setState({
      product_name: '',
      price: undefined,
      imageurl: '',
	  brand: '',
	  plink: '',
      advancedSearch: ''
    });
  },
  setValue: function (field, event) {
    var object = {};
    object[field] = event.target.value;
    this.setState(object);
  },
  render: function() {
    return (
      <form className="productForm" onSubmit={this.handleSubmit}>
        <h2>Search</h2>
      
			{/* Search component */}
			<AdvancedSearch
			  value={this.state.advancedSearch} 
			  onChange={this.setValue.bind(this, 'advancedSearch')} />
        
			<TextInput
			  value={this.state.product_name}
			  uniqueName="product_name"
			  text="Product Name"
			  onChange={this.setValue.bind(this, 'product_name')} />
        
			<TextInput
			  value={this.state.brand}
			  uniqueName="brand"
			  text="Brand"
			  onChange={this.setValue.bind(this, 'brand')} />
		
		<br />
        <input type="submit" value="Submit" />
      </form>
    );
  }
});

var AdvancedSearch = React.createClass({

  handleClick: function(e) {
    this.props.onChange(e);	
  },
  render: function() {
    var value = this.props.value;
    
    return (
      <div className="AdvancedSearch">
        <select value={value} onChange={this.handleClick} multiple={false}>
		  <option value="none"></option>
          <option value="all">All</option>
          <option value="top10price">Top 10 product price........</option>
          <option value="other">Other</option>
        </select>
      
      </div>
    );
  }
});

var TextInput = React.createClass({

  handleChange: function(event){
    //Updating it's state
    if(this.props.onChange) {
      this.props.onChange(event);
    }
  },

  render: function() {
    return (
        <div className={this.props.uniqueName}>
          <input
            placeholder={this.props.text}
            className={'input input-' + this.props.uniqueName}
            onChange={this.handleChange}
            value={this.props.value} />
        </div>
      );
  }
});

ReactDOM.render(
  //<ProductBox url="/api/products" pollInterval={2000} />,
  <ProductBox url="/api/products" perPage={10} />,
  document.getElementById('content')
);
