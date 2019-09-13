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
				//validação dos campos
				if (!txtfIsbn.getText().isEmpty() && !txtfEdicao.getText().isEmpty() &&
						!txtfTitulo.getText().isEmpty() && !txtfAutor.getText().isEmpty() &&
						!txtfEditora.getText().isEmpty() && !txtfNPag.getText().isEmpty() && 
						!txtfQuant.getText().isEmpty()) 
					if(fakeServer.addNewBookRoutine(txtfIsbn.getText(), txtfEdicao.getText(), txtfTitulo.getText(), txtfAutor.getText(), txtfEditora.getText(), txtfNPag.getText(), txtfQuant.getText()))
						JOptionPane.showMessageDialog(null, "Livro(s) inserido(s) com sucesso!");
					else
						JOptionPane.showMessageDialog(null, "Ops, aconteceu um erro na inserção do livro!");
				else
					JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			}
		});
		
		JButton btnPesquisarLivro = new JButton("Pesquisar");
		panelLivro.add(btnPesquisarLivro);
		
		btnPesquisarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// caso alguma string seja vazia, temos que definir valores default para elas
				Livro l = fakeServer.findABook(txtfIsbn.getText(), 
						txtfEdicao.getText().isEmpty() ? 0 : Integer.parseInt(txtfEdicao.getText()), 
						txtfTitulo.getText(), txtfAutor.getText(), txtfEditora.getText(), 
						txtfNPag.getText().isEmpty() ? 0 : Integer.parseInt(txtfNPag.getText()), 
						txtfQuant.getText().isEmpty() ? 0 : Integer.parseInt(txtfQuant.getText()));
				if	(l == null)
					JOptionPane.showMessageDialog(null, "Nenhum livro encontrado!");
				else {
					txtfIsbn.setText(l.getISBN());
					txtfEdicao.setText("" + l.getEdicao());
					txtfTitulo.setText(l.getTitulo());
					txtfAutor.setText(l.getAutor());
					txtfEditora.setText(l.getEditora());
					txtfNPag.setText("" + l.getNumeroPaginas());
					txtfQuant.setText("" + l.getQuantCopias());
				}
			}
		});
		
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
