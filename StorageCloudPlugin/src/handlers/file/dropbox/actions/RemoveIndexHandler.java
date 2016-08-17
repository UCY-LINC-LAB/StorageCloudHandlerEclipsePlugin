package handlers.file.dropbox.actions;


import java.util.ArrayList;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import support.actions.plugin.CreateIgnore;
import support.actions.plugin.FindIgnore;
import support.actions.plugin.FindLocalPath;
import support.actions.plugin.ReadIgnore;
import org.eclipse.swt.widgets.Shell;



/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RemoveIndexHandler extends AbstractHandler {

	
	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage page = win.getActivePage();

	/**
	 * The constructor.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public RemoveIndexHandler() {
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
		
		ReadIgnore ri=new ReadIgnore();
		if(ri.Read(path)){;
			ri.Ignore(Fp);
		}
		
		Shell xx = win.getShell();
		RemoveIndexDialogScreen newScreen = new RemoveIndexDialogScreen(xx, Fp.filesPathslocal);
		newScreen.create();
		newScreen.open();

		if (newScreen.status()) {

			ArrayList<String> removeFiles = new ArrayList<String>();
			for (int i = 0; i < Fp.filesPathslocal.size(); i++) {
				if (newScreen.select[i]) {
					removeFiles.add(Fp.filesPathslocal.get(i));
				}
			}
			for (int i = 0; i < removeFiles.size(); i++) {
				CreateIgnore ci = new CreateIgnore();
				ci.UpdateIgnore(path, removeFiles);
			}
		}
		return null;
	}
}
