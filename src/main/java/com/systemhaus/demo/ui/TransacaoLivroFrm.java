package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.common.collect.ArrayListModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.systemhaus.demo.Server;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.Livro;
import com.systemhaus.demo.domain.LivroRetirado;

public class TransacaoLivroFrm extends SkeletonFrm{

	private JInternalFrame ifTranBook;
	private JComboBox<String> cbEscolhaLivro;
	private JTextField txtfEscolhaLivro;
	private JButton btnPesquisaLivro;
	private JTextField txtfIsbn;
	private JTextField txtfEdicao;
	private JTextField txtfTitulo;
	private JTextField txtfNPag;
	private JTextField txtfQuant;
	private JTextField txtfQuantDisp;
	private JTextField txtfAutor;
	private JTextField txtfEditora;
	private JTextField txtfRetirado;
	private LivroSelectionPanel livroTablePanel;
	private PresentationModel<Livro> livroModel;
	private SelectionInList<Livro> livroSelection = new SelectionInList<>();
	private JComboBox<String> cbEscolhaCliente;
	private JTextField txtfEscolhaCliente;
	private JButton btnPesquisaCliente;
	private JTextField txtfNome;
	private JTextField txtfCpf;
	private JTextField txtfTelefone;
	private JTextField txtfCidade;
	private JTextField txtfBairro;
	private JTextField txtfRua;
	private JTextField txtfNumero;
	private JTextField txtfCodCartao;
	private JTextField txtfValidade;
	private ClienteSelectionPanel clienteTablePanel;
	private PresentationModel<Cliente> clienteModel;
	private SelectionInList<Cliente> clienteSelection = new SelectionInList<>();
	private JPanel contentPanel;
	private LivroRetiradoSelectionPanel livroRetiradoTablePanel;
	private SelectionInList<LivroRetirado> livroRetiradoSelection = new SelectionInList<>();
	private Server server;
	private String selectedPanel;
	private final boolean[] addMode = {true, true, false, false};
	private final boolean[] editMode = {false, false, true, true};
	private final boolean[] searchMode = {false, false, false, false};
	
	
	public JInternalFrame createForm(Server server) {
		initComponents();
		initLayout();
		this.server = server;
		return ifTranBook;
	}

	protected void initLayout() {
		ifTranBook.getContentPane().setLayout(new BorderLayout());	
		ifTranBook.getContentPane().add(contentPanel, BorderLayout.CENTER);
		ifTranBook.getContentPane().add(createButtonBar(), BorderLayout.SOUTH);
	}

