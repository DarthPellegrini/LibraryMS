package com.systemhaus.demo.ui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.Livro;

@SuppressWarnings("serial")
public class LivroTableModel extends AbstractTableAdapter<Livro> {
	
	public LivroTableModel(SelectionInList<Livro> selection) {
		super(selection, "ISBN","Título","Autor","Editora","Edição","Nº Páginas","Disponibilidade");
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Livro livro = getRow(rowIndex);
		switch(columnIndex) {
		case 0: return livro.getISBN();
		case 1: return livro.getTitulo();
		case 2: return livro.getAutor();
		case 3: return livro.getEditora();
		case 4: return livro.getEdicao();
		case 5: return livro.getNumeroPaginas();
		case 6: return livro.isRetirado() ? "Indisponível" : "Disponível";
		}
		return null;
	}

}
