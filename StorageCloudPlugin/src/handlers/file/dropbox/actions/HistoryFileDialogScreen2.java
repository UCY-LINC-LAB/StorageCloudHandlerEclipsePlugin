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

public class HistoryFileDialogScreen2 extends TitleAreaDialog {

	private ArrayList<String> SelectName;
	Button cb[];
	private boolean complete = false;
	int pozision;

	public HistoryFileDialogScreen2(Shell parentShell, ArrayList<String> temp1) {

		super(parentShell);
		SelectName = temp1;
		cb = new Button[SelectName.size()];

	}

	@Override
	public void create() {
		super.create();
		setTitle("History Information");
		setMessage("These are your Dropbox files versions", IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		Label lblNewLabel = new Label(area, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		lblNewLabel.setText("Select the version that you want to restore (Current is Selected):");
		new Label(area, SWT.NONE);

		Label lblNewLabel1 = new Label(area, SWT.NONE);
		lblNewLabel1.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		lblNewLabel1.setText("Modified(UTC time)             Size    User");
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
		Button btnPull = createButton(parent, IDialogConstants.OK_ID, "Restore", true);
		btnPull.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				okPressed();

			}
		});
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, "OK", false);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		Button button_2 = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		button_2.addMouseListener(new MouseAdapter() {
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
				pozision = i;
				break;
			}
		}
		complete = true;
		super.okPressed();

	}

	public boolean status() {
		return complete;
	}

}
