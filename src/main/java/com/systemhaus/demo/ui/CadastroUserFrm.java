package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.FormLayout;
public class CadastroUserFrm {

	private JTextField txtfNome;
	private JTextField txtfCpf;
	private JTextField txtfTelefone;
	private JTextField txtfCidade;
	private JTextField txtfBairro;
	private JTextField txtfRua;
	private JTextField txtfNumero;
	private JTextField txtfCodCartao;
	private JInternalFrame ifCadUser;
	
	public JInternalFrame createUserCRUD() {
		initComponents();
		initLayout();
		
		return ifCadUser;
	}

	private void initLayout() {
		ifCadUser.getContentPane().setLayout(new BorderLayout());
		ifCadUser.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		ifCadUser.getContentPane().add(criarButtonBar(), BorderLayout.SOUTH);
	}
	
	private JPanel criarButtonBar() {
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
	
	private JPanel createMainPanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, pref:grow", "18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu"), new FormDebugPanel());
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

	private void initComponents() {
		ifCadUser = new JInternalFrame("Cadastro de Clientes",false, true);
		ifCadUser.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ifCadUser.setBounds(190, 35, 535, 345);
		
		txtfNome = new JTextField();
		txtfNome.setColumns(10);
		
		txtfCpf = new JTextField();
		txtfCpf.setColumns(10);
		
		txtfTelefone = new JTextField();
		txtfTelefone.setColumns(10);
		
		txtfCidade = new JTextField();
		txtfCidade.setColumns(10);
		
		txtfBairro = new JTextField();
		txtfBairro.setColumns(10);
		
		txtfRua = new JTextField();
		txtfRua.setColumns(10);
		
		txtfNumero = new JTextField();
		txtfNumero.setColumns(10);
		
		txtfCodCartao = new JTextField();
		txtfCodCartao.setText("Código gerado automaticamente");
		txtfCodCartao.setEnabled(false);
		txtfCodCartao.setColumns(10);
	}
}
