Author: Kieu Nguyen

I'm learning React and Node JS and have created the following programs:

	1. Scrape Data program
		Named: scrape-data
		This program written by Java and third party Jsoup 
		To pull e-commerce data from any other sites (ebay, amazon, etc.)
		And then store data into database, this program stores into Mysql database
		This program can run and pull data from multiple sites by providing parameters
			cd scrape-data/dist
			run runScrapeData.bat ebay,amazon
			
	2. React Nodejs program
		Named: react-nodejs
		This program written by React and Nodejs
		To display information (product name, price, brand, image, etc.) that pulled from the program above
		In order by to run this program, you need to install Nodejs and mysqldb library using NPM package included in Nodejs
		
	3. Scraped Data
		Named: scraped_data_products.json
		Data were generated in JSON format so it is easy to load into database or read by programs.
	
...