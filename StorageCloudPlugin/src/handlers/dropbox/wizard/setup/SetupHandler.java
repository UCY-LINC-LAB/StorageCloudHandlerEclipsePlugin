package handlers.dropbox.wizard.setup;

import java.util.HashMap;

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

import org.eclipse.swt.widgets.Shell;

import support.actions.plugin.ErrorDialogScreen;
import support.actions.plugin.FindLocalPath;
import support.actions.plugin.createComf;


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SetupHandler extends AbstractHandler {
	public static String DEFAULT_CONFIG_PATH = "conf.properties";
	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage page = win.getActivePage();

	/**
	 * The constructor.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public SetupHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Shell shell = win.getShell();

		FirstScreenAuthInsert firstScreen = new FirstScreenAuthInsert(shell);
		firstScreen.create();
		firstScreen.open();

		if (firstScreen.complete) {

			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("ACCESS_TOKEN", firstScreen.getPassword());
			temp.put("opersyst", "temp");

			ICloudStorageHandler handler = new DropboxHandler();
			handler.cloudStorageHandlerinit(temp);

			temp = null;
			try {
				temp = handler.AccountInfo();
			} catch (ExceptionHandler e) {
				ErrorDialogScreen erroScreen = new ErrorDialogScreen(shell);
				erroScreen.create("Error Account", "EITHER the Access Token Key is NOT valid OR There is a Connection Error");
				erroScreen.open();
				return null;
			}

			SecondScreenAuthInfo secondSreen = new SecondScreenAuthInfo(shell, temp);
			secondSreen.create();
			secondSreen.open();

			if (secondSreen.complete) {
				createComf.writeComfFile(firstScreen.getPassword());
			}

		}

		return null;
	}
}