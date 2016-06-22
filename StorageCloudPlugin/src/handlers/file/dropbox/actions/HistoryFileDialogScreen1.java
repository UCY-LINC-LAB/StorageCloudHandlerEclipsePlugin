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

public class HistoryFileDialogScreen1 extends TitleAreaDialog {

	private ArrayList<String> SelectName;

	Button cb[];

	private boolean complete = false;
	String filepath;

	public HistoryFileDialogScreen1(Shell parentShell, ArrayList<String> temp1) {

		super(parentShell);
		SelectName = temp1;
		cb = new Button[SelectName.size()];

	}

	@Override
	public void create() {
		super.create();
		setTitle("History Information");
		setMessage("These are your Dropbox files", IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		Label lblNewLabel = new Label(area, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		lblNewLabel.setText("Select the file that you want to see its history:");
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
		Button btnPull = createButton(parent, IDialogConstants.OK_ID, "Show History", true);
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
				filepath = SelectName.get(i);
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
