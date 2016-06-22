package handlers.file.dropbox.actions;

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

public class AddIndexDialogScreen extends TitleAreaDialog {

	private ArrayList<String> SelectName;

	private boolean complete = false;

	public AddIndexDialogScreen(Shell parentShell, ArrayList<String> temp1) {
		super(parentShell);
		SelectName = temp1;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Add to Index");
		setMessage("These files will be added to Index", IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		Label lblNewLabel = new Label(area, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		lblNewLabel.setText("These files will be included at the next push action:");
		new Label(area, SWT.NONE);

		for (int i = 0; i < SelectName.size(); i++) {
			int k = (SelectName.get(i).lastIndexOf("\\"));
			String option = SelectName.get(i).substring(k + 1, SelectName.get(i).length());
			Label label = new Label(area, SWT.NONE);
			label.setText(option);
			label.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		}

		return area;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button btnPull = createButton(parent, IDialogConstants.OK_ID, "Add to Index", true);
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
		complete = true;
		super.okPressed();
	}

	public boolean status() {
		return complete;
	}

}
