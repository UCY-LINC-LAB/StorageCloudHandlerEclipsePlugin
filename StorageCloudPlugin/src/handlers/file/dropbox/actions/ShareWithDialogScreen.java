package handlers.file.dropbox.actions;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;

public class ShareWithDialogScreen extends TitleAreaDialog {

	boolean complete = false;
	String project;
	private Text text;
	public String email = "";

	public ShareWithDialogScreen(Shell parentShell, String name) {
		super(parentShell);
		project = name;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Share Information");
		setMessage("Share Project with collaborator", IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		Label lblNewLabel = new Label(area, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		lblNewLabel.setText("Type the e-mail that you want to Share \"" + project +"\" with : " );
		new Label(area, SWT.NONE);
		
		text = new Text(area, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setText(email);

		return area;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, "Ok", true);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				okPressed();
			}
		});
		
		Button button2 = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
		button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				okPressed();
			}
		});

	}

	@Override
	protected boolean isResizable() {
		return false;
	}

	@Override
	protected void okPressed() {
		email = text.getText();
		complete = true;
		super.okPressed();
	}

}
