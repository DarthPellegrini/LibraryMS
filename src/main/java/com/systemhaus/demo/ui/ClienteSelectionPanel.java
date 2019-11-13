package com.systemhaus.demo.ui;

import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.Cliente;

public class ClienteSelectionPanel extends SkeletonSelectionPanel<Cliente>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ClienteSelectionPanel(SelectionInList<Cliente> selection, double widthModifier, int heightModifier) {
		super(selection, new Cliente(), new ClienteTableModel(selection), widthModifier, heightModifier);
	}
	
	public final void setSelectionToANewObject() {
		super.setSelectionToANewObject(new Cliente());
	}
}
