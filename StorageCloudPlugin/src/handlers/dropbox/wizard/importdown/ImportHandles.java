package handlers.dropbox.wizard.importdown;

import java.util.ArrayList;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import cy.ac.ucy.cs.linc.storagecloud.ICloudStorageHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.DropboxHandler;
import cy.ac.ucy.cs.linc.storagecloud.dropbox.exceptions.ExceptionHandler;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import support.actions.plugin.CompleteDialogScreen;
import support.actions.plugin.CreateLocalPath;
import support.actions.plugin.ErrorDialogScreen;
import support.actions.plugin.FindDroboxPath;
import support.actions.plugin.readConf;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */

public class ImportHandles implements IWorkbenchWizard {
	public static String DEFAULT_CONFIG_PATH = "conf.properties";
	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage page = win.getActivePage();
	String path = "";
	String Project;
	@Override
	public void addPages() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPageControls(Composite arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public IWizardContainer getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getDefaultPageImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDialogSettings getDialogSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizardPage getNextPage(IWizardPage arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizardPage getPage(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPageCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IWizardPage[] getPages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizardPage getStartingPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RGB getTitleBarColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWindowTitle() {
		// TODO Auto-generated method stub
		return "Import Dropbox Project";
	}

	@Override
	public boolean isHelpAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean needsPreviousAndNextButtons() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean needsProgressMonitor() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean performCancel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setContainer(IWizardContainer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
	
		Shell xx = win.getShell();

		readConf input = new readConf();

		if (input.readforProperties()) {

			ICloudStorageHandler handler = new DropboxHandler();
			handler.cloudStorageHandlerinit(input.params);

			ArrayList<String> projects = new ArrayList<String>();
		
			try {
				handler.ListOfFolder(projects, path);
			} catch (ExceptionHandler e) {
				ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
				erroScreen.create("Error Import", "Connection Error: Cannot Browse Project from Dropbox.");
				erroScreen.open();
				return;
			}

			ImportDialogScreen1 Screen1 = new ImportDialogScreen1(xx, projects);
			Screen1.create();
			Screen1.open();
			
			
			int choose=Screen1.status();
			
			if(choose==2){
				path=path+"/"+Screen1.ProjectName;
				Project=Screen1.ProjectName;
				init( arg0,  arg1);
			}
			
			if(choose==1){
				if(!path.isEmpty()){
					int thesh=path.lastIndexOf("/");
					path=path.substring(0,thesh);
				}
				init( arg0,  arg1);
			}
			
			if (choose==3) {
				
				if(!Screen1.ProjectName.isEmpty()){
					path=path+"/"+Screen1.ProjectName;
					Project=Screen1.ProjectName;
				}
				else{
					ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
					erroScreen.create("Error Import", "Selection Error: Cannot define Selected Project");
					erroScreen.open();
					return;
				}
				
		

				ArrayList<String> files = new ArrayList<String>();
				
				try {
					handler.ListOfAllFile(files, path);
				} catch (ExceptionHandler e) {
					ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
					erroScreen.create("Error Import", "Connection Error: Cannot Browse Files from Dropbox Project.");
					erroScreen.open();
					return;
				}

				ImportDialogScreen2 Screen2 = new ImportDialogScreen2(xx, files);
				Screen2.create();
				Screen2.open();

				if (Screen2.status()) {

					ArrayList<String> importFiles = new ArrayList<String>();

					for (int i = 0; i < files.size(); i++) {
						if (Screen2.select[i]) {
							importFiles.add(files.get(i));
						}
					}

					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(Project);

					if (!project.exists()) {
						IProgressMonitor monitor = null;
						try {
							project.create(monitor);
							project.open(monitor);
						} catch (CoreException e) {
							ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
							erroScreen.create("Error Create Project", "Error: Cannot Create Project at your WorkSpace");
							erroScreen.open();
							return;
						}

					}

					IPath mypath = project.getLocation();
					String pathproject = mypath.toString();
					

					CreateLocalPath clp = new CreateLocalPath();

					
					clp.dbPathToLocalPath(importFiles, pathproject,project.getName());

					for (int i = 0; i < importFiles.size(); i++) {

						try {
							handler.CloneFileOrContainer(importFiles.get(i), clp.LocalPath.get(i));
							
						} catch (ExceptionHandler e) {
							ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
							erroScreen.create("Error Import", "Connection Error: Cannot import files from Dropbox.");
							erroScreen.open();
							return;
						}
					}
					
					FindDroboxPath Fd =new FindDroboxPath();
					
					String wordkspace_location=ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
				 	int last=wordkspace_location.lastIndexOf("/");
				 	String wordkspace=wordkspace_location.substring(last+1,wordkspace_location.length() );
				 
				 	String CloudStartPath=wordkspace_location+"/"+project.getName()+"/";
				 	
				 	int thesh=path.lastIndexOf("/");
					path=path.substring(0,thesh);
					path=path+"/";
				
					Fd.writeforProperties(CloudStartPath, path);
					
					CompleteDialogScreen complete = new CompleteDialogScreen(xx);
					complete.create("Successful Import", "All files have been import from Dropbox.");
					complete.open();

				}
			}

		} else {
			ErrorDialogScreen erroScreen = new ErrorDialogScreen(xx);
			erroScreen.create("Error Conf", "EITHER the conf file does NOT exist OR it cannot be read.");
			erroScreen.open();
		}

	}

}