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
	private JPanel livroPanel;
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
	private JPanel clientePanel;
	private ClienteSelectionPanel clienteTablePanel;
	private PresentationModel<Cliente> clienteModel;
	private SelectionInList<Cliente> clienteSelection = new SelectionInList<>();
	private Server server;
	
	public JInternalFrame createForm(Server server) {
		initComponents();
		initLayout();
		this.server = server;
		return ifTranBook;
	}

	protected void initLayout() {
		ifTranBook.getContentPane().setLayout(new BorderLayout());	
		ifTranBook.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		ifTranBook.getContentPane().add(createButtonBar(), BorderLayout.SOUTH);
	}

	protected JPanel createButtonBar() {
		JPanel panelTran = new JPanel();
		panelTran.setLayout(new FlowLayout());
		
		JButton btnRetirar = new JButton("Retirar");
		panelTran.add(btnRetirar);
		
		JButton btnDevolver = new JButton("Devolver");
		panelTran.add(btnDevolver);
		
		JButton btnLivroTableConfirm = livroTablePanel.getConfirmButton();
		JButton btnLivroTableCancel = livroTablePanel.getCancelButton();
		
		JButton btnClienteTableConfirm = clienteTablePanel.getConfirmButton();
		JButton btnClienteTableCancel = clienteTablePanel.getCancelButton();
		
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
			List<Livro> livros = server.findSimilarBooks(livroModel.getBean());
			if (!livros.isEmpty()) {
				livroSelection.setList(new ArrayListModel<>(livros));
				if(livros.size() == 1) {
					this.livroTablePanel.setSelectionToLastObject();
					txtfQuant.setText(String.valueOf(server.returnBookCount(livroModel.getBean().getISBN())));
					txtfQuantDisp.setText(String.valueOf(server.returnAvailableBookCount(livroModel.getBean().getISBN())));
					txtfRetirado.setText(livroModel.getBean().isRetirado() ? "Retirado" : "Disponível");
				}else {
					changePanel(livroPanel,"table");
				}
			} else	JOptionPane.showMessageDialog(null, "Nenhum livro encontrado!");
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
					this.clienteTablePanel.setSelectionToLastObject();
				}else {
					changePanel(clientePanel, "table");
				}
			}else
				JOptionPane.showMessageDialog(null, "Nenhum cliente encontrado!");
		});
		
		btnRetirar.addActionListener(l -> {
			//realiza a retirada
			
		});
		
		btnDevolver.addActionListener(l -> {
			//realiza a devolução
			
		});
		
		btnLivroTableConfirm.addActionListener(l -> {
			txtfQuant.setText(String.valueOf(server.returnBookCount(livroModel.getBean().getISBN())));
			txtfQuantDisp.setText(String.valueOf(server.returnAvailableBookCount(livroModel.getBean().getISBN())));
			txtfRetirado.setText(livroModel.getBean().isRetirado() ? "Retirado" : "Disponível");
			changePanel(livroPanel, "data");
		});
		
		btnLivroTableCancel.addActionListener(l -> {
			livroTablePanel.clearList();
			livroTablePanel.setSelectionToANewObject();
			changePanel(livroPanel, "data");
		});
		
		btnClienteTableConfirm.addActionListener(l -> {
			changePanel(clientePanel, "data");
		});
		
		btnClienteTableCancel.addActionListener(l -> {
			changePanel(clientePanel, "data");
			clienteTablePanel.clearList();
			clienteTablePanel.setSelectionToANewObject();
		});
		
		return panelTran;
	}
	
	protected void initComponents() {
		ifTranBook = new JInternalFrame("Retiradas & Devoluções",false, true);
		ifTranBook.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ifTranBook.setBounds(190, 35, 900, 400);
		
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
		livroTablePanel = new LivroSelectionPanel(livroSelection);
		
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
		
		livroPanel = new JPanel();
		livroPanel.setLayout(new CardLayout());
		livroPanel.add(createLivroPanel(), "data");
		livroPanel.add(livroTablePanel, "table");
		
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
		clienteTablePanel = new ClienteSelectionPanel(clienteSelection);
		
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
		
		clientePanel = new JPanel();
		clientePanel.setLayout(new CardLayout());
		clientePanel.add(createClientePanel(), "data");
		clientePanel.add(clienteTablePanel, "table");
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
								"18dlu,18dlu,162dlu"));
		builder.border(new EmptyBorder(5, 5, 10, 5));
		
		// Search Bars
		builder.append(cbEscolhaLivro);
		builder.append(txtfEscolhaLivro);
		builder.append(btnPesquisaLivro);
		builder.append(cbEscolhaCliente);
		builder.append(txtfEscolhaCliente);
		builder.append(btnPesquisaCliente);
		builder.nextLine(2);
		
		builder.append(livroPanel,5);
		builder.append(clientePanel,4);
		
		return builder.build();
	}

	@Override
	protected boolean allFieldsAreFilled() {
		// TODO verificar se ambos os panels estão preenchidos
		return false;
	}

	@Override
	protected void changePanel(JPanel panel, String name) {
		((CardLayout)panel.getLayout()).show(panel, name);
	}

	@Override
	protected void clearDataAndSetButtons(boolean clearData, JButton[] btnArray, boolean[] modeList) {
		//não é usado nesse form
	}
	
}
