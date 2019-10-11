package com.systemhaus.demo.ui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.Cliente;
import com.systemhaus.demo.domain.Livro;

public class ClienteTableModel extends AbstractTableAdapter<Livro>{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	public ClienteTableModel(SelectionInList<Cliente> selection) {
		//TODO definir campos
		super(selection, "");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
