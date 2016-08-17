package handlers.file.dropbox.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.joda.time.DateTime;
import org.json.JSONObject;
import cy.ac.ucy.cs.linc.storagecloud.ICloudStorageHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.DropboxHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.exceptions.ExceptionHandler;
import support.actions.plugin.CompleteDialogScreen;
import support.actions.plugin.CreateLocalPath;
import support.actions.plugin.ErrorDialogScreen;
import support.actions.plugin.FindDroboxPath;
import support.actions.plugin.FindLocalPath;
import support.actions.plugin.readConf;
import org.eclipse.swt.widgets.Shell;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class PullHandler extends AbstractHandler {
	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage page = win.getActivePage();

	/**
	 * The constructor.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public PullHandler() {
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

		FindDroboxPath Fb = new FindDroboxPath();
		Fb.Droboxpath(Fp.filesPathslocal, Fp.projectName);

		HashMap<String, String> localFiles = new HashMap<String, String>();
		for (int i = 0; i < Fb.filesPathsdropbox.size(); i++) {
			//System.out.println(Fb.filesPathsdropbox.get(i) +" <----->"+Fp.filesPathslocal.get(i));
			localFiles.put(Fb.filesPathsdropbox.get(i), Fp.filesPathslocal.get(i));
		}

		readConf input = new readConf();
		Shell xx = win.getShell();
		
		if (input.readforProperties()) {
			ICloudStorageHandler handler = new DropboxHandler();
			handler.cloudStorageHandlerinit(input.params);

			ArrayList<String> listOfFiles = new ArrayList<String>();
			//System.out.print(Fb.CloudStartPath);
			String path = Fb.CloudStartPath+Fp.projectName;
			try {
				handler.ListOfAllFile(listOfFiles, path);
			} catch (ExceptionHandler e) {
				ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
				erroScreen.create("Error Pull", "EITHER this project does NOT exist in Dropbox OR There is a Connection Error.");
				erroScreen.open();
				return null;
			}

			ArrayList<String> listOfFilesDown = new ArrayList<String>();
			for (int i = 0; i < listOfFiles.size(); i++) {
				if (localFiles.containsKey(listOfFiles.get(i))) {

					JSONObject tpt = null;
					try {
						tpt = handler.FileMetadata(listOfFiles.get(i));
					} catch (ExceptionHandler e) {
						ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
						erroScreen.create("Error Pull", "Connection Error: Cannot retrieve files metadata.");
						erroScreen.open();
						return null;
					}
					//System.out.println(tpt.toString()+"----->" );
					//long TimestampClient = new DateTime(tpt.get("client_modified")).getMillis();
					long TimestampServer = new DateTime(tpt.get("server_modified")).getMillis();

					File f = new File(localFiles.get(listOfFiles.get(i)));
					long localTimestamp = f.lastModified();

					//System.out.println("Time stamp local " + f.lastModified());
					//System.out.println("Time stamp for client_modified " + TimestampServer);
					//System.out.println("Time stamp for server_modified " + TimestampServer);

					if (TimestampServer > localTimestamp) {
						listOfFilesDown.add(listOfFiles.get(i));

					}
				} else {
					//System.out.println("+++++");
					listOfFilesDown.add(listOfFiles.get(i));
				}
			}
			
			PullDialogScreen newScreen = new PullDialogScreen(xx, listOfFilesDown);
			newScreen.create();
			newScreen.open();

			CreateLocalPath clp = new CreateLocalPath();
			if (newScreen.status()) {

				ArrayList<String> pullFiles = new ArrayList<String>();

				for (int i = 0; i < listOfFilesDown.size(); i++) {
					if (newScreen.select[i]) {
						pullFiles.add(listOfFilesDown.get(i));
					}
				}
				boolean flag=true;
				if (clp.dbPathToLocalPath(pullFiles, Fp.filesPathslocal.get(0), Fp.projectName)) {
					for (int i = 0; i < clp.LocalPath.size(); i++) {
						//System.out.println(clp.LocalPath.get(i));
						try {
							handler.CloneFileOrContainer(pullFiles.get(i), clp.LocalPath.get(i));
						} catch (ExceptionHandler e) {
							flag = false;
							break;
						}
					}
					if (flag) {
						CompleteDialogScreen complete = new CompleteDialogScreen(xx);
						complete.create("Successful Pull", "All files pulled from Dropbox.");
						complete.open();
					} else {
						ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
						erroScreen.create("Error Pull","Connection Error: Cannot pull files.");
						erroScreen.open();
					}	
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
