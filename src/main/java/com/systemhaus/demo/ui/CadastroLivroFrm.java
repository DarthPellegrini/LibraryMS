package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.common.collect.ArrayListModel;
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
	private String livroISBN;
	private Server server;
	private JPanel contentPanel;
	private JPanel dataPanel;
	private JInternalFrame iFrameCadLivro;
	private LivroSelectionPanel tablePanel;
	private PresentationModel<Livro> model;
	private SelectionInList<Livro> livroSelection = new SelectionInList<>();
	private final boolean[] addMode = {true, true, false, false, false};
	private final boolean[] editMode = {false, false, true, true, true};
	private final boolean[] searchMode = {false, false, false, false, true};
	
	public JInternalFrame createForm(Server server) {
		initComponents();
		initLayout();
		this.server = server;
		return iFrameCadLivro;
	}

	protected void initLayout() {
		iFrameCadLivro.getContentPane().setLayout(new BorderLayout());
		iFrameCadLivro.getContentPane().add(contentPanel, BorderLayout.CENTER);
		iFrameCadLivro.getContentPane().add(createButtonBar(), BorderLayout.SOUTH);
	}


	protected JPanel createButtonBar() {
		JPanel panelLivroButtonBar = new JPanel();
		panelLivroButtonBar.setLayout(new FlowLayout());
		
		JButton btnAdicionarLivro = new JButton("Adicionar");
		panelLivroButtonBar.add(btnAdicionarLivro);
		
		JButton btnPesquisarLivro = new JButton("Pesquisar");
		panelLivroButtonBar.add(btnPesquisarLivro);
		
		JButton btnSalvarLivro = new JButton("Salvar");
		btnSalvarLivro.setEnabled(false);
		panelLivroButtonBar.add(btnSalvarLivro);
		
		JButton btnDeletarLivro = new JButton("Deletar");
		btnDeletarLivro.setEnabled(false);
		panelLivroButtonBar.add(btnDeletarLivro);
		
		JButton btnCancelarLivro = new JButton("Cancelar");
		btnCancelarLivro.setEnabled(false);
		panelLivroButtonBar.add(btnCancelarLivro);
		
		JButton btnTableConfirm = tablePanel.getConfirmButton();
		JButton btnTableCancel = tablePanel.getCancelButton();
		
		JButton btnTest = new JButton("Gerar Relatório");
		panelLivroButtonBar.add(btnTest);
		
		btnTest.addActionListener(l -> {
			server.testJasperReports();
		});
		
		JButton[] btnArray = {btnAdicionarLivro, btnPesquisarLivro, btnSalvarLivro, 
				btnDeletarLivro, btnCancelarLivro};
		
		btnAdicionarLivro.addActionListener(l -> {
			//validação dos campos
			if (allFieldsAreFilled()) {
				if(server.addNewBookRoutine(model.getBean(), server.strToInt(txtfQuant.getText()))){
					JOptionPane.showMessageDialog(null, "Livro(s) inserido(s) com sucesso!");
					this.clearDataAndSetButtons(true, btnArray, addMode);
				}else
					JOptionPane.showMessageDialog(null, "Ocorreu um erro na inserção do livro!");
			}else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
		});
		
		btnPesquisarLivro.addActionListener(l -> {
			List<Livro> livros = server.findSimilarBooks(model.getBean());
			if (!livros.isEmpty()) {
				livroSelection.setList(new ArrayListModel<>(livros));
				if(livros.size() == 1) {
					this.tablePanel.setSelectionToLastObject();
					livroISBN = model.getBean().getISBN();
					txtfQuant.setText(String.valueOf(server.returnBookCount(livroISBN)));
					this.clearDataAndSetButtons(false, btnArray, editMode);
				}else {
					this.clearDataAndSetButtons(false, btnArray, searchMode);
					changePanel(contentPanel,"table");
				}
			} else
				JOptionPane.showMessageDialog(null, "Nenhum livro encontrado!");
		});
		
		btnSalvarLivro.addActionListener(l -> {
			Livro livroBean = model.getBean();
			if (allFieldsAreFilled()) {
				if(server.editBook(livroISBN,livroBean,server.strToInt(txtfQuant.getText()))){
					JOptionPane.showMessageDialog(null, "Livro(s) modificado(s) com sucesso!");
					this.clearDataAndSetButtons(true, btnArray, addMode);
					livroISBN = "";
				}else
					JOptionPane.showMessageDialog(null, "Ocorreu um erro na modificação do(s) livro(s)!");
			}else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
		});
		
		btnDeletarLivro.addActionListener(l -> {
			if (allFieldsAreFilled()) 
				if(server.deleteBook(livroISBN, 0)) {
					JOptionPane.showMessageDialog(null, "Livro(s) deletado(s) com sucesso!");
					this.clearDataAndSetButtons(true, btnArray, addMode);
				}else
					JOptionPane.showMessageDialog(null, "Ocorreu um erro na remoção do(s) livro(s)!");
			else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
		});
		
		btnCancelarLivro.addActionListener(l -> {
			changePanel(contentPanel,"data");
			this.clearDataAndSetButtons(true, btnArray, addMode);
		});
		
		btnTableConfirm.addActionListener(l -> {
			changePanel(contentPanel,"data");
			livroISBN = model.getBean().getISBN();
			txtfQuant.setText(String.valueOf(server.returnBookCount(livroISBN)));
			this.clearDataAndSetButtons(false, btnArray, editMode);
		});
		
		btnTableCancel.addActionListener(l -> {
			changePanel(contentPanel,"data");
			this.clearDataAndSetButtons(true, btnArray, addMode);
		});
		
		return panelLivroButtonBar;
	}

	protected JPanel createMainPanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, pref:grow","18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,"));
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
		iFrameCadLivro = new JInternalFrame("Cadastro de Livros", true, true);
		iFrameCadLivro.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		iFrameCadLivro.setBounds(190, 35, 580, 300);
		
		model = new PresentationModel<Livro>(livroSelection);
		tablePanel = new LivroSelectionPanel(livroSelection, 1, 10);
		
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
		
		dataPanel = createMainPanel();
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new CardLayout());
		contentPanel.add(dataPanel, "data");
		contentPanel.add(tablePanel, "table");
	}

	protected void clearDataAndSetButtons(boolean clearData, JButton[] btnArray, boolean[] modeList) {
		if (clearData) {
			this.tablePanel.clearList();
			this.tablePanel.setSelectionToANewObject();
			txtfQuant.setText("");
		}
		for(int i = 0; i < btnArray.length; i++)
			btnArray[i].setEnabled(modeList[i]);
	}
	
	@Override
	protected void changePanel(JPanel panel, String name) {
		((CardLayout)panel.getLayout()).show(panel, name);
	}
	
	protected boolean allFieldsAreFilled() {
		return (!txtfIsbn.getText().isEmpty() && !txtfEditora.getText().isEmpty() &&
				!txtfTitulo.getText().isEmpty() && !txtfAutor.getText().isEmpty() &&
				!txtfEdicao.getText().isEmpty() && !txtfNPag.getText().isEmpty() && 
				!txtfQuant.getText().isEmpty());
	}
	
}
