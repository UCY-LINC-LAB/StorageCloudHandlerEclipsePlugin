package support.actions.plugin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadIgnore {
	
	public HashMap<String,String> filesIgnore=null;
	
	public Boolean  Read(String Path){
		filesIgnore=new HashMap<String,String>();
		
		 // The name of the file to open.
        String fileName = Path;

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                filesIgnore.put(line, null);
            }   

            // Always close files.
            bufferedReader.close(); 
            return true;
        }
        catch(FileNotFoundException ex) {
            return false;
        }
        catch(IOException ex) {
            return false;
          
        }
    	
	}
	
	// kanei remove ola ta path apo to FindLocalParth pou uparxoyn sto 
	// ignore file
	public void Ignore(FindLocalParth fl){
		boolean flag[]=new boolean [fl.filesPathslocal.size()] ;
		//int size =fl.filesPathslocal.size();
		for (int i=0; i<fl.filesPathslocal.size(); i++){
			if(!filesIgnore.containsKey(fl.filesPathslocal.get(i))){			
				flag[i]=true;	
			}
			else{
				flag[i]=false;	
			}
		}
		ArrayList<String> temp =new ArrayList<String>();
		for (int i=0; i<fl.filesPathslocal.size(); i++){
			if(flag[i]){
				temp.add(fl.filesPathslocal.get(i));
			}
			
		}
		
		fl.filesPathslocal=temp;
	}

	public void RemoveIgnore(FindLocalParth fl){
		for (int i=0; i<fl.filesPathslocal.size(); i++){
			
			if(filesIgnore.containsKey(fl.filesPathslocal.get(i))){	
				filesIgnore.remove(fl.filesPathslocal.get(i));
			}	
		}
	}
}
