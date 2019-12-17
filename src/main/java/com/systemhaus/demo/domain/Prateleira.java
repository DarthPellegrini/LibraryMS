package com.systemhaus.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Prateleira {

	// cada prateleira terá no máximo 20 livros
	private int id;
	private Estante estante;
	private List<Livro> livros;
	private final int size = 20;

	public Prateleira(Estante e) {
		setEstante(e);
		setLivros(new ArrayList<Livro>());
	}
	
	public Prateleira(List<Livro> livros, Estante e) {
		setEstante(e);
		setLivros(livros);
	}
	
	public Prateleira() {
		setLivros(new ArrayList<Livro>());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Estante getEstante() {
		return estante;
	}

	public void setEstante(Estante estante) {
		this.estante = estante;
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
			l.setPrateleira(this);
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
