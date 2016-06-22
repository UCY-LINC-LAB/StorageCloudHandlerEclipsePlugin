package handlers.dropbox.wizard.setup;


import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class FirstScreenAuthInsert extends TitleAreaDialog {

	private Text txtPassword;
	public String password = "";
	boolean complete = false;

	public FirstScreenAuthInsert(Shell parentShell) {
		super(parentShell);

	}

	@Override
	public void create() {
		super.create();
		setTitle("Authorization");
		setMessage("Insert your Assecc Token Key from your Dropbox Account", IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout(1, false);

		layout.marginRight = 5;
		layout.marginLeft = 10;
		container.setLayout(layout);
		new Label(container, SWT.NONE);

		Label lblPassword = new Label(container, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.horizontalIndent = 1;

		lblPassword.setLayoutData(gd_lblNewLabel);
		lblPassword.setText("Access Token Key:");

		txtPassword = new Text(container, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtPassword.setText(password);
		return container;
	}

	// override method to use "Login" as label for the OK button
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, "Next >>", true);
		button.addMouseListener(new MouseAdapter() {
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

	protected boolean isResizable() {
		return false;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	protected void okPressed() {
		password = txtPassword.getText();
		complete = true;
		super.okPressed();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}