	protected JPanel createButtonBar() {
		JPanel panelTran = new JPanel();
		panelTran.setLayout(new FlowLayout());
		
		JButton btnRetirar = new JButton("Retirar");
		panelTran.add(btnRetirar);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		panelTran.add(btnPesquisar);
		
		JButton btnRenovar = new JButton("Renovar");
		panelTran.add(btnRenovar);
		
		JButton btnDevolver = new JButton("Devolver");
		panelTran.add(btnDevolver);
		
		JButton btnCancelar = new JButton("Cancelar");
		panelTran.add(btnCancelar);
		
		JButton[] btnArray = {btnRetirar, btnPesquisar, btnRenovar, btnDevolver};
		
		
		JButton btnLivroTableConfirm = livroTablePanel.getConfirmButton();
		JButton btnLivroTableCancel = livroTablePanel.getCancelButton();
		
		JButton btnClienteTableConfirm = clienteTablePanel.getConfirmButton();
		JButton btnClienteTableCancel = clienteTablePanel.getCancelButton();
		
		JButton btnRetiradaTableConfirm = livroRetiradoTablePanel.getConfirmButton();
		JButton btnRetiradaTableCancel = livroRetiradoTablePanel.getCancelButton();
		
		btnPesquisaLivro.addActionListener(l -> {
			txtfQuant.setText("");
			txtfQuantDisp.setText("");
			txtfRetirado.setText("");
			livroTablePanel.clearList();
			livroTablePanel.setSelectionToANewObject();
			switch(((String)cbEscolhaLivro.getSelectedItem()).toString()) {
				case "ISBN":
					livroModel.getBean().setISBN(txtfEscolhaLivro.getText());
					break;
				case "Título":
					livroModel.getBean().setTitulo(txtfEscolhaLivro.getText());
					break;
				case "Autor":
					livroModel.getBean().setAutor(txtfEscolhaLivro.getText());
					break;
				case "Editora":
					livroModel.getBean().setEditora(txtfEscolhaLivro.getText());
					break;
				case "Edição":
					livroModel.getBean().setEdicao(server.strToInt(txtfEscolhaLivro.getText()));
					break;
				case "Nº de Páginas":
					livroModel.getBean().setNumeroPaginas(server.strToInt(txtfEscolhaLivro.getText()));
					break;
			}
			List<Livro> livros = server.findAvailableBooks(livroModel.getBean());
			if (!livros.isEmpty()) {
				setDataFromGivenBookList(livros);
			} else {
				livros = server.findSimilarBooks(livroModel.getBean());
				if (!livros.isEmpty()) {
					setDataFromGivenBookList(livros);
					JOptionPane.showMessageDialog(null, "Nenhum livro buscado está disponível para retirada, somente devolução!");
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum livro encontrado!");
				}
			}
		});
		
		btnPesquisaCliente.addActionListener(l -> {
			clienteTablePanel.clearList();
			clienteTablePanel.setSelectionToANewObject();
			switch(((String)cbEscolhaCliente.getSelectedItem()).toString()) {
				case "Nome":
					clienteModel.getBean().setNome(txtfEscolhaCliente.getText());
					break;
				case "CPF":
					clienteModel.getBean().setCPF(txtfEscolhaCliente.getText());
					break;
				case "Telefone":
					clienteModel.getBean().setTelefone(txtfEscolhaCliente.getText());
					break;
				case "Cidade":
					clienteModel.getBean().setCidade(txtfEscolhaCliente.getText());
					break;
				case "Bairro":
					clienteModel.getBean().setBairro(txtfEscolhaCliente.getText());
					break;
				case "Rua":
					clienteModel.getBean().setRua(txtfEscolhaCliente.getText());
					break;
				case "Número":
					clienteModel.getBean().setNumero(server.strToInt(txtfEscolhaCliente.getText()));
					break;
			}
			List<Cliente> clientes = server.findSimilarClients(clienteModel.getBean());
			if(!clientes.isEmpty()){
				clienteSelection.setList(new ArrayListModel<>(clientes));
				if(clientes.size() == 1) {
					txtfEscolhaCliente.setText("");
					this.clienteTablePanel.setSelectionToLastObject();
				}else {
					changePanel(contentPanel, "tableC");
				}
			}else
				JOptionPane.showMessageDialog(null, "Nenhum cliente encontrado!");
		});
		
		btnRetirar.addActionListener(l -> {
			switch(server.retirada(livroModel.getBean(), clienteModel.getBean().getCartao())) {
				case 0:
					JOptionPane.showMessageDialog(null, "O livro foi retirado com sucesso!");
					this.selectedPanel = "cl";
					clearDataAndSetButtons(true, btnArray, addMode);
					break;
				case 1:
					JOptionPane.showMessageDialog(null, "O livro buscado não possui cópias disponíveis.");
					break;
				case 2:
					JOptionPane.showMessageDialog(null, "O livro buscado não foi encontrado.");
					break;
				case 3:
					JOptionPane.showMessageDialog(null, "O cliente não foi selecionado, por favor, selecione-o.");
					break;
				case 4:
					JOptionPane.showMessageDialog(null, "Os exemplares deste livro não estão disponíveis para retirada, somente devolução!");
					break;
			}
		});
		
		btnPesquisar.addActionListener(l -> {
			//pesquisa por um livro retirado
		});
		
		btnRenovar.addActionListener(l -> {
			//realiza a renovação
			changePanel(contentPanel,"tableLR");
			//TODO: abrir tela de busca de renovação com o cliente e/ou o livro preenchido
		});
		
		btnDevolver.addActionListener(l -> {
			//realiza a devolução
			if (livroModel.getBean().validate() && clienteModel.getBean().validate())
				switch (server.devolucao(livroModel.getBean(), clienteModel.getBean().getCartao())) {
					case 0:
						JOptionPane.showMessageDialog(null, "O livro devolvido com sucesso!");
						break;
						
				}
			//TODO: abrir tela de busca de renovação com o cliente e/ou o livro preenchido
		});
		
		btnCancelar.addActionListener(l -> {
			this.selectedPanel = "clr";
			clearDataAndSetButtons(true, btnArray, addMode);
			changePanel(contentPanel, "data");
		});
		
		btnLivroTableConfirm.addActionListener(l -> {
			txtfQuant.setText(String.valueOf(server.returnBookCount(livroModel.getBean().getISBN())));
			txtfQuantDisp.setText(String.valueOf(server.returnAvailableBookCount(livroModel.getBean().getISBN())));
			txtfRetirado.setText(livroModel.getBean().isRetirado() ? "Retirado" : "Disponível");
			this.selectedPanel = "l";
			clearDataAndSetButtons(false, btnArray, addMode);
			changePanel(contentPanel, "data");
		});
		
		btnLivroTableCancel.addActionListener(l -> {
			this.selectedPanel = "l";
			clearDataAndSetButtons(true, btnArray, addMode);
			changePanel(contentPanel, "data");
		});
		
		btnClienteTableConfirm.addActionListener(l -> {
			this.selectedPanel = "c";
			clearDataAndSetButtons(false, btnArray, addMode);
			changePanel(contentPanel, "data");
		});
		
		btnClienteTableCancel.addActionListener(l -> {
			this.selectedPanel = "c";
			clearDataAndSetButtons(true, btnArray, addMode);
			changePanel(contentPanel, "data");
		});
		
		btnRetiradaTableConfirm.addActionListener(l -> {
			this.selectedPanel = "r";
			clearDataAndSetButtons(false, btnArray, addMode);
			changePanel(contentPanel, "data");
		});
		
		btnRetiradaTableCancel.addActionListener(l -> {
			this.selectedPanel = "r";
			clearDataAndSetButtons(true, btnArray, addMode);
			changePanel(contentPanel, "data");
		});
		
		return panelTran;
	}
	
