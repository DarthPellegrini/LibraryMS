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
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
		frame = new JFrame("SGBooks - Sistema de Gerenciamento de Biblioteca");
		frame.setBounds(100, 100, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDesktopPane desktopPane = new JDesktopPane();
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 898, 23);
		desktopPane.add(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenu mnOpes = new JMenu("Opções");
		menuBar.add(mnOpes);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		menuBar.add(mntmSobre);
		
		//loading image and scaling it to the button size
		Image imgBook = new ImageIcon(getClass().getResource("/book.png")).getImage(); 
	    ImageIcon imageBook = new ImageIcon(imgBook.getScaledInstance( 35,35,  java.awt.Image.SCALE_SMOOTH ));
		JButton btnNewButton = new JButton("",imageBook);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBounds(56, 35, 45, 45);
		desktopPane.add(btnNewButton);
		
		JLabel lblAdicionarLivro = new JLabel("Cadastro de Livros");
		lblAdicionarLivro.setForeground(Color.WHITE);
		lblAdicionarLivro.setBounds(22, 86, 124, 15);
		desktopPane.add(lblAdicionarLivro);
		
		Image imgUser = new ImageIcon(getClass().getResource("/user.png")).getImage(); 
	    ImageIcon imageUser = new ImageIcon(imgUser.getScaledInstance( 35,35,  java.awt.Image.SCALE_SMOOTH ));
		JButton button = new JButton("", imageUser);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setBounds(56, 113, 45, 45);
		desktopPane.add(button);
		
		JLabel lblCadastroDeUsurios = new JLabel("Cadastro de Usuários");
		lblCadastroDeUsurios.setForeground(Color.WHITE);
		lblCadastroDeUsurios.setBounds(10, 162, 136, 15);
		desktopPane.add(lblCadastroDeUsurios);
		
	}
}
