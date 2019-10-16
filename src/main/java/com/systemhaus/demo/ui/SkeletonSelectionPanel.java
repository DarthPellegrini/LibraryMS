package com.systemhaus.demo.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.collect.ArrayListModel;
import com.systemhaus.demo.domain.Livro;

public abstract class SkeletonSelectionPanel<Bean> extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SelectionInList<Bean> selection;
	private AbstractTableAdapter<Bean> adapter;
	private JButton ok;
	private JButton cancel;
	
	public SkeletonSelectionPanel(SelectionInList<Bean> selection, Bean bean, AbstractTableAdapter<Bean> adapter) { 
		this.selection = selection;
		this.adapter = adapter;
		this.initializeTable(bean);
		this.initializeButtons();
	}
	
	public Bean getSelectedElement() {
		return selection.getSelection();
	}
	
	public List<Bean> getListFromSelection() {
		return selection.getList();
	}
	
	protected void setSelectionToANewObject(Bean bean) {
		selection.getList().add(bean);
		this.setSelectionToLastObject();
	}
	
	public void setSelectionToLastObject() {
		selection.setSelectionIndex(selection.getList().size()-1);
	}
	
	private void initializeTable(Bean bean) {
		JTable jtable = new JTable(adapter);
		jtable.setSelectionModel(new SingleListSelectionAdapter(selection.getSelectionIndexHolder()));
		this.setSelectionToANewObject(bean);
		JScrollPane scrollPane = new JScrollPane(jtable);
		scrollPane.setPreferredSize(
                new Dimension(jtable.getPreferredSize().width,jtable.getRowHeight()*10));
	    this.add(scrollPane, BorderLayout.CENTER);
	}
	
	private void initializeButtons() {
		JPanel buttonPanel = new JPanel();
		cancel = new JButton("Cancelar");
		ok = new JButton("Ok");
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	protected void clearList() {
		selection.setList(new ArrayListModel<>(new ArrayList<Bean>()));
	}
	
	public JButton getConfirmButton() {
		return this.ok;
	}
	
	public JButton getCancelButton() {
		return this.cancel;
	}
}
