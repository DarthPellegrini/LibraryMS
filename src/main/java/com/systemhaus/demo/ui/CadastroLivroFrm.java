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
	
	public JInternalFrame criarCadastroLivro() {
		JInternalFrame ifCadLivro = new JInternalFrame("Cadastro de Livros", false, true);
		ifCadLivro.setBounds(198, 35, 400, 320);
		
		ifCadLivro.getContentPane().setLayout(new BorderLayout());
		
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("pref, 5px, pref:grow", "27px, 27px,27px, 27px,27px,27px,27px"));
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		txtfIsbn = new JTextField();
		txtfIsbn.setColumns(10);
		builder.append("ISBN:", txtfIsbn);
		builder.nextLine();
		
		txtfTitulo = new JTextField();
		txtfTitulo.setColumns(10);
		builder.append("Título:",txtfTitulo);
		builder.nextLine();
		
		txtfAutor = new JTextField();
		txtfAutor.setColumns(10);
		builder.append("Autor:", txtfAutor);
		builder.nextLine();
		
		txtfEditora = new JTextField();
		txtfEditora.setColumns(10);
		builder.append("Editora:", txtfEditora);
		builder.nextLine();
		
		txtfEdicao = new JTextField();
		txtfEdicao.setColumns(10);
		builder.append("Edição:", txtfEdicao);
		builder.nextLine();
		
		txtfNPag = new JTextField();
		txtfNPag.setColumns(10);
		builder.append("Num. Páginas:", txtfNPag);
		builder.nextLine();
		
		txtfQuant = new JTextField();
		txtfQuant.setColumns(10);
		builder.append("Quantidade:", txtfQuant);
		builder.nextLine();
		
		ifCadLivro.getContentPane().add(builder.build(), BorderLayout.CENTER);
		
		JPanel panelLivro = new JPanel();
		panelLivro.setLayout(new FlowLayout());
		builder.append(panelLivro, 3);
		
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
		
		ifCadLivro.getContentPane().add(panelLivro, BorderLayout.SOUTH);
		return ifCadLivro;
	}
}
