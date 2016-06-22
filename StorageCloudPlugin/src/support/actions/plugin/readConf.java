package support.actions.plugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class readConf {

	public HashMap<String,String> params=null;
	public static String DEFAULT_CONFIG_PATH="conf.properties";
	
	  public boolean  readforProperties(){
		  Properties proprt= new Properties();
			
			try {
				InputStream input = new FileInputStream(DEFAULT_CONFIG_PATH);
				proprt.load(input);
				
				params = new HashMap<String,String>();
				params.put("opersyst", proprt.getProperty("operating.system"));
				params.put("ACCESS_TOKEN", proprt.getProperty("storage.cloud.token"));
				params.put("extra", null);
				
				input.close();					
			} 
			catch (FileNotFoundException e) {
				return false;
			} 
			catch (IOException e) {
				return false;
			}
		
			
		return true;
		  
	  }
}
