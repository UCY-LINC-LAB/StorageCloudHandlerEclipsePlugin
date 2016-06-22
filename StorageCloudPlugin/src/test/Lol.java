package test;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import support.actions.plugin.ErrorDialogScreen;

public class Lol {
	 
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Shell xx = null;
		
		ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
		erroScreen.create("Error Import", "Connection Error: Cannot import files from Dropbox.");
		erroScreen.open();
	}

}
