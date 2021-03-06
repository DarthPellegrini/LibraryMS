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

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
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
	private JTextField txtfEstante;
	private JTextField txtfPrateleira;
	private String livroISBN;
	private Server server;
	private JPanel contentPanel;
	private JPanel dataPanel;
	private JInternalFrame iFrameCadLivro;
	private LivroSelectionPanel tablePanel;
	private PresentationModel<Livro> model;
	private SelectionInList<Livro> livroSelection = new SelectionInList<>();
	private final boolean[] addMode = {true, true, false, false, true};
	private final boolean[] editMode = {false, false, true, true, true};
	private final boolean[] searchMode = {false, false, false, false, true};
	
	public JInternalFrame createForm(Server server) {
		this.server = server;
		initComponents();
		initLayout();
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
		panelLivroButtonBar.add(btnCancelarLivro);
		
		JButton btnTableConfirm = tablePanel.getConfirmButton();
		JButton btnTableCancel = tablePanel.getCancelButton();
		
		JButton btnReport = new JButton("Gerar Relatório");
		panelLivroButtonBar.add(btnReport);
		
		btnReport.addActionListener(l -> {
			server.generateLivroReport();
		});
		
		JButton[] btnArray = {btnAdicionarLivro, btnPesquisarLivro, btnSalvarLivro, 
				btnDeletarLivro, btnCancelarLivro};
		
		btnAdicionarLivro.addActionListener(l -> {
			//validação dos campos
			if(server.getUserAccessLevel()>1) {
			if (allFieldsAreFilled()) {
				if(server.addNewBookRoutine(model.getBean(), server.strToInt(txtfQuant.getText()))){
					JOptionPane.showMessageDialog(null, "Livro(s) inserido(s) com sucesso!");
					this.clearDataAndSetButtons(true, btnArray, addMode);
				}else
					JOptionPane.showMessageDialog(null, "Ocorreu um erro na inserção do livro!");
			}else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			} else {
				JOptionPane.showMessageDialog(null, "Você não tem permissão para realizar esta ação!");
			}
		});
		
		btnPesquisarLivro.addActionListener(l -> {
			List<Livro> livros = server.findSimilarBooks(model.getBean());
			if (!livros.isEmpty()) {
				livroSelection.setList(new ArrayListModel<>(livros));
				if(livros.size() == 1) {
					this.tablePanel.setSelectionToLastObject();
					//server.initializeLivro(model.getBean());
					livroISBN = model.getBean().getISBN();
					txtfQuant.setText(String.valueOf(server.returnBookCount(livroISBN)));
					txtfPrateleira.setText(String.valueOf(model.getBean().getPrateleira().getNumero()));
					txtfEstante.setText(String.valueOf(model.getBean().getPrateleira().getEstante().getNumero()));
					this.clearDataAndSetButtons(false, btnArray, editMode);
				}else {
					this.clearDataAndSetButtons(false, btnArray, searchMode);
					changePanel(contentPanel,"table");
				}
			} else
				JOptionPane.showMessageDialog(null, "Nenhum livro encontrado!");
		});
		
		btnSalvarLivro.addActionListener(l -> {
			if(server.getUserAccessLevel()	>	1) {
			Livro livroBean = model.getBean();
			if (allFieldsAreFilled()) {
				if(server.editBook(livroISBN,livroBean,server.strToInt(txtfQuant.getText()))){
					JOptionPane.showMessageDialog(null, "Livro(s) modificado(s) com sucesso!");
					this.clearDataAndSetButtons(true, btnArray, addMode);
					livroISBN = "";
				}else
					JOptionPane.showMessageDialog(null, "Não é possível remover todos os livros, pois ainda existem livros retirados!");
			}else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			} else {
				JOptionPane.showMessageDialog(null, "Você não tem permissão para realizar esta ação!");
			}
		});
		
		btnDeletarLivro.addActionListener(l -> {
			if(server.getUserAccessLevel()	>	1) {
			if (allFieldsAreFilled()) 
				if(server.deleteBook(livroISBN, 0)) {
					JOptionPane.showMessageDialog(null, "Livro(s) deletado(s) com sucesso!");
					this.clearDataAndSetButtons(true, btnArray, addMode);
				}else
					JOptionPane.showMessageDialog(null, "Não é possível deletar todos os livros, pois ainda existem livros retirados!");
			else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			} else {
				JOptionPane.showMessageDialog(null, "Você não tem permissão para realizar esta ação!");
			}
		});
		
		btnCancelarLivro.addActionListener(l -> {
			changePanel(contentPanel,"data");
			this.clearDataAndSetButtons(true, btnArray, addMode);
		});
		
		btnTableConfirm.addActionListener(l -> {
			changePanel(contentPanel,"data");
			//server.initializeLivro(model.getBean());
			livroISBN = model.getBean().getISBN();
			txtfQuant.setText(String.valueOf(server.returnBookCount(livroISBN)));
			txtfPrateleira.setText(String.valueOf(model.getBean().getPrateleira().getNumero()));
			txtfEstante.setText(String.valueOf(model.getBean().getPrateleira().getEstante().getNumero()));
			this.clearDataAndSetButtons(false, btnArray, editMode);
		});
		
		btnTableCancel.addActionListener(l -> {
			changePanel(contentPanel,"data");
			this.clearDataAndSetButtons(true, btnArray, addMode);
		});
		
		if(server.getUserAccessLevel() == 4) {
			JButton btnGenerateData = new JButton("Gerar dados");
			panelLivroButtonBar.add(btnGenerateData);
			
			btnGenerateData.addActionListener(l ->{
				Faker faker = new Faker(new java.util.Locale("pt-BR"));
				model.getBean().setISBN("978"+faker.number().digits(10));
				model.getBean().setTitulo(faker.book().title());
				model.getBean().setAutor(faker.book().author());
				model.getBean().setEditora(faker.book().publisher());
				model.getBean().setEdicao(faker.number().numberBetween(1,10));
				model.getBean().setNumeroPaginas(faker.number().numberBetween(10,3000));
				txtfQuant.setText(String.valueOf(faker.number().numberBetween(1,100)));
			});
		}
		
		return panelLivroButtonBar;
	}

	protected JPanel createMainPanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, pref:grow, 6dlu, right:pref, 3dlu, pref:grow","18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,"));
		builder.border(new EmptyBorder(5, 5, 5, 5));

		builder.append("ISBN:", txtfIsbn, 5);
		builder.nextLine();
		
		builder.append("Título:",txtfTitulo, 5);
		builder.nextLine();
		
		builder.append("Autor:", txtfAutor, 5);
		builder.nextLine();
		
		builder.append("Editora:", txtfEditora, 5);
		builder.nextLine();
		
		builder.append("Edição:", txtfEdicao);
		
		builder.append("Nº de Páginas:", txtfNPag);
		builder.nextLine();
		
		builder.append("Quantidade:", txtfQuant, 5);
		builder.nextLine();
		
		builder.append("Estante:",txtfEstante);
		
		builder.append("Prateleira:",txtfPrateleira);
		
		return builder.build();
	}

	protected void initComponents() {
		iFrameCadLivro = new JInternalFrame("Cadastro de Livros", true, true);
		iFrameCadLivro.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		iFrameCadLivro.setBounds(190, 35, 680, 300);
		
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
		
		txtfEstante = new JTextField();
		txtfEstante.setEditable(false);
		
		txtfPrateleira = new JTextField();
		txtfPrateleira.setEditable(false);
		
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
			txtfPrateleira.setText("");
			txtfEstante.setText("");
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
