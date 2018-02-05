package Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

public class DownUpImage {
	
	public static String downloadImage(String strImageURL,String name){
		//Get path to store image in test.properties file
        HashMap<String, String> hash=ReadPropertiesFile.getProperties();
        String folderStorage= hash.get("IMAGE_DESTINATION_FOLDER");
        hash=null;
        
        
        //get file name from image path
        String strImageName = name+strImageURL.substring( strImageURL.lastIndexOf("/") + 1) ;
        strImageName=strImageName.replaceAll("[^a-zA-Z0-9.]", "");		
        System.out.println("Saving: " + strImageName + ", from: " + strImageURL );
        
        try {
            
            //open the stream from URL
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();
            
            byte[] buffer = new byte[4096];
            int n = -1;
            
            OutputStream os = 
                new FileOutputStream( folderStorage + "/" + strImageName );
            
            //write bytes to the output stream
            while ( (n = in.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }
            
            //close the stream
            os.close();
            
            System.out.println("Image saved");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
       return ("/images/"+strImageName);
    }


}
