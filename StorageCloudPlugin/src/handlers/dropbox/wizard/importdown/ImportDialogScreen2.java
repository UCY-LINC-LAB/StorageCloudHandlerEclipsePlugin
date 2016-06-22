package handlers.dropbox.wizard.importdown;



import java.util.ArrayList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class ImportDialogScreen2 extends TitleAreaDialog {

	private  ArrayList<String> SelectName;

	Button cb[]; 
	boolean[] select;
	private boolean complete =false;
	
	public ImportDialogScreen2(Shell parentShell, ArrayList<String> temp1) {
		super(parentShell);
		SelectName=temp1;
		cb=new Button[SelectName.size()]; 
		select= new boolean[SelectName.size()]; 
	}

@Override
  public void create() {
    super.create();
    setTitle("Dropbox Import");
    setMessage("Import files from Dropbox", IMessageProvider.INFORMATION);
    
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite area = (Composite) super.createDialogArea(parent);
  
    Label lblNewLabel = new Label(area, SWT.NONE);
    lblNewLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
    lblNewLabel.setText("Select the files that you want to import:");
    new Label(area, SWT.NONE);
    
    for(int i=0; i<SelectName.size(); i++){
 
		int k=(SelectName.get(i).lastIndexOf("\\"));
		String option =  SelectName.get(i).substring(k+1,SelectName.get(i).length());
	    //String temp= option + i +"";
	    cb[i]=new Button(area, SWT.CHECK);
	    //(option);
	    cb[i].setText(option);
	    cb[i].setSelection(true);
	    cb[i].setVisible(true);
	    
	    //panel.add(cb[i]);
	    //boolean flag = x.isEnabled();

    }
      
    return area;
  }
  
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
	    Button btnPull = createButton(parent, IDialogConstants.OK_ID, "Import", true);
	    btnPull.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseDoubleClick(MouseEvent e) {
	    		okPressed();
	    		
	    	}
	    });
	    Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID,
	        IDialogConstants.CANCEL_LABEL, false);
	    button_1.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseDoubleClick(MouseEvent e) {
	    	}
	    });
	    
	  }
 
  @Override
  protected boolean isResizable() {
    return false;
  }

  @Override
  protected void okPressed (){
	  
	  for (int i=0; i<SelectName.size();i++){
		  if(cb[i].getSelection()){
			  select[i]=true;
		  }
		  else{
			  select[i]=false;
		  }
	  }
	  complete =true;
	  super.okPressed();
	 
  }
 
  public boolean status() {
	    return complete;
  }
  
  
  
} 
