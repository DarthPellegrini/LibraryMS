package com.systemhaus.demo.ui;

import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.Livro;

public class LivroSelectionPanel extends SkeletonSelectionPanel<Livro>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LivroSelectionPanel(SelectionInList<Livro> livroSelection) {
		super(livroSelection, new Livro(), new LivroTableModel(livroSelection));
	}
	

}