	protected void initComponents() {
		ifTranBook = new JInternalFrame("Retiradas & Devoluções",false, true);
		ifTranBook.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ifTranBook.setBounds(190, 35, 900, 420);
		
		/*
		 * LIVRO
		 */
		
		cbEscolhaLivro = new JComboBox<String>();
		cbEscolhaLivro.addItem("ISBN");
		cbEscolhaLivro.addItem("Título");
		cbEscolhaLivro.addItem("Autor");
		cbEscolhaLivro.addItem("Editora");
		cbEscolhaLivro.addItem("Edição");
		cbEscolhaLivro.addItem("Nº de Páginas");
		txtfEscolhaLivro = new JTextField();
		btnPesquisaLivro = new JButton("Pesquisar");
		
		livroModel = new PresentationModel<Livro>(livroSelection);
		livroTablePanel = new LivroSelectionPanel(livroSelection, 1.7, 18);
		
		ValueModel ISBNAdapter = livroModel.getModel("ISBN");
		ValueModel tituloAdapter = livroModel.getModel("titulo");
		ValueModel autorAdapter = livroModel.getModel("autor");
		ValueModel editoraAdapter = livroModel.getModel("editora");
		ValueModel edicaoAdapter = livroModel.getModel("edicao");
		ValueModel numPagAdapter = livroModel.getModel("numeroPaginas");
		
		txtfIsbn = BasicComponentFactory.createTextField(ISBNAdapter);
		txtfIsbn.setEditable(false);
		
		txtfTitulo = BasicComponentFactory.createTextField(tituloAdapter);
		txtfTitulo.setEditable(false);
		
		txtfAutor = BasicComponentFactory.createTextField(autorAdapter);
		txtfAutor.setEditable(false);
		
		txtfEditora = BasicComponentFactory.createTextField(editoraAdapter);
		txtfEditora.setEditable(false);
		
		txtfEdicao = BasicComponentFactory.createIntegerField(edicaoAdapter, 0);
		txtfEdicao.setEditable(false);
		
		txtfNPag = BasicComponentFactory.createIntegerField(numPagAdapter, 0);
		txtfNPag.setEditable(false);
		
		txtfRetirado = new JTextField(); 
		txtfRetirado.setEditable(false);
		
		txtfQuant = new JTextField();
		txtfQuant.setEditable(false);
		
		txtfQuantDisp = new JTextField();
		txtfQuantDisp.setEditable(false);
		
		/*
		 * CLIENTE
		 */
		
		cbEscolhaCliente = new JComboBox<String>();
		cbEscolhaCliente.addItem("Nome");
		cbEscolhaCliente.addItem("CPF");
		cbEscolhaCliente.addItem("Telefone");
		cbEscolhaCliente.addItem("Cidade");
		cbEscolhaCliente.addItem("Bairro");
		cbEscolhaCliente.addItem("Rua");
		cbEscolhaCliente.addItem("Número");
		txtfEscolhaCliente = new JTextField();
		btnPesquisaCliente = new JButton("Pesquisar");
		
		clienteModel = new PresentationModel<Cliente>(clienteSelection);
		clienteTablePanel = new ClienteSelectionPanel(clienteSelection, 1.2, 18);
		
		ValueModel NomeAdapter = clienteModel.getModel("nome");
		ValueModel CPFAdapter = clienteModel.getModel("CPF");
		ValueModel TelefoneAdapter = clienteModel.getModel("telefone");
		ValueModel CidadeAdapter = clienteModel.getModel("cidade");
		ValueModel BairroAdapter = clienteModel.getModel("bairro");
		ValueModel RuaAdapter = clienteModel.getModel("rua");
		ValueModel NumeroAdapter = clienteModel.getModel("numero");
		ValueModel CodCartaoAdapter = clienteModel.getModel("codCartao");
		ValueModel ValidadeAdapter = clienteModel.getModel("validade");
		
		txtfNome = BasicComponentFactory.createTextField(NomeAdapter);
		txtfNome.setEditable(false);
		
		txtfCpf = BasicComponentFactory.createTextField(CPFAdapter);
		txtfCpf.setEditable(false);
		
		txtfTelefone = BasicComponentFactory.createTextField(TelefoneAdapter);
		txtfTelefone.setEditable(false);
		
		txtfCidade = BasicComponentFactory.createTextField(CidadeAdapter);
		txtfCidade.setEditable(false);
		
		txtfBairro = BasicComponentFactory.createTextField(BairroAdapter);
		txtfBairro.setEditable(false);
		
		txtfRua = BasicComponentFactory.createTextField(RuaAdapter);
		txtfRua.setEditable(false);
		
		txtfNumero = BasicComponentFactory.createIntegerField(NumeroAdapter, 0);
		txtfNumero.setEditable(false);
		
		txtfCodCartao = BasicComponentFactory.createTextField(CodCartaoAdapter);
		txtfCodCartao.setEditable(false);
		
		txtfValidade = BasicComponentFactory.createFormattedTextField(ValidadeAdapter, 
				new DateFormatter(new SimpleDateFormat("MM/yy")));
		txtfValidade.setEditable(false);
		
		/*
		 * LIVRO RETIRADO
		 */
		
		livroRetiradoTablePanel = new LivroRetiradoSelectionPanel(livroRetiradoSelection, 1.3, 18);
		
		livroRetiradoTablePanel.clearList();
		livroRetiradoTablePanel.setSelectionToANewObject();
		
		/*
		 * PAINEL DE DADOS
		 */
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new CardLayout());
		contentPanel.add(createMainPanel(), "data");
		contentPanel.add(livroRetiradoTablePanel,"tableLR");
		contentPanel.add(clienteTablePanel,"tableC");
		contentPanel.add(livroTablePanel,"tableL");
	}
	
	private JPanel createLivroPanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, 127dlu, 3dlu, pref", 
								"18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu"));
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.append("ISBN:",txtfIsbn);
		builder.nextLine();
		
		builder.append("Título:", txtfTitulo);
		builder.nextLine();
		
		builder.append("Autor:", txtfAutor);
		builder.nextLine();
		
		builder.append("Editora:", txtfEditora);
		builder.nextLine();
		
		builder.append("Edicao:", txtfEdicao);
		builder.nextLine();
		
		builder.append("Nº de Páginas:", txtfNPag);
		builder.nextLine();
		
		builder.append("Retirado", txtfRetirado);
		builder.nextLine();
		
		builder.append("Quantidade:", txtfQuant);
		builder.nextLine();
		
		builder.append("Quant. Disponível:", txtfQuantDisp);
		builder.nextLine();
		
		return builder.build();
	}
	
	private JPanel createClientePanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, 127dlu, 3dlu, pref", 
								"18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu"));
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.append("Nome:", txtfNome);
		builder.nextLine();
		
		builder.append("CPF:", txtfCpf);
		builder.nextLine();
		
		builder.append("Telefone:", txtfTelefone);
		builder.nextLine();
		
		builder.append("Cidade:", txtfCidade);
		builder.nextLine();
		
		builder.append("Bairro:", txtfBairro);
		builder.nextLine();
		
		builder.append("Rua:", txtfRua);
		builder.nextLine();
		
		builder.append("Número:", txtfNumero);
		builder.nextLine();
		
		builder.append("Cód. Cartão:", txtfCodCartao);
		builder.nextLine();
		
		builder.append("Validade:", txtfValidade);
		builder.nextLine();
		
		return builder.build();
	}
	
	protected JPanel createMainPanel(){
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("pref, 3dlu, pref:grow, 3dlu, pref, 9dlu, pref, 3dlu, pref:grow, 3dlu, pref", 
								"18dlu,9dlu,200dlu"));
		builder.border(new EmptyBorder(5, 5, 10, 5));
		
		// Search Bars
		builder.append(cbEscolhaLivro);
		builder.append(txtfEscolhaLivro);
		builder.append(btnPesquisaLivro);
		builder.append(cbEscolhaCliente);
		builder.append(txtfEscolhaCliente);
		builder.append(btnPesquisaCliente);
		builder.nextLine(2);
		
		builder.append(createLivroPanel(),5);
		builder.append(createClientePanel(),5);
		
		return builder.build();
	}

	private void setDataFromGivenBookList(List<Livro> livros) {
		livroSelection.setList(new ArrayListModel<>(livros));
		if(livros.size() == 1) {
			txtfEscolhaLivro.setText("");
			this.livroTablePanel.setSelectionToLastObject();
			txtfQuant.setText(String.valueOf(server.returnBookCount(livroModel.getBean().getISBN())));
			txtfQuantDisp.setText(String.valueOf(server.returnAvailableBookCount(livroModel.getBean().getISBN())));
			txtfRetirado.setText(livroModel.getBean().isRetirado() ? "Retirado" : "Disponível");
		}else {
			changePanel(contentPanel,"tableL");
		}
	}
	
	@Override
	protected boolean allFieldsAreFilled() {
		return (livroModel.getBean().validate() && clienteModel.getBean().validate());
	}

	@Override
	protected void changePanel(JPanel panel, String name) {
		((CardLayout)panel.getLayout()).show(panel, name);
	}
	
	@Override
	protected void clearDataAndSetButtons(boolean clearData, JButton[] btnArray, boolean[] modeList) {
		if (clearData) {
			if(selectedPanel.contains("l")) {
				txtfEscolhaLivro.setText("");
				this.livroTablePanel.clearList();
				this.livroTablePanel.setSelectionToANewObject();
				txtfRetirado.setText(""); txtfQuant.setText(""); txtfQuantDisp.setText("");
			}
			if(selectedPanel.contains("c")) {
				txtfEscolhaCliente.setText("");
				this.clienteTablePanel.clearList();
				this.clienteTablePanel.setSelectionToANewObject();
			}
			if(selectedPanel.contains("r")) {
				txtfEscolhaLivro.setText("");
				txtfEscolhaCliente.setText("");
				this.livroRetiradoTablePanel.clearList();
				this.livroRetiradoTablePanel.setSelectionToANewObject();
			}
		}
		for(int i = 0; i < btnArray.length; i++)
			btnArray[i].setEnabled(modeList[i]);
	}
	
}
