package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Prateleira {

	// cada prateleira terá no máximo 20 livros
	private int id;
	private List<Livro> livros;
	private final int size = 20;

	public Prateleira() {
		setLivros(new ArrayList<Livro>());
	}
	
	public Prateleira(List<Livro> livros) {
		setLivros(livros);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}
	
	public List<Livro> getLivros() {
		return livros;
	}

	private boolean setLivros(List<Livro> livros) {
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
