package com.systemhaus.demo.ui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.Livro;

@SuppressWarnings("serial")
public class LivroTableModel extends AbstractTableAdapter<Livro> {
	
	public LivroTableModel(SelectionInList<Livro> selection) {
		super(selection, "ISBN","Título","Edição","Autor","Editora","Nº Páginas");
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Livro livro = getRow(rowIndex);
		switch(columnIndex) {
		case 0: return livro.getISBN();
		case 1: return livro.getTitulo();
		case 2: return livro.getEdicao();
		case 3: return livro.getAutor();
		case 4: return livro.getEditora();
		case 5: return livro.getNumeroPaginas();
		}
		return null;
	}

}
