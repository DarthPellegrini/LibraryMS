package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
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
public class CadastroUserFrm extends SkeletonFrm{

	private JTextField txtfNome;
	private JTextField txtfCpf;
	private JTextField txtfTelefone;
	private JTextField txtfCidade;
	private JTextField txtfBairro;
	private JTextField txtfRua;
	private JTextField txtfNumero;
	private JTextField txtfCodCartao;
	private JTextField txtfValidade;
	private JInternalFrame iFrameCadUser;
	private JPanel contentPanel;
	private CardLayout layout;
	private JPanel dataPanel;
	private Server server;
	private ClienteSelectionPanel tablePanel;
	private PresentationModel<Cliente> model;
	private SelectionInList<Cliente> clienteSelection = new SelectionInList<>();
	
	public JInternalFrame createForm(Server server) {
		initComponents();
		initLayout();
		this.server = server;
		return iFrameCadUser;
	}

	protected void initLayout() {
		iFrameCadUser.getContentPane().setLayout(new BorderLayout());
		iFrameCadUser.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		iFrameCadUser.getContentPane().add(createButtonBar(), BorderLayout.SOUTH);
	}
	
	protected JPanel createButtonBar() {
		JPanel panelUser = new JPanel();
		panelUser.setLayout(new FlowLayout());
		
		JButton btnAdicionarUser = new JButton("Adicionar");
		panelUser.add(btnAdicionarUser);
		
		JButton btnPesquisarUser = new JButton("Pesquisar");
		panelUser.add(btnPesquisarUser);
		
		JButton btnSalvarUser = new JButton("Salvar");
		btnSalvarUser.setEnabled(false);
		panelUser.add(btnSalvarUser);
		
		JButton btnExcluirUser = new JButton("Excluir");
		btnExcluirUser.setEnabled(false);
		panelUser.add(btnExcluirUser);
		
		
		
		return panelUser;
	}
	
	protected JPanel createMainPanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, pref:grow", "18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu"));
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.append("Nome:",txtfNome);
		builder.nextLine();
		
		builder.append("CPF:",txtfCpf);
		builder.nextLine();
		
		builder.append("Telefone:",txtfTelefone);
		builder.nextLine();
		
		builder.append("Cidade:",txtfCidade);
		builder.nextLine();
		
		builder.append("Bairro:",txtfBairro);
		builder.nextLine();
		
		builder.append("Rua:",txtfRua);
		builder.nextLine();
		
		builder.append("Número:",txtfNumero);
		builder.nextLine();
		
		builder.append("Cód. do Cartão:",txtfCodCartao);
		builder.nextLine();
		
		builder.append("Data de Validade:",txtfValidade);
		builder.nextLine();
		
		return builder.build();
	}

	protected void initComponents() {
		iFrameCadUser = new JInternalFrame("Cadastro de Clientes",false, true);
		iFrameCadUser.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		iFrameCadUser.setBounds(190, 35, 535, 345);
		
		model = new PresentationModel<Cliente>(clienteSelection);
		tablePanel = new ClienteSelectionPanel(clienteSelection);
		
		ValueModel NomeAdapter = model.getModel("nome");
		ValueModel CPFAdapter = model.getModel("CPF");
		ValueModel TelefoneAdapter = model.getModel("telefone");
		ValueModel CidadeAdapter = model.getModel("cidade");
		ValueModel BairroAdapter = model.getModel("bairro");
		ValueModel RuaAdapter = model.getModel("rua");
		ValueModel NumeroAdapter = model.getModel("numero");
		ValueModel CodCartaoAdapter = model.getModel("codCartao");
		ValueModel ValidadeAdapter = model.getModel("validade");
		
		txtfNome = BasicComponentFactory.createTextField(NomeAdapter);
		
		txtfCpf = BasicComponentFactory.createTextField(CPFAdapter);
		
		txtfTelefone = BasicComponentFactory.createTextField(TelefoneAdapter);
		
		txtfCidade = BasicComponentFactory.createTextField(CidadeAdapter);
		
		txtfBairro = BasicComponentFactory.createTextField(BairroAdapter);
		
		txtfRua = BasicComponentFactory.createTextField(RuaAdapter);
		
		txtfNumero = BasicComponentFactory.createIntegerField(NumeroAdapter, 0);
		
		txtfCodCartao = BasicComponentFactory.createTextField(CodCartaoAdapter);
		txtfCodCartao.setText("Cartão gerado automaticamente");
		txtfCodCartao.setEditable(false);
		
		txtfValidade = BasicComponentFactory.createFormattedTextField(ValidadeAdapter, 
				new DateFormatter(new SimpleDateFormat("dd/mm/yyyy")));
		txtfValidade.setText("Cartão gerado automaticamente");
		txtfValidade.setEditable(false);
		
	}

	@Override
	protected boolean allFieldsAreFilled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void changePanel(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void clearDataAndSetButtons(boolean clearData, JButton[] btnArray, boolean[] modeList) {
		// TODO Auto-generated method stub
		
	}
}
