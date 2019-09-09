package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.FormLayout;

public class TransacaoLivroFrm extends SkeletonFrm{

	private JInternalFrame ifTranBook;
	
	public JInternalFrame createForm() {
		initComponents();
		initLayout();
		
		return ifTranBook;
	}

	protected void initLayout() {
		ifTranBook.getContentPane().setLayout(new BorderLayout());	
		ifTranBook.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		ifTranBook.getContentPane().add(createButtonBar(), BorderLayout.SOUTH);
	}

	protected JPanel createButtonBar() {
		JPanel panelTran = new JPanel();
		panelTran.setLayout(new FlowLayout());
		
		JButton btnRetirar = new JButton("Retirar");
		panelTran.add(btnRetirar);
		
		JButton btnDevolver = new JButton("Devolver");
		panelTran.add(btnDevolver);
		
		return panelTran;
	}
	
	protected void initComponents() {
		ifTranBook = new JInternalFrame("Retiradas & Devoluções",false, true);
		ifTranBook.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ifTranBook.setBounds(190, 35, 600, 500);
		
	}
	
	protected JPanel createMainPanel(){
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, pref:grow, 3dlu, pref", "18dlu,18dlu,18dlu"), new FormDebugPanel());
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		
		
		
		
		
		
		
		
		
		
		
		return builder.build();
	}
	
	
	
}
