package support.actions.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class FindLocalParth {
	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	
	//local Paths for all files
	public ArrayList<String> filesPathslocal=null;
	
	//Selection Item Name
	public ArrayList<String> SelectionName;
	
	//Selection Item Path
	public ArrayList<String> SelectionPath;
	
	public String projectName;
	
	public FindLocalParth(){
		
	}
	
	private IResource test(Object element){
//		if (!(sel instanceof IStructuredSelection))
//			return null;
//		IStructuredSelection ss = (IStructuredSelection) sel;
//		Object element = ss.getFirstElement();
		if (element instanceof IResource)
			return (IResource) element;
		if (!(element instanceof IAdaptable))
			return null;
		IAdaptable adaptable = (IAdaptable) element;
		Object adapter = adaptable.getAdapter(IResource.class);
		return (IResource) adapter;
	}
	
	public void getSelection(ExecutionEvent event){
		System.out.println("-----------------------------------------------------");
		IPath path=null;
		ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		filesPathslocal= new  ArrayList<String>();
		SelectionName = new ArrayList<String>() ;
		SelectionPath = new ArrayList<String>();
		if (selection instanceof IStructuredSelection) { 
			 for (Iterator it = ((IStructuredSelection) selection).iterator(); it.hasNext();) { 
		           Object selectedProject =it.next();
		           //System.out.println(test(selectedProject).getLocation().toString());
		           IResource element =test(selectedProject);
		           if(element!=null){
			           String prname =test(selectedProject).getProject().toString();
			           projectName=prname.substring(2,prname.length());
			           //System.out.println("ProjectName:" + projectName);
			           path=element.getLocation();
			           SelectionPath.add(path.toString());
			           File temp = new File(path.toFile().getAbsolutePath());
			           SelectionName.add(temp.getName());
			           
			           if(temp.isDirectory()){	        	 
			               getAllFiles(path);
			           }
			           else if(temp.isFile()){	   
			        	   filesPathslocal.add(temp.getAbsolutePath());
			        	   
			           }
		           }
	           }
		}
	}
	
	
	public  void getAllFiles(IPath path){
		File f = new File(path.toFile().getAbsolutePath());
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
 
       for(int i=0; i<files.size(); i++){
    	   if(files.get(i).isFile()){
    		  
    		   String  temp=files.get(i).getAbsolutePath();
    		   filesPathslocal.add(temp);
    		   
    	   }
    	   else if(files.get(i).isDirectory()){
    		 IPath temp =new Path(files.get(i).getAbsolutePath());
    		 getAllFiles(temp);
    		 
    	   }
       }
	}
	
	
}
