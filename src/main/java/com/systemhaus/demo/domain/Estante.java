package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Estante {

	// cada estante terá no máximo 5 prateleiras
	private List<Prateleira> prateleiras;
	private final int size = 5;
	
	public Estante() {
		setEstante(new ArrayList<Prateleira>());
		initializeEstante();
	}

	public Estante(ArrayList<Prateleira> prateleiras) {
		setEstante(prateleiras);
	}
	
	public void initializeEstante() {
		for (int i = 0; i < size; i++)
			this.getPrateleiras().add(new Prateleira());
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
	
	public boolean isEmpty() {
		for (Prateleira p : this.getPrateleiras())
			if (!p.isEmpty())
				return false;
		return true;
	}
	
	public boolean isFull() {
		for (Prateleira p : this.getPrateleiras())
			if (!p.isFull())
				return false;
		return true;
	}
}
