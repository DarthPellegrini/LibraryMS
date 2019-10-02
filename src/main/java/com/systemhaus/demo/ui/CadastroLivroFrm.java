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

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.systemhaus.demo.Server;
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
	private Server server;
	private String livroISBN;
	private PresentationModel<Livro> model;
	
	public JInternalFrame createForm(Server server) {
		initComponents();
		initLayout();
		this.server = server;
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
		
		//TODO transferir actionListeners para uma classe careTaker (possivelmente)
		btnAdicionarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//validação dos campos
				if (allFieldsAreFilled()) {
					//TODO: criar mensagens de erro para cada tipo de erro diferente
					if(server.addNewBookRoutine(model.getBean(), server.strToInt(txtfQuant.getText()))){
						JOptionPane.showMessageDialog(null, "Livro(s) inserido(s) com sucesso!");
						clearField();
					}else
						JOptionPane.showMessageDialog(null, "Ocorreu um erro na inserção do livro!");
				}else
					JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			}
		});
		
		btnPesquisarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("FIND antes trigger: " + livroBean.toString());
				//System.out.println("after trigger: " + livroBean.toString());
				Livro livro = server.findBook(model.getBean());
				//System.out.println("after find: " + livro);
				if	(livro == null) {
					JOptionPane.showMessageDialog(null, "Nenhum livro encontrado!");
				} else {
					model.setBean(livro.copy());
					livroISBN = model.getBean().getISBN();
					txtfQuant.setText(String.valueOf(server.returnBookCount(livro.getISBN())));
					btnAdicionarLivro.setEnabled(false);
					btnSalvarLivro.setEnabled(true);
					btnDeletarLivro.setEnabled(true);
				}
			}
		});
		
		btnSalvarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Livro livroBean = model.getBean();
				if (allFieldsAreFilled()) {
					System.out.println("EDIT antes trigger: " + livroBean.toString());
					System.out.println("after trigger: " + livroBean.toString());
					if(server.editBook(livroISBN,livroBean,server.strToInt(txtfQuant.getText()))){
						JOptionPane.showMessageDialog(null, "Livro(s) modificado(s) com sucesso!");
						btnAdicionarLivro.setEnabled(true);
						btnSalvarLivro.setEnabled(false);
						btnDeletarLivro.setEnabled(false);
						livroISBN = "";
						System.out.println("after op: " + livroBean.toString());
						clearField();
						System.out.println("after clear: " + livroBean.toString());
					}else
						JOptionPane.showMessageDialog(null, "Ocorreu um erro na modificação do(s) livro(s)!");
				}else
					JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			}
		});
		
		btnDeletarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (allFieldsAreFilled()) 
					if(server.deleteBook(livroISBN, 0)) {
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
		
		model = new PresentationModel<Livro>(new Livro());
		
		ValueModel ISBNAdapter = model.getModel("ISBN");
		ValueModel tituloAdapter = model.getModel("titulo");
		ValueModel autorAdapter = model.getModel("autor");
		ValueModel editoraAdapter = model.getModel("editora");
		ValueModel edicaoAdapter = model.getModel("edicao");
		ValueModel numPagAdapter = model.getModel("numeroPaginas");
		
		txtfIsbn = BasicComponentFactory.createTextField(ISBNAdapter);
		
		txtfTitulo = BasicComponentFactory.createTextField(tituloAdapter);
		
		txtfAutor = BasicComponentFactory.createTextField(autorAdapter);
		
		txtfEditora = BasicComponentFactory.createTextField(editoraAdapter);
		
		txtfEdicao = BasicComponentFactory.createIntegerField(edicaoAdapter, 0);
		
		txtfNPag = BasicComponentFactory.createIntegerField(numPagAdapter, 0);
		
		txtfQuant = new JTextField();
	}

	protected void clearField() {
		model.setBean(new Livro());
	}
	
	protected boolean allFieldsAreFilled() {
		return (!txtfIsbn.getText().isEmpty() && !txtfEdicao.getText().isEmpty() &&
				!txtfTitulo.getText().isEmpty() && !txtfAutor.getText().isEmpty() &&
				!txtfEditora.getText().isEmpty() && !txtfNPag.getText().isEmpty() && 
				!txtfQuant.getText().isEmpty());
	}
	
}
