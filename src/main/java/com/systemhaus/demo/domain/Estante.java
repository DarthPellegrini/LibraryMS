package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Estante {

	// cada estante terá no máximo 5 prateleiras
	private int id;
	private List<Prateleira> prateleiras;
	private Biblioteca biblioteca;
	private final int size = 5;
	
	public Estante(Biblioteca b) {
		setBiblioteca(b);
		setPrateleiras(new ArrayList<Prateleira>());
		initializeEstante();
	}

	public Estante(List<Prateleira> prateleiras, Biblioteca b) {
		setBiblioteca(b);
		setPrateleiras(prateleiras);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Biblioteca getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}

	public void initializeEstante() {
		for (int i = 0; i < size; i++)
			this.getPrateleiras().add(new Prateleira(this));
	}
	
	public int getSize() {
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
}
