package com.systemhaus.demo.ui;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.bean.Bean;

public class SkeletonTableModel extends AbstractTableAdapter<Bean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Bean bean;
	
	public SkeletonTableModel(SelectionInList<Bean> selection, Bean bean, String[] columns) {
		super(selection, columns);
		this.bean = bean;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		bean = getRow(rowIndex);
		this.getColumnCount();
		
		return null;
	}
	
	public Bean getTheRow(int rowIndex) {
		return getRow(rowIndex);
	}

}
