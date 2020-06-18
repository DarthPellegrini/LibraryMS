package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.systemhaus.demo.Server;

public class LoginFrm extends SkeletonFrm{
	
	private JInternalFrame ifLogin;
	private Server server;
	private JTextField txtfUser;
	private JPasswordField passfSenha;
	private JButton btnLogin;
	
	/*
	 * Sim, essa tela poderia ter uma puta l√≥gica boa e seguran√ßa de login
	 * mas s√£o 01:06 da madrugrada, e eu n√£o t√¥ com paci√™ncia e nem tempo pra isso
	 * portanto, vai ficar essa bosta
	 */
	
	@Override
	public JInternalFrame createForm(Server server) {
		this.server = server;
		initComponents();
		initLayout();
		return ifLogin;
	}

	@Override
	protected Component createMainPanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, 25dlu, 70dlu", "36dlu,18dlu,18dlu,6dlu,18dlu,12dlu,18dlu"));
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.nextColumn(3);
		builder.appendTitle("LOGIN");
		builder.nextLine(2);
		
		builder.append("Usu·rio:",txtfUser,2);
		builder.nextLine(2);
		
		builder.append("Senha:",passfSenha,2);
		builder.nextLine(2);
		
		builder.nextColumn(2);
		builder.append(btnLogin,2);
		
		return builder.build();
	}

	@Override
	protected JPanel createButtonBar() {
		//unused
		return null;
	}

	@Override
	protected void initComponents() {
		ifLogin = new JInternalFrame("Login",true, false);
		ifLogin.setBounds(190, 35, 275, 275);
		
		txtfUser = new JTextField();
		passfSenha = new JPasswordField();
		btnLogin = new JButton("Entrar no Sistema");
		
		btnLogin.addActionListener( l->{
			if (allFieldsAreFilled()) {
				if (server.logIn(txtfUser.getText(),new String(passfSenha.getPassword()))) {
					JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
					ifLogin.setClosable(true);
					ifLogin.doDefaultCloseAction();
				} else {
					JOptionPane.showMessageDialog(null, "Campo de Usu·rio ou Senha inv·lidos!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Campo de Usu·rio ou Senha n√£o foi preenchido!");
			}
		});
	}

	@Override
	protected void initLayout() {
		ifLogin.getContentPane().setLayout(new BorderLayout());
		ifLogin.getContentPane().add(createMainPanel(),BorderLayout.CENTER);
	}

	@Override
	protected void changePanel(JPanel panel, String name) {
		//unused
		
	}

	@Override
	protected boolean allFieldsAreFilled() {
		return (!txtfUser.getText().isEmpty() || !new String(passfSenha.getPassword()).isEmpty());
	}

	@Override
	protected void clearDataAndSetButtons(boolean clearData, JButton[] btnArray, boolean[] modeList) {
		//unused
		
	}
}
