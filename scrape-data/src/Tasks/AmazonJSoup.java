package Tasks;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import DataAccess.Dao;
import Domain.Product;
import Util.DownUpImage;
import Util.ReadPropertiesFile;

public class AmazonJSoup  implements TaskBase{

	static HashMap<String, String> properties=null;
	@Test
	public void pullData() {
		// TODO Auto-generated method stub
		properties= ReadPropertiesFile.getProperties();
		
		try {
			
			// turn off htmlunit warnings
		    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
			
			
			// load page using HTML Unit and fire scripts
			//WebClient webClient = new WebClient();
		//	webClient.getOptions().setJavaScriptEnabled(true);
			
			WebClient webClient = new WebClient(BrowserVersion.CHROME);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getCookieManager().setCookiesEnabled(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getCookieManager().setCookiesEnabled(true);

			
			
			HtmlPage myPage = webClient.getPage(properties.get("amazon.url"));

			// convert page to generated HTML and convert to document
			Document doc = Jsoup.parse(myPage.asXml());
			System.out.println(doc.title());
			Elements elfootPage= doc.select(properties.get("amazon.totalPage"));
			
			//System.out.println(doc.text());
			if (elfootPage!=null){
				int numberPages= Integer.parseInt(elfootPage.text());
				numberPages=400;
				
				System.out.println("Total page is " + elfootPage.text()+"   ******************"+"\n");

			
				//go to each page
				for (int i=1; i<numberPages;i++){
						System.out.println("Go page " + i +"   ******************"+"\n"+"   ******************"+"\n");
						
						if (i!=1){
							Elements currPage= doc.select("#pagn>span>a:matches("+i+")");	
							String nextPage= "https://www.amazon.com"+ currPage.attr("href");
							System.out.println("Page"+ i +": " +nextPage);
							
							myPage=webClient.getPage(nextPage);
							doc= Jsoup.parse(myPage.asXml());
							
						}
						
						System.out.println("Call next page");
						Elements elListEachPage= doc.select(properties.get("amazon.list"));
						//System.out.println(elListEachPage.size());
						goEachPage(elListEachPage);
						
						System.out.println("Save data is succefull");
			/*			if(i==2)
							System.exit(0);
			*/	}
		
			}
			else{
				System.out.println("Can't find the number of pages ");
			}
		
			// clean up resources        
			webClient.close();	

		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}//close try catch				

	}
	public static void goEachPage(Elements elList){
		int i=0;
		System.out.println("Begining extract data"+ elList.size());
		
		ArrayList<Product > list= new ArrayList<Product>();
	
		for (Element el : elList) {
			i++;
			String image=null,name=null,price=null,brand=null,link=null;
			System.out.println("Item " + i);
			Elements elImage=  el.select(properties.get("amazon.imageUrl"));
			if (elImage!=null)
				 image= elImage.attr("src");
			System.out.println("Image " + image);
			Elements elName= el.select(properties.get("amazon.product_Name"));
			if (elName!=null){
				name= elName.text();
				if (name==null){
					name= el.getElementsByClass(properties.get("amazon.product_Nam2")).text();
				}
			}
				
			System.out.println("Name " + name );			
			Elements elBrand= el.select(properties.get("amazon.brand"));
			if (elBrand!=null){
				brand= elBrand.text();
				if (name==null){
					elBrand= el.select(properties.get("amazon.brand2"));
					brand= elBrand.text();
				}
			}
			
			System.out.println("Brand " + brand);
			
			Elements elLink= el.select(properties.get("amazon.link"));
			
			if (elLink!=null){
				link= elLink.attr("href");
				if (link==null){
					elLink= el.select(properties.get("amazon.brand2"));
					link= elLink.text();
				}
			}
			
			link= elLink.attr("href");
			System.out.println("Link " + link);
			
			
			if((el.getElementsByClass(properties.get("amazon.product_Price1")).first()) !=null){
				price =el.getElementsByClass(properties.get("amazon.product_Price1")).first().text();
				System.out.println("Price1 " + price );
			}
			
			if (price==null && el.getElementsByClass(properties.get("amazon.product_Price2")).first()!=null){
				price= el.getElementsByClass(properties.get("amazon.product_Price2")).first().text();
				System.out.println("Price2 " + price +"\n");
			}
			if (price==null){
				price="0";
			}
			else{
				if(price.contains("-")){
					String[] tam = price.split("-");
					price=tam[0];
					tam=null;
				}
				price= price.replace("$", "");
				price=price.trim().replace(" ", ".").replace(",", "");
			}		
			System.out.println("Price after format " + price +"\n");
			
			String strImageName= DownUpImage.downloadImage(image,"");
			System.out.println("Image save as " + strImageName+"\n");
			Product pro= new Product();
			pro.setProductId(System.currentTimeMillis()+i);
			pro.setProductName(name);
			pro.setPrice(Double.parseDouble(price));
			pro.setImageUrl(strImageName);
			pro.setBrand(brand);
			pro.setLink(link);
			pro.setWebsite("Amazon");
			list.add(pro);
			
		
		}
		try {

			Dao dao= new Dao();
			dao.getConnectDB();
			//dao.deleteAllData();
			dao.saveProducts(list);
			list=null;
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//System.err.println("Insert database is fail!!!!!!!!!!!!!!!!");
			e.printStackTrace();
		}
	
	}
	
	

}
