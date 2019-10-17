package com.systemhaus.demo.ui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.Cliente;

public class ClienteTableModel extends AbstractTableAdapter<Cliente>{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	public ClienteTableModel(SelectionInList<Cliente> selection) {
		super(selection, "Nome", "CPF", "Telefone", "Cidade", "Bairro", "Rua", "Número", "Cartão", "Validade");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cliente cliente = getRow(rowIndex);
		switch(columnIndex){
		case 0: return cliente.getNome();
		case 1: return cliente.getCPF();
		case 2: return cliente.getTelefone();
		case 3: return cliente.getCidade();
		case 4: return cliente.getBairro();
		case 5: return cliente.getRua();
		case 6: return cliente.getNumero();
		case 7: return cliente.getCartao();
		case 8: return cliente.getValidade();
		}
		return null;
	}

}
