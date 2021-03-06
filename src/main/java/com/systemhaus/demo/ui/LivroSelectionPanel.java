package com.systemhaus.demo.ui;

import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.Livro;

public class LivroSelectionPanel extends SkeletonSelectionPanel<Livro>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LivroSelectionPanel(SelectionInList<Livro> livroSelection, double widthModifier, int heightModifier) {
		super(livroSelection, new Livro(), new LivroTableModel(livroSelection), widthModifier, heightModifier);
	}

	public final void setSelectionToANewObject() {
		super.setSelectionToANewObject(new Livro());
	}
}
