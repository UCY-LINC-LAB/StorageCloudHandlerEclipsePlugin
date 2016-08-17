package handlers.file.dropbox.actions;

import java.util.ArrayList;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import cy.ac.ucy.cs.linc.storagecloud.ICloudStorageHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.DropboxHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.exceptions.ExceptionHandler;
import support.actions.plugin.CompleteDialogScreen;
import support.actions.plugin.ErrorDialogScreen;
import support.actions.plugin.FindDroboxPath;
import support.actions.plugin.FindLocalPath;
import support.actions.plugin.readConf;
import handlers.file.dropbox.actions.Delete_RemoveDialogScreen;
import org.eclipse.swt.widgets.Shell;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class Delete_RemoveHandler extends AbstractHandler {
	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage page = win.getActivePage();

	/**
	 * The constructor.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public Delete_RemoveHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		FindLocalPath Fp = new FindLocalPath();
		Fp.getSelection(event);

	
		
		int pozision=Fp.filesPathslocal.get(0).lastIndexOf('\\');
		System.out.println(Fp.filesPathslocal.get(0).substring(0, pozision+1));
		
		Shell xx = win.getShell();
		readConf input = new readConf();

		if (input.readforProperties()) {

			ICloudStorageHandler handler = new DropboxHandler();
			handler.cloudStorageHandlerinit(input.params);
			ArrayList<String> files = new ArrayList<String>();
			
			
			FindDroboxPath Fb = new FindDroboxPath();
			Fb.Droboxpath(Fp.filesPathslocal, Fp.projectName);
			
			String projectpath =  Fb.CloudStartPath+Fp.projectName;
			try {
				handler.ListOfAllFile(files, projectpath);
			} catch (ExceptionHandler e) {
				ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
				erroScreen.create("Error Delete", "EITHER this project does NOT exist in Dropbox OR There is a Connection Error.");
				erroScreen.open();
				return null;
			}
			
			Delete_RemoveDialogScreen Screen =new Delete_RemoveDialogScreen(xx,files);
			Screen.create();
			Screen.open();
			
			if(Screen.status()){
				ArrayList<String> deletefiles = new ArrayList<String>();
				for(int i=0; i<files.size(); i++){
					if(Screen.select[i]){
						deletefiles.add(files.get(i));
					}
				}
				
				for(int i=0; i<deletefiles.size(); i++){
					try {
						handler.deleteFileOrDicertoryFromContainer(deletefiles.get(i));
					} catch (ExceptionHandler e) {
						ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
						erroScreen.create("Error Delete", "Connection Error: Cannot be deleted files from Dropbox.");
						erroScreen.open();
						return null;
					}
				}
				
				CompleteDialogScreen complete = new CompleteDialogScreen(xx);
				complete.create("Successful Deleted", "All files deleted from Dropbox.");
				complete.open();
			}
			
		} else {
			ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
			erroScreen.create("Error Conf", "EITHER the conf file does NOT exist OR it cannot be read.");
			erroScreen.open();
		}
		return null;
	}
}
