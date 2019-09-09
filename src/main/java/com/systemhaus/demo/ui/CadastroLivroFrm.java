package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class CadastroLivroFrm {
	
	private JTextField txtfIsbn;
	private JTextField txtfEdicao;
	private JTextField txtfTitulo;
	private JTextField txtfNPag;
	private JTextField txtfQuant;
	private JTextField txtfAutor;
	private JTextField txtfEditora;
	private JInternalFrame ifCadLivro;
	
	public JInternalFrame createBookCRUD() {
		initComponents();
		initLayout();

		return ifCadLivro;
	}

	private void initLayout() {
		ifCadLivro.getContentPane().setLayout(new BorderLayout());
		ifCadLivro.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		ifCadLivro.getContentPane().add(criarButtonBar(), BorderLayout.SOUTH);
	}

	private JPanel criarButtonBar() {
		JPanel panelLivro = new JPanel();
		panelLivro.setLayout(new FlowLayout());
		
		JButton btnAdicionarLivro = new JButton("Adicionar");
		panelLivro.add(btnAdicionarLivro);
		
		JButton btnPesquisarLivro = new JButton("Pesquisar");
		panelLivro.add(btnPesquisarLivro);
		
		JButton btnSalvarLivro = new JButton("Salvar");
		btnSalvarLivro.setEnabled(false);
		panelLivro.add(btnSalvarLivro);
		
		JButton btnDeletarLivro = new JButton("Deletar");
		btnDeletarLivro.setEnabled(false);
		panelLivro.add(btnDeletarLivro);
		return panelLivro;
	}

	private JPanel createMainPanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, pref:grow", "18dlu,18dlu,18dlu, 18dlu,18dlu,18dlu,18dlu"));
		builder.border(new EmptyBorder(5, 5, 5, 5));

		builder.append("ISBN:", txtfIsbn);
		builder.nextLine();
		
		builder.append("Título:",txtfTitulo);
		builder.nextLine();
		
		builder.append("Autor:", txtfAutor);
		builder.nextLine();
		
		builder.append("Editora:", txtfEditora);
		builder.nextLine();
		
		builder.append("Edição:", txtfEdicao);
		builder.nextLine();
		
		builder.append("Num. Páginas:", txtfNPag);
		builder.nextLine();
		
		builder.append("Quantidade:", txtfQuant);
		builder.nextLine();
		
		return builder.build();
	}

	private void initComponents() {
		ifCadLivro = new JInternalFrame("Cadastro de Livros", false, true);
		ifCadLivro.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ifCadLivro.setBounds(198, 35, 400, 320);
		
		txtfIsbn = new JTextField();
		txtfIsbn.setColumns(10);
		
		txtfTitulo = new JTextField();
		txtfTitulo.setColumns(10);
		
		txtfAutor = new JTextField();
		txtfAutor.setColumns(10);
		
		txtfEditora = new JTextField();
		txtfEditora.setColumns(10);
		
		txtfEdicao = new JTextField();
		txtfEdicao.setColumns(10);
		
		txtfNPag = new JTextField();
		txtfNPag.setColumns(10);
		
		txtfQuant = new JTextField();
		txtfQuant.setColumns(10);
	}
}
