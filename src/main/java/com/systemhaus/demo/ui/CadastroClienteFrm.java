package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import com.github.javafaker.Faker;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.common.collect.ArrayListModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.systemhaus.demo.Server;
import com.systemhaus.demo.domain.Cliente;
public class CadastroClienteFrm extends SkeletonFrm{

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
	private JPanel dataPanel;
	private JPanel cardPanel;
	private Server server;
	private String clienteCPF;
	private ClienteSelectionPanel tablePanel;
	private PresentationModel<Cliente> model;
	private SelectionInList<Cliente> clienteSelection = new SelectionInList<>();
	private final boolean[] addMode = {true, true, false, false, true};
	private final boolean[] editMode = {false, false, true, true, true};
	private final boolean[] searchMode = {false, false, false, false, true};
	
	public JInternalFrame createForm(Server server) {
		this.server = server;
		initComponents();
		initLayout();
		return iFrameCadUser;
	}

	protected void initLayout() {
		iFrameCadUser.getContentPane().setLayout(new BorderLayout());
		iFrameCadUser.getContentPane().add(contentPanel, BorderLayout.CENTER);
		iFrameCadUser.getContentPane().add(createButtonBar(), BorderLayout.SOUTH);
	}
	
	protected JPanel createButtonBar() {
		JPanel panelCliente = new JPanel();
		panelCliente.setLayout(new FlowLayout());
		
		JButton btnAdicionarCliente = new JButton("Adicionar");
		panelCliente.add(btnAdicionarCliente);
		
		JButton btnPesquisarCliente = new JButton("Pesquisar");
		panelCliente.add(btnPesquisarCliente);
		
		JButton btnSalvarCliente = new JButton("Salvar");
		btnSalvarCliente.setEnabled(false);
		panelCliente.add(btnSalvarCliente);
		
		JButton btnDeletarCliente = new JButton("Excluir");
		btnDeletarCliente.setEnabled(false);
		panelCliente.add(btnDeletarCliente);
		
		JButton btnCancelarCliente = new JButton("Cancelar");
		panelCliente.add(btnCancelarCliente);
		
		JButton btnTableConfirm = tablePanel.getConfirmButton();
		JButton btnTableCancel = tablePanel.getCancelButton();
		
		JButton btnRedefinirCartao = new JButton("Gerar novo cartão");
		cardPanel.add(btnRedefinirCartao);
		
		JButton btnReport = new JButton("Gerar Relatório");
		panelCliente.add(btnReport);
		
		btnReport.addActionListener(l -> {
			server.generateClienteReport();
		});
		
		JButton[] btnArray = {btnAdicionarCliente, btnPesquisarCliente, btnSalvarCliente, btnDeletarCliente, btnCancelarCliente};
		
		btnTableConfirm.addActionListener(l -> {
			changePanel(contentPanel, "data");
			clienteCPF = model.getBean().getCpf();
			this.clearDataAndSetButtons(false, btnArray, editMode);
		});
		
		btnTableCancel.addActionListener(l -> {
			changePanel(contentPanel, "data");
			this.clearDataAndSetButtons(true, btnArray, addMode);
		});
		
		btnAdicionarCliente.addActionListener(l -> {

			if(server.getUserAccessLevel()	>	2) {
				if(allFieldsAreFilled()) {
					switch(server.addClienteRoutine(model.getBean())) {
					case 0:
						JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso!");
						this.clearDataAndSetButtons(true, btnArray, addMode);
						break;
					case 1:
						JOptionPane.showMessageDialog(null, "Os dados não foram inseridos corretamente, por favor, verifique se:\n# O cpf contém apenas números (ex: 09934567832).\n# O telefone está inserido como 55 DDD 9 número (ex: 5551912345678), sem espaços.");
						break;	
					case 2:
						JOptionPane.showMessageDialog(null, "Já existem muitos clientes nesse endereço, por favor, insira outro!");
						break;
					case 3:
						JOptionPane.showMessageDialog(null, "Já existe um cliente com esse cpf, por favor, insira outro!");
						break;
				}
				} else JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");

			} else {
							JOptionPane.showMessageDialog(null, "Você não tem permissão para realizar esta ação!");
			}
		});
		
		btnPesquisarCliente.addActionListener(l -> {
			List<Cliente> clientes = server.findSimilarClients(model.getBean());
			if(!clientes.isEmpty()){
				clienteSelection.setList(new ArrayListModel<>(clientes));
				if(clientes.size() == 1) {
					this.tablePanel.setSelectionToLastObject();
					clienteCPF = model.getBean().getCpf();
					this.clearDataAndSetButtons(false, btnArray, editMode);
				}else {
					this.clearDataAndSetButtons(false, btnArray, searchMode);
					changePanel(contentPanel, "table");
				}
			}else
				JOptionPane.showMessageDialog(null, "Nenhum cliente encontrado!");
		});

		btnSalvarCliente.addActionListener(l -> {
			if(server.getUserAccessLevel()	>	2) {
			if (allFieldsAreFilled()) {
				if(server.updateClient(clienteCPF,model.getBean())){
					JOptionPane.showMessageDialog(null, "Cliente modificado com sucesso!");
					this.clearDataAndSetButtons(true, btnArray, addMode);
					clienteCPF = "";
				}else
					JOptionPane.showMessageDialog(null, "Ocorreu um erro na modificação do cliente!");
			}else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			} else {
				JOptionPane.showMessageDialog(null, "Você não tem permissão para realizar esta ação!");
			}
		});
		
		btnDeletarCliente.addActionListener(l -> {
			if(server.getUserAccessLevel()	>	2) {
		
			if (allFieldsAreFilled()) {
				if(server.deleteClient(model.getBean())) {
					JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");
					this.clearDataAndSetButtons(true, btnArray, addMode);
				}else
					JOptionPane.showMessageDialog(null, "Este cliente não pode ser deletado pois ainda possui devoluções pendentes!");
			}else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			} else {
				JOptionPane.showMessageDialog(null, "Você não tem permissão para realizar esta ação!");
			}
		});
		
		btnCancelarCliente.addActionListener(l -> {
			changePanel(contentPanel,"data");
			this.clearDataAndSetButtons(true, btnArray, addMode);
		});
		
		btnRedefinirCartao.addActionListener(l -> {
			if(server.getUserAccessLevel()	>	2) {
			server.generateNewCodigoCartao(model.getBean());
			} else {
				JOptionPane.showMessageDialog(null, "Você não tem permissão para realizar esta ação!");
			}
		});
		
		if(server.getUserAccessLevel() == 4) {
			JButton btnGenerateData = new JButton("Gerar dados");
			panelCliente.add(btnGenerateData);
			
			btnGenerateData.addActionListener(l ->{
				Faker faker = new Faker(new java.util.Locale("pt-BR"));
				model.getBean().setNome(faker.name().fullName());
				model.getBean().setCpf(faker.number().digits(11));
				model.getBean().setTelefone("55519"+faker.number().digits(8));
				model.getBean().setCidade(faker.address().city());
				model.getBean().setBairro(faker.address().city());
				model.getBean().setRua(faker.address().streetName());
				model.getBean().setNumero(faker.number().numberBetween(1,1000));
			});
		}
		
		return panelCliente;
	}
	
