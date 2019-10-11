package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.systemhaus.demo.Server;
public class CadastroUserFrm extends SkeletonFrm{

	private JTextField txtfNome;
	private JTextField txtfCpf;
	private JTextField txtfTelefone;
	private JTextField txtfCidade;
	private JTextField txtfBairro;
	private JTextField txtfRua;
	private JTextField txtfNumero;
	private JTextField txtfCodCartao;
	private JInternalFrame ifCadUser;
	private Server fakeServer;
	
	public JInternalFrame createForm(Server fakeServer) {
		initComponents();
		initLayout();
		this.fakeServer = fakeServer;
		
		return ifCadUser;
	}

	protected void initLayout() {
		ifCadUser.getContentPane().setLayout(new BorderLayout());
		ifCadUser.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		ifCadUser.getContentPane().add(createButtonBar(), BorderLayout.SOUTH);
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
		
		return builder.build();
	}

	protected void initComponents() {
		ifCadUser = new JInternalFrame("Cadastro de Clientes",false, true);
		ifCadUser.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ifCadUser.setBounds(190, 35, 535, 345);
		
		txtfNome = new JTextField();
		
		txtfCpf = new JTextField();
		
		txtfTelefone = new JTextField();
		
		txtfCidade = new JTextField();
		
		txtfBairro = new JTextField();
		
		txtfRua = new JTextField();
		
		txtfNumero = new JTextField();
		
		txtfCodCartao = new JTextField();
		txtfCodCartao.setText("Código gerado automaticamente");
		txtfCodCartao.setEnabled(false);
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
