package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Estante {

	// cada estante terá no máximo 5 prateleiras
	private List<Prateleira> prateleiras;
	private final int size = 5;
	
	public Estante() {
		setEstante(new ArrayList<Prateleira>());
		prateleiras.add(new Prateleira());
	}

	public Estante(ArrayList<Prateleira> prateleiras) {
		setEstante(prateleiras);
	}
	
	public int getSize() {
		return size;
	}
	
	public List<Prateleira> getPrateleiras() {
		return prateleiras;
	}

	private boolean setEstante(ArrayList<Prateleira> prateleiras) {
		if (prateleiras.size() < size) {
			this.prateleiras = prateleiras;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addPrateleira(Prateleira p) {
		if (prateleiras.size() < size) {
			prateleiras.add(p);
			return true;
		} else {
			return false;
		}
	}
}
