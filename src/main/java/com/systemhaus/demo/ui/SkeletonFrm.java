package com.systemhaus.demo.ui;
import com.systemhaus.demo.FakeServer;

public abstract class SkeletonFrm {
	
	protected abstract javax.swing.JInternalFrame createForm(FakeServer fakeserver);
	
	protected abstract java.awt.Component createMainPanel();

	protected abstract javax.swing.JPanel createButtonBar();
	
	protected abstract void initComponents();
	protected abstract void initLayout();
}
