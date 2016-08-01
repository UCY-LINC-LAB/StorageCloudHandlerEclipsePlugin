package handlers.file.dropbox.actions;

import java.util.ArrayList;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Shell;

import cy.ac.ucy.cs.linc.storagecloud.ICloudStorageHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.DropboxHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.exceptions.ExceptionHandler;
import support.actions.plugin.ErrorDialogScreen;
import support.actions.plugin.FindDroboxPath;
import support.actions.plugin.readConf;

public class ChangePath {

	String path="";
	public void  ChangePathed(String projectName,Shell xx) {
		
		readConf input = new readConf();
		
		if (input.readforProperties()) {
			ICloudStorageHandler handler = new DropboxHandler();
			handler.cloudStorageHandlerinit(input.params);
		
			ArrayList<String> projects = new ArrayList<String>();
			
			try {
				handler.ListOfFolder(projects, path);
			} catch (ExceptionHandler e) {
				ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
				erroScreen.create("Error Find Path", "Connection Error: Cannot Browse Project from Dropbox.");
				erroScreen.open();
				
			}
			
			ChangePathDialogScreen ChangeScreen = new ChangePathDialogScreen(xx, projects, path);
			ChangeScreen.create();
			ChangeScreen.open();
			
			
			int choose=ChangeScreen.status();
			
			if(choose==2){
				path=path+"/"+ChangeScreen.filepath;
				ChangePathed(projectName,xx);
			}
			
			if(choose==1){
				if(!path.isEmpty()){
					int thesh=path.lastIndexOf("/");
					path=path.substring(0,thesh);
				}
				ChangePathed(projectName,xx);
			}
			if(choose==3){
				FindDroboxPath Fd =new FindDroboxPath();
				
				String wordkspace_location=ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
			 	int last=wordkspace_location.lastIndexOf("/");
			 	String wordkspace=wordkspace_location.substring(last+1,wordkspace_location.length() );
			 
			 	String CloudStartPath=wordkspace_location+"/"+projectName+"/";

				path=path+"/";
			
				Fd.writeforProperties(CloudStartPath, path);
				
			}
		
			
		}
		 else {
					ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
					erroScreen.create("Error Conf", "EITHER the conf file does NOT exist OR it cannot be read.");
					erroScreen.open();
		 }

	}

}
