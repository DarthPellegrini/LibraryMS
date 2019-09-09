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
	private JTextField txtfNome;
	private JTextField txtfCpf;
	private JTextField txtfTelefone;
	private JTextField txtfCidade;
	private JTextField txtfBairro;
	private JTextField txtfRua;
	private JTextField txtfCodCartao;

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
		
		//TODO: create individual classes for each JInternalFrame
		
		/*
		 * Tela de Cadastro de Livros
		 */
		JInternalFrame ifCadLivro = new JInternalFrame("Cadastro de Livros", false, true);
		ifCadLivro.setBounds(198, 35, 400, 320);
		desktopPane.add(ifCadLivro);
		ifCadLivro.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("90px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("257px"),},
			new RowSpec[] {
				RowSpec.decode("24px"),
				RowSpec.decode("27px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("39px"),}));
		
		JLabel lblIsbn = new JLabel("ISBN:");
		ifCadLivro.getContentPane().add(lblIsbn, "2, 2, right, center");
		
		txtfIsbn = new JTextField();
		ifCadLivro.getContentPane().add(txtfIsbn, "4, 2, fill, top");
		txtfIsbn.setColumns(10);
		
		JLabel lblTtulo = new JLabel("Título:");
		ifCadLivro.getContentPane().add(lblTtulo, "2, 4, right, center");
		
		txtfTitulo = new JTextField();
		ifCadLivro.getContentPane().add(txtfTitulo, "4, 4, fill, top");
		txtfTitulo.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		ifCadLivro.getContentPane().add(lblAutor, "2, 6, right, center");
		
		txtfAutor = new JTextField();
		ifCadLivro.getContentPane().add(txtfAutor, "4, 6, fill, top");
		txtfAutor.setColumns(10);
		
		JLabel lblEditora = new JLabel("Editora:");
		ifCadLivro.getContentPane().add(lblEditora, "2, 8, right, center");
		
		txtfEditora = new JTextField();
		ifCadLivro.getContentPane().add(txtfEditora, "4, 8, fill, top");
		txtfEditora.setColumns(10);
		
		JLabel lblEdio = new JLabel("Edição:");
		ifCadLivro.getContentPane().add(lblEdio, "2, 10, right, center");
		
		txtfEdicao = new JTextField();
		ifCadLivro.getContentPane().add(txtfEdicao, "4, 10, fill, top");
		txtfEdicao.setColumns(10);
		
		JLabel lblNumPginas = new JLabel("Num. Páginas:");
		ifCadLivro.getContentPane().add(lblNumPginas, "2, 12, right, center");
		
		txtfNPag = new JTextField();
		ifCadLivro.getContentPane().add(txtfNPag, "4, 12, fill, top");
		txtfNPag.setColumns(10);
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		ifCadLivro.getContentPane().add(lblQuantidade, "2, 14, right, center");
		
		txtfQuant = new JTextField();
		ifCadLivro.getContentPane().add(txtfQuant, "4, 14, fill, top");
		txtfQuant.setColumns(10);
		
		JPanel panelLivro = new JPanel();
		ifCadLivro.getContentPane().add(panelLivro, "2, 16, 3, 1, fill, fill");
		panelLivro.setLayout(null);
		
		JButton btnAdicionarLivro = new JButton("Adicionar");
		btnAdicionarLivro.setBounds(9, 17, 86, 27);
		panelLivro.add(btnAdicionarLivro);
		
		JButton btnPesquisarLivro = new JButton("Pesquisar");
		btnPesquisarLivro.setBounds(102, 17, 90, 27);
		panelLivro.add(btnPesquisarLivro);
		
		JButton btnSalvarLivro = new JButton("Salvar");
		btnSalvarLivro.setBounds(199, 17, 66, 27);
		btnSalvarLivro.setEnabled(false);
		panelLivro.add(btnSalvarLivro);
		
		JButton btnDeletarLivro = new JButton("Deletar");
		btnDeletarLivro.setBounds(272, 17, 74, 27);
		btnDeletarLivro.setEnabled(false);
		panelLivro.add(btnDeletarLivro);
		
		/*
		 * Tela de Cadastro de Usuários
		 */
		JInternalFrame ifCadUser = new JInternalFrame("Cadastro de Clientes",false, true);
		ifCadUser.setBounds(190, 35, 535, 345);
		desktopPane.add(ifCadUser);
		ifCadUser.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:pref"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("pref:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblNome = new JLabel("Nome:");
		ifCadUser.getContentPane().add(lblNome, "2, 2, right, default");
		
		txtfNome = new JTextField();
		ifCadUser.getContentPane().add(txtfNome, "4, 2, fill, default");
		txtfNome.setColumns(10);
		
		JLabel lblCpf = new JLabel("CPF:");
		ifCadUser.getContentPane().add(lblCpf, "2, 4, right, default");
		
		txtfCpf = new JTextField();
		ifCadUser.getContentPane().add(txtfCpf, "4, 4, fill, default");
		txtfCpf.setColumns(10);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		ifCadUser.getContentPane().add(lblTelefone, "2, 6, right, default");
		
		txtfTelefone = new JTextField();
		ifCadUser.getContentPane().add(txtfTelefone, "4, 6, fill, default");
		txtfTelefone.setColumns(10);
		
		JLabel lblCidade = new JLabel("Cidade:");
		ifCadUser.getContentPane().add(lblCidade, "2, 8, right, default");
		
		txtfCidade = new JTextField();
		ifCadUser.getContentPane().add(txtfCidade, "4, 8, fill, default");
		txtfCidade.setColumns(10);
		
		JLabel lblBairro = new JLabel("Bairro:");
		ifCadUser.getContentPane().add(lblBairro, "2, 10, right, default");
		
		txtfBairro = new JTextField();
		ifCadUser.getContentPane().add(txtfBairro, "4, 10, fill, default");
		txtfBairro.setColumns(10);
		
		JLabel lblRua = new JLabel("Rua:");
		ifCadUser.getContentPane().add(lblRua, "2, 12, right, default");
		
		txtfRua = new JTextField();
		ifCadUser.getContentPane().add(txtfRua, "4, 12, fill, default");
		txtfRua.setColumns(10);
		
		JLabel lblNmero = new JLabel("Número:");
		ifCadUser.getContentPane().add(lblNmero, "2, 14, right, default");
		
		txtfCodCartao = new JTextField();
		ifCadUser.getContentPane().add(txtfCodCartao, "4, 14, fill, default");
		txtfCodCartao.setColumns(10);
		
		JLabel lblCodCartao = new JLabel("Cód. do Cartão:");
		ifCadUser.getContentPane().add(lblCodCartao, "2, 16, right, default");
		
		txtfCodCartao = new JTextField();
		txtfCodCartao.setText("Código gerado automaticamente");
		ifCadUser.getContentPane().add(txtfCodCartao, "4, 16, fill, default");
		txtfCodCartao.setEnabled(false);
		txtfCodCartao.setColumns(10);
		
		JPanel panel = new JPanel();
		ifCadUser.getContentPane().add(panel, "2, 18, 3, 1, fill, fill");
		panel.setLayout(null);
		
		JButton btnAdicionarUser = new JButton("Adicionar");
		btnAdicionarUser.setBounds(19, 20, 100, 27);
		panel.add(btnAdicionarUser);
		
		JButton btnPesquisarUser = new JButton("Pesquisar");
		btnPesquisarUser.setBounds(138, 20, 100, 27);
		panel.add(btnPesquisarUser);
		
		JButton btnSalvarUser = new JButton("Salvar");
		btnSalvarUser.setBounds(257, 20, 100, 27);
		panel.add(btnSalvarUser);
		
		JButton btnExcluirUser = new JButton("Excluir");
		btnExcluirUser.setBounds(376, 20, 100, 27);
		panel.add(btnExcluirUser);
		
		
		//TODO: remodelar depois em uma classe que cuidará dos Listeners de eventos
		/*
		 * Funcionalidades da tela
		 */
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
