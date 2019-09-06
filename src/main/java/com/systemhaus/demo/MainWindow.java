package com.systemhaus.demo;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JInternalFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JPanel;

public class MainWindow {

	private JFrame frame;
	private JTextField txtfIsbn;
	private JTextField txtfEdicao;
	private JTextField txtfTitulo;
	private JTextField txtfNPag;
	private JTextField txtfQuant;
	private JTextField txtfAutor;
	private JTextField txtfEditora;

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
		
		JLabel lblCadastroDeUsurios = new JLabel("Cadastro de Usuários");
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
		
		/*
		 * Tela de Cadastro de Livros
		 */
		JInternalFrame ifCadLivro = new JInternalFrame("Cadastro de Livros", false, true);
		ifCadLivro.setBounds(200, 35, 400, 320);
		desktopPane.add(ifCadLivro);
		FormLayout layout = new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10dlu"),
				ColumnSpec.decode("right:pref"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("pref:grow"),
				ColumnSpec.decode("10dlu"),},
			new RowSpec[] {
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}); // rows
		ifCadLivro.getContentPane().setLayout(layout);      
		
		JLabel lblIsbn = new JLabel("ISBN:");
		ifCadLivro.getContentPane().add(lblIsbn, "2, 3");
		
		txtfIsbn = new JTextField();
		ifCadLivro.getContentPane().add(txtfIsbn, "4, 3, fill, default");
		txtfIsbn.setColumns(10);
		
		JLabel lblTtulo = new JLabel("Título:");
		ifCadLivro.getContentPane().add(lblTtulo, "2, 5, right, default");
		
		txtfTitulo = new JTextField();
		ifCadLivro.getContentPane().add(txtfTitulo, "4, 5, fill, default");
		txtfTitulo.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		ifCadLivro.getContentPane().add(lblAutor, "2, 7, right, default");
		
		txtfAutor = new JTextField();
		ifCadLivro.getContentPane().add(txtfAutor, "4, 7, fill, default");
		txtfAutor.setColumns(10);
		
		JLabel lblEditora = new JLabel("Editora:");
		ifCadLivro.getContentPane().add(lblEditora, "2, 9, right, default");
		
		txtfEditora = new JTextField();
		ifCadLivro.getContentPane().add(txtfEditora, "4, 9, fill, default");
		txtfEditora.setColumns(10);
		
		JLabel lblEdio = new JLabel("Edição:");
		ifCadLivro.getContentPane().add(lblEdio, "2, 11, right, default");
		
		txtfEdicao = new JTextField();
		ifCadLivro.getContentPane().add(txtfEdicao, "4, 11, fill, default");
		txtfEdicao.setColumns(10);
		
		JLabel lblNumPginas = new JLabel("Num. Páginas:");
		ifCadLivro.getContentPane().add(lblNumPginas, "2, 13, right, default");
		
		txtfNPag = new JTextField();
		ifCadLivro.getContentPane().add(txtfNPag, "4, 13, fill, default");
		txtfNPag.setColumns(10);
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		ifCadLivro.getContentPane().add(lblQuantidade, "2, 15, right, default");
		
		txtfQuant = new JTextField();
		ifCadLivro.getContentPane().add(txtfQuant, "4, 15, fill, default");
		txtfQuant.setColumns(10);
		
		JPanel panelLivro = new JPanel();
		ifCadLivro.getContentPane().add(panelLivro, "2, 17, 3, 1, fill, fill");
		panelLivro.setLayout(null);
		
		JButton btnAdicionarLivro = new JButton("Adicionar");
		btnAdicionarLivro.setBounds(9, 17, 86, 27);
		panelLivro.add(btnAdicionarLivro);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(102, 17, 90, 27);
		panelLivro.add(btnPesquisar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(199, 17, 66, 27);
		panelLivro.add(btnSalvar);
		
		JButton btnDeletar = new JButton("Deletar");
		btnDeletar.setBounds(272, 17, 74, 27);
		panelLivro.add(btnDeletar);
		
		/*
		 * Tela de Cadastro de Usuários
		 */
		JInternalFrame ifCadUser = new JInternalFrame("New JInternalFrame",false, true);
		ifCadUser.setBounds(200, 35, 535, 387);
		desktopPane.add(ifCadUser);
		
		
		/*
		 * Funcionalidades da tela
		 */
		//remodelar depois em uma classe que cuidará dos Listeners de eventos
		btnLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ifCadLivro.setVisible(true);
			}
		});
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ifCadUser.setVisible(true);
			}
		});
	}
}
