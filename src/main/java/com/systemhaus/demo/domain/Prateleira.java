package com.systemhaus.demo.domain;

import java.util.ArrayList;

public class Prateleira {

	// cada prateleira terá no máximo 20 livros
	private ArrayList<Livro> prateleira;
	private final int size = 20;

	public Prateleira() {
		prateleira = new ArrayList<Livro>();
	}
	
	public ArrayList<Livro> getPrateleira() {
		return prateleira;
	}

	public boolean setPrateleira(ArrayList<Livro> prateleira) {
		if (prateleira.size() < size) {
			this.prateleira = prateleira;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addLivro(Livro l) {
		if (prateleira.size() < size) {
			prateleira.add(l);
			return true;
		} else {
			return false;
		}
	}
	
}
