package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.FormLayout;

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
	
	
	public JInternalFrame createForm() {
		initComponents();
		initLayout();
		
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
		ifTranBook.setBounds(190, 35, 900, 375);
		
		cbEscolhaLivro = new JComboBox<String>();
		cbEscolhaLivro.addItem("ISBN");
		cbEscolhaLivro.addItem("Título");
		cbEscolhaLivro.addItem("Autor");
		cbEscolhaLivro.addItem("Editora");
		cbEscolhaLivro.addItem("Edição");
		cbEscolhaLivro.addItem("Nº de Páginas");
		cbEscolhaLivro.addItem("Quantidade");
		txtfEscolhaLivro = new JTextField();
		btnPesquisaLivro = new JButton("Pesquisar");
		btnPesquisaLivro.setEnabled(false);
		
		txtfIsbn = new JTextField();
		txtfTitulo = new JTextField();
		txtfAutor = new JTextField();
		txtfEditora = new JTextField();
		txtfEdicao = new JTextField();
		txtfNPag = new JTextField();
		txtfQuant = new JTextField();
		txtfQuantDisp = new JTextField();
		
		cbEscolhaUser = new JComboBox<String>();
		cbEscolhaUser.addItem("Nome");
		cbEscolhaUser.addItem("CPF");
		cbEscolhaUser.addItem("Telefone");
		cbEscolhaUser.addItem("Cidade");
		cbEscolhaUser.addItem("Bairro");
		cbEscolhaUser.addItem("Rua");
		cbEscolhaUser.addItem("Número");
		cbEscolhaUser.addItem("Cód. Cartão");
		txtfEscolhaUser = new JTextField();
		btnPesquisaUser = new JButton("Pesquisar");
		btnPesquisaUser.setEnabled(false);
		
		txtfNome = new JTextField();
		txtfCpf = new JTextField();
		txtfTelefone = new JTextField();
		txtfCidade = new JTextField();
		txtfBairro = new JTextField();
		txtfRua = new JTextField();
		txtfNumero = new JTextField();
		txtfCodCartao = new JTextField();
	}
	
	protected JPanel createMainPanel(){
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, pref:grow, 3dlu, pref, 9dlu, right:pref, 3dlu, pref:grow, 3dlu, pref", 
								"18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu"));
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		// Search Bars
		builder.append(cbEscolhaLivro);
		builder.append(txtfEscolhaLivro);
		builder.append(btnPesquisaLivro);
		builder.append(cbEscolhaUser);
		builder.append(txtfEscolhaUser);
		builder.append(btnPesquisaUser);
		builder.nextLine(2);
		
		// Results from Search
		builder.append("ISBN:",txtfIsbn);
		builder.nextColumn(2);
		builder.append("Nome:", txtfNome);
		builder.nextLine();
		
		builder.append("Título:", txtfTitulo);
		builder.nextColumn(2);
		builder.append("CPF:", txtfCpf);
		builder.nextLine();
		
		builder.append("Autor:", txtfAutor);
		builder.nextColumn(2);
		builder.append("Telefone:", txtfTelefone);
		builder.nextLine();
		
		builder.append("Editora:", txtfEditora);
		builder.nextColumn(2);
		builder.append("Cidade:", txtfCidade);
		builder.nextLine();
		
		builder.append("Edicao:", txtfEdicao);
		builder.nextColumn(2);
		builder.append("Bairro:", txtfBairro);
		builder.nextLine();
		
		builder.append("Nº de Páginas:", txtfNPag);
		builder.nextColumn(2);
		builder.append("Rua:", txtfRua);
		builder.nextLine();
		
		builder.append("Quantidade:", txtfQuant);
		builder.nextColumn(2);
		builder.append("Número:", txtfNumero);
		builder.nextLine();
		
		builder.append("Quant. Disponível:", txtfQuantDisp);
		builder.nextColumn(2);
		builder.append("Cód. Cartão:", txtfCodCartao);
		builder.nextLine();
			
		return builder.build();
	}
	
	
	
}
