package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.Livro;

public class LivroSelectionPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SelectionInList<Livro> selection;
	private JButton ok;
	private JButton cancel;
	
	public LivroSelectionPanel(SelectionInList<Livro> livroSelection) {
		selection = livroSelection;
		this.initializeTable();
		this.initializeButtons();
	}
	
	public Livro getSelectedLivro() {
		return selection.getSelection();
	}
	
	public List<Livro> getListFromSelection() {
		return selection.getList();
	}
	
	public void setSelectionToANewObject() {
		selection.getList().add(new Livro());
		selection.setSelectionIndex(selection.getList().size()-1);
	}
	
	public void initializeTable() {
		JTable jtable = new JTable(new LivroTableModel(selection));
		jtable.setSelectionModel(new SingleListSelectionAdapter(selection.getSelectionIndexHolder()));
		this.setSelectionToANewObject();
		JScrollPane scrollPane = new JScrollPane(jtable);
		scrollPane.setPreferredSize(
                new Dimension(jtable.getPreferredSize().width,jtable.getRowHeight()*10));
	    this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void initializeButtons() {
		JPanel buttonPanel = new JPanel();
		cancel = new JButton("Cancelar");
		ok = new JButton("Ok");
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public JButton getConfirmButton() {
		return this.ok;
	}
	
	public JButton getCancelButton() {
		return this.cancel;
	}

}
