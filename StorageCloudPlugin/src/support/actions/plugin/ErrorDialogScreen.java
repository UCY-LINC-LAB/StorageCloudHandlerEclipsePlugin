package support.actions.plugin;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class ErrorDialogScreen extends TitleAreaDialog {

	public ErrorDialogScreen(Shell parentShell) {
		super(parentShell);
	}

	public void create(String title, String Body) {
		super.create();
		setTitle(title);
		setMessage(Body, IMessageProvider.ERROR);

	}

	protected void createButtonsForButtonBar(Composite parent) {
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, "OK", false);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});

	}

}