	protected JPanel createMainPanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, pref:grow, 6dlu, right:pref, 3dlu, pref:grow", "18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,18dlu,26dlu"));
		builder.border(new EmptyBorder(5, 5, 5, 5));
		
		builder.append("Nome:",txtfNome, 5);
		builder.nextLine();
		
		builder.append("CPF:",txtfCpf, 5);
		builder.nextLine();
		
		builder.append("Telefone:",txtfTelefone, 5);
		builder.nextLine();
		
		builder.append("Cidade:",txtfCidade, 5);
		builder.nextLine();
		
		builder.append("Bairro:",txtfBairro, 5);
		builder.nextLine();
		
		builder.append("Rua:",txtfRua, 5);
		builder.nextLine();
		
		builder.append("Número:",txtfNumero, 5);
		builder.nextLine();
		
		builder.append("Cód. cartão:",txtfCodCartao);
		
		builder.append("Data de Validade:",txtfValidade);
		builder.nextLine();
		
		builder.nextColumn();
		builder.append(cardPanel,3);
		builder.nextLine();
		
		return builder.build();
	}

	protected void initComponents() {
		iFrameCadUser = new JInternalFrame("Cadastro de Clientes",false, true);
		iFrameCadUser.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		iFrameCadUser.setBounds(190, 35, 735, 380);
		
		model = new PresentationModel<Cliente>(clienteSelection);
		tablePanel = new ClienteSelectionPanel(clienteSelection, 1, 16);
		cardPanel = new JPanel(new FlowLayout());
		
		ValueModel NomeAdapter = model.getModel("nome");
		ValueModel CPFAdapter = model.getModel("cpf");
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
		txtfCodCartao.setEditable(false);
		txtfCodCartao.setToolTipText("Código gerado automaticamente pelo botão 'Gerar novo cartão'");
		
		txtfValidade = BasicComponentFactory.createFormattedTextField(ValidadeAdapter, 
				new DateFormatter(new SimpleDateFormat("MM/yy")));
		txtfValidade.setEditable(false);
		txtfValidade.setToolTipText("Data gerada automaticamente pelo botão 'Gerar novo cartão'");
		
		dataPanel = createMainPanel();
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new CardLayout());
		contentPanel.add(dataPanel, "data");
		contentPanel.add(tablePanel, "table");
	}

	@Override
	protected boolean allFieldsAreFilled() {
		return (!txtfNome.getText().isEmpty() && !txtfCpf.getText().isEmpty() 
				&& !txtfTelefone.getText().isEmpty() && !txtfCidade.getText().isEmpty() 
				&& !txtfBairro.getText().isEmpty() && !txtfRua.getText().isEmpty() 
				&& !txtfNumero.getText().isEmpty() && !txtfCodCartao.getText().isEmpty());
	}

	@Override
	protected void changePanel(JPanel panel, String name) {
		((CardLayout)panel.getLayout()).show(panel, name);
	}

	@Override
	protected void clearDataAndSetButtons(boolean clearData, JButton[] btnArray, boolean[] modeList) {
		if (clearData) {
			this.tablePanel.clearList();
			this.tablePanel.setSelectionToANewObject();
		}
		for(int i = 0; i < btnArray.length; i++)
			btnArray[i].setEnabled(modeList[i]);
	}
}
