package com.systemhaus.demo.domain;

import java.util.ArrayList;

public class Estante {

	// cada estante terá no máximo 5 prateleiras
	private ArrayList<Prateleira> prateleiras;
	private final int size = 5;
	
	public Estante() {
		prateleiras = new ArrayList<Prateleira>();
	}

	public ArrayList<Prateleira> getPrateleira() {
		return prateleiras;
	}

	public boolean setEstante(ArrayList<Prateleira> prateleiras) {
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
