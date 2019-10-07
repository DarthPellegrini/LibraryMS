package com.systemhaus.demo.ui;
import com.systemhaus.demo.Server;

public abstract class SkeletonFrm {
	
	protected abstract javax.swing.JInternalFrame createForm(Server fakeserver);
	
	protected abstract java.awt.Component createMainPanel();

	protected abstract javax.swing.JPanel createButtonBar();
	
	protected abstract void initComponents();
	protected abstract void initLayout();
	protected abstract void clearFields();
	protected abstract boolean allFieldsAreFilled();
}
