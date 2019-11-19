package com.systemhaus.demo.ui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.LivroRetirado;

@SuppressWarnings("serial")
public class LivroRetiradoTableModel extends AbstractTableAdapter<LivroRetirado> {

	public LivroRetiradoTableModel(SelectionInList<LivroRetirado> selection) {
		super(selection, "ISBN","Título","Cliente","Cartao","Data de Retirada","Nº Renovações","Data real de Devolução","Data para Devolução");
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LivroRetirado livroRetirado = getRow(rowIndex);
		switch(columnIndex) {
		case 0: return livroRetirado.getLivro().getISBN();
		case 1: return livroRetirado.getLivro().getTitulo();
		case 2: return livroRetirado.getCartao().getNome();
		case 3: return livroRetirado.getCartao().getCodigo();
		case 4: return livroRetirado.getDataRetiradaAsLocalDate();
		case 5: return livroRetirado.getTotalRenovacoes();
		case 6: return livroRetirado.getDataDevolucaoAsLocalDate();
		case 7: return livroRetirado.getDataDevolucaoRealAsLocalDate();
		}
		return null;
	}

}
