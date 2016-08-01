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
import support.actions.plugin.CreateIgnore;
import support.actions.plugin.ErrorDialogScreen;
import support.actions.plugin.FindDroboxPath;
import support.actions.plugin.FindIgnore;
import support.actions.plugin.FindLocalParth;
import support.actions.plugin.ReadIgnore;
import support.actions.plugin.readConf;
import org.eclipse.swt.widgets.Shell;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class PushHandler extends AbstractHandler {

	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage page = win.getActivePage();

	/**
	 * The constructor.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public PushHandler() {
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

		String oper_syste = System.getProperty("os.name");

		if (oper_syste.contains("Windows")) {
			for (int i = 0; i < Fp.SelectionPath.size(); i++) {
				String temp = Fp.SelectionPath.get(i);
				Fp.SelectionPath.remove(i);
				temp = temp.replace('/', '\\');
				Fp.SelectionPath.add(temp);

			}
		}

		String path = FindIgnore.IgnorePath(Fp.SelectionPath.get(0), Fp.projectName);

		ReadIgnore ri = new ReadIgnore();
		if (ri.Read(path)) {
			
			ri.Ignore(Fp);
		}

		Shell xx = win.getShell();
		PushDialogScreen newScreen = new PushDialogScreen(xx, Fp.filesPathslocal,Fp.projectName);
		newScreen.create();
		newScreen.open();

		if (newScreen.status()) {

			ArrayList<String> pushFiles = new ArrayList<String>();
			ArrayList<String> ignoreFiles = new ArrayList<String>();

			for (int i = 0; i < Fp.filesPathslocal.size(); i++) {
				if (newScreen.select[i]) {
					pushFiles.add(Fp.filesPathslocal.get(i));
				} else {
					ignoreFiles.add(Fp.filesPathslocal.get(i));
				}
			}

			CreateIgnore ci = new CreateIgnore();
			ci.UpdateIgnore(path, ignoreFiles);

			readConf input = new readConf();

			if (input.readforProperties()) {
				FindDroboxPath Fb = new FindDroboxPath();
				Fb.Droboxpath(pushFiles, Fp.projectName);

				ICloudStorageHandler handler = new DropboxHandler();
				handler.cloudStorageHandlerinit(input.params);
				boolean flag = true;
				for (int i = 0; i < pushFiles.size(); i++) {
					// System.out.println(pushFiles.get(i)+"---->"+Fb.filesPathsdropbox.get(i));
					try {
						handler.addFileToContainer(pushFiles.get(i), Fb.filesPathsdropbox.get(i));
					} catch (ExceptionHandler e) {
						flag = false;
						break;
					}
				}
				if (flag) {
					CompleteDialogScreen complete = new CompleteDialogScreen(xx);
					complete.create("Successful Push", "All files pushed at Dropbox.");
					complete.open();
				} else {
					ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
					erroScreen.create("Error Push", "Connection Error: Cannot push files.");
					erroScreen.open();
				}

			} else {
				ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
				erroScreen.create("Error Conf", "EITHER the conf file does NOT exist OR it cannot be read.");
				erroScreen.open();
			}

		}

		return null;
	}
}
