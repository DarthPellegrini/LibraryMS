package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
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
	private JComboBox<String> cbEscolhaUser;
	private JTextField txtfEscolhaUser;
	private JButton btnPesquisaUser;
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
	private JPanel contentPanel;
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
		
		return panelTran;
	}
	
	protected void initComponents() {
		ifTranBook = new JInternalFrame("Retiradas & Devoluções",false, true);
		ifTranBook.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ifTranBook.setBounds(190, 35, 900, 400);
		
		contentPanel = new JPanel(new BorderLayout());
		
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
		ValueModel retiradoAdapter = livroModel.getModel("retirado");
		
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
		
		txtfRetirado = new JTextField(); //BasicComponentFactory.createTextField(retiradoAdapter);
		txtfRetirado.setEditable(false);
		
		txtfQuant = new JTextField();
		txtfQuant.setEditable(false);
		
		txtfQuantDisp = new JTextField();
		txtfQuantDisp.setEditable(false);
		
		livroPanel = new JPanel();
		livroPanel.setLayout(new CardLayout());
		livroPanel.add(createLivroPanel(), "data");
		livroPanel.add(livroTablePanel, "table");
		contentPanel.add(livroPanel, BorderLayout.WEST);
		
		/*
		 * CLIENTE
		 */
		
		cbEscolhaUser = new JComboBox<String>();
		cbEscolhaUser.addItem("Nome");
		cbEscolhaUser.addItem("CPF");
		cbEscolhaUser.addItem("Telefone");
		cbEscolhaUser.addItem("Cidade");
		cbEscolhaUser.addItem("Bairro");
		cbEscolhaUser.addItem("Rua");
		cbEscolhaUser.addItem("Número");
		txtfEscolhaUser = new JTextField();
		btnPesquisaUser = new JButton("Pesquisar");
		
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
		contentPanel.add(clientePanel, BorderLayout.EAST);
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
				new FormLayout("right:pref, 3dlu, pref:grow, 3dlu, pref, 9dlu, right:pref, 3dlu, pref:grow, 3dlu, pref", 
								"18dlu,18dlu,162dlu"));
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		// Search Bars
		builder.append(cbEscolhaLivro);
		builder.append(txtfEscolhaLivro);
		builder.append(btnPesquisaLivro);
		builder.append(cbEscolhaUser);
		builder.append(txtfEscolhaUser);
		builder.append(btnPesquisaUser);
		builder.nextLine(2);
		
		// panel que contém
		builder.append(contentPanel,11);
		
		return builder.build();
	}

	@Override
	protected boolean allFieldsAreFilled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void changePanel(JPanel panel, String name) {
		((CardLayout)panel.getLayout()).show(panel, name);
	}

	@Override
	protected void clearDataAndSetButtons(boolean clearData, JButton[] btnArray, boolean[] modeList) {
		// TODO Auto-generated method stub
		
	}
	
}
