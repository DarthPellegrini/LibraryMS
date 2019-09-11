package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Estante {

	// cada estante terá no máximo 5 prateleiras
	private List<Prateleira> prateleiras;
	private final int size = 5;
	
	public Estante() {
		setEstante(new ArrayList<Prateleira>());
		
	}

	public Estante(ArrayList<Prateleira> prateleiras) {
		setEstante(prateleiras);
	}
	
	public List<Prateleira> getPrateleiras() {
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
