package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Prateleira {

	// cada prateleira terá no máximo 20 livros
	private List<Livro> livros;
	private final int size = 20;

	public Prateleira() {
		setPrateleira(new ArrayList<Livro>());
	}
	
	public Prateleira(ArrayList<Livro> livros) {
		setPrateleira(livros);
	}
	
	public int getSize() {
		return size;
	}
	
	public List<Livro> getLivros() {
		return livros;
	}

	private boolean setPrateleira(List<Livro> livros) {
		if (livros.size() < size) {
			this.livros = livros;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addLivro(Livro l) {
		if (livros.size() < size) {
			livros.add(l);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFull() {
		return this.livros.size() == size;
	}
	
	public boolean isEmpty() {
		return this.livros.size() == 0;
	}
	
}
