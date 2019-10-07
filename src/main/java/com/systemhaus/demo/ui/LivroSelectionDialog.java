package com.systemhaus.demo.ui;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.systemhaus.demo.domain.Livro;

public class LivroSelectionDialog extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable jtable;
	private Livro livro;
	
	public LivroSelectionDialog(List<Livro> list) {
		this.initializeTable(list);
	}
	
	private String[][] generateData(List<Livro> list) {
		String[][] data = new String[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			Livro l = list.get(i);
			String[] row = {l.getISBN(), 
							l.getTitulo(), 
							String.valueOf(l.getEdicao()),
							l.getAutor(),
							l.getEditora(),
							String.valueOf(l.getEdicao())};
			data[i] = row;
		}
		return data;
	}

	public Livro getSelectedLivro() {
		return livro;
	}
	
	public void initializeTable(List<Livro> list) {
		String[] columns = {"ISBN","Título","Edição","Autor","Editora","Nº Páginas"};
		
		this.jtable = new JTable(generateData(list), columns);
		
		jtable.setCellSelectionEnabled(true);
		jtable.setDefaultEditor(Object.class, null);
		ListSelectionModel cellSelectionModel = jtable.getSelectionModel();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent e) {
	        int selectedRow = jtable.getSelectedRow();

	        livro = new Livro(
	        		(String) jtable.getValueAt(selectedRow, 0),
	        		Integer.parseInt((String) jtable.getValueAt(selectedRow, 2)),
	        		(String) jtable.getValueAt(selectedRow, 1),
	        		(String) jtable.getValueAt(selectedRow, 3),
	        		(String) jtable.getValueAt(selectedRow, 4),
	        		Integer.parseInt((String) jtable.getValueAt(selectedRow, 5)),
	        		false);
	      }

	    });

	    this.add(new JScrollPane(jtable));
	}

}
