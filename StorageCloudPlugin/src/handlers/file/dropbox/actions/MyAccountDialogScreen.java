package handlers.file.dropbox.actions;

import java.util.HashMap;

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

public class MyAccountDialogScreen extends TitleAreaDialog {

	boolean complete = false;
	HashMap<String, String> info;

	public MyAccountDialogScreen(Shell parentShell, HashMap<String, String> temp) {
		super(parentShell);
		info = temp;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Dropbox Account");
		setMessage("Information for My Dropbox Account", IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		Label lblNewLabel = new Label(area, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		lblNewLabel.setText("You are connented as (Name Surname):");

		Label label = new Label(area, SWT.NONE);
		label.setText(info.get("name"));
		label.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		Label lblToThisDropbox = new Label(area, SWT.NONE);
		lblToThisDropbox.setText("");
		lblToThisDropbox.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		Label lblToThisDropbox1 = new Label(area, SWT.NONE);
		lblToThisDropbox1.setText("Your using e-mail is:");
		lblToThisDropbox1.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		Label label_3 = new Label(area, SWT.NONE);
		label_3.setText(info.get("e-mail"));
		label_3.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		lblToThisDropbox = new Label(area, SWT.NONE);
		lblToThisDropbox.setText("");
		lblToThisDropbox.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		Label lblToThisDropbox2 = new Label(area, SWT.NONE);
		lblToThisDropbox2.setText("Account Type:");
		lblToThisDropbox2.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		Label label_2 = new Label(area, SWT.NONE);
		label_2.setText(info.get("Dropbox Type"));
		label_2.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		lblToThisDropbox = new Label(area, SWT.NONE);
		lblToThisDropbox.setText("");
		lblToThisDropbox.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		double used = Double.parseDouble(info.get("used_GB"));
		double allocated = Double.parseDouble(info.get("Allocated_GB"));
		double free = allocated - used;
		double precentace = 100 * free / allocated;

		Label lblToThisDropbox4 = new Label(area, SWT.NONE);
		lblToThisDropbox4.setText("Used Space :" + String.format("%.3f", used) + " GB");
		lblToThisDropbox4.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		Label label_4 = new Label(area, SWT.NONE);
		label_4.setText("Total Space :" + String.format("%.3f", allocated) + " GB ( "
				+ String.format("%.2f", precentace) + "% free Space )");
		label_4.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));

		return area;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, "OK", false);
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

}
