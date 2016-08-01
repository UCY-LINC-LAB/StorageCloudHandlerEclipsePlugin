package handlers.file.dropbox.actions;

import java.util.ArrayList;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.json.JSONObject;
import cy.ac.ucy.cs.linc.storagecloud.ICloudStorageHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.DropboxHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.exceptions.ExceptionHandler;
import support.actions.plugin.CompleteDialogScreen;
import support.actions.plugin.ErrorDialogScreen;
import support.actions.plugin.FindDroboxPath;
import support.actions.plugin.FindLocalParth;
import support.actions.plugin.readConf;
import org.eclipse.swt.widgets.Shell;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class HistoryFileHandler extends AbstractHandler {
	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage page = win.getActivePage();

	/**
	 * The constructor.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public HistoryFileHandler() {
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


		
		int pozision=Fp.filesPathslocal.get(0).lastIndexOf('\\');
		System.out.println(Fp.filesPathslocal.get(0).substring(0, pozision+1));
		
		FindDroboxPath Fb = new FindDroboxPath();
		Fb.Droboxpath(Fp.filesPathslocal, Fp.projectName);
		
		String projectpath =  Fb.CloudStartPath+Fp.projectName;
		
		Shell xx = win.getShell();

		readConf input = new readConf();

		if (input.readforProperties()) {

			ICloudStorageHandler handler = new DropboxHandler();
			handler.cloudStorageHandlerinit(input.params);

			ArrayList<String> Files = new ArrayList<String>();

			try {
				handler.ListOfAllFile(Files,projectpath);
			} catch (ExceptionHandler e) {
				ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
				erroScreen.create("Error History",
						"EITHER this project does NOT exist in Dropbox OR There is a Connection Error.");
				erroScreen.open();
				return null;
			}

			HistoryFileDialogScreen1 Screen1 = new HistoryFileDialogScreen1(xx, Files);
			Screen1.create();
			Screen1.open();

			if (Screen1.status()) {

				String fileDbpath = Screen1.filepath;

				ArrayList<JSONObject> history = null;
				try {
					history = handler.HistoryFile(fileDbpath);
				} catch (ExceptionHandler e) {
					ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
					erroScreen.create("Error History", "Connection Error: Cannot Browse History for this File.");
					erroScreen.open();
					return null;
				}

				ArrayList<String> choises = new ArrayList<String>();
				for (int i = 0; i < history.size(); i++) {

					String choice;
					String temp = history.get(i).toString();

					int k = temp.lastIndexOf('{');
					int z = temp.indexOf('}');
					temp = temp.substring(k, z + 1);
					final JSONObject json1 = new JSONObject(temp);
					String id = "";
					if (json1.has("modified_by")) {
						id = json1.getString("modified_by");
					}

					String name = null;

					try {
						name = handler.StorageUsersName(id);
					} catch (ExceptionHandler e) {
						ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
						erroScreen.create("Error History", "Connection Error:  Cannot retrieve Dropbox Username");
						erroScreen.open();
						return null;
					}

					choice = history.get(i).getString("server_modified") + "     "
							+ history.get(i).get("size").toString() + "     " + name;
					// System.out.println(choice);
					choises.add(choice);
				}

				HistoryFileDialogScreen2 Screen2 = new HistoryFileDialogScreen2(xx, choises);
				Screen2.create();
				Screen2.open();

				if (Screen2.status()) {
					int pos = Screen2.pozision;
					String rev = history.get(pos).getString("rev");

					try {
						handler.RestoreHistoryFile(fileDbpath, rev);
					} catch (ExceptionHandler e) {
						ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
						erroScreen.create("Error History",
								"Connection Error: Cannot restore to this version of the file.");
						erroScreen.open();
						return null;
					}
					CompleteDialogScreen complete = new CompleteDialogScreen(xx);
					complete.create("Successful Restore", "This version of the file restored at Dropbox");
					complete.open();
				}
			}

		} else {
			ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
			erroScreen.create("Error Conf", "EITHER the conf file does NOT exist OR it cannot be read.");
			erroScreen.open();
		}
		return null;
	}
}
