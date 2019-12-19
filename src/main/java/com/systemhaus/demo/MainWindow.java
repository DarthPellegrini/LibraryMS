package com.systemhaus.demo;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JInternalFrame;

import com.systemhaus.demo.ui.CadastroLivroFrm;
import com.systemhaus.demo.ui.CadastroClienteFrm;
import com.systemhaus.demo.ui.TransacaoLivroFrm;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class MainWindow {

	private JFrame frame;
	private Server fakeServer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Look and Feel
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		// Starts the application
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fakeServer = ServerFactory.createServer();
		frame = new JFrame("SGBooks - Sistema de Gerenciamento de Biblioteca");
		frame.setBounds(0, 0, 1280, 720);
		frame.setMaximumSize(new java.awt.Dimension(1280,720));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDesktopPane desktopPane = new JDesktopPane();
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, frame.getWidth(), 23);
		desktopPane.add(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenu mnOpes = new JMenu("Opções");
		menuBar.add(mnOpes);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		menuBar.add(mntmSobre);
		
		/*
		 * Criação e personalização de Ícones e Labels 
		 */
		
		//loading image and scaling it to the button size
		Image imgBook = new ImageIcon(getClass().getResource("/book.png")).getImage(); 
	    ImageIcon imageBook = new ImageIcon(imgBook.getScaledInstance( 35,35,  java.awt.Image.SCALE_SMOOTH ));
		JButton btnLivro = new JButton("",imageBook);
		btnLivro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLivro.setBounds(60, 35, 45, 45);
		desktopPane.add(btnLivro);
		
		JLabel lblAdicionarLivro = new JLabel("Cadastro de Livros");
		lblAdicionarLivro.setForeground(Color.WHITE);
		lblAdicionarLivro.setBounds(26, 88, 124, 15);
		desktopPane.add(lblAdicionarLivro);
		
		//loading image and scaling it to the button size
		Image imgUser = new ImageIcon(getClass().getResource("/user.png")).getImage(); 
	    ImageIcon imageUser = new ImageIcon(imgUser.getScaledInstance( 35,35,  java.awt.Image.SCALE_SMOOTH ));
		JButton btnUser = new JButton("", imageUser);
		btnUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUser.setBounds(60, 115, 45, 45);
		desktopPane.add(btnUser);
		
		JLabel lblCadastroDeUsurios = new JLabel("Cadastro de Clientes");
		lblCadastroDeUsurios.setForeground(Color.WHITE);
		lblCadastroDeUsurios.setBounds(14, 168, 136, 15);
		desktopPane.add(lblCadastroDeUsurios);

		//loading image and scaling it to the button size
		Image imgTran = new ImageIcon(getClass().getResource("/transaction.png")).getImage(); 
	    ImageIcon imageTran = new ImageIcon(imgTran.getScaledInstance( 35,35,  java.awt.Image.SCALE_SMOOTH ));
		JButton btnTran = new JButton("",imageTran);
		btnTran.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTran.setBounds(60, 195, 45, 45);
		desktopPane.add(btnTran);
		
		JLabel lblRetiradasdevolues = new JLabel("Retiradas & Devoluções");
		lblRetiradasdevolues.setForeground(Color.WHITE);
		lblRetiradasdevolues.setBounds(10, 246, 150, 15);
		desktopPane.add(lblRetiradasdevolues);
		
		//TODO: remodelar depois em uma classe que cuidará dos Listeners de eventos
		/*
		 * Funcionalidades da tela
		 */
		btnLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame cadastroLivro = new CadastroLivroFrm().createForm(fakeServer);
				desktopPane.add(cadastroLivro);
				cadastroLivro.setVisible(true);
			}
		});
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame cadastroUser = new CadastroClienteFrm().createForm(fakeServer);
				desktopPane.add(cadastroUser);
				cadastroUser.setVisible(true);
			}
		});
		btnTran.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame transacaoLivro = new TransacaoLivroFrm().createForm(fakeServer);
				desktopPane.add(transacaoLivro);
				transacaoLivro.setVisible(true);
			}
		});
		
	}
}
