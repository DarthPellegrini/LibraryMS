package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.systemhaus.demo.FakeServer;

public class CadastroLivroFrm extends SkeletonFrm{
	
	private JTextField txtfIsbn;
	private JTextField txtfEdicao;
	private JTextField txtfTitulo;
	private JTextField txtfNPag;
	private JTextField txtfQuant;
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
		
		btnAdicionarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fakeServer.addBook(txtfIsbn.getText(), txtfEdicao.getText(), txtfTitulo.getText(), txtfAutor.getText(), txtfEditora.getText(), txtfNPag.getText(), txtfQuant.getText());
			}
		});
		
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
		txtfIsbn.setText("Ex.: 9789988877773");
		
		txtfTitulo = new JTextField();
		
		txtfAutor = new JTextField();
		
		txtfEditora = new JTextField();
		
		txtfEdicao = new JTextField();
		
		txtfNPag = new JTextField();
		
		txtfQuant = new JTextField();
	}
}
