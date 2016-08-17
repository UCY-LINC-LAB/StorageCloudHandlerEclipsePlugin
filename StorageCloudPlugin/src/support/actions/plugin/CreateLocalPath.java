package support.actions.plugin;

import java.util.ArrayList;

public class CreateLocalPath {

	public ArrayList<String> LocalPath;
	
	public boolean dbPathToLocalPath (ArrayList<String> dbpath,String path,String projectname){
		
		
		LocalPath=new ArrayList<String>();

		
		
		int k= path.lastIndexOf(projectname);
		
		

		String local= path.substring(0, (k-1));
		
		for(int i=0; i<dbpath.size();i++){
			
			
			
			String locPath=dbpath.get(i);
			String newpath= locPath.replace("/","\\");
			
			int l=0;
			String word = " ";
			
			for(int  j=0; j<newpath.length(); j++){
				l++;
				if(newpath.charAt(j)!='\\'){
					word=word+locPath.charAt(j);
				}
				else{
					
					if(word.equals(projectname)){
						break;
					}
					word="";
				}
			}
			
			newpath=newpath.substring(l-1, locPath.length());
			String droboxpath =local+"\\"+projectname+newpath;
			//System.out.println(droboxpath);
			LocalPath.add(droboxpath);
		}
		
		if(LocalPath.size()!=0){
			return true;
		}
			return false;
		
	}

}
