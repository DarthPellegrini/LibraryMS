package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Estante {

	// cada estante terá no máximo 5 prateleiras
	private int id;
	private List<Prateleira> prateleiras;
	private static final int size = 5;
	private int numero;
	
	public Estante(List<Prateleira> prateleiras) {
		setPrateleiras(prateleiras);
	}
	
	public Estante() {
		setPrateleiras(new ArrayList<Prateleira>());
	}
	
	public Estante(int numero) {
		this();
		this.numero = numero;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void initializeEstante() {
		for (int i = 0; i < size; i++) {
			this.getPrateleiras().add(new Prateleira(this,i));
			
		}
	}
	
	public static int getSize() {
		return size;
	}
	
	public List<Prateleira> getPrateleiras() {
		return prateleiras;
	}

	private boolean setPrateleiras(List<Prateleira> prateleiras) {
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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}
