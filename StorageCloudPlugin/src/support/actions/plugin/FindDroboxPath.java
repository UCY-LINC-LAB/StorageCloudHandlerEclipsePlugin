package support.actions.plugin;

import java.util.ArrayList;

import org.eclipse.core.resources.ResourcesPlugin;

public class FindDroboxPath {
	public ArrayList<String> filesPathsdropbox=null;
	public char PathSeperetor; 
	public String wordkspace;
	
	public void Droboxpath(ArrayList<String> local, String projectname){
		filesPathsdropbox= new  ArrayList<String>();
		String oper_syste= System.getProperty("os.name");
	
	 	if (oper_syste.contains("Windows")){
	 		PathSeperetor='\\';
	 	}
	 	String wordkspace_location=ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
	 	int last=wordkspace_location.lastIndexOf("/");
	 	wordkspace=wordkspace_location.substring(last+1,wordkspace_location.length() );
	 	//System.out.println(wordkspace);	
	 	
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
			String droboxpath ="/"+projectname+newpath;
			//System.out.println(droboxpath);
			filesPathsdropbox.add(droboxpath);
		}
	}
}
