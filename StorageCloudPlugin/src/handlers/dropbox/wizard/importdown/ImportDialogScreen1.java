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

public class ImportDialogScreen1 extends TitleAreaDialog {

	private ArrayList<String> SelectName;

	Button cb[];
	boolean[] select;
	private boolean complete = false;
	String ProjectName="";
	
	private boolean next=false;
	private boolean previous=false;
	
	public ImportDialogScreen1(Shell parentShell, ArrayList<String> temp1) {

		super(parentShell);
		SelectName = temp1;
		cb = new Button[SelectName.size()];
		select = new boolean[SelectName.size()];
	}

	@Override
	public void create() {
		super.create();
		setTitle("Dropbox Import");
		setMessage("Import project from Dropbox", IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		Label lblNewLabel = new Label(area, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		lblNewLabel.setText("Select the project that you want to import:");
		new Label(area, SWT.NONE);

		for (int i = 0; i < SelectName.size(); i++) {

			cb[i] = new Button(area, SWT.RADIO);
			cb[i].setText(SelectName.get(i));
			cb[i].setVisible(true);

		}
		return area;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {

		Button btnPr = createButton(parent, IDialogConstants.NO_ID, "< Previous", true);
		btnPr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Prev();

			}
		});
		
		Button btnnext = createButton(parent, IDialogConstants.NEXT_ID, "Next >", true);
		btnnext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Next();

			}
		});
		
		Button btnPull = createButton(parent, IDialogConstants.OK_ID, "Import", true);
		btnPull.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				okPressed();

			}
		});
	
	
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
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
	protected void okPressed() {
		for (int i = 0; i < SelectName.size(); i++) {
			if (cb[i].getSelection()) {
				ProjectName = SelectName.get(i);
				break;
			}
		}
		complete = true;
		super.okPressed();

	}
	protected void Next() {
		for (int i = 0; i < SelectName.size(); i++) {
			if (cb[i].getSelection()) {
				ProjectName = SelectName.get(i);
				break;
			}
		}
		next = true;
		 super .getButton(IDialogConstants.NEXT_ID);
		 super.okPressed();

	}
	protected void Prev() {
		previous = true;
		 super .getButton(IDialogConstants.NO_ID);
		 super.okPressed();

	}
	public int status() {
		if(previous){
			return 1;
		}
		if(next){
			return 2;
		}
		if(complete){
			return 3;
		}
		
		return 0;
	}

}
