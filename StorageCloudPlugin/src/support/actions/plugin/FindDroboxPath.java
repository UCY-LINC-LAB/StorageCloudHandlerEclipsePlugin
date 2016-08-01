package support.actions.plugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.eclipse.core.resources.ResourcesPlugin;

public class FindDroboxPath {
	public ArrayList<String> filesPathsdropbox=null;
	public char PathSeperetor; 
	public String wordkspace;
	public String CloudStartPath;
	
	public void Droboxpath(ArrayList<String> local, String projectname){
		filesPathsdropbox= new  ArrayList<String>();
		String oper_syste= System.getProperty("os.name");
	
	 	if (oper_syste.contains("Windows")){
	 		PathSeperetor='\\';
	 	}
	 	String wordkspace_location=ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
	 	int last=wordkspace_location.lastIndexOf("/");
	 	wordkspace=wordkspace_location.substring(last+1,wordkspace_location.length() );
	 
	 	CloudStartPath=readforProperties(wordkspace_location+"/"+projectname+"/");
		for(int i=0; i<local.size(); i++){
			String locPath=local.get(i);
			String newpath= locPath.replace("\\","/");
			
			int k=0;
			String word = null;
			
			for(int  j=0; j<locPath.length(); j++){
				k++;
				if(locPath.charAt(j)!=PathSeperetor){
					word=word+locPath.charAt(j);
				}
				else{
					
					if(word.equals(projectname)){
						break;
					}
					word="";
				}
			}
			
			newpath=newpath.substring(k-1, locPath.length());
			//String droboxpath ="/Eclipse Repository/"+wordkspace+"/"+projectname+newpath;
			String droboxpath=CloudStartPath+projectname+newpath;
			//System.out.println(droboxpath);
			filesPathsdropbox.add(droboxpath);
		}
	}
	
	

	
	public String readforProperties(String filepath){
		  Properties proprt= new Properties();
			String path;
			try {
				InputStream input = new FileInputStream(filepath+"StorageCloud.properties");
				proprt.load(input);
				path=proprt.getProperty("PATH");
				input.close();					
			} 
			catch (FileNotFoundException e) {
				return "/";
			} 
			catch (IOException e) {
				return null;
			}
		
			
		return path;
		  
	  }
	
	public static boolean writeforProperties(String filepath,String Path) {

		Properties pro = new Properties();
	
		pro.setProperty("PATH", Path);
		
		FileWriter write = null;
		try {
			write = new FileWriter(filepath+"StorageCloud.properties");
			pro.store(write, "Author :panos");
			write.close();

			return true;

		} catch (IOException error) {
			// TODO Auto-generated catch block
			error.printStackTrace();
			return false;
		}

	}
}
