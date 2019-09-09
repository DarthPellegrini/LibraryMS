package com.systemhaus.demo.ui;

public abstract class SkeletonFrm {
	
	protected abstract javax.swing.JInternalFrame createForm();
	
	protected abstract java.awt.Component createMainPanel();

	protected abstract javax.swing.JPanel createButtonBar();
	
	protected abstract void initComponents();
	protected abstract void initLayout();
}
