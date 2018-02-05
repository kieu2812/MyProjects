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

public class EbayByJSoup implements TaskBase {

	
	@Test
	public  void pullData() {
		// TODO Auto-generated method stub
		Document doc;
		HashMap<String, String> properties= ReadPropertiesFile.getProperties();
		 
		try {
			  	java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
			    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
				
				
				// load page using HTML Unit and fire scripts
			   
				
				@SuppressWarnings("resource")
				WebClient webClient = new WebClient(BrowserVersion.CHROME);
				webClient.getOptions().setJavaScriptEnabled(true);
				webClient.getOptions().setCssEnabled(true);//false
				webClient.getOptions().setUseInsecureSSL(true);
				webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
				webClient.getCookieManager().setCookiesEnabled(true);
				webClient.setAjaxController(new NicelyResynchronizingAjaxController());
				webClient.getOptions().setThrowExceptionOnScriptError(false);
				webClient.getCookieManager().setCookiesEnabled(true);

				       
				
				HtmlPage myPage = webClient.getPage(properties.get("url"));

				// convert page to generated HTML and convert to document
				doc = Jsoup.parse(myPage.asXml());
				
				System.out.println(doc.title());
				
				
			System.out.println("Opening page");
			
			Elements elList= doc.select(properties.get("list"));
			
			System.out.println("Total " + elList.size());
			
			extractData(elList,properties);

		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//save image
		public static void extractData(Elements elList,HashMap<String, String > properties){
			int i=0;
			System.out.println("Begining extract data"+ elList.size());
			
			ArrayList<Product > list= new ArrayList<Product>();
			for (Element el : elList) {
				i++;
				System.out.println("Item " + i);
				String image= el.select(properties.get("imageUrl")).attr("src");
				System.out.println("Image " + image);
				String name= el.select(properties.get("product_Name")).attr("title");
				System.out.println("Name " + name );
				String price= el.select(properties.get("product_Price1")).first().text();
				if(price.equals("") || price==null)
					price= el.select(properties.get("product_Price2")).text();
				price= price.replace("$", "").replace(",", "");
					
				System.out.println("Price " + price );
				String link= el.select(properties.get("link")).attr("href");
				System.out.println("Link "+ link);
				//save image with name is i with extension ".gif"
				String strImageName= DownUpImage.downloadImage(image, Integer.toString(i));
				System.out.println("********************\n");
				Product pro= new Product();
				pro.setProductId(System.currentTimeMillis()+i);
				pro.setProductName(name);
				pro.setPrice(Double.parseDouble(price));
				pro.setImageUrl(strImageName);
				pro.setBrand("");
				pro.setWebsite("Ebay");
				list.add(pro);
				
			
			}
	

			try {

				Dao dao= new Dao();
				dao.getConnectDB();
				//dao.deleteAllData();
				dao.saveProducts(list);
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				//System.err.println("Insert database is fail!!!!!!!!!!!!!!!!");
				e.printStackTrace();
			}
		
		
	

			
		}
		
	
}
