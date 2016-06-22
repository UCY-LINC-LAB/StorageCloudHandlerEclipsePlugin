package handlers.file.dropbox.actions;


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
import support.actions.plugin.ErrorDialogScreen;
import support.actions.plugin.FindLocalParth;
import support.actions.plugin.readConf;
import org.eclipse.swt.widgets.Shell;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ShareWithHandler extends AbstractHandler {
	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage page = win.getActivePage();

	/**
	 * The constructor.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public ShareWithHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		FindLocalParth Fp = new FindLocalParth();
		Fp.getSelection(event);
		
		String project=Fp.projectName;
		Shell xx = win.getShell();
		
		ShareWithDialogScreen newScreen = new ShareWithDialogScreen(xx,project);
		newScreen.create();
		newScreen.open();
		
		if(newScreen.complete){
			project="/"+project;
			String email =newScreen.email;
			readConf input = new readConf();

			if (input.readforProperties()) {
				ICloudStorageHandler handler = new DropboxHandler();
				handler.cloudStorageHandlerinit(input.params);
				
				try {
					handler.ShareProjectWithEmail(project, email);
				} catch (ExceptionHandler e) {
					ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
					erroScreen.create("Error Share", "EITHER the given e-mail is NOT valid OR There is a Connection Error");
					erroScreen.open();
				}

			}
			else {
				ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
				erroScreen.create("Error Conf", "EITHER the conf file does NOT exist OR it cannot be read.");
				erroScreen.open();
			}
		}
		return null;
	}
}
