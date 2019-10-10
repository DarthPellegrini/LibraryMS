package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
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
	private JPanel dataPanel;
	private JInternalFrame iFrameCadLivro;
	private LivroSelectionPanel tablePanel;
	private PresentationModel<Livro> model;
	private SelectionInList<Livro> livroSelection = new SelectionInList<>();
	
	public JInternalFrame createForm(Server server) {
		initComponents();
		initLayout();
		this.server = server;
		return iFrameCadLivro;
	}

	protected void initLayout() {
		iFrameCadLivro.getContentPane().setLayout(new BorderLayout());
		iFrameCadLivro.getContentPane().add(dataPanel, BorderLayout.CENTER);
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

		//TODO transferir actionListeners para uma classe careTaker (possivelmente)
		btnTableConfirm.addActionListener(l -> {
			iFrameCadLivro.getContentPane().remove(tablePanel);
			iFrameCadLivro.getContentPane().add(dataPanel, BorderLayout.CENTER);
			iFrameCadLivro.revalidate();
			iFrameCadLivro.repaint();
			livroISBN = model.getBean().getISBN();
			txtfQuant.setText(String.valueOf(server.returnBookCount(livroISBN)));
			this.clearDataAndSetButtons(false, btnAdicionarLivro, btnPesquisarLivro, btnSalvarLivro, 
					btnDeletarLivro, btnCancelarLivro, 
					false, false, true, true, true);
		});
		
		btnTableCancel.addActionListener(l -> {
			iFrameCadLivro.getContentPane().remove(tablePanel);
			iFrameCadLivro.getContentPane().add(dataPanel, BorderLayout.CENTER);
			iFrameCadLivro.revalidate();
			iFrameCadLivro.repaint();
			this.clearDataAndSetButtons(true, btnAdicionarLivro, btnPesquisarLivro, btnSalvarLivro, 
					btnDeletarLivro, btnCancelarLivro, 
					true, true, false, false, false);
		});
		
		btnAdicionarLivro.addActionListener(l -> {
			//validação dos campos
			if (allFieldsAreFilled()) {
				//TODO: criar mensagens de erro para cada tipo de erro diferente
				if(server.addNewBookRoutine(model.getBean(), server.strToInt(txtfQuant.getText()))){
					JOptionPane.showMessageDialog(null, "Livro(s) inserido(s) com sucesso!");
					this.clearDataAndSetButtons(true, btnAdicionarLivro, btnPesquisarLivro, btnSalvarLivro, 
							btnDeletarLivro, btnCancelarLivro, 
							true, true, false, false, false);
				}else
					JOptionPane.showMessageDialog(null, "Ocorreu um erro na inserção do livro!");
			}else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
		});
		
		btnPesquisarLivro.addActionListener(l -> {
			List<Livro> livros = server.findSimilarBooks(model.getBean());
			if (!livros.isEmpty()) {
				btnAdicionarLivro.setEnabled(false);
				btnPesquisarLivro.setEnabled(false);
				livroSelection.setList(new ArrayListModel<>(livros));
				iFrameCadLivro.getContentPane().remove(dataPanel);
				iFrameCadLivro.getContentPane().add(tablePanel, BorderLayout.CENTER);
				iFrameCadLivro.revalidate();
				iFrameCadLivro.repaint();
			} else
				JOptionPane.showMessageDialog(null, "Nenhum livro encontrado!");
		});
		
		btnSalvarLivro.addActionListener(l -> {
			Livro livroBean = model.getBean();
			if (allFieldsAreFilled()) {
				if(server.editBook(livroISBN,livroBean,server.strToInt(txtfQuant.getText()))){
					JOptionPane.showMessageDialog(null, "Livro(s) modificado(s) com sucesso!");
					this.clearDataAndSetButtons(true, btnAdicionarLivro, btnPesquisarLivro, btnSalvarLivro, 
							btnDeletarLivro, btnCancelarLivro, 
							true, true, false, false, false);
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
					this.clearDataAndSetButtons(true, btnAdicionarLivro, btnPesquisarLivro, btnSalvarLivro, 
							btnDeletarLivro, btnCancelarLivro, 
							true, true, false, false, false);
				}else
					JOptionPane.showMessageDialog(null, "Ocorreu um erro na remoção do(s) livro(s)!");
			else
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
		});
		
		btnCancelarLivro.addActionListener(l -> {
			this.clearDataAndSetButtons(true, btnAdicionarLivro, btnPesquisarLivro, btnSalvarLivro, 
										btnDeletarLivro, btnCancelarLivro, 
										true, true, false, false, false);
		});
		
		return panelLivroButtonBar;
	}

	protected JPanel createMainPanel() {
		DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("right:pref, 3dlu, pref:grow", "18dlu,18dlu,18dlu, 18dlu,18dlu,18dlu,18dlu"));
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
		iFrameCadLivro.setBounds(190, 35, 480, 300);
		
		model = new PresentationModel<Livro>(livroSelection);
		tablePanel = new LivroSelectionPanel(livroSelection);
		
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
		
		//txtfEdicao = BasicComponentFactory.createTextField(edicaoAdapter);
		
		txtfEdicao = BasicComponentFactory.createIntegerField(edicaoAdapter, 0);
		
		//txtfEdicao = BasicComponentFactory.createIntegerField(edicaoAdapter, 0);
		
		txtfNPag = BasicComponentFactory.createIntegerField(numPagAdapter, 0);
		
		txtfQuant = new JTextField();
		
		dataPanel = createMainPanel();
	}

	protected void clearDataAndSetButtons(boolean clearData,
			JButton btnAdd, JButton btnSeek, JButton btnSave, JButton btnDel, JButton btnCancel,
			boolean boolAdd, boolean boolSeek, boolean boolSave, boolean boolDel, boolean boolCancel) {
		if (clearData) {
			livroSelection.setList(new ArrayListModel<>(new ArrayList<Livro>()));
			this.tablePanel.setSelectionToANewObject();
			txtfQuant.setText("");
		}
		btnAdd.setEnabled(boolAdd);
		btnSeek.setEnabled(boolSeek);
		btnSave.setEnabled(boolSave);
		btnDel.setEnabled(boolDel);
		btnCancel.setEnabled(boolCancel);
	}
	
	/*
	 * Deprecated: usar clearDataAndSetButtons.
	 */
	protected void clearFields() {
		model.setBean(new Livro());
		txtfQuant.setText("");
	}
	
	protected boolean allFieldsAreFilled() {
		return (!txtfIsbn.getText().isEmpty() && !txtfEditora.getText().isEmpty() &&
				!txtfTitulo.getText().isEmpty() && !txtfAutor.getText().isEmpty() &&
				!txtfEdicao.getText().isEmpty() && !txtfNPag.getText().isEmpty() && 
				!txtfQuant.getText().isEmpty());
	}
	
}
