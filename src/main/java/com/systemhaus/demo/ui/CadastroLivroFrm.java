package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.systemhaus.demo.FakeServer;
import com.systemhaus.demo.domain.Livro;

public class CadastroLivroFrm extends SkeletonFrm{
	
	private JTextField txtfIsbn;
	private JTextField txtfTitulo;
	private JTextField txtfNPag;
	private JTextField txtfQuant;
	private JTextField txtfEdicao;
	private JTextField txtfAutor;
	private JTextField txtfEditora;
	private JInternalFrame ifCadLivro;
	private FakeServer fakeServer;
	
	public JInternalFrame createForm(FakeServer fakeServer) {
		initComponents();
		initLayout();
		this.fakeServer = fakeServer;

		return ifCadLivro;
	}

	protected void initLayout() {
		ifCadLivro.getContentPane().setLayout(new BorderLayout());
		ifCadLivro.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		ifCadLivro.getContentPane().add(createButtonBar(), BorderLayout.SOUTH);
	}

	protected JPanel createButtonBar() {
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
		
		//TODO transferir actionListeners para uma classe CareTaker (possivelmente)
		btnAdicionarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//validação dos campos
				if (!txtfIsbn.getText().isEmpty() && !txtfEdicao.getText().isEmpty() &&
						!txtfTitulo.getText().isEmpty() && !txtfAutor.getText().isEmpty() &&
						!txtfEditora.getText().isEmpty() && !txtfNPag.getText().isEmpty() && 
						!txtfQuant.getText().isEmpty()) 
					//TODO: criar mensagens de erro para cada tipo de erro diferente
					if(fakeServer.addNewBookRoutine(txtfIsbn.getText(), 
													fakeServer.strToInt(txtfEdicao.getText()), 
													txtfTitulo.getText(), txtfAutor.getText(), txtfEditora.getText(), 
													fakeServer.strToInt(txtfNPag.getText()), 
													fakeServer.strToInt(txtfQuant.getText()))) {
						JOptionPane.showMessageDialog(null, "Livro(s) inserido(s) com sucesso!");
						System.out.println(
						fakeServer.getB().getRegistroDeLivros());
						clearField();
					}else
						JOptionPane.showMessageDialog(null, "Ocorreu um erro na inserção do livro!");
				else
					JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			}
		});
		
		btnPesquisarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Livro l = fakeServer.findBook(txtfIsbn.getText(), 
						txtfEdicao.getText(), txtfTitulo.getText(), 
						txtfAutor.getText(), txtfEditora.getText(), 
						txtfNPag.getText());
				if	(l == null)
					JOptionPane.showMessageDialog(null, "Nenhum livro encontrado!");
				else {
					txtfIsbn.setText(l.getISBN());
					txtfEdicao.setText("" + l.getEdicao());
					txtfTitulo.setText(l.getTitulo());
					txtfAutor.setText(l.getAutor());
					txtfEditora.setText(l.getEditora());
					txtfNPag.setText("" + l.getNumeroPaginas());
					btnAdicionarLivro.setEnabled(false);
					btnSalvarLivro.setEnabled(true);
					btnDeletarLivro.setEnabled(true);
				}
			}
		});
		
		btnSalvarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtfIsbn.getText().isEmpty() || !txtfEdicao.getText().isEmpty() ||
						!txtfTitulo.getText().isEmpty() || !txtfAutor.getText().isEmpty() ||
						!txtfEditora.getText().isEmpty() || !txtfNPag.getText().isEmpty() || 
						!txtfQuant.getText().isEmpty())
					if(fakeServer.editBook(txtfIsbn.getText(), 
											fakeServer.strToInt(txtfEdicao.getText()), 
											txtfTitulo.getText(), txtfAutor.getText(), txtfEditora.getText(), 
											fakeServer.strToInt(txtfNPag.getText()), 
											fakeServer.strToInt(txtfQuant.getText()))) {
						JOptionPane.showMessageDialog(null, "Livro(s) modificado(s) com sucesso!");
						btnAdicionarLivro.setEnabled(true);
						btnSalvarLivro.setEnabled(false);
						btnDeletarLivro.setEnabled(false);
						clearField();
					}else
						JOptionPane.showMessageDialog(null, "Ocorreu um erro na modificação do(s) livro(s)!");
				else
					JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
						
			}
		});
		
		btnDeletarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//validação dos campos
				if (!txtfIsbn.getText().isEmpty() || !txtfEdicao.getText().isEmpty() ||
						!txtfTitulo.getText().isEmpty() || !txtfAutor.getText().isEmpty() ||
						!txtfEditora.getText().isEmpty() || !txtfNPag.getText().isEmpty() || 
						!txtfQuant.getText().isEmpty()) 
					if(fakeServer.deleteBook(txtfIsbn.getText(), 
											fakeServer.strToInt(txtfEdicao.getText()), 
											txtfTitulo.getText(), txtfAutor.getText(), txtfEditora.getText(), 
											fakeServer.strToInt(txtfNPag.getText()), 
											fakeServer.strToInt(txtfQuant.getText()),0)) {
						JOptionPane.showMessageDialog(null, "Livro(s) deletado(s) com sucesso!");
						btnAdicionarLivro.setEnabled(true);
						btnSalvarLivro.setEnabled(false);
						btnDeletarLivro.setEnabled(false);
						clearField();
					}else
						JOptionPane.showMessageDialog(null, "Ocorreu um erro na remoção do(s) livro(s)!");
				else
					JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			}
		});
		
		return panelLivro;
	}

	protected JPanel createMainPanel() {
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
		
		builder.append("Nº de Páginas:", txtfNPag);
		builder.nextLine();
		
		builder.append("Quantidade:", txtfQuant);
		builder.nextLine();
		
		return builder.build();
	}

	protected void initComponents() {
		ifCadLivro = new JInternalFrame("Cadastro de Livros", false, true);
		ifCadLivro.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ifCadLivro.setBounds(190, 35, 400, 320);
	    
		txtfIsbn = new JTextField();
		
		txtfTitulo = new JTextField();
		
		txtfAutor = new JTextField();
		
		txtfEditora = new JTextField();
		
		txtfEdicao = new JTextField();
		
		txtfNPag = new JTextField();
		
		txtfQuant = new JTextField();
	}

	protected void clearField() {
		txtfIsbn.setText("");
		txtfTitulo.setText("");
		txtfAutor.setText("");
		txtfEditora.setText("");
		txtfEdicao.setText("");
		txtfNPag.setText("");
		txtfQuant.setText("");
	}
	
}
