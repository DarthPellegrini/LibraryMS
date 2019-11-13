package com.systemhaus.demo.ui;
import com.jgoodies.binding.list.SelectionInList;
import com.systemhaus.demo.domain.LivroRetirado;

public class LivroRetiradoSelectionPanel extends SkeletonSelectionPanel<LivroRetirado>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LivroRetiradoSelectionPanel(SelectionInList<LivroRetirado> livroRetiradoSelection, double widthModifier, int heightModifier) {
		super(livroRetiradoSelection, new LivroRetirado(), new LivroRetiradoTableModel(livroRetiradoSelection), widthModifier, heightModifier);
	}

	public final void setSelectionToANewObject() {
		super.setSelectionToANewObject(new LivroRetirado());
	}
}