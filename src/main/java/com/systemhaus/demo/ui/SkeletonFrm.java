package com.systemhaus.demo.ui;

public abstract class SkeletonFrm {
	
	protected abstract javax.swing.JInternalFrame createForm(com.systemhaus.demo.Server fakeserver);
	
	protected abstract java.awt.Component createMainPanel();

	protected abstract javax.swing.JPanel createButtonBar();
	
	protected abstract void initComponents();
	protected abstract void initLayout();
	protected abstract void changePanel(javax.swing.JPanel panel, String name);
	protected abstract boolean allFieldsAreFilled();
	protected abstract void clearDataAndSetButtons(boolean clearData, javax.swing.JButton[] btnArray, boolean[] modeList);
}
