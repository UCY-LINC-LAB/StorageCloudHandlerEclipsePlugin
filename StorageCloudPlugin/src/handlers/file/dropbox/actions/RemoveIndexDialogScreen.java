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

public class RemoveIndexDialogScreen extends TitleAreaDialog {

	private boolean complete = false;
	private ArrayList<String> SelectPath;
	Button cb[];
	boolean[] select;

	public RemoveIndexDialogScreen(Shell parentShell, ArrayList<String> temp2) {
		super(parentShell);
		SelectPath = temp2;
		cb = new Button[SelectPath.size()];
		select = new boolean[SelectPath.size()];
	}

	@Override
	public void create() {
		super.create();
		setTitle("Remove from Index");
		setMessage("These files will be removed from Index", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		Label lblNewLabel = new Label(area, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Calibri", 10, SWT.NORMAL));
		lblNewLabel.setText("These files will NOT be included at the next push action:");
		new Label(area, SWT.NONE);

		for (int i = 0; i < SelectPath.size(); i++) {
			int k = (SelectPath.get(i).lastIndexOf("\\"));
			String option = SelectPath.get(i).substring(k + 1, SelectPath.get(i).length());

			cb[i] = new Button(area, SWT.CHECK);
			cb[i].setText(option);
			cb[i].setSelection(true);
			cb[i].setVisible(true);
		}

		return area;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, "Remove Index", true);
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

	@Override
	protected boolean isResizable() {
		return false;
	}

	@Override
	protected void okPressed() {
		for (int i = 0; i < SelectPath.size(); i++) {
			if (cb[i].getSelection()) {
				select[i] = true;
			} else {
				select[i] = false;
			}
		}
		complete = true;
		super.okPressed();
	}

	public boolean status() {
		return complete;
	}

}
