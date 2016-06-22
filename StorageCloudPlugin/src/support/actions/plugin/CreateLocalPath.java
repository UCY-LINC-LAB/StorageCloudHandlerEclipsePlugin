package support.actions.plugin;

import java.util.ArrayList;

public class CreateLocalPath {

	public ArrayList<String> LocalPath;
	
	public boolean dbPathToLocalPath (ArrayList<String> dbpath,String path,String projectname){
		
		
		LocalPath=new ArrayList<String>();

//		System.out.println(path);
//		System.out.println(projectname);
//		System.out.println(projectname.length());
		int k= path.lastIndexOf(projectname);
		
		//System.out.println("----P----->"+path.substring(0, (k-1)));

		String local= path.substring(0, (k-1));
		System.out.println(local);
		for(int i=0; i<dbpath.size();i++){
			String locPath=dbpath.get(i);
			String newpath= locPath.replace("/","\\");
			
			int l=0;
			String word = " ";
			
			for(int  j=0; j<locPath.length(); j++){
				l++;
				if(locPath.charAt(j)!='/'){
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
			//System.out.println(newpath);
			String droboxpath =local+"\\"+projectname+newpath;
			//System.out.println(droboxpath);
			LocalPath.add(droboxpath);
		}
		
		if(LocalPath.size()!=0){
			return true;
		}
			return false;
		
	}

	public boolean dbPathToLocalPath (ArrayList<String> dbpath,String pathproject){
		int k=pathproject.lastIndexOf('/');
		pathproject = pathproject.substring(0, k);
		pathproject=pathproject.replace('/','\\');
		
		LocalPath = new ArrayList<String>();
		for(int i=0;i<dbpath.size(); i++){
			String temp1=dbpath.get(i);
			
			temp1=temp1.replace('/','\\');
			String temp2=pathproject+temp1;
			LocalPath.add(temp2);
			
		}
		return true;
		
	}


}
