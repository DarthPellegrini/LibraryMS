package com.systemhaus.demo.ui;

import java.awt.BorderLayout;

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
	
	public LivroSelectionPanel(SelectionInList<Livro> livroSelection) {
		selection = livroSelection;
		this.initializeTable();
	}
	
	public Livro getSelectedLivro() {
		return selection.getSelection();
	}
	
	public void initializeTable() {
		JTable jtable = new JTable(new LivroTableModel(selection));
		jtable.setSelectionModel(new SingleListSelectionAdapter(selection.getSelectionIndexHolder()));
		this.setLayout(new BorderLayout());
	    this.add(new JScrollPane(jtable), BorderLayout.CENTER);
	    JButton btn = new JButton();
	    btn.addActionListener(e -> {
	    	selection.getList().add(new Livro("9787896541230", 3, "1", "3", "3", 3, false));
	    });
	    this.add(btn, BorderLayout.SOUTH);
	}

}
